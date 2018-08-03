package com.league.blockchain.socket.pbft.event;

import com.league.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.context.ApplicationEvent;

/**
 *  消息已被验证,进入到Prepare集合中
 */
public class MsgPrepareEvent extends ApplicationEvent {
    public MsgPrepareEvent(VoteMsg source){
        super(source);
    }
}
