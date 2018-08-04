package com.league.blockchain.core.controller;

import com.league.blockchain.block.check.BlockChecker;
import com.league.blockchain.common.exception.TrustSDKException;
import com.league.blockchain.core.bean.BaseData;
import com.league.blockchain.core.bean.ResultGenerator;
import com.league.blockchain.core.manager.DbBlockManager;
import com.league.blockchain.core.manager.MessageManager;
import com.league.blockchain.core.manager.SyncManager;
import com.league.blockchain.core.requestbody.BlockRequestBody;
import com.league.blockchain.core.service.BlockService;
import com.league.blockchain.core.service.InstructionService;
import com.league.blockchain.socket.client.PacketSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/block")
public class BlockController {
    @Resource
    private BlockService blockService;
    @Resource
    private PacketSender packetSender;
    @Resource
    private DbBlockManager dbBlockManager;
    @Resource
    private InstructionService instructionService;
    @Resource
    private SyncManager syncManager;
    @Resource
    private MessageManager messageManager;
    @Resource
    private BlockChecker blockChecker;

    /**
     *  添加一个block，需要先在InstructionController构建1-N个instruction指令，然后调用该接口生成Block
     * @param blockRequestBody
     * @return 结果
     * @throws TrustSDKException
     */
    @PostMapping
    public BaseData add(@RequestBody BlockRequestBody blockRequestBody) throws TrustSDKException{
        String msg = blockService.check(blockRequestBody);
        if(msg!=null){
            return ResultGenerator.genFailResult(msg);
        }
        return ResultGenerator.genSuccessResult(blockService.addBlock(blockRequestBody));
    }
}
