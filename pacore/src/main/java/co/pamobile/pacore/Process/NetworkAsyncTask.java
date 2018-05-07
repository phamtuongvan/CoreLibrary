//package PACore.Process;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.inject.Inject;
//
//import PACore.Utilities.JsonConvert;
//import PACore.Utilities.Utils;
//import PACore.View.ConnectDialog;
//import co.pamobile.baseproject.App.MyApplication;
//import dmax.dialog.SpotsDialog;
//
///**
// * Created by KhoaVin on 24/06/2017.
// */
//
//public abstract class NetworkAsyncTask extends AsyncTask<Integer, Integer, Void> {
//    private AlertDialog progressDialog;
//    private Activity currentActivity;
//    private ConnectDialog connectDialog;
//    int HttpMethod = Request.Method.POST;
//
//    public void setHttpMethod(int httpMethod) {
//        HttpMethod = httpMethod;
//    }
//
//    @Inject
//    IVolleyService volleyService;
//
//    public NetworkAsyncTask(Activity currentActivity) {
//        this.currentActivity = currentActivity;
//
//        try {
//            progressDialog = new SpotsDialog(currentActivity);
//            progressDialog.setTitle("Connecting...");
//            ((MyApplication) currentActivity.getApplication()).getGeneralComponent().Inject(this);
//            connectDialog = new ConnectDialog();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//    @Override
//    protected void onProgressUpdate(Integer... values) {
//
//        try {
//            progressDialog.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        super.onProgressUpdate(values);
//    }
//
//    @Override
//    protected Void doInBackground(Integer... params) {
//
//        publishProgress(params);
//        if(HttpMethod == Request.Method.GET || HttpMethod == Request.Method.DELETE){
//            setApi(getAPI_URL());
//        }else {
//            setApi2(getAPI_URL());
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        if(progressDialog.isShowing())
//            progressDialog.dismiss();
//        super.onPostExecute(aVoid);
//
//    }
//
//    public abstract void Response(String response);
//
//    public abstract Map<String,String> getListParams();
//
//    public abstract Object objectParam();
//
//    public abstract String getAPI_URL();
//
//    public void setApi(final String API_URL){
//        RequestQueue queue = volleyService.getRequestQueue(currentActivity.getApplicationContext());
//
//        StringRequest stringRequest = new StringRequest(HttpMethod, API_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Response(response);
//                }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try {
//                    connectDialog.show(((AppCompatActivity)currentActivity).getSupportFragmentManager(),"network dialog");
//                    connectDialog.setOnClickListener(new ConnectDialog.Listener() {
//                        @Override
//                        public void onClick() {
//                            setApi(API_URL);
//                            connectDialog.dismiss();
//                        }
//                    });
//                } catch (Exception e) {
//                    Utils.showMessage(currentActivity, "Error loading data from server !!");
//                    e.printStackTrace();
//                }
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                return getListParams();
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                //params.put("Accept","application/json");
//                //params.put("Content-Type","application/json");
//                return params;
//            }
//            @Override
//            public String getBodyContentType()
//            {
//                return "application/json";
//            }
//
//        };
//        queue.add(stringRequest);
//    }
//    public void setApi2(final String API_URL){
//        RequestQueue queue = volleyService.getRequestQueue(currentActivity.getApplicationContext());
//
//        StringRequest sr = new StringRequest(HttpMethod, API_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Response(response);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try {
//                    connectDialog.show(((AppCompatActivity)currentActivity).getSupportFragmentManager(),"network dialog");
//                    connectDialog.setOnClickListener(new ConnectDialog.Listener() {
//                        @Override
//                        public void onClick() {
//                            setApi2(API_URL);
//                            connectDialog.dismiss();
//                        }
//                    });
//                } catch (Exception e) {
//                    Utils.showMessage(currentActivity, "Error loading data from server !!");
//                    e.printStackTrace();
//                }
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                return getListParams();
//            }
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//
//                JSONObject obj = null;
//                try {
//                    obj = new JSONObject(JsonConvert.convertToJson(objectParam()));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return obj.toString().getBytes();
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "application/json";
//            }
//        };
//        queue.add(sr);
//    }
//}
