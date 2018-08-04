package com.league.blockchain.core.manager;

import cn.hutool.core.util.StrUtil;
import com.league.blockchain.block.Block;
import com.league.blockchain.block.db.DbStore;
import com.league.blockchain.common.Constants;
import com.league.blockchain.common.FastJsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DbBlockManager {
    @Resource
    private DbStore dbStore;

    /**
     *  查找第一个区块
     * @return
     */
    public Block getFirstBlock(){
        String firstBlockHash = dbStore.get(Constants.KEY_FIRST_BLOCK);
        if(StrUtil.isEmpty(firstBlockHash)){
            return null;
        }
        return getBlockByHash(firstBlockHash);
    }

    /**
     *  获取最后一个区块信息
     * @return
     */
    public Block getLastBlock(){
        String lastBlockHash = dbStore.get(Constants.KEY_LAST_BLOCK);
        if(StrUtil.isEmpty(lastBlockHash)){
            return null;
        }
        return getBlockByHash(lastBlockHash);
    }

    /**
     *  获取最后一个区块的hash
     * @return
     */
    public String getLastBlockHash(){
        Block block = getLastBlock();
        if(block!=null){
            return block.getHash();
        }
        return null;
    }

    /**
     * 获取最后一个block的number
     * @return
     */
    public int getLastBlockNumber(){
        Block block = getLastBlock();
        if(block!=null){
            return block.getBlockHeader().getNumber();
        }
        return 0;
    }

    /**
     *  获取某一个block的下一个block
     * @param block
     * @return
     */
    public Block getNextBlock(Block block){
        if(block==null){
            return getFirstBlock();
        }
        String nextHash = dbStore.get(Constants.KEY_BLOCK_NEXT_PREFIX + block.getHash());
        if(nextHash==null){
            return null;
        }
        return getBlockByHash(nextHash);
    }

    public Block getNextBlockByHash(String hash){
        if(hash==null){
            return getFirstBlock();
        }
        String nextHash = dbStore.get(Constants.KEY_BLOCK_NEXT_PREFIX + hash);
        if(nextHash==null){
            return null;
        }
        return getBlockByHash(nextHash);
    }

    public Block getBlockByHash(String hash){
        String blockJson = dbStore.get(hash);
        return FastJsonUtil.toBean(blockJson, Block.class);
    }
}
