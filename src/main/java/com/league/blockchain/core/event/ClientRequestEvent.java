package com.league.blockchain.core.event;

import com.league.blockchain.socket.packet.BlockPacket;
import org.springframework.context.ApplicationEvent;

/**
 *  客户端对外发请求时会触发该Event
 */
public class ClientRequestEvent extends ApplicationEvent {
    public ClientRequestEvent(BlockPacket blockPacket){
        super(blockPacket);
    }
}
