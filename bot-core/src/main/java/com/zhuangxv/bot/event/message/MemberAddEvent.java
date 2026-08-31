/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSONObject
 *  com.alibaba.fastjson2.annotation.JSONField
 */
package com.zhuangxv.bot.event.message;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.zhuangxv.bot.event.BaseEvent;

public class MemberAddEvent
extends BaseEvent {
    @JSONField(name="notice_type")
    private String noticeType;
    @JSONField(name="group_id")
    private Long groupId;
    @JSONField(name="user_id")
    private Long userId;

    public static boolean isSupport(JSONObject jsonObject) {
        return "notice".equals(jsonObject.getString("post_type")) && "group_increase".equals(jsonObject.getString("notice_type"));
    }

    public String getNoticeType() {
        return this.noticeType;
    }

    public Long getGroupId() {
        return this.groupId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MemberAddEvent(super=" + super.toString() + ", noticeType=" + this.getNoticeType() + ", groupId=" + this.getGroupId() + ", userId=" + this.getUserId() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MemberAddEvent)) {
            return false;
        }
        MemberAddEvent other = (MemberAddEvent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$noticeType = this.getNoticeType();
        String other$noticeType = other.getNoticeType();
        if (this$noticeType == null ? other$noticeType != null : !this$noticeType.equals(other$noticeType)) {
            return false;
        }
        Long this$groupId = this.getGroupId();
        Long other$groupId = other.getGroupId();
        if (this$groupId == null ? other$groupId != null : !((Object)this$groupId).equals(other$groupId)) {
            return false;
        }
        Long this$userId = this.getUserId();
        Long other$userId = other.getUserId();
        return !(this$userId == null ? other$userId != null : !((Object)this$userId).equals(other$userId));
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof MemberAddEvent;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $noticeType = this.getNoticeType();
        result = result * 59 + ($noticeType == null ? 43 : $noticeType.hashCode());
        Long $groupId = this.getGroupId();
        result = result * 59 + ($groupId == null ? 43 : ((Object)$groupId).hashCode());
        Long $userId = this.getUserId();
        result = result * 59 + ($userId == null ? 43 : ((Object)$userId).hashCode());
        return result;
    }
}

