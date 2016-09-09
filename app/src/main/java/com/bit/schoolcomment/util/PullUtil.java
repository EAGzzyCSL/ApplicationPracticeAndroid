package com.bit.schoolcomment.util;

import com.bit.schoolcomment.event.CommentListEvent;
import com.bit.schoolcomment.event.GoodsCollectionEvent;
import com.bit.schoolcomment.event.GoodsListEvent;
import com.bit.schoolcomment.event.LoginEvent;
import com.bit.schoolcomment.event.LogoutEvent;
import com.bit.schoolcomment.event.RegisterEvent;
import com.bit.schoolcomment.event.SchoolListEvent;
import com.bit.schoolcomment.event.ShopListEvent;
import com.bit.schoolcomment.fragment.comment.GoodsCommentListFragment;
import com.bit.schoolcomment.fragment.goods.GoodsCollectionListFragment;
import com.bit.schoolcomment.fragment.goods.HotGoodsListFragment;
import com.bit.schoolcomment.fragment.goods.SearchGoodsListFragment;
import com.bit.schoolcomment.fragment.goods.ShopGoodsListFragment;
import com.bit.schoolcomment.fragment.shop.HotShopListFragment;
import com.bit.schoolcomment.fragment.shop.SearchShopListFragment;
import com.bit.schoolcomment.model.UserModel;
import com.bit.schoolcomment.model.list.CommentListModel;
import com.bit.schoolcomment.model.list.GoodsListModel;
import com.bit.schoolcomment.model.list.SchoolListModel;
import com.bit.schoolcomment.model.list.ShopListModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;

public class PullUtil {

    private static final String BASE_URL = "http://123.206.84.137/ApplicationPracticeWeb/php/receive.php?PostType=";
    private static final String REGISTER = BASE_URL + "Register";
    private static final String LOGIN = BASE_URL + "Login";
    private static final String LOGOUT = BASE_URL + "logout";
    private static final String CHECK_TOKEN = BASE_URL + "Check_token";
    private static final String SEARCH_SHOP = BASE_URL + "Shop_search";
    private static final String SEARCH_GOODS = BASE_URL + "Goods_search";
    private static final String GET_SCHOOL = BASE_URL + "Get_school";
    private static final String GET_HOT_SHOP = BASE_URL + "Get_hotshop";
    private static final String GET_HOT_GOODS = BASE_URL + "Get_hotgoods";
    private static final String GET_SHOP_GOODS = BASE_URL + "Get_shopgoods";
    private static final String GET_GOODS_COMMENT = BASE_URL + "Get_goodscomment";
    private static final String GET_COLLECTION = BASE_URL + "Get_collection";
    private static final String JUDGE_COLLECTION = BASE_URL + "Judge_collection";
    private static final String ADD_COLLECTION = BASE_URL + "add_collection";
    private static final String CANCEL_COLLECTION = BASE_URL + "delete_collection";

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

    private void addIdAndToken(PullRequest request) {
        int userId = DataUtil.getUserModel().ID;
        String token = DataUtil.getUserModel().token;
        request.setParams("user_ID", String.valueOf(userId));
        request.setParams("token", token);
    }

    private boolean isSuccessCode(String result, int code) {
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        ToastUtil.show(jsonObject.get("msg").getAsString());
        return jsonObject.get("code").getAsInt() == code;
    }

