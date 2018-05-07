//package PACore.Process;
//
//import android.app.Activity;
//import android.os.CountDownTimer;
//import android.support.v7.app.AppCompatActivity;
//
//import com.android.volley.AuthFailureError;
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
//import PACore.Utilities.JsonConvert;
//import PACore.Utilities.Utils;
//import PACore.View.ConnectDialog2;
//
///**
// * Created by tuongvan on 12/27/17.
// */
//
//public class NetworkAsyncWithTimer extends NetworkAsyncTask {
//    private ConnectDialog2 connectDialog2;
//    private Activity currentActivity;
//    CountDownTimer cTimer = null;
//    private boolean isFirst = true;
//
//    public NetworkAsyncWithTimer(Activity currentActivity) {
//        super(currentActivity);
//        this.currentActivity = currentActivity;
//        connectDialog2 = new ConnectDialog2();
//        isFirst = true;
//    }
//
//    @Override
//    public void Response(String response) {
//
//    }
//
//    @Override
//    public Map<String, String> getListParams() {
//        return null;
//    }
//
//    @Override
//    public Object objectParam() {
//        return null;
//    }
//
//    @Override
//    public String getAPI_URL() {
//        return null;
//    }
//
//
//    @Override
//    public void setApi(final String API_URL) {
//        //super.setApi(API_URL);
//        RequestQueue queue = volleyService.getRequestQueue(currentActivity.getApplicationContext());
//        StringRequest stringRequest = new StringRequest(HttpMethod, API_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Response(response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                    try {
//                      if(isFirst){
//                          connectDialog2.show(((AppCompatActivity)currentActivity).getSupportFragmentManager(),"network dialog");
//                          connectDialog2.setOnClickListenerReconnect(new ConnectDialog2.Listener() {
//                              @Override
//                              public void onClick() {
//                                  setApi(API_URL);
//                                  connectDialog2.dismiss();
//                                  isFirst = false;
//                              }
//                          });
//                          connectDialog2.setOnClickListenerCancel(new ConnectDialog2.Listener() {
//                              @Override
//                              public void onClick() {
//                                  connectDialog2.dismiss();
//                              }
//                          });
//                      }
//
//                    } catch (Exception e) {
//                        Utils.showMessage(currentActivity, "Error loading data from server !!");
//                        e.printStackTrace();
//                    }
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
//
//
//
//    @Override
//    public void setApi2(final String API_URL) {
//       // super.setApi2(API_URL);
//
//        RequestQueue queue = volleyService.getRequestQueue(currentActivity.getApplicationContext());
//
//        StringRequest sr = new StringRequest(HttpMethod, API_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Response(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try {
//                    if(isFirst){
//                            connectDialog2.show(((AppCompatActivity)currentActivity).getSupportFragmentManager(),"network dialog");
//                            connectDialog2.setOnClickListenerReconnect(new ConnectDialog2.Listener() {
//                                @Override
//                                public void onClick() {
//                                    setApi2(API_URL);
//                                    connectDialog2.dismiss();
//
//                                    isFirst = false;
//                                }
//                            });
//                            connectDialog2.setOnClickListenerCancel(new ConnectDialog2.Listener() {
//                                @Override
//                                public void onClick() {
//                                    connectDialog2.dismiss();
//                                }
//                            });
//                    }
//
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
