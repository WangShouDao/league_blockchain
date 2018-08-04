package com.league.blockchain.socket.handler.client;

import com.league.blockchain.ApplicationContextProvider;
import com.league.blockchain.block.Block;
import com.league.blockchain.block.check.CheckerManager;
import com.league.blockchain.core.event.AddBlockEvent;
import com.league.blockchain.socket.base.AbstractBlockHandler;
import com.league.blockchain.socket.body.RpcBlockBody;
import com.league.blockchain.socket.body.RpcCheckBlockBody;
import com.league.blockchain.socket.client.PacketSender;
import com.league.blockchain.socket.packet.BlockPacket;
import com.league.blockchain.socket.packet.NextBlockPacketBuilder;
import com.league.blockchain.socket.pbft.queue.NextBlockQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 *   对方根据我们传的hash，给我们返回的block
 */
public class FetchBlockResponseHandler extends AbstractBlockHandler<RpcBlockBody> {
    private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);

    @Override
    public Class<RpcBlockBody> bodyClass() {
        return RpcBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcBlockBody rpcBlockBody, ChannelContext channelContext){
        logger.info("收到来自于<" + rpcBlockBody.getAppId() + ">的回复，Block为：" + Json.toJson(rpcBlockBody));

        Block block = rpcBlockBody.getBlock();
        // 如果为null, 说明对方也没有该Block
        if(block==null){
            logger.info("对方也没有该Block");
        } else {
            // //此处校验传过来的block的合法性，如果合法，则更新到本地，作为next区块
            if(ApplicationContextProvider.getBean(NextBlockQueue.class).pop(block.getHash())==null)
                return null;

            CheckerManager checkerManager = ApplicationContextProvider.getBean(CheckerManager.class);
            RpcCheckBlockBody rpcCheckBlockBody = checkerManager.check(block);
            // 校验通过，则存入本地DB，保存新区块
            if(rpcCheckBlockBody.getCode()==0){
                ApplicationContextProvider.publishEvent(new AddBlockEvent(block));
                // 继续请求下一块
                BlockPacket blockPacket = NextBlockPacketBuilder.build();
                ApplicationContextProvider.getBean(PacketSender.class).sendGroup(blockPacket);
            }
        }
        return null;
    }
}
