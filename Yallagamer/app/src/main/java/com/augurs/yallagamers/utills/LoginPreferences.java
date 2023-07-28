package com.augurs.yallagamers.utills;

import android.content.Context;

public class LoginPreferences extends PrefManager {
    public LoginPreferences(Context context, String prefName) {
        super(context, prefName);
    }

    public LoginPreferences(Context context) {
        super(context,"user_login");
    }
}
