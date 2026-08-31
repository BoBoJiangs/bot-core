//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.injector.support;

import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.event.BaseEvent;
import com.zhuangxv.bot.event.notice.BotOfflineEvent;
import com.zhuangxv.bot.injector.ObjectInjector;

public class BotOfflineEventInjector implements ObjectInjector<BotOfflineEvent> {
    public Class<BotOfflineEvent> getClassType() {
        return BotOfflineEvent.class;
    }

    public String[] getType() {
        return new String[]{"notice"};
    }

    public BotOfflineEvent getObject(BaseEvent event, Bot bot) {
        return event instanceof BotOfflineEvent ? (BotOfflineEvent)event : null;
    }
}
