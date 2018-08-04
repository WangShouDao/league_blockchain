package com.league.blockchain.socket.pbft.queue;

import com.league.blockchain.socket.client.ClientStarter;
import com.league.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public abstract class BaseMsgQueue {
    @Resource
    private ClientStarter clientStarter;

    public int pbftSize(){
        return clientStarter.pbftSize();
    }

    public int pbftAgreeSize(){
        return clientStarter.pbftAgreeCount();
    }

    /**
     *  来了新消息
     * @param voteMsg
     */
    protected abstract void push(VoteMsg voteMsg);
}
