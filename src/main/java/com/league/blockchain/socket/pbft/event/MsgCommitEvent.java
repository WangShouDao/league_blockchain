package com.league.blockchain.socket.pbft.event;

import com.league.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.context.ApplicationEvent;

/**
 *  消息已被验证，进入到Commit集合中
 */
public class MsgCommitEvent extends ApplicationEvent {
    public MsgCommitEvent(VoteMsg source){
        super(source);
    }
}
