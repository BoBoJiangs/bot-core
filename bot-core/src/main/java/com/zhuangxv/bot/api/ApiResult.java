/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.annotation.JSONField
 */
package com.zhuangxv.bot.api;

import com.alibaba.fastjson2.annotation.JSONField;

public class ApiResult {
    private String status;
    @JSONField(name="retcode")
    private int retCode;
    private Object data;
    private String message;
    private Object raw;
    private String wording;
    private String echo;

    public String getStatus() {
        return this.status;
    }

    public int getRetCode() {
        return this.retCode;
    }

    public Object getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getWording() {
        return this.wording;
    }

    public String getEcho() {
        return this.echo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public Object getRaw() {
        return this.raw;
    }

    public void setRaw(Object raw) {
        this.raw = raw;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ApiResult)) {
            return false;
        }
        ApiResult other = (ApiResult)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (!(this$status == null ? other$status == null : this$status.equals(other$status))) {
            return false;
        }
        if (this.getRetCode() != other.getRetCode()) {
            return false;
        }
        Object this$data = this.getData();
        Object other$data = other.getData();
        if (this$data == null ? other$data != null : !this$data.equals(other$data)) {
            return false;
        }
        String this$message = this.getMessage();
        String other$message = other.getMessage();
        if (!(this$message == null ? other$message == null : this$message.equals(other$message))) {
            return false;
        }
        String this$wording = this.getWording();
        String other$wording = other.getWording();
        if (this$wording == null ? other$wording != null : !this$wording.equals(other$wording)) {
            return false;
        }
        String this$echo = this.getEcho();
        String other$echo = other.getEcho();
        return !(this$echo == null ? other$echo != null : !this$echo.equals(other$echo));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ApiResult;
    }

    public int hashCode() {
        int result = 1;
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        result = result * 59 + this.getRetCode();
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        String $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        String $wording = this.getWording();
        result = result * 59 + ($wording == null ? 43 : $wording.hashCode());
        String $echo = this.getEcho();
        result = result * 59 + ($echo == null ? 43 : $echo.hashCode());
        return result;
    }

    public String toString() {
        return "ApiResult(status=" + this.getStatus() + ", retCode=" + this.getRetCode() + ", data=" + this.getData() + ", message=" + this.getMessage() + ", wording=" + this.getWording() + ", echo=" + this.getEcho() + ")";
    }
}

