package com.mobicloud.plugins.phonelogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CallComplete extends BroadcastReceiver {

    private static final String TAG = "CallComplete";

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (state != null && state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            Log.i(TAG, "Call Disconnected.");
            Toast.makeText(context, "Call Disconnected!", Toast.LENGTH_SHORT).show();
            // You can perform further actions here, such as invoking a service or sending a broadcast.
        } else if (state == null) {
            Log.w(TAG, "State is null. Unable to determine call status.");
        }
    }
}
