//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.api.support;

import com.alibaba.fastjson2.annotation.JSONField;
import com.zhuangxv.bot.api.BaseApi;

public class SetGroupAdmin extends BaseApi {
    private final Param param = new Param();

    public SetGroupAdmin(long userId, long groupId, boolean enable) {
        this.param.setUserId(userId);
        this.param.setEnable(enable);
        this.param.setGroupId(groupId);
    }

    public String getAction() {
        return "set_group_admin";
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
            name = "user_id"
        )
        private long userId;
        @JSONField(
            name = "enable"
        )
        private boolean enable;

        public long getGroupId() {
            return this.groupId;
        }

        public void setGroupId(long groupId) {
            this.groupId = groupId;
        }

        public long getUserId() {
            return this.userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public boolean isEnable() {
            return this.enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }
}
