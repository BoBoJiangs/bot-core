/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSON
 */
package com.zhuangxv.bot.message.support;

import com.alibaba.fastjson2.JSON;
import com.zhuangxv.bot.message.Message;

public class ImageMessage
implements Message {
    private String file;
    private String type;
    private String url;
    private Integer id;
    private Integer cache;

    public ImageMessage(String file) {
        this.file = file;
    }

    public String toString() {
        return "image[" + this.file + "]";
    }

    @Override
    public String toMessageString() {
        return String.format("{\"type\":\"%s\",\"data\":%s}", "image", JSON.toJSONString((Object)this));
    }

    public String getFile() {
        return this.file;
    }

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getCache() {
        return this.cache;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCache(Integer cache) {
        this.cache = cache;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ImageMessage)) {
            return false;
        }
        ImageMessage other = (ImageMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$file = this.getFile();
        String other$file = other.getFile();
        if (this$file == null ? other$file != null : !this$file.equals(other$file)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        String this$url = this.getUrl();
        String other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) {
            return false;
        }
        Integer this$id = this.getId();
        Integer other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Integer this$cache = this.getCache();
        Integer other$cache = other.getCache();
        return !(this$cache == null ? other$cache != null : !((Object)this$cache).equals(other$cache));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ImageMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $file = this.getFile();
        result = result * 59 + ($file == null ? 43 : $file.hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Integer $cache = this.getCache();
        result = result * 59 + ($cache == null ? 43 : ((Object)$cache).hashCode());
        return result;
    }

    public ImageMessage() {
    }
}

