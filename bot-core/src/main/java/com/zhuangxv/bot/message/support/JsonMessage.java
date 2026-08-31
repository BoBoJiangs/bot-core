package com.zhuangxv.bot.message.support;

import com.zhuangxv.bot.message.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonMessage implements Message {

    private String json;

    public JsonMessage(String json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return "json[" + json + "]";
    }

    @Override
    public String toMessageString() {
        return String.format("{\"type\":\"%s\",\"data\":%s}", "json", json);
    }

}
