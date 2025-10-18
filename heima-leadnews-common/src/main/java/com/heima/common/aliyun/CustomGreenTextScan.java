package com.heima.common.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.green20220302.Client;
import com.aliyun.green20220302.models.TextModerationPlusRequest;
import com.aliyun.green20220302.models.TextModerationPlusResponse;
import com.aliyun.green20220302.models.TextModerationPlusResponseBody;
import com.aliyun.teaopenapi.models.Config;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun")
public class CustomGreenTextScan {
    private String accessKeyId;
    private String secret;
    private String textService;

    public Map greeTextScan(String content) throws Exception {



        Config config = new Config();

        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(secret);
        //接入区域和地址请根据实际情况修改
        config.setRegionId("cn-shanghai");
        config.setEndpoint("green-cip.cn-shanghai.aliyuncs.com");
        //读取时超时时间，单位毫秒（ms）。
        config.setReadTimeout(6000);
        //连接时超时时间，单位毫秒（ms）。
        config.setConnectTimeout(3000);
        //设置http代理。
        //config.setHttpProxy("http://xx.xx.xx.xx:xxxx");
        //设置https代理。
        //config.setHttpsProxy("https://xx.xx.xx.xx:xxxx");
        Client client = new Client(config);

        JSONObject serviceParameters = new JSONObject();
        serviceParameters.put("content", content);

        TextModerationPlusRequest textModerationPlusRequest = new TextModerationPlusRequest();
        // 检测类型
        textModerationPlusRequest.setService(textService);
        textModerationPlusRequest.setServiceParameters(serviceParameters.toJSONString());

        try {
            TextModerationPlusResponse response = client.textModerationPlus(textModerationPlusRequest);
            Map<String, Object> resultMap = new HashMap<>();

            if (response != null) {
                resultMap.put("statusCode", response.getStatusCode());

                if (response.getStatusCode() == 200) {
                    TextModerationPlusResponseBody body = response.getBody();
                    resultMap.put("requestId", body.getRequestId());
                    resultMap.put("code", body.getCode());
                    resultMap.put("msg", body.getMessage());

                    Integer code = body.getCode();
                    if (code == 200) {
                        TextModerationPlusResponseBody.TextModerationPlusResponseBodyData data = body.getData();
                        resultMap.put("data",data);
                    } else {
                        resultMap.put("error", "text moderation not success. code: " + code);
                    }

                } else {
                    resultMap.put("error", "response not success. status: " + response.getStatusCode());
                }

            } else {
                resultMap.put("error", "response is null");
            }

            //  打印美化 JSON
            System.out.println(JSON.toJSONString(resultMap, true));

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
