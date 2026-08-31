//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.core.component;

import com.zhuangxv.bot.annotation.OnQQConnected;
import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.event.message.QQConnectedEvent;
import java.lang.reflect.Method;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class QQEventDispatcher {
    @EventListener
    public void handleQQConnected(QQConnectedEvent event) {
        Bot bot = event.getBot();

        for(Object bean : BotFactory.getApplicationContext().getBeansOfType(Object.class).values()) {
            for(Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(OnQQConnected.class)) {
                    try {
                        method.setAccessible(true);
                        if (method.getParameterCount() == 1 && method.getParameterTypes()[0] == Bot.class) {
                            method.invoke(bean, event.getBot());
                        } else {
                            method.invoke(bean);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
