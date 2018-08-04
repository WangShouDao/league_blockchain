package com.league.blockchain.socket.handler.client;

import com.league.blockchain.socket.base.AbstractBlockHandler;
import com.league.blockchain.socket.body.RpcNextBlockBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/**
 *  对方根据我们传的hash，给我们返回的next block
 */
public class NextBlockResponseHandler extends AbstractBlockHandler<RpcNextBlockBody> {
    private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);

    @Order
}
