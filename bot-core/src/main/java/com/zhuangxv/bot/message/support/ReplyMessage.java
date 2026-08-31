/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSON
 *  com.alibaba.fastjson2.annotation.JSONField
 */
package com.zhuangxv.bot.message.support;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import com.zhuangxv.bot.message.Message;
import com.zhuangxv.bot.message.MessageChain;

public class ReplyMessage
implements Message {
    private String id;
    private String text;
    private String qq;
    @JSONField(serialize=false, deserialize=false)
    private transient MessageChain chain;
    private long seq;

    public ReplyMessage(int messageId) {
        this.id = String.valueOf(messageId);
    }

    public String toString() {
        return "reply[" + this.id + "]";
    }

    @Override
    public String toMessageString() {
        return String.format("{\"type\":\"%s\",\"data\":%s}", "reply", JSON.toJSONString((Object)this));
    }

    public String getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public String getQq() {
        return this.qq;
    }

    public MessageChain getChain() {
        return this.chain;
    }

    public long getSeq() {
        return this.seq;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public void setChain(MessageChain chain) {
        this.chain = chain;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ReplyMessage)) {
            return false;
        }
        ReplyMessage other = (ReplyMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$text = this.getText();
        String other$text = other.getText();
        if (this$text == null ? other$text != null : !this$text.equals(other$text)) {
            return false;
        }
        String this$qq = this.getQq();
        String other$qq = other.getQq();
        if (this$qq == null ? other$qq != null : !this$qq.equals(other$qq)) {
            return false;
        }
        return this.getSeq() == other.getSeq();
    }

    protected boolean canEqual(Object other) {
        return other instanceof ReplyMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $text = this.getText();
        result = result * 59 + ($text == null ? 43 : $text.hashCode());
        String $qq = this.getQq();
        result = result * 59 + ($qq == null ? 43 : $qq.hashCode());
        long $seq = this.getSeq();
        result = result * 59 + (int)($seq >>> 32 ^ $seq);
        return result;
    }

    public ReplyMessage() {
    }
}

