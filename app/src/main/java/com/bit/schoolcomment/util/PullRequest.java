package com.bit.schoolcomment.util;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bit.schoolcomment.MyApplication;
import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.NetworkErrorEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class PullRequest {

    private String url;
    private HashMap<String, String> map;
    private ResponseListener listener;

    public PullRequest(String url) {
        this.url = url;
        System.out.println(url);
        map = new HashMap<>();
    }

    public void setParams(String key, String value) {
        System.out.println(key + " : " + value);
        map.put(key, value);
    }

    public void doPost() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        listener.getResult(response);
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.getError();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                return map;
            }
        };

        MyApplication.getDefault().mRequestQueue.add(request);
    }

    public void setResponseListener(ResponseListener listener) {
        this.listener = listener;
    }
}

abstract class ResponseListener {

    public abstract void getResult(String result);

    public void getError() {
        ToastUtil.show(MyApplication.getDefault().getString(R.string.network_error));
        EventBus.getDefault().post(new NetworkErrorEvent());
    }
}
