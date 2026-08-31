//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.event.notice;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.zhuangxv.bot.event.BaseEvent;

public class BotOfflineEvent extends BaseEvent {
    @JSONField(
        name = "time"
    )
    private Long time;
    @JSONField(
        name = "self_id"
    )
    private Long selfId;
    @JSONField(
        name = "post_type"
    )
    private String postType;
    @JSONField(
        name = "notice_type"
    )
    private String noticeType;
    @JSONField(
        name = "user_id"
    )
    private Long userId;
    @JSONField(
        name = "tag"
    )
    private String tag;
    @JSONField(
        name = "message"
    )
    private String message;

    public static boolean isSupport(JSONObject jsonObject) {
        return "notice".equals(jsonObject.getString("post_type")) && "bot_offline".equals(jsonObject.getString("notice_type"));
    }

    public Long getTime() {
        return this.time;
    }

    public Long getSelfId() {
        return this.selfId;
    }

    public String getPostType() {
        return this.postType;
    }

    public String getNoticeType() {
        return this.noticeType;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getTag() {
        return this.tag;
    }

    public String getMessage() {
        return this.message;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setSelfId(Long selfId) {
        this.selfId = selfId;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "BotOfflineEvent(super=" + super.toString() + ", time=" + this.getTime() + ", selfId=" + this.getSelfId() + ", postType=" + this.getPostType() + ", noticeType=" + this.getNoticeType() + ", userId=" + this.getUserId() + ", tag=" + this.getTag() + ", message=" + this.getMessage() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BotOfflineEvent)) {
            return false;
        } else {
            BotOfflineEvent other = (BotOfflineEvent)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$time = this.getTime();
                Object other$time = other.getTime();
                if (this$time == null) {
                    if (other$time != null) {
                        return false;
                    }
                } else if (!this$time.equals(other$time)) {
                    return false;
                }

                Object this$selfId = this.getSelfId();
                Object other$selfId = other.getSelfId();
                if (this$selfId == null) {
                    if (other$selfId != null) {
                        return false;
                    }
                } else if (!this$selfId.equals(other$selfId)) {
                    return false;
                }

                Object this$postType = this.getPostType();
                Object other$postType = other.getPostType();
                if (this$postType == null) {
                    if (other$postType != null) {
                        return false;
                    }
                } else if (!this$postType.equals(other$postType)) {
                    return false;
                }

                Object this$noticeType = this.getNoticeType();
                Object other$noticeType = other.getNoticeType();
                if (this$noticeType == null) {
                    if (other$noticeType != null) {
                        return false;
                    }
                } else if (!this$noticeType.equals(other$noticeType)) {
                    return false;
                }

                Object this$userId = this.getUserId();
                Object other$userId = other.getUserId();
                if (this$userId == null) {
                    if (other$userId != null) {
                        return false;
                    }
                } else if (!this$userId.equals(other$userId)) {
                    return false;
                }

                Object this$tag = this.getTag();
                Object other$tag = other.getTag();
                if (this$tag == null) {
                    if (other$tag != null) {
                        return false;
                    }
                } else if (!this$tag.equals(other$tag)) {
                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BotOfflineEvent;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Object $time = this.getTime();
        result = result * 59 + ($time == null ? 43 : $time.hashCode());
        Object $selfId = this.getSelfId();
        result = result * 59 + ($selfId == null ? 43 : $selfId.hashCode());
        Object $postType = this.getPostType();
        result = result * 59 + ($postType == null ? 43 : $postType.hashCode());
        Object $noticeType = this.getNoticeType();
        result = result * 59 + ($noticeType == null ? 43 : $noticeType.hashCode());
        Object $userId = this.getUserId();
        result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
        Object $tag = this.getTag();
        result = result * 59 + ($tag == null ? 43 : $tag.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        return result;
    }
}
