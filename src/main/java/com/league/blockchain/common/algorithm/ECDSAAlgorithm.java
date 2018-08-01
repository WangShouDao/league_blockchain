package com.league.blockchain.common.algorithm;

import com.google.common.base.Objects;
import com.league.blockchain.common.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECPoint;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.DERSequenceGenerator;
import org.spongycastle.asn1.DLSequence;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;
import org.spongycastle.math.ec.FixedPointUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class ECDSAAlgorithm {
    public static final ECDomainParameters CURVE;
    public static final BigInteger HALF_CURVE_ORDER;

    static {
        X9ECParameters CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1");
        FixedPointUtil.precompute(CURVE_PARAMS.getG(), 12);
        CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(),
                CURVE_PARAMS.getH());
        HALF_CURVE_ORDER = CURVE_PARAMS.getN().shiftRight(1);
    }

    public static String generatePrivateKey(){
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstance(Constants.RANDOM_NUMBER_ALGORITHM,
                    Constants.RANDOM_NUMBER_ALGORITHM_PROVIDER);
        } catch (Exception e){
            secureRandom = new SecureRandom();
        }
        // Generate the key, skipping as many as desired.
        byte[] privateKeyAttempt = new byte[32];
        secureRandom.nextBytes(privateKeyAttempt);
        BigInteger privateKeyCheck = new BigInteger(1, privateKeyAttempt);
        while (privateKeyCheck.compareTo(BigInteger.ZERO)==0||privateKeyCheck.compareTo(Constants.MAXPRIVATEKEY)>0){
            secureRandom.nextBytes(privateKeyAttempt);
            privateKeyCheck = new BigInteger(1, privateKeyAttempt);
        }
        String result = Base64.encodeBase64String(privateKeyAttempt);
        result = result.replaceAll("[\\s*\t\n\r]", "");
        return result;
    }

    public static String generatePublicKey(String privateKeyBase64String, boolean encode){
        try{
            byte[] privateKeyBytes = Base64.decodeBase64(privateKeyBase64String);
            ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");
            ECPoint pointQ = spec.getG().multiply(new BigInteger(1, privateKeyBytes));
            String result = Base64.encodeBase64String(pointQ.getEncoded(encode));
            result = result.replaceAll("[\\s*\t\n\r]", "");
            return result;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成长公钥
     * @param privateKeyBase64String
     * @return
     */
    public static String generatePublicKey(String privateKeyBase64String){
        return generatePublicKey(privateKeyBase64String, false);
    }

    public static String decodePublicKey(String encodePubKeyBase64String){
        try{
            byte[] encodePubKeyBytes = Base64.decodeBase64(encodePubKeyBase64String);
            ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");
            ECPoint pointQ = spec.getG().getCurve().decodePoint(encodePubKeyBytes);
            String result = Base64.encodeBase64String(pointQ.getEncoded(false));
            result = result.replaceAll("[\\s*\t\n\r]", "");
            return result;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     *  测试使用私钥签名，并使用公钥验证签名
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        String priKey = generatePrivateKey();
        System.out.println(priKey);
        String pubKey = generatePublicKey(priKey, true);
        String pubKey1 = generatePublicKey(priKey);
        System.out.println(pubKey);
        System.out.println(pubKey1);
        String sign = sign(priKey, "abc");
        System.out.println(sign);
        boolean verify = verify("abc", sign, pubKey);
        System.out.println(verify);
    }

    /**
     *  根据公钥生成address
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String getAddress(String publicKey) throws Exception{
        return getAddress(publicKey.getBytes("UTF-8"), 0);
    }
}
