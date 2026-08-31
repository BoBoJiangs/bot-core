/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSONArray
 *  com.alibaba.fastjson2.JSONObject
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.zhuangxv.bot.core;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.zhuangxv.bot.api.ApiResult;
import com.zhuangxv.bot.api.BaseApi;
import com.zhuangxv.bot.api.support.Ban;
import com.zhuangxv.bot.api.support.ClickKeyboardButton;
import com.zhuangxv.bot.api.support.DeleteMsg;
import com.zhuangxv.bot.api.support.GetFriends;
import com.zhuangxv.bot.api.support.GetGroup;
import com.zhuangxv.bot.api.support.GetGroupMembers;
import com.zhuangxv.bot.api.support.GetGroups;
import com.zhuangxv.bot.api.support.GetLoginInfo;
import com.zhuangxv.bot.api.support.GetMemberInfo;
import com.zhuangxv.bot.api.support.GetMessage;
import com.zhuangxv.bot.api.support.GroupBan;
import com.zhuangxv.bot.api.support.SendGroupForwardMsg;
import com.zhuangxv.bot.api.support.SendGroupMsg;
import com.zhuangxv.bot.api.support.SendPrivateMsg;
import com.zhuangxv.bot.api.support.SendTempMsg;
import com.zhuangxv.bot.api.support.SetGroupAdmin;
import com.zhuangxv.bot.api.support.SetGroupCard;
import com.zhuangxv.bot.api.support.SetGroupSpecialTitle;
import com.zhuangxv.bot.api.support.UploadGroupFile;
import com.zhuangxv.bot.config.BotConfig;
import com.zhuangxv.bot.core.Friend;
import com.zhuangxv.bot.core.Group;
import com.zhuangxv.bot.core.Member;
import com.zhuangxv.bot.core.network.BotClient;
import com.zhuangxv.bot.exception.BotException;
import com.zhuangxv.bot.message.CacheMessage;
import com.zhuangxv.bot.message.MessageChain;
import com.zhuangxv.bot.message.MessageTypeHandle;
import com.zhuangxv.bot.message.support.ForwardNodeMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bot {
    private static final Logger log = LoggerFactory.getLogger(Bot.class);
    private final Map<Long, Friend> friends = new ConcurrentHashMap<Long, Friend>();
    private final Map<Long, Group> groups = new ConcurrentHashMap<Long, Group>();
    private final Map<Long, Map<Long, Member>> groupMembers = new ConcurrentHashMap<Long, Map<Long, Member>>();
    private final Map<String, Map<Integer, CacheMessage>> cacheMessageChain = new HashMap<String, Map<Integer, CacheMessage>>();
    private final Lock cacheMessageChainLock = new ReentrantLock();
    private final CompletableFuture<Long> completableFuture = new CompletableFuture();
    private final BotConfig botConfig;
    private final BotClient botClient;
    private long botId = 0L;
    private String botName;

    public long getBotId() {
        if (this.botId == 0L) {
            log.info("bot qq \u4e3a\u7a7a");
            this.flushBotInfo();
        }
        return this.botId;
    }

    public String getBotName() {
        return this.botName;
    }

    public Bot(BotConfig botConfig, BotClient botClient) {
        this.botConfig = botConfig;
        this.botClient = botClient;
    }

    public BotConfig getBotConfig() {
        return this.botConfig;
    }

    public BotClient getBotClient() {
        return this.botClient;
    }

    public CompletableFuture<Long> getCompletableFuture() {
        return this.completableFuture;
    }

    public void pushGroupCacheMessageChain(Long groupId, Integer messageId, CacheMessage cacheMessage) {
        this.pushCacheMessageChain("group", groupId, messageId, cacheMessage);
    }

    public void pushUserCacheMessageChain(Long userId, Integer messageId, CacheMessage cacheMessage) {
        this.pushCacheMessageChain("user", userId, messageId, cacheMessage);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void pushCacheMessageChain(String prefix, Long id, Integer messageId, CacheMessage cacheMessage) {
        this.cacheMessageChainLock.lock();
        try {
            new LinkedHashMap();
            Map messageChainMap = this.cacheMessageChain.computeIfAbsent(prefix + id, key -> new LinkedHashMap());
            messageChainMap.put(messageId, cacheMessage);
        }
        finally {
            this.cacheMessageChainLock.unlock();
        }
    }

    public List<CacheMessage> getGroupCacheMessageChain(Long groupId, Integer messageId, Integer size) {
        return this.getCacheMessageChain("group", groupId, messageId, size);
    }

    public List<CacheMessage> getUserCacheMessageChain(Long groupId, Integer messageId, Integer size) {
        return this.getCacheMessageChain("user", groupId, messageId, size);
    }

    private List<CacheMessage> getCacheMessageChain(String prefix, long id, Integer messageId, Integer size) {
        this.cacheMessageChainLock.lock();
        try {
            ArrayList<CacheMessage> result = new ArrayList<CacheMessage>();
            Map<Integer, CacheMessage> messageChainMap = this.cacheMessageChain.get(prefix + id);
            if (messageChainMap == null) {
                ArrayList<CacheMessage> messageIds;
                ArrayList<CacheMessage> var10;
                ArrayList<CacheMessage> arrayList = var10 = (messageIds = result);
                return arrayList;
            }
            if (messageChainMap.isEmpty()) {
                ArrayList<CacheMessage> messageIds;
                ArrayList<CacheMessage> var10;
                ArrayList<CacheMessage> var17;
                ArrayList<CacheMessage> arrayList = var17 = (var10 = (messageIds = result));
                return arrayList;
            }
            ArrayList<Integer> messageIds = new ArrayList<Integer>(messageChainMap.keySet());
            boolean find = false;
            int i = messageIds.size() - 1;
            while (true) {
                block14: {
                    ArrayList<CacheMessage> var13;
                    block13: {
                        if (i < 0) break block13;
                        Integer messageIdTemp = messageIds.get(i);
                        if (!find && messageId.equals(messageIdTemp)) {
                            find = true;
                        }
                        if (!find) break block14;
                        result.add(messageChainMap.get(messageIdTemp));
                        if (result.size() < size) break block14;
                    }
                    Collections.reverse(result);
                    ArrayList<CacheMessage> arrayList = var13 = result;
                    return arrayList;
                }
                --i;
            }
        }
        finally {
            this.cacheMessageChainLock.unlock();
        }
    }

    private JSONObject getObject(Object object) {
        if (!(object instanceof JSONObject)) {
            throw new BotException(String.format("[%s]\u8c03\u7528api\u5931\u8d25\uff1a\u89e3\u6790\u7ed3\u679c\u51fa\u9519\u3002", this.botName));
        }
        return (JSONObject)object;
    }

    private JSONArray getArray(Object object) {
        if (!(object instanceof JSONArray)) {
            throw new BotException(String.format("[%s]\u8c03\u7528api\u5931\u8d25\uff1a\u89e3\u6790\u7ed3\u679c\u51fa\u9519\u3002", this.botName));
        }
        return (JSONArray)object;
    }

    public void flushFriends() {
        log.debug(String.format("[%s]\u6b63\u5728\u5237\u65b0\u597d\u53cb\u5217\u8868.", this.botName));
        ApiResult apiResult = this.botClient.invokeApi(new GetFriends(), this);
        JSONArray resultArray = this.getArray(apiResult.getData());
        for (int i = 0; i < resultArray.size(); ++i) {
            JSONObject resultObject = resultArray.getJSONObject(i);
            long userId = resultObject.getLongValue("user_id");
            String nickname = resultObject.getString("nickname");
            String remark = resultObject.getString("remark");
            this.friends.put(userId, new Friend(userId, nickname, remark, this));
        }
        log.debug(String.format("[%s]\u5237\u65b0\u597d\u53cb\u5217\u8868\u5b8c\u6210,\u5171\u6709\u597d\u53cb%d\u4e2a.", this.botName, this.friends.size()));
    }

    public Collection<Group> flushGroups() {
        log.debug(String.format("[%s]\u6b63\u5728\u5237\u65b0\u7fa4\u5217\u8868.", this.botName));
        ApiResult apiResult = this.botClient.invokeApi(new GetGroups(), this);
        JSONArray resultArray = this.getArray(apiResult.getData());
        for (int i = 0; i < resultArray.size(); ++i) {
            JSONObject resultObject = resultArray.getJSONObject(i);
            long groupId = resultObject.getLongValue("group_id");
            String groupName = resultObject.getString("group_name");
            this.groups.put(groupId, new Group(groupId, groupName, this));
        }
        log.debug(String.format("[%s]\u5237\u65b0\u7fa4\u5217\u8868\u5b8c\u6210,\u5171\u6709\u7fa4%d\u4e2a.", this.botName, this.groups.size()));
        return this.groups.values();
    }

    public void flushGroupMembers(Group group) {
        ApiResult apiResult = this.botClient.invokeApi(new GetGroupMembers(group.getGroupId()), this);
        if (apiResult.getRetCode() != 1200) {
            JSONArray resultArray = this.getArray(apiResult.getData());
            Map members = this.groupMembers.computeIfAbsent(group.getGroupId(), key -> new ConcurrentHashMap());
            for (int i = 0; i < resultArray.size(); ++i) {
                JSONObject resultObject = resultArray.getJSONObject(i);
                long userId = resultObject.getLongValue("user_id");
                String nickname = resultObject.getString("nickname");
                String card = resultObject.getString("card");
                String sex = resultObject.getString("sex");
                int age = resultObject.getIntValue("age");
                String area = resultObject.getString("area");
                Date joinTime = resultObject.getDate("join_time");
                Date lastSentTime = resultObject.getDate("last_sent_time");
                String level = resultObject.getString("level");
                String role = resultObject.getString("role");
                boolean unfriendly = resultObject.getBoolean("unfriendly");
                String title = resultObject.getString("title");
                Date titleExpireTime = resultObject.getDate("title_expire_time");
                boolean cardChangeable = resultObject.getBoolean("card_changeable");
                members.put(userId, new Member(userId, group.getGroupId(), nickname, card, sex, age, area, joinTime, lastSentTime, level, role, unfriendly, title, titleExpireTime, cardChangeable, this));
            }
            log.debug(String.format("[%s]\u5237\u65b0\u7fa4%s\u7684\u6210\u5458\u5217\u8868\u5b8c\u6210,\u5171\u6709\u7fa4\u6210\u5458%d\u4e2a", this.botName, group.getGroupName(), members.size()));
        }
    }

    public boolean isFriend(long userId) throws InterruptedException, ExecutionException {
        if (!this.completableFuture.isDone()) {
            this.completableFuture.get();
        }
        return this.friends.containsKey(userId);
    }

    public Friend getFriend(long userId) {
        try {
            Friend friend;
            if (!this.completableFuture.isDone()) {
                this.completableFuture.get();
            }
            if ((friend = this.friends.get(userId)) == null) {
                ApiResult apiResult = this.botClient.invokeApi(new GetFriends(), this);
                JSONArray resultArray = this.getArray(apiResult.getData());
                for (int i = 0; i < resultArray.size(); ++i) {
                    JSONObject resultObject = resultArray.getJSONObject(i);
                    long userIdTemp = resultObject.getLongValue("user_id");
                    String nickname = resultObject.getString("nickname");
                    String remark = resultObject.getString("remark");
                    this.friends.put(userIdTemp, new Friend(userIdTemp, nickname, remark, this));
                }
                friend = this.friends.get(userId);
            }
            return friend;
        }
        catch (Exception var12) {
            return null;
        }
    }

    public Collection<Friend> getFriends() {
        try {
            if (!this.completableFuture.isDone()) {
                this.completableFuture.get();
            }
            return this.friends.values();
        }
        catch (Exception var2) {
            return null;
        }
    }

    public Group getGroup(long groupId) {
        try {
            Group group;
            if (!this.completableFuture.isDone()) {
                this.completableFuture.get();
            }
            if ((group = this.groups.get(groupId)) == null) {
                ApiResult apiResult = this.botClient.invokeApi(new GetGroup(groupId), this);
                JSONObject resultObject = this.getObject(apiResult.getData());
                String groupName = resultObject.getString("group_name");
                group = new Group(groupId, groupName, this);
                this.groups.put(groupId, group);
                return group;
            }
            return group;
        }
        catch (Exception var7) {
            return null;
        }
    }

    public Collection<Group> getGroups() {
        try {
            if (!this.completableFuture.isDone()) {
                this.completableFuture.get();
            }
            return this.groups.values();
        }
        catch (Exception var2) {
            return null;
        }
    }

    public Member getMember(long groupId, long userId) {
        try {
            Member member;
            Map<Long, Member> groupMembers;
            if (!this.completableFuture.isDone()) {
                this.completableFuture.get();
            }
            if ((groupMembers = this.groupMembers.get(groupId)) == null || groupMembers.isEmpty()) {
                this.flushGroupMembers(this.getGroup(groupId));
                groupMembers = this.groupMembers.get(groupId);
            }
            if ((member = groupMembers.get(userId)) == null) {
                ApiResult apiResult = this.botClient.invokeApi(new GetMemberInfo(groupId, userId), this);
                JSONObject resultObject = this.getObject(apiResult.getData());
                String nickname = resultObject.getString("nickname");
                String card = resultObject.getString("card");
                String sex = resultObject.getString("sex");
                int age = resultObject.getIntValue("age");
                String area = resultObject.getString("area");
                Date joinTime = resultObject.getDate("join_time");
                Date lastSentTime = resultObject.getDate("last_sent_time");
                String level = resultObject.getString("level");
                String role = resultObject.getString("role");
                boolean unfriendly = resultObject.getBoolean("unfriendly");
                String title = resultObject.getString("title");
                Date titleExpireTime = resultObject.getDate("title_expire_time");
                boolean cardChangeable = resultObject.getBoolean("card_changeable");
                member = new Member(userId, groupId, nickname, card, sex, age, area, joinTime, lastSentTime, level, role, unfriendly, title, titleExpireTime, cardChangeable, this);
                groupMembers.put(userId, member);
            }
            return member;
        }
        catch (Exception var22) {
            return null;
        }
    }

    public Collection<Member> getMembers(long groupId) {
        try {
            Map<Long, Member> groupMembers;
            if (!this.completableFuture.isDone()) {
                this.completableFuture.get();
            }
            if ((groupMembers = this.groupMembers.get(groupId)).isEmpty()) {
                this.flushGroupMembers(this.getGroup(groupId));
                groupMembers = this.groupMembers.get(groupId);
            }
            return groupMembers.values();
        }
        catch (Exception var4) {
            return null;
        }
    }

    public int sendGroupMessage(long groupId, MessageChain messageChain) {
        ApiResult apiResult = this.botClient.invokeApi(new SendGroupMsg(groupId, messageChain), this);
        return this.getObject(apiResult.getData()).getIntValue("message_id");
    }

    public int sendGroupForwardMessage(long groupId, List<ForwardNodeMessage> messageList) {
        ApiResult apiResult = this.botClient.invokeApi(new SendGroupForwardMsg(groupId, messageList), this);
        return this.getObject(apiResult.getData()).getIntValue("message_id");
    }

    public int sendTempMessage(long userId, long groupId, MessageChain messageChain) {
        ApiResult apiResult = this.botClient.invokeApi(new SendTempMsg(userId, groupId, messageChain), this);
        return this.getObject(apiResult.getData()).getIntValue("message_id");
    }

    public void groupBan(long groupId) {
        this.botClient.invokeApi(new GroupBan(groupId, true), this);
    }

    public void groupPardon(long groupId) {
        this.botClient.invokeApi(new GroupBan(groupId, false), this);
    }

    public void memberBan(long groupId, long userId, long duration) {
        this.botClient.invokeApi(new Ban(groupId, userId, duration), this);
    }

    public void memberPardon(long groupId, long userId) {
        this.botClient.invokeApi(new Ban(groupId, userId, 0L), this);
    }

    public int sendPrivateMessage(long userId, MessageChain messageChain) {
        ApiResult apiResult = this.botClient.invokeApi(new SendPrivateMsg(userId, messageChain), this);
        return this.getObject(apiResult.getData()).getIntValue("message_id");
    }

    public void deleteMsg(long messageId) {
        this.botClient.invokeApi(new DeleteMsg(messageId), this);
    }

    public void setGroupCard(long groupId, long userId, String card) {
        this.botClient.invokeApi(new SetGroupCard(groupId, userId, card), this);
    }

    public void setGroupSpecialTitle(long userId, String specialTitle, Number duration, long groupId) {
        this.botClient.invokeApi(new SetGroupSpecialTitle(userId, specialTitle, duration, groupId), this);
    }

    public void uploadGroupFile(long groupId, String file, String name, String folder) {
        this.botClient.invokeApi(new UploadGroupFile(groupId, file, name, folder), this);
    }

    public void setGroupAdmin(long userId, long groupId, boolean enable) {
        this.botClient.invokeApi(new SetGroupAdmin(userId, groupId, enable), this);
    }

    public void clickKeyboardButton(long groupId, String botAppid, String buttonId, String callbackData, String msgSeq) {
        this.botClient.invokeApi(new ClickKeyboardButton(groupId, botAppid, buttonId, callbackData, msgSeq), this);
    }

    public void flushBotInfo() {
        ApiResult apiResult = this.botClient.invokeApi(new GetLoginInfo(), this);
        if (apiResult != null) {
            JSONObject jsonObject = this.getObject(apiResult.getData());
            this.botId = jsonObject.getLongValue("user_id");
            this.botName = jsonObject.getString("nickname");
            LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>(){

                @Override
                protected boolean removeEldestEntry(Map.Entry eldest) {
                    return this.size() > 10;
                }
            };
        }
    }

    public MessageChain getMessage(int messageId) {
        ApiResult apiResult = this.botClient.invokeApi(new GetMessage(messageId), this);
        JSONArray jsonArray = this.getObject(apiResult.getData()).getJSONArray("message");
        MessageChain messageChain = new MessageChain();
        for (int i = 0; i < jsonArray.size(); ++i) {
            messageChain.add(MessageTypeHandle.getMessage(jsonArray.getJSONObject(i)));
        }
        return messageChain;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int cleanCacheMessageChain(int maxSizePerKey) {
        int totalRemoved = 0;
        this.cacheMessageChainLock.lock();
        try {
            int var12;
            int var5;
            for (Map.Entry<String, Map<Integer, CacheMessage>> entry : this.cacheMessageChain.entrySet()) {
                Map<Integer, CacheMessage> messageMap = entry.getValue();
                if (messageMap.size() <= maxSizePerKey) continue;
                ArrayList<Integer> sortedMessageIds = new ArrayList<Integer>(messageMap.keySet());
                Collections.sort(sortedMessageIds);
                int removeCount = messageMap.size() - maxSizePerKey;
                for (int i = 0; i < removeCount; ++i) {
                    messageMap.remove(sortedMessageIds.get(i));
                    ++totalRemoved;
                }
            }
            int n = var5 = (var12 = totalRemoved);
            return n;
        }
        finally {
            this.cacheMessageChainLock.unlock();
        }
    }

    public ApiResult invoke(BaseApi baseApi) {
        return this.botClient.invokeApi(baseApi, this);
    }
}

