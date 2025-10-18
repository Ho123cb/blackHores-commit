package com.heima.common.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.green20220302.Client;
import com.aliyun.green20220302.models.DescribeUploadTokenResponse;
import com.aliyun.green20220302.models.DescribeUploadTokenResponseBody;
import com.aliyun.green20220302.models.ImageModerationRequest;
import com.aliyun.green20220302.models.ImageModerationResponse;
import com.aliyun.green20220302.models.ImageModerationResponseBody;
import com.aliyun.green20220302.models.ImageModerationResponseBody.ImageModerationResponseBodyData;
import com.aliyun.green20220302.models.ImageModerationResponseBody.ImageModerationResponseBodyDataResult;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.Getter;
import lombok.Setter;
import org.apache.avro.data.Json;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun")
public class CustomGreenImageScan {
    private String accessKeyId;
    private String secret;
    private String imageService;
    
    //服务是否部署在vpc上
    public static boolean isVPC = false;

    //文件上传token endpoint->token
    public static Map<String, DescribeUploadTokenResponseBody.DescribeUploadTokenResponseBodyData> tokenMap = new HashMap<>();

    //上传文件请求客户端
    public static OSS ossClient = null;

    /**
     * 创建请求客户端
     *
     * @param accessKeyId
     * @param secret
     * @param endpoint
     * @return
     * @throws Exception
     */
    public static Client createClient(String accessKeyId, String secret, String endpoint) throws Exception {
        Config config = new Config();
        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(secret);
        // 接入区域和地址请根据实际情况修改
        config.setEndpoint(endpoint);
        return new Client(config);
    }

    /**
     * 创建上传文件请求客户端
     *
     * @param tokenData
     * @param isVPC
     */
    public static void getOssClient(DescribeUploadTokenResponseBody.DescribeUploadTokenResponseBodyData tokenData, boolean isVPC) {
        //注意，此处实例化的client请尽可能重复使用，避免重复建立连接，提升检测性能。
        if (isVPC) {
            ossClient = new OSSClientBuilder().build(tokenData.ossInternalEndPoint, tokenData.getAccessKeyId(), tokenData.getAccessKeySecret(), tokenData.getSecurityToken());
        } else {
            ossClient = new OSSClientBuilder().build(tokenData.ossInternetEndPoint, tokenData.getAccessKeyId(), tokenData.getAccessKeySecret(), tokenData.getSecurityToken());
        }
    }

    /**
     * 上传文件
     *
     * @param filePath
     * @param tokenData
     * @return
     * @throws Exception
     */
    public static String uploadFile(String filePath, DescribeUploadTokenResponseBody.DescribeUploadTokenResponseBodyData tokenData) throws Exception {
        String[] split = filePath.split("\\.");
        String objectName;
        if (split.length > 1) {
            objectName = tokenData.getFileNamePrefix() + UUID.randomUUID() + "." + split[split.length - 1];
        } else {
            objectName = tokenData.getFileNamePrefix() + UUID.randomUUID();
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(tokenData.getBucketName(), objectName, new File(filePath));
        ossClient.putObject(putObjectRequest);
        return objectName;
    }

    public static ImageModerationResponse invokeFunction(String accessKeyId, String secret, String service, String endpoint, String filePath) throws Exception {
        //注意，此处实例化的client请尽可能重复使用，避免重复建立连接，提升检测性能。
        Client client = createClient(accessKeyId, secret, endpoint);
        RuntimeOptions runtime = new RuntimeOptions();

        //获取文件上传token
        if (tokenMap.get(endpoint) == null || tokenMap.get(endpoint).expiration <= System.currentTimeMillis() / 1000) {
            DescribeUploadTokenResponse tokenResponse = client.describeUploadToken();
            tokenMap.put(endpoint,tokenResponse.getBody().getData());
        }
        //上传文件请求客户端
        getOssClient(tokenMap.get(endpoint), isVPC);

        //上传文件
        String objectName = uploadFile(filePath, tokenMap.get(endpoint));

        // 检测参数构造。
        Map<String, String> serviceParameters = new HashMap<>();
        //文件上传信息
        serviceParameters.put("ossBucketName", tokenMap.get(endpoint).getBucketName());
        serviceParameters.put("ossObjectName", objectName);
        serviceParameters.put("dataId", UUID.randomUUID().toString());

        ImageModerationRequest request = new ImageModerationRequest();
        // 图片检测service：内容安全控制台图片增强版规则配置的serviceCode，示例：baselineCheck
        request.setService(service);
        request.setServiceParameters(JSON.toJSONString(serviceParameters));

        ImageModerationResponse response = null;
        try {
            response = client.imageModerationWithOptions(request, runtime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public Map greeImageScan(String filePath) throws Exception {
        // 接入区域和地址请根据实际情况修改。
        ImageModerationResponse response = invokeFunction(accessKeyId, secret, imageService,"green-cip.cn-shanghai.aliyuncs.com", filePath);
        try {
            // 自动路由。
            if (response != null) {
                //区域切换到cn-beijing。
                if (500 == response.getStatusCode() || (response.getBody() != null && 500 == (response.getBody().getCode()))) {
                    // 接入区域和地址请根据实际情况修改。
                    response = invokeFunction(accessKeyId, secret, imageService,"green-cip.cn-beijing.aliyuncs.com", filePath);
                }
            }
            // 打印检测结果。
            Map<String, Object> result = new HashMap<>();
            if (response != null) {
                result.put("statusCode", response.getStatusCode());

                if (response.getStatusCode() == 200) {
                    ImageModerationResponseBody body = response.getBody();
                    result.put("requestId", body.getRequestId());
                    result.put("code", body.getCode());
                    result.put("msg", body.getMsg());

                    if (body.getCode() == 200) {
                        ImageModerationResponseBodyData data = body.getData();
                        result.put("dataId", data.getDataId());

                        List<Map<String, Object>> resultList = new java.util.ArrayList<>();
                        List<ImageModerationResponseBodyDataResult> results = data.getResult();

                        for (ImageModerationResponseBodyDataResult r : results) {
                            Map<String, Object> item = new HashMap<>();
                            item.put("label", r.getLabel());
                            item.put("confidence", r.getConfidence());
                            item.put("description", r.getDescription());
                            item.put("riskLevel", r.getRiskLevel());
                            resultList.add(item);
                        }

                        result.put("results", resultList);
                    } else {
                        result.put("error", "image moderation not success, code: " + body.getCode());
                    }
                } else {
                    result.put("error", "response not success, status: " + response.getStatusCode());
                }
            } else {
                result.put("error", "response is null");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

}
