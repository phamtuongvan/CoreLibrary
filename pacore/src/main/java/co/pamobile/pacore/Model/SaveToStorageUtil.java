package co.pamobile.pacore.Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Utility class for saving images to external storage
 */
public class SaveToStorageUtil {

    public static String save(Bitmap bitmap, String imgName, Context context) {

        String path = getFolderPath() + "/" + imgName + ".png";
        Log.i("SaveToStorage", "Path: " + path);
        try {
            File file = new File(path);
            if(file.exists()){
                path = getFolderPath() + "/" + imgName+ DateFormat.format("yyyyMMdd_kkmmss", new Date()) + ".png";
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


}

