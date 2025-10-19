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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Slf4j
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
                if (response.getStatusCode() == 200) {
                    TextModerationPlusResponseBody body = response.getBody();
                    Integer code = body.getCode();
                    if (code == 200) {
                        log.info("阿里云检测文本成功~~~");
                        TextModerationPlusResponseBody.TextModerationPlusResponseBodyData data = body.getData();
                        List<TextModerationPlusResponseBody.TextModerationPlusResponseBodyDataResult> result = data.getResult();
                        String riskLevel = data.getRiskLevel();

                        if(riskLevel != "none") {
                            List<String> labels = new ArrayList<>();
                            for (int i = 0; i < result.size(); i++) {
                                TextModerationPlusResponseBody.TextModerationPlusResponseBodyDataResult r = result.get(i);
                                String label = r.getLabel();
                                labels.add(label);
                            }

                            resultMap.put("riskLevel",riskLevel);
                            resultMap.put("label", StringUtils.join(labels,","));
                            if(riskLevel == "hign")
                                resultMap.put("suggestion", "block");
                            else
                                resultMap.put("suggestion", "review");
                            return resultMap;
                        }
                    } else {
                        log.info("text moderation not success. code: {}",code);
                        return null;
                    }

                } else {
                    log.info("response not success. status: {}", response.getStatusCode());
                    return null;
                }

            } else {
                log.info("response is null");
                return null;
            }

            //  打印美化 JSON
            System.out.println(JSON.toJSONString(resultMap, true));
            resultMap.put("suggestion","pass");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
