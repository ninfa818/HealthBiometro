package org.lab.biometro.model;

import org.lab.biometro.listener.OnHttpListener;
import org.lab.biometro.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class UserModel {

    public String id = "";
    public String account = "";
    public String memberName = "";
    public String mobile = "";
    public String birthDay = "";
    public String totalMembershipYn = "";
    public String personalInfoYn = "";
    public String safePerson = "";
    public String safeMobile = "";

    public void register(String pwd, String emailYn, String emailCode, OnHttpListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("pwd", pwd);
        params.put("memberName", memberName);
        params.put("mobile", mobile);
        params.put("birthDay", birthDay);
        params.put("totalMembershipYn", totalMembershipYn);
        params.put("personalInfoYn", personalInfoYn);
        params.put("safePerson", safePerson);
        params.put("safeMobile", safeMobile);
        params.put("emailYn", emailYn);
        params.put("emailCode", emailCode);

        HttpUtil.onHttpRequest(HttpUtil.url_register, params, listener);
    }

}
