//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.event.message;

import com.zhuangxv.bot.core.Bot;
import org.springframework.context.ApplicationEvent;

public class QQConnectedEvent extends ApplicationEvent {
    private final Bot bot;

    public QQConnectedEvent(Object source, Bot bot) {
        super(source);
        this.bot = bot;
    }

    public Bot getBot() {
        return this.bot;
    }
}
