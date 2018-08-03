package com.league.blockchain.socket.body;

import com.league.blockchain.socket.pbft.msg.VoteMsg;

/**
 * pbft 投票
 */
public class VoteBody extends BaseBody {
    private VoteMsg voteMsg;

    public VoteBody(){
        super();
    }

    public VoteBody(VoteMsg voteMsg) {
        super();
        this.voteMsg = voteMsg;
    }

    public VoteMsg getVoteMsg() {
        return voteMsg;
    }

    public void setVoteMsg(VoteMsg voteMsg) {
        this.voteMsg = voteMsg;
    }
}
