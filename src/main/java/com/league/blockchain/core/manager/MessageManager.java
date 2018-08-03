package com.league.blockchain.core.manager;

import com.league.blockchain.core.model.MessageEntity;
import com.league.blockchain.core.repository.MessageRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageManager {
    @Resource
    private MessageRepository messageRepository;

    public List<MessageEntity> findAll(){
        return messageRepository.findAll();
    }

    public List<String> findAllContent(){
        return findAll().stream().map(MessageEntity::getContent).collect(Collectors.toList());
    }
}
