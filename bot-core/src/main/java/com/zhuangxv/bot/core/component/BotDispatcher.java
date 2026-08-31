//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.core.component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.core.network.ws.WsBotClient;
import com.zhuangxv.bot.handler.EventHandler;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BotDispatcher {
    private static final Logger log = LoggerFactory.getLogger(BotDispatcher.class);
    private final Map<String, EventHandler> eventHandlerMap;
    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void handle(String message) {
        try {
            JSONObject jsonObject = JSON.parseObject(message);
            if (jsonObject.containsKey("echo") && jsonObject.containsKey("status") && jsonObject.containsKey("retcode") && jsonObject.containsKey("data")) {
                WsBotClient.handleWebSocketResponse(message);
                return;
            }

            Bot bot = (Bot)BotFactory.getBots().get(jsonObject.getLong("self_id"));
            if (bot == null) {
                return;
            }

            this.executorService.submit(() -> {
                try {
                    for(EventHandler eventHandler : this.eventHandlerMap.values()) {
                        eventHandler.handle(jsonObject, bot);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    @Autowired
    public BotDispatcher(Map<String, EventHandler> eventHandlerMap) {
        this.eventHandlerMap = eventHandlerMap;
    }
}
