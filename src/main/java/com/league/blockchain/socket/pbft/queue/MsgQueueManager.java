package com.league.blockchain.socket.pbft.queue;

import com.league.blockchain.ApplicationContextProvider;
import com.league.blockchain.socket.pbft.VoteType;
import com.league.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.stereotype.Component;

@Component
public class MsgQueueManager {
    public void pushMsg(VoteMsg voteMsg){
        BaseMsgQueue baseMsgQueue = null;
        switch (voteMsg.getVoteType()){
            case VoteType.PREPREPARE:
                baseMsgQueue = ApplicationContextProvider.getBean(PreMsgQueue.class);
                break;
            case VoteType.PREPARE:
                baseMsgQueue = ApplicationContextProvider.getBean(PrepareMsgQueue.class);
                break;
            case VoteType.COMMIT:
                baseMsgQueue = ApplicationContextProvider.getBean(CommitMsgQueue.class);
                break;
        }
        if(baseMsgQueue!=null){
            baseMsgQueue.push(voteMsg);
        }
    }
}
