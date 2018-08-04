package com.league.blockchain.socket.disruptor.base;

public interface MessageConsumer {
    void receive(BaseEvent baseEvent) throws Exception;
}
