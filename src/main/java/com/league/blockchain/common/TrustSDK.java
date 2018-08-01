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
            String pubKey = ECDSAAlgorithm.generatePrivateKey(privateKey.trim(), encodePubKey);
            pair.setPrivateKey(privateKey);
            pair.setPublicKey(pubKey);
            return pair;
        } catch (Exception e){
            throw new TrustSDKException(ErrorNum.ECDSA_ENCRYPT_ERROE.getRetCode(), ErrorNum.ECDSA_ENCRYPT_ERROE.getRetMsg(), e);
        }
    }

    public static boolean checkPairKey(String prvKey, String pubKey) throws TrustSDKException{
        if(StringUtils.isEmpty(prvKey)||StringUtils.isEmpty(pubKey)){
            throw new TrustSDKException(ErrorNum.INVALID_PARAM_ERROR.getRetCode(), ErrorNum.INVALID_PARAM_ERROR.getRetMsg());
        }
        try {
            String correctPubKey = ECDSAAlgorithm.generatePrivateKey(prvKey.trim(), true);
            return pubKey.trim().equals(correctPubKey);
        } catch (Exception e){
            throw new TrustSDKException(ErrorNum.ECDSA_ENCRYPT_ERROE.getRetCode(), ErrorNum.ECDSA_ENCRYPT_ERROE.getRetCode(), e);
        }
    }
}
