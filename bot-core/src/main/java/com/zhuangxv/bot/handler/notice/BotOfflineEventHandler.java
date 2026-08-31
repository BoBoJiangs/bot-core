//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.handler.notice;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.zhuangxv.bot.annotation.BotOfflineHandler;
import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.core.component.BotFactory;
import com.zhuangxv.bot.event.notice.BotOfflineEvent;
import com.zhuangxv.bot.handler.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotOfflineEventHandler implements EventHandler {
    private static final Logger log = LoggerFactory.getLogger(BotOfflineEventHandler.class);

    public void handle(JSONObject jsonObject, Bot bot) {
        if (BotOfflineEvent.isSupport(jsonObject)) {
            BotOfflineEvent botOfflineEvent = (BotOfflineEvent)jsonObject.toJavaObject(BotOfflineEvent.class, new JSONReader.Feature[0]);
            BotFactory.handleMethod(bot, botOfflineEvent, (handlerMethod) -> {
                if (!handlerMethod.getMethod().isAnnotationPresent(BotOfflineHandler.class)) {
                    return false;
                } else {
                    BotOfflineHandler botOfflineHandler = (BotOfflineHandler)handlerMethod.getMethod().getAnnotation(BotOfflineHandler.class);
                    return botOfflineHandler.bot() == 0L || botOfflineHandler.bot() == botOfflineEvent.getSelfId();
                }
            }, "notice");
        }

    }
}
