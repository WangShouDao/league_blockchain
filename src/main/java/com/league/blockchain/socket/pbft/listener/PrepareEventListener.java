package com.league.blockchain.socket.pbft.listener;

import com.league.blockchain.socket.body.VoteBody;
import com.league.blockchain.socket.client.PacketSender;
import com.league.blockchain.socket.packet.BlockPacket;
import com.league.blockchain.socket.packet.PacketBuilder;
import com.league.blockchain.socket.packet.PacketType;
import com.league.blockchain.socket.pbft.event.MsgPrepareEvent;
import com.league.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PrepareEventListener {
    @Resource
    private PacketSender packetSender;

    /**
     *  block已经开始进入Prepare状态
     * @param msgPrepareEvent
     */
    @EventListener
    public void msgIsPrepare(MsgPrepareEvent msgPrepareEvent){
        VoteMsg voteMsg = (VoteMsg) msgPrepareEvent.getSource();

        // 群发消息，通知别的节点，我已对该Block Prepare
        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.PBFT_VOTE).setBody(
                new VoteBody(voteMsg)).build();

        // 广播给所有人我已Prepare
        packetSender.sendGroup(blockPacket);
    }
}
