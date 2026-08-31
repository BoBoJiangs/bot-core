/*
 * Decompiled with CFR 0.152.
 */
package com.zhuangxv.bot.config;

public class BotConfig {
    private String type;
    private String url;
    private long groupId;
    private long taskId;
    private long masterQQ;
    private String controlQQ;
    private String groupQQ;
    private String accessToken;
    private boolean enableCheckPrice = false;
    private boolean enableGuessTheIdiom = false;
    private boolean enableCheckMarket = false;
    private boolean enableXslPriceQuery = false;
    private boolean enableAutoBuyLowPrice = false;
    private boolean enableSectMission = true;
    private boolean BUY_DELAY = true;
    private boolean isStartScheduled = true;
    private long lastSendTime = 0L;
    private long mjTime = -1L;
    private long xslTime = -1L;
    private int autoBuyHerbsMode = 0;
    private boolean isStartScheduledMarket = false;
    private boolean isStartScheduledEquip = false;
    private boolean isStartScheduledSkills = false;
    private boolean isStartScheduledHerbs = false;
    private int TaskStatusEquip = 1;
    private int TaskStatusSkills = 1;
    private int TaskStatusHerbs = 1;
    private int cultivationMode = 1;
    private boolean stop = false;
    private int FamilyTaskStatus = 0;
    private long lastRefreshTime = 0L;
    private long lastExecuteTime = 0L;
    private int sectMode = 1;
    private boolean enableAutoReward = false;
    private boolean isAutoField;
    private boolean isAutoSecret;
    private boolean enableAutoField = true;
    private boolean enableAutoSecret = true;
    private int rewardMode = 3;
    private String command;
    private boolean enableAutoRepair = false;
    private boolean isStartAutoLingG = false;
    private boolean enableSelfTitle = false;
    private boolean isStartAuto = false;
    private int botNumber = 0;
    private String aiCheng = "\u5080\u5121";
    private boolean enableXiaoBei = false;
    private int xslPriceLimit = 800;
    private int lingShiNum = 0;
    private Long lingShiQQ;
    private boolean enableForwardMessage = true;
    private int page = 1;
    private int shuangXuNumber = 30;
    private int remainingSxNumber;
    private boolean enableAutomaticReply;
    private int forwardMode = 1;
    private boolean enableAutoVerify = false;
    private boolean enableSavePic = false;
    private int autoVerifyModel = 0;
    private boolean enableAutoTask = false;
    private String verificationStatus;
    private String lastVerificationContent;
    private int frequency = 1;
    private String markItem;
    private long autoTaskRefreshTime;
    private int challengeMode = 0;
    private boolean enableXyHerbClass;
    private boolean enableAlchemy;
    private boolean enableAutoCqMj = false;
    private long lingShiTotal;

    public long getLingShiTotal() {
        return this.lingShiTotal;
    }

    public void setLingShiTotal(long lingShiTotal) {
        this.lingShiTotal = lingShiTotal;
    }

    public boolean isEnableAutoCqMj() {
        return this.enableAutoCqMj;
    }

    public void setEnableAutoCqMj(boolean enableAutoCqMj) {
        this.enableAutoCqMj = enableAutoCqMj;
    }

    public boolean isEnableAlchemy() {
        return this.enableAlchemy;
    }

    public void setEnableAlchemy(boolean enableAlchemy) {
        this.enableAlchemy = enableAlchemy;
    }

    public boolean isEnableXyHerbClass() {
        return this.enableXyHerbClass;
    }

    public void setEnableXyHerbClass(boolean enableXyHerbClass) {
        this.enableXyHerbClass = enableXyHerbClass;
    }

    public int getChallengeMode() {
        return this.challengeMode;
    }

    public void setChallengeMode(int challengeMode) {
        this.challengeMode = challengeMode;
    }

    public long getAutoTaskRefreshTime() {
        return this.autoTaskRefreshTime;
    }

