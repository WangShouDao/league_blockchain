package com.league.blockchain.socket.handler.server;

import com.league.blockchain.ApplicationContextProvider;
import com.league.blockchain.block.Block;
import com.league.blockchain.core.manager.DbBlockManager;
import com.league.blockchain.socket.base.AbstractBlockHandler;
import com.league.blockchain.socket.body.RpcBlockBody;
import com.league.blockchain.socket.body.RpcSimpleBlockBody;
import com.league.blockchain.socket.packet.BlockPacket;
import com.league.blockchain.socket.packet.PacketBuilder;
import com.league.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

/**
 *  请求别人某个区块的消息
 */
public class FetchBlockRequestHandler extends AbstractBlockHandler<RpcSimpleBlockBody> {
    private Logger logger = LoggerFactory.getLogger(FetchBlockRequestHandler.class);

    @Override
    public Class<RpcSimpleBlockBody> bodyClass(){
        return RpcSimpleBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcSimpleBlockBody rpcBlockBody, ChannelContext channelContext){
        logger.info("收到来自于<" + rpcBlockBody.getAppId() + "><请求该Block>消息，block hash为[" + rpcBlockBody.getHash() + "]");

        Block block = ApplicationContextProvider.getBean(DbBlockManager.class).getBlockByHash(rpcBlockBody.getHash());
        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.FETCH_BLOCK_INFO_RESPONSE).setBody(new
                RpcBlockBody(block)).build();
        Aio.send(channelContext, blockPacket);

        return null;

    }
}
