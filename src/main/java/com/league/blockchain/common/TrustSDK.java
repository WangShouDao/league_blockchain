package com.league.blockchain.common;

import com.league.blockchain.common.algorithm.ECDSAAlgorithm;
import com.league.blockchain.common.exception.ErrorNum;
import com.league.blockchain.common.exception.TrustSDKException;
import com.league.blockchain.block.PairKey;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

public class TrustSDK {

    /**
     * generatePairKey:产生一对公私钥，并返回
     * @return
     * @throws TrustSDKException
     */
    public static PairKey generatePairKey() throws TrustSDKException{
        return generatePairKey(false);
    }

    /**
     * generatePairKey:生成私钥公钥对。
     * @param encodePubKey 是否压缩
     * @return
     * @throws TrustSDKException
     */
    public static PairKey generatePairKey(boolean encodePubKey) throws TrustSDKException{
        try{
            PairKey pair = new PairKey();
            String privateKey = ECDSAAlgorithm.generatePrivateKey();
            String pubKey = ECDSAAlgorithm.generatePublicKey(privateKey.trim(), encodePubKey);
            pair.setPrivateKey(privateKey);
            pair.setPublicKey(pubKey);
            return pair;
        } catch (Exception e){
            throw new TrustSDKException(ErrorNum.ECDSA_ENCRYPT_ERROE.getRetCode(), ErrorNum.ECDSA_ENCRYPT_ERROE.getRetMsg(), e);
        }
    }

    /**
     *  checkPairKey:验证一对公私钥是否匹配.
     * @param prvKey 输入 存放私钥 长度必须为PRVKEY_DIGEST_LENGTH
     * @param pubKey 输入 存放公钥 长度必须为PUBKEY_DIGEST_LENGTH
     * @return true 公私钥匹配  false 公私钥不匹配
     * @throws TrustSDKException
     */
    public static boolean checkPairKey(String prvKey, String pubKey) throws TrustSDKException{
        if(StringUtils.isEmpty(prvKey)||StringUtils.isEmpty(pubKey)){
            throw new TrustSDKException(ErrorNum.INVALID_PARAM_ERROR.getRetCode(), ErrorNum.INVALID_PARAM_ERROR.getRetMsg());
        }
        try {
            String correctPubKey = ECDSAAlgorithm.generatePublicKey(prvKey.trim(), true);
            return pubKey.trim().equals(correctPubKey);
        } catch (Exception e){
            throw new TrustSDKException(ErrorNum.ECDSA_ENCRYPT_ERROE.getRetCode(), ErrorNum.ECDSA_ENCRYPT_ERROE.getRetCode(), e);
        }
    }

    /**
     *  generatePubkeyByPrvkey: 通过私钥计算相应公钥.
     * @param privateKey 私钥字符串
     * @param encode 是否压缩公钥
     * @return 返回公钥字符串
     * @throws TrustSDKException
     */
    public static String generatePubkeyByPrvkey(String privateKey, boolean encode) throws TrustSDKException {
        if(StringUtils.isEmpty(privateKey)){
            throw new TrustSDKException(ErrorNum.INVALID_PARAM_ERROR.getRetCode(), ErrorNum.INVALID_PARAM_ERROR.getRetMsg());
        }
        try {
            return ECDSAAlgorithm.generatePublicKey(privateKey, encode);
        } catch (Exception e){
            throw new TrustSDKException(ErrorNum.ECDSA_ENCRYPT_ERROE.getRetCode(), ErrorNum.ECDSA_ENCRYPT_ERROE.getRetMsg(), e);
        }
    }

    /**
     *  generatePubkeyByPrvkey: 通过私钥计算相应公钥.
     * @param privateKey 私钥字符串
     * @return 返回公钥字符串
     * @throws TrustSDKException
     */
    public static String generatePubkeyByPrvkey(String privateKey) throws TrustSDKException{
        return generatePubkeyByPrvkey(privateKey, false);
    }

