package com.league.blockchain.socket.base;

import com.league.blockchain.socket.body.BaseBody;
import com.league.blockchain.socket.common.Const;
import com.league.blockchain.socket.packet.BlockPacket;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 *  基础handler
 * @param <T>
 */
public abstract class AbstractBlockHandler<T extends BaseBody> implements HandlerInterface {
    public AbstractBlockHandler(){

    }

    public abstract Class<T> bodyClass();

    @Override
    public Object handler(BlockPacket packet, ChannelContext channelContext) throws Exception{
        String jsonStr;
        T bsBody = null;
        if(packet.getBody()!=null){
            jsonStr = new String(packet.getBody(), Const.CHARSET);
            bsBody = Json.toBean(jsonStr, bodyClass());
        }
        return handler(packet, bsBody, channelContext);
    }

    /**
     *  实际的handler处理
     * @param packet
     * @param bsBody
     * @param channelContext
     * @return
     * @throws Exception
     */
    public abstract Object handler(BlockPacket packet, T bsBody, ChannelContext channelContext) throws Exception;
}
