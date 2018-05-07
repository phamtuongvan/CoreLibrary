package co.pamobile.pacore.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.Button;

import co.pamobile.pacore.R;
import co.pamobile.pacore.Storage.SharedPreference;
import co.pamobile.pacore.Utilities.Const;

/**
 * Created by tuongvan on 3/2/18.
 */

public class DefaultFunction {
    Activity mActivity;
    SharedPreference sharedPreferences;


    public DefaultFunction(Activity mActivity){
        this.mActivity = mActivity;
        sharedPreferences = new SharedPreference(mActivity);
    }

    //check package name if wrong then close app
    public void checkPackageName(String packageName){
        if(!mActivity.getPackageName().equals(packageName)){
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Const.URL_GOOGLEPLAY + mActivity.getPackageName()));
            mActivity.startActivity(intent);
            mActivity.finish();
        }
    }

    //check the number open
    private void checkNumOpen() {
        int num = sharedPreferences.getNum(sharedPreferences.NUM_OPEN);
        if (num != 0) {
            if (num <= 3) {
                if (num == 3) {
                    confirmApp();
                }
                num++;
                sharedPreferences.saveNum(num, sharedPreferences.NUM_OPEN);
            }
        } else {
            sharedPreferences.saveNum(1,sharedPreferences.NUM_OPEN);
        }
    }

    //show dialog rate app
    public void confirmApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("Thank you");
        builder.setMessage(R.string.message_rate_app);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Const.URL_GOOGLEPLAY + mActivity.getPackageName())));
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("LATER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sharedPreferences.saveNum(1, sharedPreferences.NUM_OPEN);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.GRAY);

    }

    public void rateApp() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        boolean rate_status = prefs.getBoolean("rate_status", false);
        final SharedPreferences.Editor editor = prefs.edit();
        int count = prefs.getInt("viewCount", 0);
        if (count % 3 == 0) {
            if (!rate_status) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("Thank you");
                builder.setMessage(R.string.message_rate_app);
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Const.URL_GOOGLEPLAY + mActivity.getPackageName())));
                        //never show again
                        editor.putBoolean("rate_status", true);
                        editor.apply();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("LATER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setTextColor(Color.GRAY);
            }
        }
        count++;
        editor.putInt("viewCount", count);
        editor.apply();
    }


    //check new version code
    public void checkCodeVersion(int versionCode) {
        try {
            PackageInfo pInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            int currentCodeVersion = pInfo.versionCode;
            if (currentCodeVersion < versionCode) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage(R.string.message_new_version);
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(Const.URL_GOOGLEPLAY + mActivity.getPackageName()));
                        mActivity.startActivity(intent);
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setTextColor(Color.GRAY);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }



}
