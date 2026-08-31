//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zhuangxv.bot.injector.support.group;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.zhuangxv.bot.core.Bot;
import com.zhuangxv.bot.core.Button;
import com.zhuangxv.bot.core.Buttons;
import com.zhuangxv.bot.event.BaseEvent;
import com.zhuangxv.bot.event.message.GroupMessageEvent;
import com.zhuangxv.bot.injector.ObjectInjector;
import java.util.List;

public class MessageButtonInjector implements ObjectInjector<Buttons> {
    public Class<Buttons> getClassType() {
        return Buttons.class;
    }

    public String[] getType() {
        return new String[]{"all"};
    }

    public Buttons getObject(BaseEvent event, Bot bot) {
        if (event instanceof GroupMessageEvent) {
            GroupMessageEvent groupMessageEvent = (GroupMessageEvent)event;
            JSONObject rawObj = groupMessageEvent.getRaw();
            Buttons buttons = new Buttons();

            try {
                if (rawObj != null && rawObj.containsKey("msgSeq")) {
                    buttons.setMsgSeq(rawObj.getString("msgSeq"));
                }

                if (rawObj != null && rawObj.containsKey("elements")) {
                    JSONArray elements = rawObj.getJSONArray("elements");
                    if (elements != null && !elements.isEmpty()) {
                        JSONObject firstElement = elements.getJSONObject(0);
                        if (firstElement == null) {
                            System.err.println("第一个元素为空，无法解析按钮数据");
                            return buttons;
                        }

                        if (firstElement.containsKey("inlineKeyboardElement")) {
                            JSONObject inlineKeyboardElement = firstElement.getJSONObject("inlineKeyboardElement");
                            if (inlineKeyboardElement != null) {
                                if (inlineKeyboardElement.containsKey("botAppid")) {
                                    String botAppid = inlineKeyboardElement.getString("botAppid");
                                    buttons.setBotAppid(botAppid);
                                }

                                JSONArray rows = inlineKeyboardElement.getJSONArray("rows");
                                if (rows != null && !rows.isEmpty()) {
                                    for(int i = 0; i < rows.size(); ++i) {
                                        JSONObject rowObj = rows.getJSONObject(i);
                                        if (rowObj.containsKey("buttons")) {
                                            JSONArray buttonsArray = rowObj.getJSONArray("buttons");
                                            if (buttonsArray != null) {
                                                List<Button> buttonList = JSON.parseArray(buttonsArray.toString(), Button.class);
                                                buttons.addButtonList(buttonList);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("解析按钮数据失败: " + e.getMessage());
                e.printStackTrace();
            }

            return buttons;
        } else {
            return null;
        }
    }
}
