package com.league.blockchain.core.controller;

import com.league.blockchain.common.exception.TrustSDKException;
import com.league.blockchain.core.bean.BaseData;
import com.league.blockchain.core.bean.ResultGenerator;
import com.league.blockchain.core.service.PairKeyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/pairKey")
public class PairKeyController {
    @Resource
    private PairKeyService pairKeyService;

    /**
     *  生成公钥私钥
     * @return
     * @throws TrustSDKException
     */
    @GetMapping("/random")
    public BaseData generate() throws TrustSDKException {
        return ResultGenerator.genSuccessResult(pairKeyService.generate());
    }
}
