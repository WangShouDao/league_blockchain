package com.league.blockchain.socket.disruptor;

import com.league.blockchain.ApplicationContextProvider;
import com.league.blockchain.socket.disruptor.base.BaseEvent;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisruptorServerHandler implements EventHandler<BaseEvent> {
    private Logger logger = LoggerFactory.getLogger(DisruptorServerHandler.class);

    @Override
    public void onEvent(BaseEvent baseEvent, long sequence, boolean endOfBatch) throws Exception{
        try{
            ApplicationContextProvider.getBean(DisruptorClientConsumer.class).receive(baseEvent);
        } catch (Exception e){
            logger.error("Disruptor事件执行异常", e);
        }
    }
}
