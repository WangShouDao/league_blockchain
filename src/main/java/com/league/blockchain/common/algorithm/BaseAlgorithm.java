package com.league.blockchain.common.algorithm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.MessageDigest;
import java.security.Security;

public class BaseAlgorithm {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static byte[] encode(String algorithm, byte[] data){
        if(data==null){
            return null;
        }
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(data);
            return messageDigest.digest();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static byte[] encodeTwice(String algorithm,  byte[] data){
        if(data==null){
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(data);
            return messageDigest.digest(messageDigest.digest());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
