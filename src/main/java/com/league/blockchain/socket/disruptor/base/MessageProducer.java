package com.league.blockchain.socket.disruptor.base;

public interface MessageProducer {
    void publish(BaseEvent baseEvent);
}
