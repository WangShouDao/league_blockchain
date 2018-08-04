package com.league.blockchain.core.controller;

import com.league.blockchain.socket.client.BlockClientAioHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;

/**
 *  配置ClientGroupContext
 */
@Configuration
public class BlockController {
    /**
     * 构建客户端连接的context
     */
    @Bean
    public ClientGroupContext clientGroupContext(){
        // handler, 包括编码、解码、消息处理
        ClientAioHandler clientAioHandler = new BlockClientAioHandler();
        // 事件监听器, 可以为null, 但建议自己实现该接口
        ClientAioListener clientAioListener = new BlockClientAioHandler();
        ReconnConf reconnConf = new ReconnConf(5000L, 20);
        ClientGroupContext clientGroupContext = new ClientGroupContext(clientAioHandler, clientAioListener, reconnConf);

        // clientGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
        return clientGroupContext;
    }
}
