//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.api.support;

import com.alibaba.fastjson2.annotation.JSONField;
import com.zhuangxv.bot.api.BaseApi;

public class ClickKeyboardButton extends BaseApi {
    private final Param param = new Param();

    public ClickKeyboardButton(long groupId, String botAppid, String buttonId, String callbackData, String msgSeq) {
        this.param.setGroupId(groupId);
        this.param.setBotAppid(botAppid);
        this.param.setButtonId(buttonId);
        this.param.setCallbackData(callbackData);
        this.param.setMsgSeq(msgSeq);
    }

    public String getAction() {
        return "click_inline_keyboard_button";
    }

    public Object getParams() {
        return this.param;
    }

    public static class Param {
        @JSONField(
            name = "group_id"
        )
        private long groupId;
        @JSONField(
            name = "bot_appid"
        )
        private String botAppid;
        @JSONField(
            name = "button_id"
        )
        private String buttonId;
        @JSONField(
            name = "callback_data"
        )
        private String callbackData;
        @JSONField(
            name = "msg_seq"
        )
        private String msgSeq;

        public long getGroupId() {
            return this.groupId;
        }

        public void setGroupId(long groupId) {
            this.groupId = groupId;
        }

        public String getBotAppid() {
            return this.botAppid;
        }

        public void setBotAppid(String botAppid) {
            this.botAppid = botAppid;
        }

        public String getButtonId() {
            return this.buttonId;
        }

        public void setButtonId(String buttonId) {
            this.buttonId = buttonId;
        }

        public String getCallbackData() {
            return this.callbackData;
        }

        public void setCallbackData(String callbackData) {
            this.callbackData = callbackData;
        }

        public String getMsgSeq() {
            return this.msgSeq;
        }

        public void setMsgSeq(String msgSeq) {
            this.msgSeq = msgSeq;
        }

        private Param() {
        }
    }
}
