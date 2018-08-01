package com.league.blockchain.common.algorithm;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESAlgorithm {
    /**
     * aesEncode:aes 加密
     * @param key   秘钥
     * @param data  明文
     * @return  data
     * @throws Exception
     */
    public static byte[] aesEncode(byte[] key, byte[] data) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    /**
     * aesDecode:aes  解密
     * @param key    key
     * @param encryptedText
     * @return  encryptedText
     * @throws Exception
     */
    public static byte[] aesDecode(byte[] key, byte[] encryptedText) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedText);
    }
}
