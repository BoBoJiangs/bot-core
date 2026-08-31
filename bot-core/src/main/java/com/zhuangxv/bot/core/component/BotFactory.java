/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.boot.context.properties.bind.Bindable
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.PropertySources
 *  org.springframework.util.ClassUtils
 */
package com.zhuangxv.bot.core.component;

import com.zhuangxv.bot.annotation.BotOfflineHandler;
import com.zhuangxv.bot.annotation.FriendMessageHandler;
import com.zhuangxv.bot.annotation.GroupMessageHandler;
import com.zhuangxv.bot.annotation.GroupRecallHandler;
import com.zhuangxv.bot.annotation.MemberAddHandler;
import com.zhuangxv.bot.annotation.TempMessageHandler;
import com.zhuangxv.bot.config.BotConfig;
import com.zhuangxv.bot.config.PropertySourcesUtils;
import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.core.component.BotDispatcher;
import com.zhuangxv.bot.core.component.HandlerMethod;
import com.zhuangxv.bot.core.network.BotNetworkFactory;
import com.zhuangxv.bot.event.BaseEvent;
import com.zhuangxv.bot.exception.BotException;
import com.zhuangxv.bot.injector.ObjectInjector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySources;
import org.springframework.util.ClassUtils;

public class BotFactory
implements ApplicationContextAware,
DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(BotFactory.class);
    private static final Map<Long, Bot> bots = new HashMap<Long, Bot>();
    private static ConfigurableEnvironment environment;
    private static ConfigurableApplicationContext applicationContext;
    private static final List<HandlerMethod> handlerMethodList;
    private static Map<String, Map<Class<?>, ObjectInjector<?>>> objectInjectorMap;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (BotFactory.applicationContext == null && applicationContext instanceof ConfigurableApplicationContext) {
            BotFactory.applicationContext = (ConfigurableApplicationContext)applicationContext;
        }
    }

    public static void setEnvironment(ConfigurableEnvironment environment) {
        BotFactory.environment = environment;
    }

    public void destroy() {
        log.info("clear ApplicationContext:" + applicationContext);
        applicationContext = null;
    }

    public static void initHandlerMethod() {
        Map beans = BotFactory.getApplicationContext().getBeansOfType(Object.class);
        for (Object bean : beans.values()) {
            final Class beanClass = ClassUtils.getUserClass(bean);
            Set<Method> methodSet = Arrays.stream(beanClass.getMethods()).filter(method -> method.isAnnotationPresent(GroupMessageHandler.class) || method.isAnnotationPresent(TempMessageHandler.class) || method.isAnnotationPresent(FriendMessageHandler.class) || method.isAnnotationPresent(GroupRecallHandler.class) || method.isAnnotationPresent(MemberAddHandler.class) || method.isAnnotationPresent(BotOfflineHandler.class)).collect(Collectors.toSet());
            methodSet.forEach(method -> {
                HandlerMethod handlerMethod = new HandlerMethod() {
                    {
                        setType(beanClass);
                        setMethod(method);
                        setObject(bean);
                    }
                };
                handlerMethodList.add(handlerMethod);
            });
        }
        objectInjectorMap = new HashMap();
        Map<String, ObjectInjector> objectInjectors = BotFactory.getBeansByClass(ObjectInjector.class);
        if (objectInjectors != null) {
            for (ObjectInjector objectInjector : objectInjectors.values()) {
                for (String type : objectInjector.getType()) {
                    Map objectInjectorMapTemp = objectInjectorMap.computeIfAbsent(type, key -> new HashMap());
                    objectInjectorMapTemp.put(objectInjector.getClassType(), objectInjector);
                }
            }
        }
        log.info("\u4e8b\u4ef6\u5904\u7406\u5668\u521d\u59cb\u5316\u5b8c\u6210.");
    }

    public static void initBot() {
        BotDispatcher botDispatcher = BotFactory.getBeanByClass(BotDispatcher.class);
        if (botDispatcher == null) {
            throw new BotException("BotDispatcher\u521d\u59cb\u5316\u5931\u8d25");
        }
        String configKey = "bot";
        List<BotConfig> botConfigs = null;
        if (!PropertySourcesUtils.getPrefixedProperties((PropertySources)environment.getPropertySources(), configKey).isEmpty() || !PropertySourcesUtils.getPrefixedProperties((PropertySources)environment.getPropertySources(), configKey + "[0]").isEmpty()) {
            Binder binder = Binder.get((Environment)environment);
            if (!PropertySourcesUtils.getPrefixedProperties((PropertySources)environment.getPropertySources(), configKey + "[0]").isEmpty()) {
                botConfigs = (List)binder.bind(configKey, Bindable.listOf(BotConfig.class)).get();
            } else {
                botConfigs = new ArrayList<BotConfig>();
                botConfigs.add((BotConfig)binder.bind(configKey, Bindable.of(BotConfig.class)).get());
            }
        }
        if (botConfigs != null && !botConfigs.isEmpty()) {
            BotNetworkFactory.initBotNetwork(botConfigs, bots, botDispatcher);
        }
    }

    public static void addBot(BotConfig botConfig) {
        ArrayList<BotConfig> botConfigs = new ArrayList<BotConfig>();
        botConfigs.add(botConfig);
        BotFactory.addBot(botConfigs);
    }

    public static void removeBot(Bot bot) {
        bot.getBotClient().close();
    }

    public static void addBot(List<BotConfig> botConfigs) {
        BotDispatcher botDispatcher = BotFactory.getBeanByClass(BotDispatcher.class);
        if (botDispatcher == null) {
            throw new BotException("BotDispatcher\u521d\u59cb\u5316\u5931\u8d25");
        }
        BotNetworkFactory.initBotNetwork(botConfigs, bots, botDispatcher);
    }

    public static Set<HandlerMethod> getHandlerMethodListByAnnotation(Predicate<? super HandlerMethod> predicate) {
        return handlerMethodList.isEmpty() ? new HashSet<HandlerMethod>() : handlerMethodList.stream().filter(predicate).collect(Collectors.toSet());
    }

    public static List<Object> handleMethod(Bot bot, BaseEvent event, Predicate<? super HandlerMethod> predicate, String objectInjectorType) {
        ArrayList<Object> resultList = new ArrayList<Object>();
        for (HandlerMethod handlerMethod : BotFactory.getHandlerMethodListByAnnotation(predicate)) {
            Class<?>[] parameterTypes = handlerMethod.getMethod().getParameterTypes();
            Object[] objects = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; ++i) {
                ObjectInjector<?> objectInjector;
                Class<?> parameterType = parameterTypes[i];
                ObjectInjector<?> objectInjector2 = objectInjector = objectInjectorMap.get(objectInjectorType) != null ? objectInjectorMap.get(objectInjectorType).get(parameterType) : null;
                if (objectInjector == null) {
                    objectInjector = objectInjectorMap.get("all") != null ? objectInjectorMap.get("all").get(parameterType) : null;
                }
                objects[i] = objectInjector == null ? null : objectInjector.getObject(event, bot);
            }
            try {
                resultList.add(handlerMethod.getMethod().invoke(handlerMethod.getObject(), objects));
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause == null) {
                    log.error(e.getMessage(), (Throwable)e);
                } else {
                    log.error(cause.getMessage(), cause);
                }
                return new ArrayList<Object>();
            }
        }
        return resultList;
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBeanByClass(Class<T> clazz) {
        return (T)(applicationContext == null ? null : applicationContext.getBean(clazz));
    }

    public static <T> Map<String, T> getBeansByClass(Class<T> tClass) {
        return applicationContext == null ? null : applicationContext.getBeansOfType(tClass);
    }

    public static Map<Long, Bot> getBots() {
        return bots;
    }

    static {
        handlerMethodList = new ArrayList<HandlerMethod>();
    }
}

