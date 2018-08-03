package com.league.blockchain.core.repository;

import com.league.blockchain.core.model.MessageEntity;

public interface MessageRepository extends BaseRepository<MessageEntity> {
    /**
     *  删除一条记录
     * @param messageId
     */
    void deleteByMessageId(String messageId);

    /**
     *  查询一条记录
     * @param messageId
     * @return
     */
    MessageEntity findByMessageId(String messageId);
}
