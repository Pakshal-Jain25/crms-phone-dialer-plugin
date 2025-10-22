package com.mobicloud.plugins.phonelogger;

import android.os.Build;
import android.util.Log;
import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.telecom.TelecomManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.R)
public class PhoneLogger {

    private static final String TAG = "PhoneLogger";
    public static final String[] PERMISSIONS = {
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.MANAGE_OWN_CALLS,
            Manifest.permission.CALL_PHONE,
    };
    public static final int REQUEST_CODE_CALL_LOG = 100;

    /**
     * Echo method for testing.
     */
    public String echo(String value) {
        Log.i(TAG, "Echoing: " + value);
        return value;
    }

    /**
     * Request necessary permissions.
     */
    public static void requestPermissions(Activity activity) {
        boolean shouldRequest = false;

        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                Log.w(TAG, "Requesting permission: " + permission);
                shouldRequest = true;
            } else {
                Log.i(TAG, "Permission already granted: " + permission);
            }
        }

        if (shouldRequest) {
            Log.i(TAG, "Requesting necessary permissions");
            ActivityCompat.requestPermissions(activity, PERMISSIONS, REQUEST_CODE_CALL_LOG);
        } else {
            Log.i(TAG, "All permissions already granted");
        }
    }

    /**
     * Check if required permissions are granted.
     */
    public static boolean arePermissionsGranted(Context context) {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                Log.w(TAG, "Permission not granted: " + permission);
                return false;
            }
        }
        Log.i(TAG, "All permissions granted");
        return true;
    }

    /**
     * Get call logs.
     */
    public static JSArray getCallLogs(Context context) {
        JSArray callLogs = new JSArray();

        if (!arePermissionsGranted(context)) {
            Log.e(TAG, "Permissions not granted. Unable to fetch call logs.");
            return callLogs;
        }

        ContentResolver resolver = context.getContentResolver();
        Uri callUri = CallLog.Calls.CONTENT_URI;
        Cursor cursor = resolver.query(callUri, null, null, null, CallLog.Calls.DATE + " DESC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                JSObject log = new JSObject();
                try {
                    log.put("number", cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER)));
                    log.put("type", cursor.getInt(cursor.getColumnIndexOrThrow(CallLog.Calls.TYPE)));
                    log.put("date", new Date(cursor.getLong(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))));
                    log.put("duration", cursor.getInt(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION)));
                    callLogs.put(log);
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing call log: " + e.getMessage());
                }
            }
            cursor.close();
        }

        return callLogs;
    }


    /**
     * Set the app as the default dialer.
     */
    public static void setDefaultDialer(Context context) {
        TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
        Intent intent = new Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER);
        intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, context.getPackageName());
        context.startActivity(intent);
        Log.i(TAG, "Requested to set default dialer");
    }
}
