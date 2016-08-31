package com.bit.schoolcomment.util;

import com.bit.schoolcomment.model.RawModel;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

public class PullUtil {

    public static final String SEARCH_GAS_STATION = "http://apis.juhe.cn/oil/region";

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

    public void searchGasStation() {
        PullRequest request = new PullRequest(SEARCH_GAS_STATION);
        request.setParams("city", "北京");
        request.setParams("key", "e14ab7d59ae6bd01598ff061f9411b40");
        request.setResponseListener(new PullRequest.ResponseListener() {

            @Override
            public void getResult(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                RawModel model = gson.fromJson(result, RawModel.class);
                EventBus.getDefault().post(model.result.data);
            }

            @Override
            public void getError() {
                System.out.println("error");
                EventBus.getDefault().post(null);
            }
        });
        request.doPost();
    }
}
