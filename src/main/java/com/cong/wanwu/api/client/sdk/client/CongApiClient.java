package com.cong.wanwu.api.client.sdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.cong.wanwu.api.client.sdk.model.SdkUser;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.cong.wanwu.api.client.sdk.utils.SignUtils.getSign;


/**
 * @author lhc
 * @date 2022-11-10 09:01
 */
public class CongApiClient {
    private static final String GATEWAY_HOST = "https://qingxin.store/wanwu";
    private final String accessKey;
    private final String secretKey;

    public CongApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<>(16);
        hashMap.put("accessKey", accessKey);
        hashMap.put("ApiRequest", GATEWAY_HOST);
        hashMap.put("once", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", getSign(body,secretKey));
        return hashMap;
    }



    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("name", name);

        String result = HttpUtil.get(GATEWAY_HOST+"/api/name/", paramMap);
        System.out.println("result = " + result);
        return result;
    }

    public String getNameByPost(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.post(GATEWAY_HOST+"/api/name/", paramMap);
        System.out.println("result = " + result);
        return result;
    }

    public String getUserNameByPost(SdkUser user) {

        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST+"/api/name/user")
                .charset(StandardCharsets.UTF_8)
                .body(json)
                .addHeaders(getHeaderMap(json))
                .execute();
        System.out.println("httpResponse.getStatus() = " + httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println("result = " + result);
        return result;
    }
    public String getInterfaceByPost(Object jsonContent,String interfacePath) {

        String json = JSONUtil.toJsonStr(jsonContent);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST+interfacePath)
                .charset(StandardCharsets.UTF_8)
                .body(json)
                .addHeaders(getHeaderMap(json))
                .execute();
        return httpResponse.body();
    }
    public String getInterfaceByGet(Map<String, Object> paramMap,String interfacePath) {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST+interfacePath)
                .charset(StandardCharsets.UTF_8)
                .form(paramMap)
                .addHeaders(getHeaderMap(paramMap.toString()))
                .execute();
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        return httpResponse.body();
    }

}