    public static String decodePubkey(String encodePubKey) throws TrustSDKException{
        if(StringUtils.isEmpty(encodePubKey)){
            throw new TrustSDKException(ErrorNum.INVALID_PARAM_ERROR.getRetCode(), ErrorNum.INVALID_PARAM_ERROR.getRetMsg());
        }
        try{
            return ECDSAAlgorithm.decodePublicKey(encodePubKey);
        } catch (Exception e){
            throw new TrustSDKException(ErrorNum.ECDSA_ENCRYPT_ERROE.getRetCode(), ErrorNum.ECDSA_ENCRYPT_ERROE.getRetMsg(), e);
        }
    }

    /**
     *  generateAddrByPubkey:通过公钥获取对应地址.
     * @param pubKey  公钥字符串
     * @return address
     * @throws TrustSDKException
     */
    public static String generateAddrByPubkey(String pubKey) throws TrustSDKException{
        if(StringUtils.isEmpty(pubKey)){
            throw new TrustSDKException(ErrorNum.INVALID_PARAM_ERROR.getRetCode(), ErrorNum.INVALID_PARAM_ERROR.getRetMsg());
        }
        try {
            return ECDSAAlgorithm.getAddress(Base64.decodeBase64(pubKey));
        } catch (Exception e){
            throw new TrustSDKException(ErrorNum.ECDSA_ENCRYPT_ERROE.getRetCode(), ErrorNum.ECDSA_ENCRYPT_ERROE.getRetMsg(), e);
        }
    }

    /**
     *  generateAddrByPrvkey:通过私钥计算相应地址.
     * @param privateKey 私钥字符串
     * @return Address
     * @throws TrustSDKException
     */
    public static String generateAddrByPrvkey(String privateKey) throws TrustSDKException{
        if(StringUtils.isEmpty(privateKey)){
            throw new TrustSDKException(ErrorNum.INVALID_PARAM_ERROR.getRetCode(), ErrorNum.INVALID_PARAM_ERROR.getRetMsg());
        }
        try{
            String pubKey = ECDSAAlgorithm.generatePublicKey(privateKey);
            return generateAddrByPubkey(pubKey);
        }catch (Exception e){
            throw new TrustSDKException(ErrorNum.ECDSA_ENCRYPT_ERROE.getRetCode(), ErrorNum.ECDSA_ENCRYPT_ERROE.getRetMsg(), e);
        }
    }

    /**
     *  signString:为字符串进行签名, 并返回签名.
     * @param privateKey 私钥字符串
     * @param data 需要签名的字符数组
     * @return 返回签名字符串
     * @throws TrustSDKException
     */
    public static String signString(String privateKey, byte[] data) throws TrustSDKException {
        if(StringUtils.isEmpty(privateKey)) {
            throw new TrustSDKException(ErrorNum.INVALID_PARAM_ERROR.getRetCode(), ErrorNum.INVALID_PARAM_ERROR.getRetMsg());
        }
        try{
            return ECDSAAlgorithm.sign(privateKey, data);
        } catch (Exception e) {
            throw new TrustSDKException(ErrorNum.SIGN_ERROR.getRetCode(), ErrorNum.SIGN_ERROR.getRetMsg(), e);
        }
    }

    public static String signString(String privateKey, String data) throws TrustSDKException, UnsupportedEncodingException{
        return signString(privateKey, data.getBytes("UTF-8"));
    }

    /**
     *  verifyString:验证一个签名是否有效.
     * @param pubKey 公钥字符串
     * @param srcString 源字符串
     * @param sign 签名字符串
     * @return 返回验证是否通过 true:验证成功 false:验证失败
     * @throws TrustSDKException
     */
    public static boolean verifyString(String pubKey, String srcString, String sign) throws TrustSDKException{
        if(StringUtils.isEmpty(pubKey)||StringUtils.isEmpty(sign)||StringUtils.isEmpty(srcString)){
            throw new TrustSDKException(ErrorNum.INVALID_PARAM_ERROR.getRetCode(), ErrorNum.INVALID_PARAM_ERROR.getRetMsg());
        }
        try {
            return ECDSAAlgorithm.verify(srcString, sign, pubKey);
        } catch (Exception e){
            throw new TrustSDKException(ErrorNum.ECDSA_ENCRYPT_ERROE.getRetCode(), ErrorNum.ECDSA_ENCRYPT_ERROE.getRetMsg(), e);
        }
    }
}