    public void register(String name, String password) {
        PullRequest request = new PullRequest(REGISTER);
        request.setParams("name", name);
        request.setParams("password", password);
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                if (isSuccessCode(result, 1)) {
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
                if (isSuccessCode(result, 1)) {
                    JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                    UserModel model = new Gson().fromJson(jsonObject.get("data"), UserModel.class);
                    EventBus.getDefault().post(new LoginEvent(model));
                }
            }
        });
        request.doPost();
    }

    public void logout() {
        PullRequest request = new PullRequest(LOGOUT);
        addIdAndToken(request);
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                if (isSuccessCode(result, 45)) {
                    EventBus.getDefault().post(new LogoutEvent());
                }
            }
        });
        request.doPost();
    }

    public void checkToken(int userId, String token) {
        PullRequest request = new PullRequest(CHECK_TOKEN);
        request.setParams("user_ID", String.valueOf(userId));
        request.setParams("token", token);
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                UserModel model = new Gson().fromJson(jsonObject.get("data"), UserModel.class);
                if (model != null) EventBus.getDefault().postSticky(new LoginEvent(model));
            }
        });
        request.doPost();
    }

    public void searchShop(String keyword) {
        PullRequest request = new PullRequest(SEARCH_SHOP);
        if (DataUtil.isLogin()) addIdAndToken(request);
        request.setParams("keyword", keyword);
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                ShopListModel model = new Gson().fromJson(result, ShopListModel.class);
                EventBus.getDefault().post(new ShopListEvent(model, SearchShopListFragment.class));
            }
        });
        request.doPost();
    }

    public void searchGoods(String keyword) {
        PullRequest request = new PullRequest(SEARCH_GOODS);
        if (DataUtil.isLogin()) addIdAndToken(request);
        request.setParams("keyword", keyword);
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                GoodsListModel model = new Gson().fromJson(result, GoodsListModel.class);
                EventBus.getDefault().post(new GoodsListEvent(model, SearchGoodsListFragment.class));
            }
        });
        request.doPost();
    }

    public void getSchool() {
        PullRequest request = new PullRequest(GET_SCHOOL);
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                SchoolListModel model = new Gson().fromJson(result, SchoolListModel.class);
                EventBus.getDefault().post(new SchoolListEvent(model));
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
                EventBus.getDefault().post(new ShopListEvent(model, HotShopListFragment.class));
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
                EventBus.getDefault().post(new GoodsListEvent(model, HotGoodsListFragment.class));
            }
        });
        request.doPost();
    }

    public void getShopGoods(int shopId) {
        PullRequest request = new PullRequest(GET_SHOP_GOODS);
        request.setParams("shop_ID", String.valueOf(shopId));
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                GoodsListModel model = new Gson().fromJson(result, GoodsListModel.class);
                EventBus.getDefault().post(new GoodsListEvent(model, ShopGoodsListFragment.class));
            }
        });
        request.doPost();
    }

    public void getGoodsComment(int goodsId) {
        PullRequest request = new PullRequest(GET_GOODS_COMMENT);
        request.setParams("goods_ID", String.valueOf(goodsId));
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                CommentListModel model = new Gson().fromJson(result, CommentListModel.class);
                EventBus.getDefault().post(new CommentListEvent(model, GoodsCommentListFragment.class));
            }
        });
        request.doPost();
    }

    public void getCollection() {
        PullRequest request = new PullRequest(GET_COLLECTION);
        addIdAndToken(request);
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                GoodsListModel model = new Gson().fromJson(result, GoodsListModel.class);
                EventBus.getDefault().post(new GoodsListEvent(model, GoodsCollectionListFragment.class));
            }
        });
        request.doPost();
    }

    public void judgeCollection(int goodsId) {
        PullRequest request = new PullRequest(JUDGE_COLLECTION);
        addIdAndToken(request);
        request.setParams("goods_ID", String.valueOf(goodsId));
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                boolean collected = jsonObject.get("code").getAsInt() == 34;
                EventBus.getDefault().post(new GoodsCollectionEvent(collected));
            }
        });
        request.doPost();
    }

    public void addCollection(int goodsId) {
        PullRequest request = new PullRequest(ADD_COLLECTION);
        addIdAndToken(request);
        request.setParams("goods_ID", String.valueOf(goodsId));
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                if (isSuccessCode(result, 41)) {
                    EventBus.getDefault().post(new GoodsCollectionEvent(true));
                }
            }
        });
        request.doPost();
    }

    public void cancelCollection(int goodsId) {
        PullRequest request = new PullRequest(CANCEL_COLLECTION);
        addIdAndToken(request);
        request.setParams("goods_ID", String.valueOf(goodsId));
        request.setResponseListener(new ResponseListener() {

            @Override
            public void getResult(String result) {
                if (isSuccessCode(result, 43)) {
                    EventBus.getDefault().post(new GoodsCollectionEvent(false));
                }
            }
        });
        request.doPost();
    }
}
