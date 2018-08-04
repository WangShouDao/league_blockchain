package com.league.blockchain.socket.server;

import com.league.blockchain.ApplicationContextProvider;
import com.league.blockchain.socket.base.AbstractAioHandler;
import com.league.blockchain.socket.disruptor.base.BaseEvent;
import com.league.blockchain.socket.disruptor.base.MessageProducer;
import com.league.blockchain.socket.packet.BlockPacket;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

/**
 *  server端处理所有client请求的入口
 */
public class BlockServerAioHandler extends AbstractAioHandler implements ServerAioHandler {

    /**
     *  自己是server，此处接收到客户端来的消息。这里是入口
     * @param packet
     * @param channelContext
     */
    @Override
    public void handler(Packet packet, ChannelContext channelContext){
        BlockPacket blockPacket = (BlockPacket) packet;

        // 使用Disruptor来publish消息。所有收到的消息都进入Disruptor，同BlockClientAioHandler
        ApplicationContextProvider.getBean(MessageProducer.class).publish(new BaseEvent(blockPacket, channelContext));
    }
}
