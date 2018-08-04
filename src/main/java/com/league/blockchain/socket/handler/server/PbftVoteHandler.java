package com.league.blockchain.socket.handler.server;

import com.league.blockchain.ApplicationContextProvider;
import com.league.blockchain.socket.base.AbstractBlockHandler;
import com.league.blockchain.socket.body.VoteBody;
import com.league.blockchain.socket.packet.BlockPacket;
import com.league.blockchain.socket.pbft.msg.VoteMsg;
import com.league.blockchain.socket.pbft.queue.MsgQueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

/**
 *   pbft投票处理
 */
public class PbftVoteHandler extends AbstractBlockHandler<VoteBody> {
    private Logger logger = LoggerFactory.getLogger(PbftVoteHandler.class);

    @Override
    public Class<VoteBody> bodyClass(){
        return VoteBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, VoteBody voteBody, ChannelContext channelContext){
        VoteMsg voteMsg = voteBody.getVoteMsg();
        logger.info("收到来自于<" + voteMsg.getAppId() + "><投票>消息，投票信息为[" + voteMsg + "]");

        ApplicationContextProvider.getBean(MsgQueueManager.class).pushMsg(voteMsg);
        return null;
    }
}
