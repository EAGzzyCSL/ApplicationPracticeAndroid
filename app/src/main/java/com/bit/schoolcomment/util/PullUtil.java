package com.bit.schoolcomment.util;

import com.bit.schoolcomment.event.HotGoodsEvent;
import com.bit.schoolcomment.event.HotShopEvent;
import com.bit.schoolcomment.event.LoginEvent;
import com.bit.schoolcomment.event.RegisterEvent;
import com.bit.schoolcomment.model.GoodsListModel;
import com.bit.schoolcomment.model.ShopListModel;
import com.bit.schoolcomment.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;

public class PullUtil {

    private static final String BASE_URL = "http://123.206.84.137/ApplicationPracticeWeb/php/receive.php?PostType=";
    private static final String REGISTER = BASE_URL + "Register";
    private static final String LOGIN = BASE_URL + "Login";
    private static final String GET_HOT_SHOP = BASE_URL + "Get_hotshop";
    private static final String GET_HOT_GOODS = BASE_URL + "Get_hotgoods";

    private static volatile PullUtil sPullUtil;

    public static PullUtil getInstance() {
        if (sPullUtil == null) {
            synchronized (PullUtil.class) {
                if (sPullUtil == null) {
                    sPullUtil = new PullUtil();
                }
            }
        }
        return sPullUtil;
    }

    private boolean judgeCode(String result) {
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        if (jsonObject.get("code").getAsInt() == 1) {
            return true;
        } else {
            ToastUtil.show(jsonObject.get("msg").getAsString());
            return false;
        }
    }

    private Object judgeCodeAndGetData(String result, Class clazz) {
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        if (jsonObject.get("code").getAsInt() == 1) {
            return new Gson().fromJson(jsonObject.get("data"), clazz);
        } else {
            ToastUtil.show(jsonObject.get("msg").getAsString());
            return null;
        }
    }

    public void register(String name, String password) {
        PullRequest request = new PullRequest(REGISTER);
        request.setParams("name", name);
        request.setParams("password", password);
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                if (judgeCode(result)) {
                    EventBus.getDefault().post(new RegisterEvent());
                }
            }
        });
        request.doPost();
    }

    public void login(String name, String password) {
        PullRequest request = new PullRequest(LOGIN);
        request.setParams("name", name);
        request.setParams("password", password);
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                UserModel userModel = (UserModel) judgeCodeAndGetData(result, UserModel.class);
                if (userModel != null) {
                    EventBus.getDefault().post(new LoginEvent(userModel));
                }
            }
        });
        request.doPost();
    }

    public void getHotShop(int schoolId) {
        PullRequest request = new PullRequest(GET_HOT_SHOP);
        request.setParams("school_ID", String.valueOf(schoolId));
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                ShopListModel model = new Gson().fromJson(result, ShopListModel.class);
                EventBus.getDefault().post(new HotShopEvent(model));
            }
        });
        request.doPost();
    }

    public void getHotGoods(int schoolId) {
        PullRequest request = new PullRequest(GET_HOT_GOODS);
        request.setParams("school_ID", String.valueOf(schoolId));
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                GoodsListModel model = new Gson().fromJson(result, GoodsListModel.class);
                EventBus.getDefault().post(new HotGoodsEvent(model));
            }
        });
        request.doPost();
    }
}
