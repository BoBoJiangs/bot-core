package com.zhuangxv.bot.message.support;

import com.alibaba.fastjson2.JSON;
import com.zhuangxv.bot.message.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MarkdownMessage extends TextMessage implements Message {

    private String content;

    public MarkdownMessage(String content) {
        this.content = content;
    }

    /**
     * SnowLuma 等协议端下游戏卡片消息为 markdown 段，继承 TextMessage 并让
     * getText() 返回 content，使业务侧 getMessageByType(TextMessage.class)
     * 无需区分协议端即可解析卡片文本
     */
    @Override
    public String getText() {
        return content;
    }

    @Override
    public String toString() {
        return "content[" + content + "]";
    }

    @Override
    public String toMessageString() {
        return String.format("{\"type\":\"%s\",\"data\":%s}", "markdown", JSON.toJSONString(this));
    }

}
