package com.league.blockchain.socket.handler.server;

import com.league.blockchain.socket.base.AbstractBlockHandler;
import com.league.blockchain.socket.body.RpcBlockBody;
import com.league.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

/**
 *  收到请求生成区块消息，进入PrePre队列
 */
public class GenerateBlockRequestHandler extends AbstractBlockHandler<RpcBlockBody> {
    private Logger logger = LoggerFactory.getLogger(GenerateBlockRequestHandler.class);

    @Override
    public Class<RpcBlockBody> bodyClass(){
        return RpcBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcBlockBody rpcBlockBody, ChannelContext channelContext){
        
    }
}
