/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSONObject
 *  com.alibaba.fastjson2.annotation.JSONField
 */
package com.zhuangxv.bot.event.meta;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.zhuangxv.bot.event.BaseEvent;

public class HeartbeatEvent
extends BaseEvent {
    @JSONField(name="meta_event_type")
    private String metaEventType;
    @JSONField(name="interval")
    private Long interval;

    public static boolean isSupport(JSONObject jsonObject) {
        return "meta_event".equals(jsonObject.getString("post_type")) && "heartbeat".equals(jsonObject.getString("meta_event_type"));
    }

    public String getMetaEventType() {
        return this.metaEventType;
    }

    public Long getInterval() {
        return this.interval;
    }

    public void setMetaEventType(String metaEventType) {
        this.metaEventType = metaEventType;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "HeartbeatEvent(super=" + super.toString() + ", metaEventType=" + this.getMetaEventType() + ", interval=" + this.getInterval() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HeartbeatEvent)) {
            return false;
        }
        HeartbeatEvent other = (HeartbeatEvent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$metaEventType = this.getMetaEventType();
        String other$metaEventType = other.getMetaEventType();
        if (this$metaEventType == null ? other$metaEventType != null : !this$metaEventType.equals(other$metaEventType)) {
            return false;
        }
        Long this$interval = this.getInterval();
        Long other$interval = other.getInterval();
        return !(this$interval == null ? other$interval != null : !((Object)this$interval).equals(other$interval));
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof HeartbeatEvent;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $metaEventType = this.getMetaEventType();
        result = result * 59 + ($metaEventType == null ? 43 : $metaEventType.hashCode());
        Long $interval = this.getInterval();
        result = result * 59 + ($interval == null ? 43 : ((Object)$interval).hashCode());
        return result;
    }
}

