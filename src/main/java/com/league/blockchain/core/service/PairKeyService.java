package com.league.blockchain.core.service;

import com.league.blockchain.block.PairKey;
import com.league.blockchain.common.TrustSDK;
import com.league.blockchain.common.exception.TrustSDKException;
import org.springframework.stereotype.Service;

@Service
public class PairKeyService {
    public PairKey generate() throws TrustSDKException{
        return TrustSDK.generatePairKey(true);
    }
}
