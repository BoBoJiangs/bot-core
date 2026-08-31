//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Buttons implements Serializable {
    private List<Button> buttonList = new ArrayList();
    private String botAppid;
    private String msgSeq;
    private long groupId;
    private String imageText;
    private String imageUrl;

    public String getImageText() {
        return this.imageText;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getMsgSeq() {
        return this.msgSeq;
    }

    public void setMsgSeq(String msgSeq) {
        this.msgSeq = msgSeq;
    }

    public List<Button> getButtonList() {
        return this.buttonList;
    }

    public void setButtonList(List<Button> buttonList) {
        this.buttonList = buttonList;
    }

    public void addButtonList(List<Button> buttonList) {
        this.buttonList.addAll(buttonList);
    }

    public String getBotAppid() {
        return this.botAppid;
    }

    public void setBotAppid(String botAppid) {
        this.botAppid = botAppid;
    }
}
