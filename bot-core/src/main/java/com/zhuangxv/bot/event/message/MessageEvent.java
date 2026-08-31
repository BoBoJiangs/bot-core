/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.annotation.JSONField
 */
package com.zhuangxv.bot.event.message;

import com.alibaba.fastjson2.annotation.JSONField;
import com.zhuangxv.bot.event.BaseEvent;

public class MessageEvent
extends BaseEvent {
    @JSONField(name="message_id")
    private Integer messageId;
    @JSONField(name="message_type")
    private String messageType;
    @JSONField(name="user_id")
    private Long userId;

    public Integer getMessageId() {
        return this.messageId;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MessageEvent(super=" + super.toString() + ", messageId=" + this.getMessageId() + ", messageType=" + this.getMessageType() + ", userId=" + this.getUserId() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MessageEvent)) {
            return false;
        }
        MessageEvent other = (MessageEvent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Integer this$messageId = this.getMessageId();
        Integer other$messageId = other.getMessageId();
        if (this$messageId == null ? other$messageId != null : !((Object)this$messageId).equals(other$messageId)) {
            return false;
        }
        String this$messageType = this.getMessageType();
        String other$messageType = other.getMessageType();
        if (this$messageType == null ? other$messageType != null : !this$messageType.equals(other$messageType)) {
            return false;
        }
        Long this$userId = this.getUserId();
        Long other$userId = other.getUserId();
        return !(this$userId == null ? other$userId != null : !((Object)this$userId).equals(other$userId));
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof MessageEvent;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Integer $messageId = this.getMessageId();
        result = result * 59 + ($messageId == null ? 43 : ((Object)$messageId).hashCode());
        String $messageType = this.getMessageType();
        result = result * 59 + ($messageType == null ? 43 : $messageType.hashCode());
        Long $userId = this.getUserId();
        result = result * 59 + ($userId == null ? 43 : ((Object)$userId).hashCode());
        return result;
    }
}

