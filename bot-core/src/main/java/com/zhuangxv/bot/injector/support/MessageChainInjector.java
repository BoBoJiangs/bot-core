package com.zhuangxv.bot.injector.support;

import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.event.BaseEvent;
import com.zhuangxv.bot.event.message.GroupMessageEvent;
import com.zhuangxv.bot.event.message.GroupRecallEvent;
import com.zhuangxv.bot.event.message.PrivateMessageEvent;
import com.zhuangxv.bot.injector.ObjectInjector;
import com.zhuangxv.bot.message.CacheMessage;
import com.zhuangxv.bot.message.Message;
import com.zhuangxv.bot.message.MessageChain;
import com.zhuangxv.bot.message.MessageTypeHandle;
import com.zhuangxv.bot.message.support.ReplyMessage;

import java.util.List;

/**
 * @author xiaoxu
 * @since 2022-05-24 10:19
 */
public class MessageChainInjector implements ObjectInjector<MessageChain> {
    @Override
    public Class<MessageChain> getClassType() {
        return MessageChain.class;
    }

    @Override
    public String[] getType() {
        return new String[]{"message", "recallMessage"};
    }

    @Override
    public MessageChain getObject(BaseEvent event, Bot bot) {
        MessageChain messageChain = null;
        if (event instanceof GroupMessageEvent) {
            messageChain = new MessageChain();
            GroupMessageEvent groupMessageEvent = (GroupMessageEvent) event;
            for (int i = 0; i < groupMessageEvent.getMessage().size(); i++) {
                Message message = MessageTypeHandle.getMessage(groupMessageEvent.getMessage().getJSONObject(i));
                if (message instanceof ReplyMessage) {
                    ReplyMessage replyMessage = (ReplyMessage)message;
                    List<CacheMessage> cacheMessages = bot.getGroupCacheMessageChain(groupMessageEvent.getGroupId(), Integer.parseInt(replyMessage.getId()), 1);
                    if (cacheMessages != null && !cacheMessages.isEmpty()) {
                        replyMessage.setChain(cacheMessages.get(0).getMessageChain());
                    } else {
                        MessageChain cacheMassage = bot.getMessage(Integer.parseInt(replyMessage.getId()));
                        replyMessage.setChain(cacheMassage);
                    }
                }
                messageChain.add(message);
            }
        }
        if (event instanceof PrivateMessageEvent) {
            PrivateMessageEvent privateMessageEvent = (PrivateMessageEvent) event;
            messageChain = new MessageChain();
            for (int i = 0; i < privateMessageEvent.getMessage().size(); i++) {
                messageChain.add(MessageTypeHandle.getMessage(privateMessageEvent.getMessage().getJSONObject(i)));
            }
        }
        if (event instanceof GroupRecallEvent) {
            GroupRecallEvent groupRecallEvent = (GroupRecallEvent) event;
            List<CacheMessage> groupCacheMessageChain = bot.getGroupCacheMessageChain(groupRecallEvent.getGroupId(), groupRecallEvent.getMessageId(), 1);
            if (groupCacheMessageChain.isEmpty()) {
                return null;
            }
            messageChain = groupCacheMessageChain.get(0).getMessageChain();
        }
        return messageChain;
    }
}
