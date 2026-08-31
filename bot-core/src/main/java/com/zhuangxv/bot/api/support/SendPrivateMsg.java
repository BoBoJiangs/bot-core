/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSON
 *  com.alibaba.fastjson2.JSONArray
 *  com.alibaba.fastjson2.annotation.JSONField
 */
package com.zhuangxv.bot.api.support;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.annotation.JSONField;
import com.zhuangxv.bot.api.BaseApi;
import com.zhuangxv.bot.message.MessageChain;

public class SendPrivateMsg
extends BaseApi {
    private final Param param = new Param();

    public SendPrivateMsg(long userId, MessageChain messageChain) {
        this.param.setUserId(userId);
        this.param.setMessage(JSON.parseArray((String)messageChain.toMessageString()));
        this.param.setAutoEscape(false);
    }

    public SendPrivateMsg(long userId, MessageChain messageChain, boolean autoEscape) {
        this.param.setUserId(userId);
        this.param.setMessage(JSON.parseArray((String)messageChain.toMessageString()));
        this.param.setAutoEscape(autoEscape);
    }

    @Override
    public boolean needSleep() {
        return true;
    }

    @Override
    public String getAction() {
        return "send_private_msg";
    }

    @Override
    public Object getParams() {
        return this.param;
    }

    public static class Param {
        @JSONField(name="user_id")
        private long userId;
        @JSONField(name="message")
        private JSONArray message;
        @JSONField(name="auto_escape")
        private boolean autoEscape;

        public long getUserId() {
            return this.userId;
        }

        public JSONArray getMessage() {
            return this.message;
        }

        public boolean isAutoEscape() {
            return this.autoEscape;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public void setMessage(JSONArray message) {
            this.message = message;
        }

        public void setAutoEscape(boolean autoEscape) {
            this.autoEscape = autoEscape;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Param)) {
                return false;
            }
            Param other = (Param)o;
            if (!other.canEqual(this)) {
                return false;
            }
            if (this.getUserId() != other.getUserId()) {
                return false;
            }
            JSONArray this$message = this.getMessage();
            JSONArray other$message = other.getMessage();
            if (this$message == null ? other$message != null : !this$message.equals(other$message)) {
                return false;
            }
            return this.isAutoEscape() == other.isAutoEscape();
        }

        protected boolean canEqual(Object other) {
            return other instanceof Param;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            long $userId = this.getUserId();
            result = result * 59 + (int)($userId >>> 32 ^ $userId);
            JSONArray $message = this.getMessage();
            result = result * 59 + ($message == null ? 43 : $message.hashCode());
            result = result * 59 + (this.isAutoEscape() ? 79 : 97);
            return result;
        }

        public String toString() {
            return "SendPrivateMsg.Param(userId=" + this.getUserId() + ", message=" + this.getMessage() + ", autoEscape=" + this.isAutoEscape() + ")";
        }

        private Param() {
        }
    }
}

