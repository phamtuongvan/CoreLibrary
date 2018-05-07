package co.pamobile.pacore.Process;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Dev02 on 4/13/2017.
 */


public abstract class ProgressAsyncTask extends AsyncTask<Integer, Integer, Void> {
    ProgressDialog progressDialog;

    public ProgressAsyncTask(Context context){
        this.progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Connecting...");
        //progressDialog.setProgressStyle(ProgressDialog.BUTTON_NEUTRAL);
    }

    public ProgressAsyncTask() {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    public abstract void onDoing();
    public Void OnDoing(){
        return null;
    }
    public abstract void onTaskComplete(Void aVoid);
    @Override
    protected Void doInBackground(Integer... params) {
        publishProgress(params);
        onDoing();
        OnDoing();
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        super.onPostExecute(aVoid);
        onTaskComplete(aVoid);
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }
}