/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.annotation.JSONField
 */
package com.zhuangxv.bot.event;

import com.alibaba.fastjson2.annotation.JSONField;

public class BaseEvent {
    @JSONField(name="post_type")
    private String postType;
    @JSONField(name="self_id")
    private Long selfId;
    @JSONField(name="time")
    private Long time;

    public String getPostType() {
        return this.postType;
    }

    public Long getSelfId() {
        return this.selfId;
    }

    public Long getTime() {
        return this.time;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public void setSelfId(Long selfId) {
        this.selfId = selfId;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseEvent)) {
            return false;
        }
        BaseEvent other = (BaseEvent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$postType = this.getPostType();
        String other$postType = other.getPostType();
        if (this$postType == null ? other$postType != null : !this$postType.equals(other$postType)) {
            return false;
        }
        Long this$selfId = this.getSelfId();
        Long other$selfId = other.getSelfId();
        if (this$selfId == null ? other$selfId != null : !((Object)this$selfId).equals(other$selfId)) {
            return false;
        }
        Long this$time = this.getTime();
        Long other$time = other.getTime();
        return !(this$time == null ? other$time != null : !((Object)this$time).equals(other$time));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseEvent;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $postType = this.getPostType();
        result = result * 59 + ($postType == null ? 43 : $postType.hashCode());
        Long $selfId = this.getSelfId();
        result = result * 59 + ($selfId == null ? 43 : ((Object)$selfId).hashCode());
        Long $time = this.getTime();
        result = result * 59 + ($time == null ? 43 : ((Object)$time).hashCode());
        return result;
    }

    public String toString() {
        return "BaseEvent(postType=" + this.getPostType() + ", selfId=" + this.getSelfId() + ", time=" + this.getTime() + ")";
    }
}

