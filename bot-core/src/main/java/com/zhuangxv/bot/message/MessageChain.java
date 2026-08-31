package com.zhuangxv.bot.message;

import com.alibaba.fastjson2.JSON;
import com.zhuangxv.bot.message.support.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoxu
 * @since 2022-05-24 10:19
 */
public class MessageChain extends ArrayList<Message> {

    @Override
    public String toString() {
        return this.stream().map(Object::toString).collect(Collectors.joining());
    }

    public String toMessageString() {
        return this.stream().map(Message::toMessageString).collect(Collectors.joining(",", "[", "]"));
    }

    public MessageChain at(String qq) {
        this.add(new AtMessage(qq));
        return this;
    }

    public MessageChain atAll() {
        this.add(new AtMessage("all"));
        return this;
    }

    public MessageChain text(String message) {
        this.add(new TextMessage(message));
        return this;
    }

    public MessageChain image(String file) {
        this.add(new ImageMessage(file));
        return this;
    }

    public MessageChain reply(int messageId) {
        this.add(new ReplyMessage(messageId));
        return this;
    }

    public MessageChain record(String file) {
        this.add(new RecordMessage(file));
        return this;
    }

    public MessageChain face(String id) {
        this.add(new FaceMessage(id));
        return this;
    }

    public MessageChain json(String json) {
        this.add(new JsonMessage(json));
        return this;
    }

    public MessageChain markdown(String content) {
        this.add(new MarkdownMessage(content));
        return this;
    }

    public MessageChain forward(String id) {
        this.add(new ForwardMessage(id));
        return this;
    }

    public boolean isAtUser(String qq) {
        List<AtMessage> messages = this.getMessageByType(AtMessage.class);
        for (AtMessage message : messages) {
            if (qq.equals(message.getQq())) {
                return true;
            }
        }
        return false;
    }

    public <T> List<T> getMessageByType(Class<T> clazz) {
        return this.stream().filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    public MessageChain copy() {
        MessageChain messageChain = new MessageChain();
        messageChain.addAll(this.stream().map(message -> MessageTypeHandle.getMessage(JSON.parseObject(message.toMessageString()))).collect(Collectors.toList()));
        return messageChain;
    }

}
