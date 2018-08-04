package com.league.blockchain.socket.disruptor;

import com.league.blockchain.ApplicationContextProvider;
import com.league.blockchain.socket.disruptor.base.BaseEvent;
import com.lmax.disruptor.EventHandler;

public class DisruptorClientHandler implements EventHandler<BaseEvent> {
    @Override
    public void onEvent(BaseEvent baseEvent, long sequence, boolean endOfBatch) throws Exception{
        ApplicationContextProvider.getBean(DisruptorClientConsumer.class).receive(baseEvent);
    }
}
