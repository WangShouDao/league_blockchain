package com.league.blockchain.block.check;

import com.league.blockchain.block.Block;
import com.league.blockchain.socket.body.RpcCheckBlockBody;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *  区块校验
 */
@Component
public class CheckerManager {
    @Resource
    private BlockChecker blockChecker;

    public RpcCheckBlockBody check(Block block){
        int code = blockChecker.checkSign(block);
        if(code != 0){
            return new RpcCheckBlockBody(-1, "block的签名不合法");
        }
        int number = blockChecker.checkNum(block);
        if(number!=0){
            return new RpcCheckBlockBody(-1, "block的number不合法");
        }
        int time = blockChecker.checkTime(block);
        if(time!=0){
            return new RpcCheckBlockBody(-4, "block的时间错误");
        }
        int hash = blockChecker.checkHash(block);
        if(hash!=0){
            return new RpcCheckBlockBody(-3, "hash校验不通过");
        }
        int permission = blockChecker.checkPermission(block);
        if(permission!=0){
            return new RpcCheckBlockBody(-2, "没有表的操作权限");
        }
        return new RpcCheckBlockBody(0, "OK", block);
    }
}
