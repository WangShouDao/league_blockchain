package com.league.blockchain.socket.pbft.queue;

import com.league.blockchain.socket.client.ClientStarter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public abstract class BaseMsgQueue {
    @Resource
    private ClientStarter clientStarter;

    public int pbftSize(){
        return clientStarter.pbftSize()
    }
}
