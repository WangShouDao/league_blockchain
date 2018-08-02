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

    /**
     *  根据公钥生成地址
     * @param keyBytes
     * @param version
     * @return
     * @throws Exception
     */
    public static String getAddress(byte[] keyBytes, int... version) throws Exception{
        byte[] hashSha256 = BaseAlgorithm.encode("SHA-256", keyBytes);
        MessageDigest messageDigest = MessageDigest.getInstance("RipeMD160");
        messageDigest.update(hashSha256);
        byte[] hashRipeMD160 = messageDigest.digest();
        byte[] hashDoubleSha256 = BaseAlgorithm.encodeTwice("SHA-256", hashRipeMD160);
        byte[] rawAddr = new byte[1+hashRipeMD160.length+4];
        rawAddr[0] = 0;
        System.arraycopy(hashRipeMD160,0, rawAddr, 1, hashRipeMD160.length);
        System.arraycopy(hashDoubleSha256, 0, rawAddr, hashRipeMD160.length+1, 4);
        return Base58Algorithm.encode(rawAddr);
    }

    public static String sign(String privateKey, String data) throws UnsupportedEncodingException{
        return sign(privateKey, data.getBytes("UTF-8"));
    }

    public static String sign(String privateKey, byte[] data){
        byte[] hash256 = BaseAlgorithm.encode("SHA-256", data);
        ECDSASigner signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()));
        BigInteger pri = new BigInteger(1, Base64.decodeBase64(privateKey));
        ECPrivateKeyParameters privKey = new ECPrivateKeyParameters(pri, CURVE);
        signer.init(true, privKey);
        BigInteger[] components = signer.generateSignature(hash256);
        byte[] content = new ECDSASignature(components[0], components[1]).toCanonicalised().encodeToDER();
        String result = Base64.encodeBase64String(content);
        result = result.replaceAll("[\\s*\t\n\r]", "");
        return result;
    }

    public static boolean verify(String srcStr, String sign, String pubKey) throws Exception{
        byte[] hash256 = BaseAlgorithm.encode("SHA-256", srcStr.getBytes("UTF-8"));
        ECDSASignature signature = ECDSASignature.decodeFromDER(Base64.decodeBase64(sign));
        ECDSASigner signer = new ECDSASigner();
        org.spongycastle.math.ec.ECPoint pub = CURVE.getCurve().decodePoint(Base64.decodeBase64(pubKey));
        ECPublicKeyParameters params = new ECPublicKeyParameters(CURVE.getCurve().decodePoint(pub.getEncoded()), CURVE);
        signer.init(false, params);
        return signer.verifySignature(hash256, signature.r, signature.s);
    }

    public static class ECDSASignature{
        /** The two components of the signature. */
        public final BigInteger r, s;

        /**
         * Constructs a signature with the given components. Does NOT
         * automatically canonicalise the signature.
         */
        public ECDSASignature(BigInteger r, BigInteger s){
            this.r = r;
            this.s = s;
        }

        /**
         * Returns true if the S component is "low", that means it is below
         *  See <a href=
         * "https://github.com/bitcoin/bips/blob/master/bip-0062.mediawiki#Low_S_values_in_signatures">
         * BIP62</a>.
         */
        public boolean isCanonical(){
            return s.compareTo(HALF_CURVE_ORDER)<=0;
        }

        /**
         * Will automatically adjust the S component to be less than or equal to
         * half the curve order, if necessary. This is required because for
         * every signature (r,s) the signature (r, -s (mod N)) is a valid
         * signature of the same message. However, we dislike the ability to
         * modify the bits of a Bitcoin transaction after it's been signed, as
         * that violates various assumed invariants. Thus in future only one of
         * those forms will be considered legal and the other will be banned.
         */
        public ECDSASignature toCanonicalised() {
            if (!isCanonical()) {
                // The order of the curve is the number of valid points that
                // exist on that curve. If S is in the upper
                // half of the number of valid points, then bring it back to the
                // lower half. Otherwise, imagine that
                // N = 10
                // s = 8, so (-8 % 10 == 2) thus both (r, 8) and (r, 2) are
                // valid solutions.
                // 10 - 8 == 2, giving us always the latter solution, which is
                // canonical.
                return new ECDSASignature(r, CURVE.getN().subtract(s));
            } else {
                return this;
            }
        }

        /**
         * DER is an international standard for serializing data structures
         * which is widely used in cryptography. It's somewhat like protocol
         * buffers but less convenient. This method returns a standard DER
         * encoding of the signature, as recognized by OpenSSL and other
         * libraries.
         */
        public byte[] encodeToDER() {
            try {
                return derByteStream().toByteArray();
            } catch (IOException e) {
                // Cannot happen.
                throw new RuntimeException(e);
            }
        }

        public static ECDSASignature decodeFromDER(byte[] bytes){
            ASN1InputStream decoder = null;
            try{
                decoder = new ASN1InputStream(bytes);
                DLSequence seq = (DLSequence) decoder.readObject();
                if(seq==null){
                    throw new RuntimeException("Reached past end of ASN.1 stream");
                }
                ASN1Integer r, s;
                try{
                    r = (ASN1Integer) seq.getObjectAt(0);
                    s = (ASN1Integer) seq.getObjectAt(1);
                } catch (Exception e){
                    throw new IllegalArgumentException(e);
                }
                // OpenSSL deviates from the DER spec by interpreting these
                // values as unsigned, though they should not be
                // Thus, we always use the positive versions. See:
                // http://r6.ca/blog/20111119T211504Z.html
                return new ECDSASignature(r.getPositiveValue(), s.getPositiveValue());
            } catch (Exception e){
                throw new RuntimeException(e);
            } finally {
                if(decoder!=null){
                    try{
                        decoder.close();
                    }catch (IOException e){
                    }
                }
            }
        }

        protected ByteArrayOutputStream derByteStream() throws IOException{
            // Usually 70-72 bytes.
            ByteArrayOutputStream bos = new ByteArrayOutputStream(72);
            DERSequenceGenerator seq = new DERSequenceGenerator(bos);
            seq.addObject(new ASN1Integer(r));
            seq.addObject(new ASN1Integer(s));
            seq.close();
            return bos;
        }

        @Override
        public boolean equals(Object o){
            if(this == o){
                return true;
            }
            if(o == null || getClass() != o.getClass()){
                return false;
            }
            ECDSASignature other = (ECDSASignature) o;
            return r.equals(other.r)&&s.equals(other.s);
        }

        @Override
        public int hashCode(){
            return Objects.hashCode(r, s);
        }
    }
}
