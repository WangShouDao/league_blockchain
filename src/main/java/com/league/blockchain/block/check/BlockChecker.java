package com.league.blockchain.block.check;

import com.league.blockchain.block.Block;

/**
 *  区块校验
 */
public interface BlockChecker {
    /**
     *  比较目标区块和自己本地的区块num大小
     * @param block
     * 被比较的区块
     * @return
     * 本地与目标区块的差值
     */
    int checkNum(Block block);

    /**
     *  校验区块内操作的权限是否合法
     * @param block
     * 被比较的区块
     * @return
     * 大于0合法
     */
    int checkPermission(Block block);

    /**
     *  校验hash，包括prevHash、内部hash（merkle tree root hash）
     * @param block
     * 被比较的区块
     * @return
     * 大于0合法
     */
    int checkHash(Block block);

    /**
     *  校验生成时间
     * @param block
     * @return
     */
    int checkTime(Block block);

    /**
     *  校验签名
     * @param block
     * @return
     */
    int checkSign(Block block);

    /**
     *  校验block, 包括签名、hash、关联关系
     * @param block
     * @return
     */
    public String checkBlock(Block block);
}
