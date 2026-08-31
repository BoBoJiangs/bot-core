package com.zhuangxv.bot.message.support;

import com.alibaba.fastjson2.JSON;
import com.zhuangxv.bot.message.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForwardMessage implements Message {

    private String id;

    public ForwardMessage(String text) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public String toMessageString() {
        return String.format("{\"type\":\"%s\",\"data\":%s}", "forward", JSON.toJSONString(this));
    }

}
