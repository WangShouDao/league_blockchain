package com.league.blockchain.socket.base;

import com.league.blockchain.socket.packet.BlockPacket;
import org.tio.core.ChannelContext;

public interface HandlerInterface {
    /**
     *  handler方法在此时封装转换
     * @param packet
     * @param channelContext
     * @return Object对象
     * @throws Exception
     */
    Object handler(BlockPacket packet, ChannelContext channelContext) throws Exception;
}
