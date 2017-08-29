package me.chanjar.weixin.cp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.util.json.WxAccessTokenAdapter;
import me.chanjar.weixin.common.util.json.WxErrorAdapter;
import me.chanjar.weixin.common.util.json.WxMediaUploadResultAdapter;
import me.chanjar.weixin.common.util.json.WxMenuGsonAdapter;

/**
 * @author 张靖
 *         2015-09-23 17:36.
 */
public class WxGsonBuilder {

    public static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.disableSpecialChar();
        INSTANCE.registerTypeAdapter(WxAccessToken.class, new WxAccessTokenAdapter());
        INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
        INSTANCE.registerTypeAdapter(WxMenu.class, new WxMenuGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMediaUploadResult.class, new WxMediaUploadResultAdapter());
    }

    public static Gson create() {
        return INSTANCE.create();
    }

}