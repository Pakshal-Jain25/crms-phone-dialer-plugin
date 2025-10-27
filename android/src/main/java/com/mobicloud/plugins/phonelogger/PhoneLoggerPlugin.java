package com.mobicloud.plugins.phonelogger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.R)
@CapacitorPlugin(name = "PhoneLogger")
public class PhoneLoggerPlugin extends Plugin {

    private PhoneLogger pobj = new PhoneLogger();
    private CallComplete cobj = new CallComplete();


    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", pobj.echo(value));
        call.resolve(ret);
    }

    @PluginMethod()
    public void requestPermissions(PluginCall call) {
        Activity activity = getActivity();
        PhoneLogger.requestPermissions(activity);
        call.resolve();
    }

    @PluginMethod()
    public void getCallLogs(PluginCall call) {
        Context context = getContext();
        JSObject result = new JSObject();

        try {
            result.put("logs", PhoneLogger.getCallLogs(context));
            call.resolve(result);
        } catch (Exception e) {
            call.reject("Error retrieving call logs: " + e.getMessage());
        }
    }

    @PluginMethod()
    public void setDefaultDialer(PluginCall call) {
        Context context = getContext();
        PhoneLogger.setDefaultDialer(context);
        call.resolve();
    }

    @PluginMethod
    public void makeCall(PluginCall call) {
        removeListener(call);
        String value = call.getString("phoneNumber", "");
        if (value == null || value.isEmpty()) {
            call.reject("phone number is required");
            return;
        }

        Context context = getContext();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final long[] callStartTime = {0};
        final boolean[] wasOffhook = {false};


        // Define listener
        PhoneStateListener listener = new PhoneStateListener() {
            boolean isConnected = false;
            long callEndTime = 0;
            long durationMillis = 0;
            long durationSeconds = 0;

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        isConnected = true;
                        wasOffhook[0] = true;
                        callStartTime[0] = System.currentTimeMillis();

                        JSObject startedObj = new JSObject();
                        startedObj.put("status", "started");
                        startedObj.put("number", value);
                        notifyListeners("callStarted", startedObj);
                        break;

                    case TelephonyManager.CALL_STATE_IDLE:
                        if (wasOffhook[0]) {

                            if(isConnected) {
                                callEndTime = System.currentTimeMillis();
                                durationMillis = callEndTime - callStartTime[0];
                                durationSeconds = durationMillis / 1000;
                            }


                            JSObject endedObj = new JSObject();
                            endedObj.put("status", "ended");
                            endedObj.put("number", value);
                            endedObj.put("durationMs", durationMillis);
                            endedObj.put("durationSec", durationSeconds);
                            notifyListeners("callEnded", endedObj);

                            tm.listen(this, PhoneStateListener.LISTEN_NONE);
                        }
                        break;
                }
            }
        };

        // Start listening before starting call
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        // Launch phone call intent
        Uri uri = Uri.parse("tel:" + value);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        // Respond back to JS immediately that call is initiated
        JSObject result = new JSObject();
        result.put("status", "call initiated");

        call.resolve(result);
    }

    @PluginMethod()
    public void callComplete(PluginCall call) {
        boolean bret = false;

        Context context = getContext();
//        bret = cobj.CallStatus();

        JSObject ret = new JSObject();
        ret.put("isDisconnected",bret);
        call.resolve(ret);
    }
}
