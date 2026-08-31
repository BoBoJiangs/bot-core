package com.zhuangxv.bot.message.support;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.annotation.JSONField;
import com.zhuangxv.bot.message.Message;
import com.zhuangxv.bot.message.MessageChain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jiluo
 * @since 2021-09-08
 */
@Getter
public class ForwardNodeMessage implements Message {

    @Setter(AccessLevel.NONE)
    private String id;

    @JSONField(name = "user_id")
    private String userId;

    private String nickname;

    @Setter
    private MessageChain content;

    public ForwardNodeMessage(String id) {
        this.id = id;
    }

    public ForwardNodeMessage(String userId, String nickname, MessageChain content) {
        this.userId = userId;
        this.nickname = nickname;
        this.content = content;
    }

    @Override
    public String toString() {
        return "node[" + JSON.toJSONString(this) + "]";
    }

    @Override
    public String toMessageString() {
        return String.format("{\"type\":\"%s\",\"data\":%s}", "node", JSON.toJSONString(this));
    }

    public JSONArray getContent() {
        return JSON.parseArray(content.toMessageString());
    }
}
