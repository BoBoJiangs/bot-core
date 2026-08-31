/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.GenericBeanDefinition
 *  org.springframework.context.annotation.AnnotatedBeanDefinitionReader
 *  org.springframework.util.ObjectUtils
 */
package com.zhuangxv.bot.util;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.util.ObjectUtils;

public class BeanRegistryUtils {
    private static final Logger log = LoggerFactory.getLogger(BeanRegistryUtils.class);

    public static void registerBeans(BeanDefinitionRegistry registry, Class<?> ... annotatedClasses) {
        if (!ObjectUtils.isEmpty((Object[])annotatedClasses)) {
            boolean debugEnabled = log.isDebugEnabled();
            AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(registry);
            if (debugEnabled) {
                log.debug(registry.getClass().getSimpleName() + " will register annotated classes : " + Arrays.asList(annotatedClasses) + " .");
            }
            reader.register((Class[])annotatedClasses);
        }
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry, Class<?> annotatedClass, String beanName) {
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(annotatedClass);
        genericBeanDefinition.setSynthetic(true);
        registry.registerBeanDefinition(beanName, (BeanDefinition)genericBeanDefinition);
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry, Class<?> annotatedClass) {
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(annotatedClass);
        genericBeanDefinition.setSynthetic(true);
        registry.registerBeanDefinition(annotatedClass.getSimpleName(), (BeanDefinition)genericBeanDefinition);
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry, BeanDefinition beanDefinition, String beanName) {
        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}

