//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.support;

import com.zhuangxv.bot.core.component.BotDispatcher;
import com.zhuangxv.bot.core.component.BotFactory;
import com.zhuangxv.bot.core.component.BotInit;
import com.zhuangxv.bot.core.component.QQEventDispatcher;
import com.zhuangxv.bot.core.component.SnowFlakeIdGenerator;
import com.zhuangxv.bot.handler.message.GroupMessageEventHandler;
import com.zhuangxv.bot.handler.message.GroupRecallEventHandler;
import com.zhuangxv.bot.handler.message.MemberAddEventHandler;
import com.zhuangxv.bot.handler.message.PrivateMessageEventHandler;
import com.zhuangxv.bot.handler.meta.HeartbeatEventHandler;
import com.zhuangxv.bot.handler.notice.BotOfflineEventHandler;
import com.zhuangxv.bot.injector.support.BotInjector;
import com.zhuangxv.bot.injector.support.BotOfflineEventInjector;
import com.zhuangxv.bot.injector.support.MessageChainInjector;
import com.zhuangxv.bot.injector.support.MessageIdInjector;
import com.zhuangxv.bot.injector.support.MessageIdIntInjector;
import com.zhuangxv.bot.injector.support.MessageStringInjector;
import com.zhuangxv.bot.injector.support.friend.FriendInjector;
import com.zhuangxv.bot.injector.support.friend.TempFriendInjector;
import com.zhuangxv.bot.injector.support.group.GroupInjector;
import com.zhuangxv.bot.injector.support.group.MemberInjector;
import com.zhuangxv.bot.injector.support.group.MessageButtonInjector;
import com.zhuangxv.bot.injector.support.group.RecallMessageInjector;
import com.zhuangxv.bot.scheduled.FlushCacheScheduled;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class BotApplicationRegistrar implements ImportSelector {
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[] { BotOfflineEventInjector.class.getName(), BotOfflineEventHandler.class.getName(),
                QQEventDispatcher.class.getName(), BotFactory.class.getName(), BotDispatcher.class.getName(),
                SnowFlakeIdGenerator.class.getName(), HeartbeatEventHandler.class.getName(),
                PrivateMessageEventHandler.class.getName(), GroupMessageEventHandler.class.getName(),
                GroupRecallEventHandler.class.getName(), MemberAddEventHandler.class.getName(),
                RecallMessageInjector.class.getName(), BotInit.class.getName(), MessageStringInjector.class.getName(),
                GroupInjector.class.getName(), MessageChainInjector.class.getName(), TempFriendInjector.class.getName(),
                MemberInjector.class.getName(), MessageIdInjector.class.getName(), MessageIdIntInjector.class.getName(),
                BotInjector.class.getName(), FlushCacheScheduled.class.getName(), FriendInjector.class.getName(),
                MessageButtonInjector.class.getName() };
    }
}
