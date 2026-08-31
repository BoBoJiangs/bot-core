/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSONArray
 *  com.alibaba.fastjson2.JSONObject
 *  com.alibaba.fastjson2.annotation.JSONField
 */
package com.zhuangxv.bot.event.message;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.zhuangxv.bot.event.message.MessageEvent;

public class PrivateMessageEvent
extends MessageEvent {
    @JSONField(name="sub_type")
    private String subType;
    @JSONField(name="message")
    private JSONArray message;
    @JSONField(name="raw_message")
    private String rawMessage;
    @JSONField(name="font")
    private Integer font;
    @JSONField(name="sender")
    private JSONObject sender;

    public static boolean isSupport(JSONObject jsonObject) {
        return "message".equals(jsonObject.getString("post_type")) && "private".equals(jsonObject.getString("message_type"));
    }

    public String getSubType() {
        return this.subType;
    }

    public JSONArray getMessage() {
        return this.message;
    }

    public String getRawMessage() {
        return this.rawMessage;
    }

    public Integer getFont() {
        return this.font;
    }

    public JSONObject getSender() {
        return this.sender;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public void setMessage(JSONArray message) {
        this.message = message;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public void setFont(Integer font) {
        this.font = font;
    }

    public void setSender(JSONObject sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "PrivateMessageEvent(super=" + super.toString() + ", subType=" + this.getSubType() + ", message=" + this.getMessage() + ", rawMessage=" + this.getRawMessage() + ", font=" + this.getFont() + ", sender=" + this.getSender() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PrivateMessageEvent)) {
            return false;
        }
        PrivateMessageEvent other = (PrivateMessageEvent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$subType = this.getSubType();
        String other$subType = other.getSubType();
        if (this$subType == null ? other$subType != null : !this$subType.equals(other$subType)) {
            return false;
        }
        JSONArray this$message = this.getMessage();
        JSONArray other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) {
            return false;
        }
        String this$rawMessage = this.getRawMessage();
        String other$rawMessage = other.getRawMessage();
        if (this$rawMessage == null ? other$rawMessage != null : !this$rawMessage.equals(other$rawMessage)) {
            return false;
        }
        Integer this$font = this.getFont();
        Integer other$font = other.getFont();
        if (this$font == null ? other$font != null : !((Object)this$font).equals(other$font)) {
            return false;
        }
        JSONObject this$sender = this.getSender();
        JSONObject other$sender = other.getSender();
        return !(this$sender == null ? other$sender != null : !this$sender.equals(other$sender));
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof PrivateMessageEvent;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $subType = this.getSubType();
        result = result * 59 + ($subType == null ? 43 : $subType.hashCode());
        JSONArray $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        String $rawMessage = this.getRawMessage();
        result = result * 59 + ($rawMessage == null ? 43 : $rawMessage.hashCode());
        Integer $font = this.getFont();
        result = result * 59 + ($font == null ? 43 : ((Object)$font).hashCode());
        JSONObject $sender = this.getSender();
        result = result * 59 + ($sender == null ? 43 : $sender.hashCode());
        return result;
    }
}

