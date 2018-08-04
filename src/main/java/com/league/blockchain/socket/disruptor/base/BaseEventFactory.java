package com.league.blockchain.socket.disruptor.base;

import com.lmax.disruptor.EventFactory;

public class BaseEventFactory implements EventFactory<BaseEvent> {
    @Override
    public BaseEvent newInstance(){
        return new BaseEvent();
    }
}