    public void setAutoTaskRefreshTime(long autoTaskRefreshTime) {
        this.autoTaskRefreshTime = autoTaskRefreshTime;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getMarkItem() {
        return this.markItem;
    }

    public void setMarkItem(String markItem) {
        this.markItem = markItem;
    }

    public String getLastVerificationContent() {
        return this.lastVerificationContent;
    }

    public void setLastVerificationContent(String lastVerificationContent) {
        this.lastVerificationContent = lastVerificationContent;
    }

    public String getVerificationStatus() {
        return this.verificationStatus;
    }

    public String isVerificationStatus() {
        return this.verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public boolean isEnableAutoTask() {
        return this.enableAutoTask;
    }

    public void setEnableAutoTask(boolean enableAutoTask) {
        this.enableAutoTask = enableAutoTask;
    }

    public int getAutoVerifyModel() {
        return this.autoVerifyModel;
    }

    public void setAutoVerifyModel(int autoVerifyModel) {
        this.autoVerifyModel = autoVerifyModel;
    }

    public boolean isEnableSavePic() {
        return this.enableSavePic;
    }

    public void setEnableSavePic(boolean enableSavePic) {
        this.enableSavePic = enableSavePic;
    }

    public boolean isEnableAutoVerify() {
        return this.enableAutoVerify;
    }

    public void setEnableAutoVerify(boolean enableAutoVerify) {
        this.enableAutoVerify = enableAutoVerify;
    }

    public int getForwardMode() {
        return this.forwardMode;
    }

    public void setForwardMode(int forwardMode) {
        this.forwardMode = forwardMode;
    }

    public boolean isEnableAutomaticReply() {
        return this.enableAutomaticReply;
    }

    public void setEnableAutomaticReply(boolean enableAutomaticReply) {
        this.enableAutomaticReply = enableAutomaticReply;
    }

    public int getRemainingSxNumber() {
        return this.remainingSxNumber;
    }

    public void setRemainingSxNumber(int remainingSxNumber) {
        this.remainingSxNumber = remainingSxNumber;
    }

    public int getShuangXuNumber() {
        return this.shuangXuNumber;
    }

    public void setShuangXuNumber(int shuangXuNumber) {
        this.shuangXuNumber = shuangXuNumber;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isEnableForwardMessage() {
        return this.enableForwardMessage;
    }

    public void setEnableForwardMessage(boolean enableForwardMessage) {
        this.enableForwardMessage = enableForwardMessage;
    }

    public Long getLingShiQQ() {
        return this.lingShiQQ;
    }

    public void setLingShiQQ(Long lingShiQQ) {
        this.lingShiQQ = lingShiQQ;
    }

    public int getLingShiNum() {
        return this.lingShiNum;
    }

    public void setLingShiNum(int lingShiNum) {
        this.lingShiNum = lingShiNum;
    }

    public int getXslPriceLimit() {
        return this.xslPriceLimit;
    }

    public void setXslPriceLimit(int xslPriceLimit) {
        this.xslPriceLimit = xslPriceLimit;
    }

    public boolean isEnableXiaoBei() {
        return this.enableXiaoBei;
    }

    public void setEnableXiaoBei(boolean enableXiaoBei) {
        this.enableXiaoBei = enableXiaoBei;
    }

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public long getGroupId() {
        return this.groupId;
    }

    public long getTaskId() {
        return this.taskId;
    }

    public long getMasterQQ() {
        return this.masterQQ;
    }

    public String getControlQQ() {
        return this.controlQQ;
    }

    public String getGroupQQ() {
        return this.groupQQ;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public boolean isEnableCheckPrice() {
        return this.enableCheckPrice;
    }

    public boolean isEnableGuessTheIdiom() {
        return this.enableGuessTheIdiom;
    }

    public boolean isEnableCheckMarket() {
        return this.enableCheckMarket;
    }

    public boolean isEnableXslPriceQuery() {
        return this.enableXslPriceQuery;
    }

    public boolean isEnableAutoBuyLowPrice() {
        return this.enableAutoBuyLowPrice;
    }

    public boolean isEnableSectMission() {
        return this.enableSectMission;
    }

    public boolean isBUY_DELAY() {
        return this.BUY_DELAY;
    }

    public boolean isStartScheduled() {
        return this.isStartScheduled;
    }

    public long getLastSendTime() {
        return this.lastSendTime;
    }

    public long getMjTime() {
        return this.mjTime;
    }

    public long getXslTime() {
        return this.xslTime;
    }

    public boolean isStartScheduledMarket() {
        return this.isStartScheduledMarket;
    }

    public boolean isStartScheduledEquip() {
        return this.isStartScheduledEquip;
    }

    public boolean isStartScheduledSkills() {
        return this.isStartScheduledSkills;
    }

    public boolean isStartScheduledHerbs() {
        return this.isStartScheduledHerbs;
    }

    public int getTaskStatusEquip() {
        return this.TaskStatusEquip;
    }

    public int getTaskStatusSkills() {
        return this.TaskStatusSkills;
    }

    public int getTaskStatusHerbs() {
        return this.TaskStatusHerbs;
    }

    public int getCultivationMode() {
        return this.cultivationMode;
    }

    public boolean isStop() {
        return this.stop;
    }

    public int getFamilyTaskStatus() {
        return this.FamilyTaskStatus;
    }

    public long getLastRefreshTime() {
        return this.lastRefreshTime;
    }

    public long getLastExecuteTime() {
        return this.lastExecuteTime;
    }

    public int getSectMode() {
        return this.sectMode;
    }

    public boolean isEnableAutoReward() {
        return this.enableAutoReward;
    }

    public boolean isAutoField() {
        return this.isAutoField;
    }

    public boolean isAutoSecret() {
        return this.isAutoSecret;
    }

    public boolean isEnableAutoField() {
        return this.enableAutoField;
    }

    public boolean isEnableAutoSecret() {
        return this.enableAutoSecret;
    }

    public int getRewardMode() {
        return this.rewardMode;
    }

    public String getCommand() {
        return this.command;
    }

    public boolean isEnableAutoRepair() {
        return this.enableAutoRepair;
    }

    public boolean isStartAutoLingG() {
        return this.isStartAutoLingG;
    }

    public boolean isEnableSelfTitle() {
        return this.enableSelfTitle;
    }

    public boolean isStartAuto() {
        return this.isStartAuto;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getAiCheng() {
        return this.aiCheng;
    }

    public void setAiCheng(String aiCheng) {
        this.aiCheng = aiCheng;
    }

    public int getBotNumber() {
        return this.botNumber;
    }

    public void setBotNumber(int botNumber) {
        this.botNumber = botNumber;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public void setMasterQQ(long masterQQ) {
        this.masterQQ = masterQQ;
    }

    public void setControlQQ(String controlQQ) {
        this.controlQQ = controlQQ;
    }

    public void setGroupQQ(String groupQQ) {
        this.groupQQ = groupQQ;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setEnableCheckPrice(boolean enableCheckPrice) {
        this.enableCheckPrice = enableCheckPrice;
    }

    public void setEnableGuessTheIdiom(boolean enableGuessTheIdiom) {
        this.enableGuessTheIdiom = enableGuessTheIdiom;
    }

    public void setEnableCheckMarket(boolean enableCheckMarket) {
        this.enableCheckMarket = enableCheckMarket;
    }

    public void setEnableXslPriceQuery(boolean enableXslPriceQuery) {
        this.enableXslPriceQuery = enableXslPriceQuery;
    }

    public void setEnableAutoBuyLowPrice(boolean enableAutoBuyLowPrice) {
        this.enableAutoBuyLowPrice = enableAutoBuyLowPrice;
    }

    public void setEnableSectMission(boolean enableSectMission) {
        this.enableSectMission = enableSectMission;
    }

    public void setBUY_DELAY(boolean BUY_DELAY) {
        this.BUY_DELAY = BUY_DELAY;
    }

    public void setStartScheduled(boolean isStartScheduled) {
        this.isStartScheduled = isStartScheduled;
    }

    public void setLastSendTime(long lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    public void setMjTime(long mjTime) {
        this.mjTime = mjTime;
    }

    public void setXslTime(long xslTime) {
        this.xslTime = xslTime;
    }

    public int getAutoBuyHerbsMode() {
        return this.autoBuyHerbsMode;
    }

    public void setAutoBuyHerbsMode(int autoBuyHerbsMode) {
        this.autoBuyHerbsMode = autoBuyHerbsMode;
    }

    public void setStartScheduledMarket(boolean isStartScheduledMarket) {
        this.isStartScheduledMarket = isStartScheduledMarket;
    }

    public void setStartScheduledEquip(boolean isStartScheduledEquip) {
        this.isStartScheduledEquip = isStartScheduledEquip;
    }

    public void setStartScheduledSkills(boolean isStartScheduledSkills) {
        this.isStartScheduledSkills = isStartScheduledSkills;
    }

    public void setStartScheduledHerbs(boolean isStartScheduledHerbs) {
        this.isStartScheduledHerbs = isStartScheduledHerbs;
    }

    public void setTaskStatusEquip(int TaskStatusEquip) {
        this.TaskStatusEquip = TaskStatusEquip;
    }

    public void setTaskStatusSkills(int TaskStatusSkills) {
        this.TaskStatusSkills = TaskStatusSkills;
    }

    public void setTaskStatusHerbs(int TaskStatusHerbs) {
        this.TaskStatusHerbs = TaskStatusHerbs;
    }

    public void setCultivationMode(int cultivationMode) {
        this.cultivationMode = cultivationMode;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void setFamilyTaskStatus(int FamilyTaskStatus) {
        this.FamilyTaskStatus = FamilyTaskStatus;
    }

    public void setLastRefreshTime(long lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }

    public void setLastExecuteTime(long lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public void setSectMode(int sectMode) {
        this.sectMode = sectMode;
    }

    public void setEnableAutoReward(boolean enableAutoReward) {
        this.enableAutoReward = enableAutoReward;
    }

    public void setAutoField(boolean isAutoField) {
        this.isAutoField = isAutoField;
    }

    public void setAutoSecret(boolean isAutoSecret) {
        this.isAutoSecret = isAutoSecret;
    }

    public void setEnableAutoField(boolean enableAutoField) {
        this.enableAutoField = enableAutoField;
    }

    public void setEnableAutoSecret(boolean enableAutoSecret) {
        this.enableAutoSecret = enableAutoSecret;
    }

    public void setRewardMode(int rewardMode) {
        this.rewardMode = rewardMode;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setEnableAutoRepair(boolean enableAutoRepair) {
        this.enableAutoRepair = enableAutoRepair;
    }

    public void setStartAutoLingG(boolean isStartAutoLingG) {
        this.isStartAutoLingG = isStartAutoLingG;
    }

    public void setEnableSelfTitle(boolean enableSelfTitle) {
        this.enableSelfTitle = enableSelfTitle;
    }

    public void setStartAuto(boolean isStartAuto) {
        this.isStartAuto = isStartAuto;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BotConfig)) {
            return false;
        }
        BotConfig other = (BotConfig)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$url = this.getType();
        String other$url = other.getType();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) {
            return false;
        }
        this$url = this.getUrl();
        other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) {
            return false;
        }
        if (this.getGroupId() != other.getGroupId()) {
            return false;
        }
        if (this.getTaskId() != other.getTaskId()) {
            return false;
        }
        if (this.getMasterQQ() != other.getMasterQQ()) {
            return false;
        }
        String this$groupQQ = this.getControlQQ();
        String other$groupQQ = other.getControlQQ();
        if (this$groupQQ == null ? other$groupQQ != null : !this$groupQQ.equals(other$groupQQ)) {
            return false;
        }
        this$groupQQ = this.getGroupQQ();
        other$groupQQ = other.getGroupQQ();
        if (this$groupQQ == null ? other$groupQQ != null : !this$groupQQ.equals(other$groupQQ)) {
            return false;
        }
        String this$accessToken = this.getAccessToken();
        String other$accessToken = other.getAccessToken();
        if (this$accessToken == null ? other$accessToken != null : !this$accessToken.equals(other$accessToken)) {
            return false;
        }
        if (this.isEnableCheckPrice() != other.isEnableCheckPrice()) {
            return false;
        }
        if (this.isEnableGuessTheIdiom() != other.isEnableGuessTheIdiom()) {
            return false;
        }
        if (this.isEnableCheckMarket() != other.isEnableCheckMarket()) {
            return false;
        }
        if (this.isEnableXslPriceQuery() != other.isEnableXslPriceQuery()) {
            return false;
        }
        if (this.isEnableAutoBuyLowPrice() != other.isEnableAutoBuyLowPrice()) {
            return false;
        }
        if (this.isEnableSectMission() != other.isEnableSectMission()) {
            return false;
        }
        if (this.isBUY_DELAY() != other.isBUY_DELAY()) {
            return false;
        }
        if (this.isStartScheduled() != other.isStartScheduled()) {
            return false;
        }
        if (this.getLastSendTime() != other.getLastSendTime()) {
            return false;
        }
        if (this.getMjTime() != other.getMjTime()) {
            return false;
        }
        if (this.getXslTime() != other.getXslTime()) {
            return false;
        }
        if (this.isStartScheduledMarket() != other.isStartScheduledMarket()) {
            return false;
        }
        if (this.isStartScheduledEquip() != other.isStartScheduledEquip()) {
            return false;
        }
        if (this.isStartScheduledSkills() != other.isStartScheduledSkills()) {
            return false;
        }
        if (this.isStartScheduledHerbs() != other.isStartScheduledHerbs()) {
            return false;
        }
        if (this.getTaskStatusEquip() != other.getTaskStatusEquip()) {
            return false;
        }
        if (this.getTaskStatusSkills() != other.getTaskStatusSkills()) {
            return false;
        }
        if (this.getTaskStatusHerbs() != other.getTaskStatusHerbs()) {
            return false;
        }
        if (this.getCultivationMode() != other.getCultivationMode()) {
            return false;
        }
        if (this.isStop() != other.isStop()) {
            return false;
        }
        if (this.getFamilyTaskStatus() != other.getFamilyTaskStatus()) {
            return false;
        }
        if (this.getLastRefreshTime() != other.getLastRefreshTime()) {
            return false;
        }
        if (this.getLastExecuteTime() != other.getLastExecuteTime()) {
            return false;
        }
        if (this.getSectMode() != other.getSectMode()) {
            return false;
        }
        if (this.isEnableAutoReward() != other.isEnableAutoReward()) {
            return false;
        }
        if (this.isAutoField() != other.isAutoField()) {
            return false;
        }
        if (this.isAutoSecret() != other.isAutoSecret()) {
            return false;
        }
        if (this.isEnableAutoField() != other.isEnableAutoField()) {
            return false;
        }
        if (this.isEnableAutoSecret() != other.isEnableAutoSecret()) {
            return false;
        }
        if (this.getRewardMode() != other.getRewardMode()) {
            return false;
        }
        String this$command = this.getCommand();
        String other$command = other.getCommand();
        if (this$command == null ? other$command != null : !this$command.equals(other$command)) {
            return false;
        }
        if (this.isEnableAutoRepair() != other.isEnableAutoRepair()) {
            return false;
        }
        if (this.isStartAutoLingG() != other.isStartAutoLingG()) {
            return false;
        }
        if (this.isEnableSelfTitle() != other.isEnableSelfTitle()) {
            return false;
        }
        return this.isStartAuto() == other.isStartAuto();
    }

    protected boolean canEqual(Object other) {
        return other instanceof BotConfig;
    }

    public int hashCode() {
        int result = 1;
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        long $groupId = this.getGroupId();
        result = result * 59 + (int)($groupId >>> 32 ^ $groupId);
        long $taskId = this.getTaskId();
        result = result * 59 + (int)($taskId >>> 32 ^ $taskId);
        long $masterQQ = this.getMasterQQ();
        result = result * 59 + (int)($masterQQ >>> 32 ^ $masterQQ);
        String $controlQQ = this.getControlQQ();
        result = result * 59 + ($controlQQ == null ? 43 : $controlQQ.hashCode());
        String $groupQQ = this.getGroupQQ();
        result = result * 59 + ($groupQQ == null ? 43 : $groupQQ.hashCode());
        String $accessToken = this.getAccessToken();
        result = result * 59 + ($accessToken == null ? 43 : $accessToken.hashCode());
        result = result * 59 + (this.isEnableCheckPrice() ? 79 : 97);
        result = result * 59 + (this.isEnableGuessTheIdiom() ? 79 : 97);
        result = result * 59 + (this.isEnableCheckMarket() ? 79 : 97);
        result = result * 59 + (this.isEnableXslPriceQuery() ? 79 : 97);
        result = result * 59 + (this.isEnableAutoBuyLowPrice() ? 79 : 97);
        result = result * 59 + (this.isEnableSectMission() ? 79 : 97);
        result = result * 59 + (this.isBUY_DELAY() ? 79 : 97);
        result = result * 59 + (this.isStartScheduled() ? 79 : 97);
        long $lastSendTime = this.getLastSendTime();
        result = result * 59 + (int)($lastSendTime >>> 32 ^ $lastSendTime);
        long $mjTime = this.getMjTime();
        result = result * 59 + (int)($mjTime >>> 32 ^ $mjTime);
        long $xslTime = this.getXslTime();
        result = result * 59 + (int)($xslTime >>> 32 ^ $xslTime);
        result = result * 59 + (this.isStartScheduledMarket() ? 79 : 97);
        result = result * 59 + (this.isStartScheduledEquip() ? 79 : 97);
        result = result * 59 + (this.isStartScheduledSkills() ? 79 : 97);
        result = result * 59 + (this.isStartScheduledHerbs() ? 79 : 97);
        result = result * 59 + this.getTaskStatusEquip();
        result = result * 59 + this.getTaskStatusSkills();
        result = result * 59 + this.getTaskStatusHerbs();
        result = result * 59 + this.getCultivationMode();
        result = result * 59 + (this.isStop() ? 79 : 97);
        result = result * 59 + this.getFamilyTaskStatus();
        long $lastRefreshTime = this.getLastRefreshTime();
        result = result * 59 + (int)($lastRefreshTime >>> 32 ^ $lastRefreshTime);
        long $lastExecuteTime = this.getLastExecuteTime();
        result = result * 59 + (int)($lastExecuteTime >>> 32 ^ $lastExecuteTime);
        result = result * 59 + this.getSectMode();
        result = result * 59 + (this.isEnableAutoReward() ? 79 : 97);
        result = result * 59 + (this.isAutoField() ? 79 : 97);
        result = result * 59 + (this.isAutoSecret() ? 79 : 97);
        result = result * 59 + (this.isEnableAutoField() ? 79 : 97);
        result = result * 59 + (this.isEnableAutoSecret() ? 79 : 97);
        result = result * 59 + this.getRewardMode();
        String $command = this.getCommand();
        result = result * 59 + ($command == null ? 43 : $command.hashCode());
        result = result * 59 + (this.isEnableAutoRepair() ? 79 : 97);
        result = result * 59 + (this.isStartAutoLingG() ? 79 : 97);
        result = result * 59 + (this.isEnableSelfTitle() ? 79 : 97);
        result = result * 59 + (this.isStartAuto() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "BotConfig(type=" + this.getType() + ", url=" + this.getUrl() + ", groupId=" + this.getGroupId() + ", taskId=" + this.getTaskId() + ", masterQQ=" + this.getMasterQQ() + ", controlQQ=" + this.getControlQQ() + ", groupQQ=" + this.getGroupQQ() + ", accessToken=" + this.getAccessToken() + ", enableCheckPrice=" + this.isEnableCheckPrice() + ", enableGuessTheIdiom=" + this.isEnableGuessTheIdiom() + ", enableCheckMarket=" + this.isEnableCheckMarket() + ", enableXslPriceQuery=" + this.isEnableXslPriceQuery() + ", enableAutoBuyLowPrice=" + this.isEnableAutoBuyLowPrice() + ", enableSectMission=" + this.isEnableSectMission() + ", BUY_DELAY=" + this.isBUY_DELAY() + ", isStartScheduled=" + this.isStartScheduled() + ", lastSendTime=" + this.getLastSendTime() + ", mjTime=" + this.getMjTime() + ", xslTime=" + this.getXslTime() + ", isStartScheduledMarket=" + this.isStartScheduledMarket() + ", isStartScheduledEquip=" + this.isStartScheduledEquip() + ", isStartScheduledSkills=" + this.isStartScheduledSkills() + ", isStartScheduledHerbs=" + this.isStartScheduledHerbs() + ", TaskStatusEquip=" + this.getTaskStatusEquip() + ", TaskStatusSkills=" + this.getTaskStatusSkills() + ", TaskStatusHerbs=" + this.getTaskStatusHerbs() + ", cultivationMode=" + this.getCultivationMode() + ", stop=" + this.isStop() + ", FamilyTaskStatus=" + this.getFamilyTaskStatus() + ", lastRefreshTime=" + this.getLastRefreshTime() + ", lastExecuteTime=" + this.getLastExecuteTime() + ", sectMode=" + this.getSectMode() + ", enableAutoReward=" + this.isEnableAutoReward() + ", isAutoField=" + this.isAutoField() + ", isAutoSecret=" + this.isAutoSecret() + ", enableAutoField=" + this.isEnableAutoField() + ", enableAutoSecret=" + this.isEnableAutoSecret() + ", rewardMode=" + this.getRewardMode() + ", command=" + this.getCommand() + ", enableAutoRepair=" + this.isEnableAutoRepair() + ", isStartAutoLingG=" + this.isStartAutoLingG() + ", enableSelfTitle=" + this.isEnableSelfTitle() + ", isStartAuto=" + this.isStartAuto() + ")";
    }
}

