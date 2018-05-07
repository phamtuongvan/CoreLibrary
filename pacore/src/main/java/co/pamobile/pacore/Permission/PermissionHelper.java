package co.pamobile.pacore.Permission;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import co.pamobile.pacore.R;


public class PermissionHelper {

    public interface PermissionAffirmativeCallback {
        void onPermissionConfirmed();
    }

    private static final int PERMISSIONS_REQUEST_STORAGE = 200;
    private static final int PERMISSIONS_REQUEST_READ_STORAGE = 201;

    private PermissionAffirmativeCallback mAffirmativeCallback;
    private Activity mActivity;

    private String mManifestPermission;
    private int mRequestCode;
    private String mDeniedMsg;
    private String mDeniedNeverAskTitle;
    private String mDeniedNeverAskMsg;

    public static PermissionHelper permissionHelper(String type,
                                                    Activity activity,
                                                    PermissionAffirmativeCallback callback) {
        return new PermissionHelper(type, activity, callback);
    }

    public PermissionHelper(String type, Activity activity, PermissionAffirmativeCallback callback) {
        this.mActivity = activity;
        this.mAffirmativeCallback = callback;

        if (type.equals(PermissionType.STORAGE)) {
            mManifestPermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            mRequestCode = PERMISSIONS_REQUEST_STORAGE;
            mDeniedMsg = mActivity.getString(R.string.permission_storage_msg);
            mDeniedNeverAskTitle = mActivity.getString(R.string.permission_storage_never_ask_title);
            mDeniedNeverAskMsg = mActivity.getString(R.string.permission_storage_never_ask_msg);
        }
        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permission = ActivityCompat.checkSelfPermission(mActivity, mManifestPermission);
            boolean should = ActivityCompat.shouldShowRequestPermissionRationale(mActivity, mManifestPermission);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                if (should) {
                    requestPermission();
                } else {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
                    boolean permission_requested = prefs.getBoolean("permission_requested", false);

                    if (!permission_requested) {
                        requestPermission();

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("permission_requested", true);
                        editor.apply();
                    } else {
                        promptSettings();
                    }
                }
                return;
            }
        }

        if (this.mAffirmativeCallback != null) {
            this.mAffirmativeCallback.onPermissionConfirmed();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(mActivity, new String[]{mManifestPermission}, mRequestCode);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == mRequestCode) {
            boolean hasSth = grantResults.length > 0;
            if (hasSth) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (this.mAffirmativeCallback != null) {
                        this.mAffirmativeCallback.onPermissionConfirmed();
                    }
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    boolean should = ActivityCompat.shouldShowRequestPermissionRationale(mActivity, mManifestPermission);

                    if (should) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                        builder.setTitle(mActivity.getString(R.string.permission_remind_title));
                        builder.setMessage(mDeniedMsg);
                        builder.setPositiveButton(mActivity.getString(R.string.permission_remind_button_sure), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton(mActivity.getString(R.string.permission_remind_button_retry), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                requestPermission();
                            }
                        });
                        builder.show();

                    } else {
                        promptSettings();
                    }
                }
            }
        }
    }

    private void promptSettings() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mDeniedNeverAskTitle);
        builder.setMessage(mDeniedNeverAskMsg);
        builder.setPositiveButton(mActivity.getString(R.string.permission_prompt_button_setting), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                goToSettings();
            }
        });
        builder.setNegativeButton(mActivity.getString(R.string.permission_prompt_button_cancel), null);
        builder.show();
    }

    private void goToSettings() {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + mActivity.getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(myAppSettings);
    }

}
