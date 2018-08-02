package com.league.blockchain.block;

/**
 *  区块body内一条指令
 */
public class Instruction extends InstructionReverse {
    /**
     *  新的内容
     */
    private String json;
    /**
     *  时间戳
     */
    private long timeStamp;
    /**
     *  操作人的公钥
     */
    private String publicKey;
    /**
     *  签名
     */
    private String sign;
    /**
     *  该操作的hash
     */
    private String hash;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
