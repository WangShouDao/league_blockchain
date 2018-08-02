package com.league.blockchain.block;

import java.util.List;
/**
 *  区块头
 */
public class BlockHeader {
    /**
     * 版本号
     */
    private int version;
    /**
     *  上一区块的hash
     */
    private String hashPreviousBlock;
    /**
     *  merkle tree 根节点 hash
     */
    private String hashMerkleRoot;
    /**
     *  生成该区块的公钥
     */
    private String publicKey;
    /**
     *  区块的序号
     */
    private int number;
    /**
     *  时间戳
     */
    private long timeStamp;
    /**
     *  32位随机数
     */
    private long nonce;

    private List<String> hashList;

    @Override
    public String toString() {
        return "BlockHeader{" +
                "version=" + version +
                ", hashPreviousBlock='" + hashPreviousBlock + '\'' +
                ", hashMerkleRoot='" + hashMerkleRoot + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", number=" + number +
                ", timeStamp=" + timeStamp +
                ", nonce=" + nonce +
                ", hashList=" + hashList +
                '}';
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getHashPreviousBlock() {
        return hashPreviousBlock;
    }

    public void setHashPreviousBlock(String hashPreviousBlock) {
        this.hashPreviousBlock = hashPreviousBlock;
    }

    public String getHashMerkleRoot() {
        return hashMerkleRoot;
    }

    public void setHashMerkleRoot(String hashMerkleRoot) {
        this.hashMerkleRoot = hashMerkleRoot;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public List<String> getHashList() {
        return hashList;
    }

    public void setHashList(List<String> hashList) {
        this.hashList = hashList;
    }
}
