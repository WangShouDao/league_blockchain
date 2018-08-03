package com.league.blockchain.core.requestbody;

import com.league.blockchain.block.BlockBody;

/**
 *  生成Block时传参
 */
public class BlockRequestBody {
    private String publickey;
    private BlockBody blockBody;

    @Override
    public String toString() {
        return "BlockRequestBody{" +
                "publickey='" + publickey + '\'' +
                ", blockBody=" + blockBody +
                '}';
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public BlockBody getBlockBody() {
        return blockBody;
    }

    public void setBlockBody(BlockBody blockBody) {
        this.blockBody = blockBody;
    }
}
