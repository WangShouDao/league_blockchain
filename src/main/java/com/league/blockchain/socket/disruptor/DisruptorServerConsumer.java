package com.league.blockchain.socket.disruptor;

import com.league.blockchain.socket.base.AbstractBlockHandler;
import com.league.blockchain.socket.disruptor.base.BaseEvent;
import com.league.blockchain.socket.disruptor.base.MessageConsumer;
import com.league.blockchain.socket.handler.server.*;
import com.league.blockchain.socket.handler.client.FetchBlockResponseHandler;
import com.league.blockchain.socket.handler.client.TotalBlockInfoResponseHandler;
import com.league.blockchain.socket.packet.BlockPacket;
import com.league.blockchain.socket.packet.PacketType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *  所有client发来的消息都在这里处理
 */
@Component
public class DisruptorServerConsumer implements MessageConsumer {
    private static Map<Byte, AbstractBlockHandler<?>> handlerMap = new HashMap<>();

    static {
        handlerMap.put(PacketType.GENERATE_COMPLETE_REQUEST, new GenerateCompleteRequestHandler());
        handlerMap.put(PacketType.GENERATE_BLOCK_REQUEST, new GenerateBlockRequestHandler());
        handlerMap.put(PacketType.TOTAL_BLOCK_INFO_REQUEST, new TotalBlockInfoResponseHandler());
        handlerMap.put(PacketType.FETCH_BLOCK_INFO_REQUEST, new FetchBlockResponseHandler());
        handlerMap.put(PacketType.HEEART_BEAT, new HeartBeatHandler());
        handlerMap.put(PacketType.NEXT_BLOCK_INFO_REQUEST, new NextBlockRequestHandler());
        handlerMap.put(PacketType.PBFT_VOTE, new PbftVoteHandler());
    }

    @Override
    public void receive(BaseEvent baseEvent) throws Exception{
        BlockPacket blockPacket = baseEvent.getBlockPacket();
        Byte type = blockPacket.getType();
        AbstractBlockHandler<?> handler = handlerMap.get(type);
        if(handler == null){
            return;
        }
        handler.handler(blockPacket, baseEvent.getChannelContext());
    }
}
