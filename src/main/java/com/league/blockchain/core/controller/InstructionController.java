package com.league.blockchain.core.controller;

import com.league.blockchain.core.bean.BaseData;
import com.league.blockchain.core.bean.ResultGenerator;
import com.league.blockchain.core.requestbody.InstructionBody;
import com.league.blockchain.core.service.InstructionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 区块body内单个指令的controller
 */
@RestController
@RequestMapping("/instruction")
public class InstructionController {
    @Resource
    private InstructionService instructionService;

    /**
     *  构建一条指令，传入各必要参数
     * @param instructionBody
     * @return
     * @throws Exception
     */
    @PostMapping
    public BaseData build(@RequestBody InstructionBody instructionBody) throws Exception{
        if(!instructionService.checkKeyPair(instructionBody)){
            return ResultGenerator.genFailResult("公私钥不是一对");
        }
        if(!instructionService.checkContent(instructionBody)){
            return ResultGenerator.genFailResult("Delete和Update操作需要有id和json内容");
        }
        return ResultGenerator.genSuccessResult(instructionService.build(instructionBody));
    }
}
