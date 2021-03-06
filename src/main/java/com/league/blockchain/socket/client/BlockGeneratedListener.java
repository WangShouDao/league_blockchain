package com.league.blockchain.socket.client;

import com.league.blockchain.block.Block;
import com.league.blockchain.core.event.AddBlockEvent;
import com.league.blockchain.socket.body.RpcSimpleBlockBody;
import com.league.blockchain.socket.packet.BlockPacket;
import com.league.blockchain.socket.packet.PacketBuilder;
import com.league.blockchain.socket.packet.PacketType;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *  本地新生成区块后，需要通知所有group内的节点
 */
@Component
public class BlockGeneratedListener {
    @Resource
    private PacketSender packetSender;

    @Order(2)
    @EventListener(AddBlockEvent.class)
    public void blockGenerated(AddBlockEvent addBlockEvent){
        Block block = (Block) addBlockEvent.getSource();
        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.GENERATE_BLOCK_REQUEST).setBody(
                new RpcSimpleBlockBody(block.getHash())).build();


        // 广播给其他人做验证
        packetSender.sendGroup(blockPacket);
    }
}
