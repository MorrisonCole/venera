package com.heisentest.skeletonandroidapp;

import android.text.TextUtils;
import android.util.Log;

public final class HeisentestLogger {

    public static void log(String calleeClass, String calleeMethodName, Object... parameters) {
        String parametersLog = "";
        for (Object parameter : parameters) {
            if (parameter != null) {
                parametersLog = parametersLog.concat(parameter.toString() + ", ");
            }
        }

        // Trim final ', '
        if (!TextUtils.isEmpty(parametersLog)) {
            parametersLog = parametersLog.substring(0, parametersLog.lastIndexOf(','));
        }

        if (TextUtils.isEmpty(parametersLog)) {
            Log.d("HeisentestLogger", String.format("Class -  %s, Method - %s", calleeClass, calleeMethodName));
        } else {
            Log.d("HeisentestLogger", String.format("Class -  %s, Method - %s, Parameters - %s", calleeClass, calleeMethodName, parametersLog));
        }
    }
}
