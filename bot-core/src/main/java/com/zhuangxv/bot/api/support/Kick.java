package com.zhuangxv.bot.api.support;

import com.alibaba.fastjson2.annotation.JSONField;
import com.zhuangxv.bot.api.BaseApi;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:
 * @description:
 * @date: 2021/9/21 17:03
 */
public class Kick extends BaseApi {

    private final Kick.Param param;

    public Kick(long groupId, long userId) {
        this.param = new Kick.Param();
        this.param.setGroupId(groupId);
        this.param.setUserId(userId);
        this.param.setReject(false);
    }

    @Override
    public String getAction() {
        return "set_group_kick";
    }

    @Override
    public Object getParams() {
        return param;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Param {

        @JSONField(name = "group_id")
        private long groupId;

        @JSONField(name = "user_id")
        private long userId;
        @JSONField(name = "reject_add_request")
        private boolean reject;
    }
}
