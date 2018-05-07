package co.pamobile.pacore.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Dev04 on 11/13/2017.
 */

public class Utils {
    public static void showMessage(Activity mActivity, String mMessage) {
        Toast.makeText(mActivity, mMessage, Toast.LENGTH_LONG).show();
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static Bitmap loadBitmap(String url) {
        File image = new File(url);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
        return bitmap;
    }

    public static void openFacebookURL(Activity mActivity, String fbURL) {
        try {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            String facebookUrl = Utils.getFacebookPageURL(mActivity, fbURL);
            facebookIntent.setData(Uri.parse(facebookUrl));
            mActivity.startActivity(facebookIntent);
        } catch (Exception e) {
            e.printStackTrace();

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fbURL));
            mActivity.startActivity(intent);
        }
    }

    public static void openYouTubeURL(Activity mActivity, String YouTubeURL) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse(YouTubeURL));
            mActivity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(YouTubeURL));
            mActivity.startActivity(intent);
        }
    }

    public static String getFacebookPageURL(Context context, String fbURL) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + fbURL;
            } else { //older versions of fb app
                return fbURL;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return fbURL; //normal web url
        }
    }

    public static Bitmap getBitmapFromAssets(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        Bitmap bitmap = null;
        InputStream istr = null;
        try {
            istr = assetManager.open(fileName);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException ignored) {

        }
        return bitmap;
    }

    public static long my_time_in() {
        TimeZone london = TimeZone.getTimeZone("Europe/London");
        long now = System.currentTimeMillis();
        return now + london.getOffset(now);
    }

    public static String save(Bitmap bitmap, String imgName, Context context) {

        String path = getFolderPath() + "/" + imgName + ".png";
        Log.i("Save To Storage", "Path: " + path);
        try {
            File file = new File(path);
            if (file.exists()) {
                path = getFolderPath() + "/" + imgName + DateFormat.format("yyyyMMdd_kkmmss", new Date()) + ".png";
            }
            FileOutputStream out = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                final Uri contentUri = Uri.fromFile(file);
                scanIntent.setData(contentUri);
                context.sendBroadcast(scanIntent);
            } else {
                context.sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse(path)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String getFolderPath() {
        String lvl1 = "Pictures";
        String lvl2 = "Card Maker";

        File folderLvl1 = checkPath(Environment.getExternalStorageDirectory(), lvl1);
        File folderLvl2 = checkPath(folderLvl1, lvl2);

        return folderLvl2.getPath();
    }

    private static File checkPath(File pathLvl1, String lvl2) {
        File pathLvl2 = new File(pathLvl1, lvl2);
        if (!pathLvl2.exists()) {
            pathLvl2.mkdir();
        }
        return pathLvl2;
    }

    public static String save(Bitmap bitmap, Context context) {
        return save(bitmap, generateImageName(), context);
    }

    public static String generateImageName() {
        return "IMG_" + DateFormat.format("yyyyMMdd_kkmmss", new Date());
    }

    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public void setWidthHeightAndMargin(View view, int width, int height, int top, int bottom, int left, int right) {
        FrameLayout.LayoutParams layoutParams = null;
        layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.topMargin = top;
        layoutParams.bottomMargin = bottom;
        layoutParams.rightMargin = right;
        layoutParams.leftMargin = left;
        //layoutParams.gravity = Gravity.TOP|Gravity.RIGHT;
        view.setLayoutParams(layoutParams);
    }


    public void setParamMargin(View view, int top, int bottom, int left, int right) {
        FrameLayout.LayoutParams layoutParams = null;
        layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.topMargin = top;
        layoutParams.bottomMargin = bottom;
        layoutParams.rightMargin = right;
        layoutParams.leftMargin = left;
        //layoutParams.gravity = Gravity.TOP|Gravity.RIGHT;
        view.setLayoutParams(layoutParams);
    }

    public void setParamWidthHeight(View view, int width, int height) {
        FrameLayout.LayoutParams layoutParams = null;
        layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.width = width;
        layoutParams.height = height;
        //layoutParams.gravity = Gravity.TOP|Gravity.RIGHT;
        view.setLayoutParams(layoutParams);
    }

    //open image after save
    public static void goToImage(Context context, String path) {
        final Intent intent = new Intent(Intent.ACTION_VIEW)//
                .setDataAndType(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ?
                                android.support.v4.content.FileProvider.getUriForFile(context,context.getPackageName() + ".provider", new File(path)) : Uri.fromFile(new File(path)),
                        "image/*").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    //share image from uri
    public static void ShareImage(Context context,String uri) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/png");
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri photoURI = FileProvider.getUriForFile(context,
                context.getPackageName() + ".provider",
                new File(uri));
        share.putExtra(Intent.EXTRA_STREAM, photoURI);
        context.startActivity(Intent.createChooser(share, "Share"));
    }

}
