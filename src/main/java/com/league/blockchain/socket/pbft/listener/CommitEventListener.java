package com.league.blockchain.socket.pbft.listener;

import com.league.blockchain.socket.body.VoteBody;
import com.league.blockchain.socket.client.PacketSender;
import com.league.blockchain.socket.packet.BlockPacket;
import com.league.blockchain.socket.packet.PacketBuilder;
import com.league.blockchain.socket.packet.PacketType;
import com.league.blockchain.socket.pbft.event.MsgCommitEvent;
import com.league.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *  监听block可以commit消息
 */
@Component
public class CommitEventListener {
    @Resource
    private PacketSender packetSender;

    @EventListener
    public void msgIsCommit(MsgCommitEvent msgCommitEvent){
        VoteMsg voteMsg = (VoteMsg) msgCommitEvent.getSource();

        // 群发消息, 通知所有节点, 已对该Block Prepare
        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.PBFT_VOTE).setBody(new
                VoteBody(voteMsg)).build();

        // 广播给所有人已commit
        packetSender.sendGroup(blockPacket);
    }
}
