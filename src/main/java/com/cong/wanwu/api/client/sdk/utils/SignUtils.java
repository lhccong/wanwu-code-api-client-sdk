package com.cong.wanwu.api.client.sdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名工具
 * @author lhc
 * @date 2022-11-10 10:56
 */
public class SignUtils {
    /**
     * 得到签名
     *
     * @param body       地图
     * @param secretKey 秘密密钥
     * @return {@link String}
     */
    public static String getSign(String body, String secretKey){
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String content = body.toString() + "." + secretKey;
        String digestHex = digester.digestHex(content);
        return digestHex;
    }
}
