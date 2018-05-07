package co.pamobile.pacore.View;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;

public class ImageScannerAdapter implements MediaScannerConnection.MediaScannerConnectionClient {

    Context context;
    String path;
    MediaScannerConnection conn;
    Uri uri;
    ImageScanner imageScanner;
    public ImageScannerAdapter(Context c) {
        this.context = c;
    }

    public void scanImage(String path,ImageScanner imageScanner) {
        this.path = path;
        this.imageScanner = imageScanner;
        if (conn != null) conn.disconnect();
        conn = new MediaScannerConnection(context, this);
        conn.connect();
    }

    public void onMediaScannerConnected() {
        try {
            Log.i("Photo Sticker", "Start Media Scanner");
            conn.scanFile(path, "image/*");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void onScanCompleted(String path, Uri uri) {
        this.uri = uri;
        conn.disconnect();
        imageScanner.onCompleted(uri);
    }
    public Uri getUri(){
        return this.uri;
    }
    public interface ImageScanner{
        void onCompleted(Uri uri);
    }
}


