package org.lab.biometro.listener;

import org.json.JSONObject;

public interface OnHttpListener {
    void onEventCallBack(JSONObject obj, int ret);
    void onEventInternetError(Exception e);
    void onEventServerError(Exception e);
}
