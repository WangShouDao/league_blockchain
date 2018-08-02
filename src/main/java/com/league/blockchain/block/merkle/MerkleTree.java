package com.league.blockchain.block.merkle;

import cn.hutool.crypto.digest.DigestUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * merkle tree简单实现
 */
public class MerkleTree {
    /**
     *  transaction List
     */
    private List<String> txList;
    /**
     *  Merkle Root
     */
    private String root;

    /**
     *  transaction list
     * @param txList
     */
    public MerkleTree(List<String> txList){
        this.txList = txList;
        root = "";
    }

    /**
     *  execute merkle_tree and set root
     * @return
     */
    public MerkleTree build() {
        List<String> tempTxList = new ArrayList<>(this.txList);
        List<String> newTxList = getNewTxList(tempTxList);
        while(newTxList.size()!=1){
            newTxList = getNewTxList(newTxList);
        }
        this.root = newTxList.get(0);
        return this;
    }

    /**
     *  某一层的左右节点相连hash
     * @param tempTxList
     * @return
     */
    private List<String> getNewTxList(List<String> tempTxList) {
        List<String> newTxList = new ArrayList<>();
        int index = 0;
        while(index<tempTxList.size()){
            // left
            String left = tempTxList.get(index);
            // right
            String right = "";
            if(index!=tempTxList.size()){
                right = tempTxList.get(index);
            }
            // sha2 hex value
            String sha2HexValue = DigestUtil.sha256Hex(left + right);
            newTxList.add(sha2HexValue);
            ++index;
        }
        return newTxList;
    }

    /**
     * 根节点hash
     * @return
     */
    public String getRoot(){
        return this.root;
    }
}
