package com.zhuangxv.bot.api.support;

import com.zhuangxv.bot.api.BaseApi;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 */
public class GetBotInfo extends BaseApi {
    private final GetBotInfo.Param param = new Param();

    @Override
    public String getAction() {
        return "get_login_info";
    }

    @Override
    public Object getParams() {
        return param;
    }

    @Override
    public boolean needSleep() {
        return false;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Param {

    }
}
