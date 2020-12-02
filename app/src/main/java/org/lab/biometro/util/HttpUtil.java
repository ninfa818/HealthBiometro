package org.lab.biometro.util;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.lab.biometro.listener.OnHttpListener;

import java.util.Map;

public class HttpUtil {

    public static final String SERVER_URL = "http://168.62.40.81:8080/";

    public static final String url_register = SERVER_URL + "auth/join";
    public static final String url_login = SERVER_URL + "auth/login";
    public static final String url_userinfo = SERVER_URL + "auth/getMemberInfo";
    public static final String url_safeinfo = SERVER_URL + "auth/getProtectorInfo";
    public static final String url_setsafe = SERVER_URL + "auth/setProtectorInfo";

    public static final String url_maindata = SERVER_URL + "auth/getMainPageData";

    public static final String url_notification = SERVER_URL + "auth/getNoticeList";


    public static void onHttpRequest (String url
            , Map<String, String> params
            , OnHttpListener apiResponse) {
        OkHttpUtils.post().url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        apiResponse.onEventInternetError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            int ret = obj.getInt("status");
                            apiResponse.onEventCallBack(obj, ret);
                        } catch (JSONException e) {
                            apiResponse.onEventServerError(e);
                            e.printStackTrace();
                        }
                    }
                });
    }

    public static void onHttpRequest (String url
            , Map<String, String> headers
            , Map<String, String> params
            , OnHttpListener apiResponse) {
        OkHttpUtils.post().url(url)
                .headers(headers)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        apiResponse.onEventInternetError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            int ret = obj.getInt("status");
                            apiResponse.onEventCallBack(obj, ret);
                        } catch (JSONException e) {
                            apiResponse.onEventServerError(e);
                            e.printStackTrace();
                        }
                    }
                });
    }

}
