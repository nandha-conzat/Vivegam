package rukina.vivegam.android.servicehelper;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;

import rukina.vivegam.android.R;
import rukina.vivegam.android.activity.Login;
import rukina.vivegam.android.app.AppController;
import rukina.vivegam.android.serviceinterfaces.IForgotPasswordServiceListener;
import rukina.vivegam.android.serviceinterfaces.IServiceListener;
import rukina.vivegam.android.serviceinterfaces.ISignUpServiceListener;
import rukina.vivegam.android.utils.Config;

/**
 * Created by zahid.r on 10/30/2015.
 */
public class SignUpServiceHelper {
    private String TAG = Login.class.getSimpleName();
    private Context context;
    ISignUpServiceListener signUpServiceListener;
    IForgotPasswordServiceListener forgotPasswordServiceListener;

    public SignUpServiceHelper(Context context) {
        this.context = context;
    }

    public void setSignUpServiceListener(ISignUpServiceListener signUpServiceListener) {
        this.signUpServiceListener = signUpServiceListener;
    }

    public void setForgotPasswordServiceListener(IForgotPasswordServiceListener forgotPasswordServiceListener) {
        this.forgotPasswordServiceListener = forgotPasswordServiceListener;
    }

    public void makeSignUpServiceCall(String params) {
        Log.d(TAG,"making sign in request"+ params);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Config.APP_SERVER_URL, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        signUpServiceListener.onSignUp(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    Log.d(TAG,"error during sign up"+ error.getLocalizedMessage());

                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        signUpServiceListener.onSignUpError(jsonObject.getString(Config.PARAM_MESSAGE));
                        String status = jsonObject.getString("status");
                        Log.d(TAG, "signup status is" + status);
                    } catch (UnsupportedEncodingException e) {
                        signUpServiceListener.onSignUpError(context.getResources().getString(R.string.error_occured));
                        e.printStackTrace();
                    } catch (JSONException e) {
                        signUpServiceListener.onSignUpError(context.getResources().getString(R.string.error_occured));
                        e.printStackTrace();
                    }

                } else {
                    signUpServiceListener.onSignUpError(context.getResources().getString(R.string.error_occured));
                }
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void makeGetEventServiceCall(String URL) {
        Log.d(TAG, "Events URL " + URL);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL, (String) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "ajaz : " + response.toString());
                        if(response != null) {
                            signUpServiceListener.onSignUp(response);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "error is" + error.getLocalizedMessage());
                if (error.networkResponse != null && error.networkResponse.data != null) {

                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        Log.d(TAG, "error response body is" + responseBody);
                        JSONObject jsonObject = new JSONObject(responseBody);
                        signUpServiceListener.onSignUpError(jsonObject.getString(Config.PARAM_MESSAGE));
                    } catch (UnsupportedEncodingException e) {
                        signUpServiceListener.onSignUpError(context.getResources().getString(R.string.error_occured));
                        e.printStackTrace();
                    } catch (JSONException e) {
                        signUpServiceListener.onSignUpError(context.getResources().getString(R.string.error_occured));
                        e.printStackTrace();
                    }

                } else {
                    signUpServiceListener.onSignUpError(context.getResources().getString(R.string.error_occured));
                }
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    public void updateUserProfile(String url, final IServiceListener listener){
        Log.d(TAG,"updateprofile URL"+ url);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, (String) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            //Parse the response and convert to Java class
                            listener.onSuccess(0,response);

                        }catch(Exception e){
                            Log.d(TAG,"Exception while parsing");
                            e.printStackTrace();
                            listener.onError("JSON Parser error");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.data != null) {

                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        listener.onError(jsonObject.getString(Config.PARAM_MESSAGE));
                    } catch (UnsupportedEncodingException e) {
                        listener.onError(context.getResources().getString(R.string.error_occured));
                        e.printStackTrace();
                    } catch (JSONException e) {
                        listener.onError(context.getResources().getString(R.string.error_occured));
                        e.printStackTrace();
                    }

                } else {
                    listener.onError(context.getResources().getString(R.string.error_occured));
                }
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void uploadUserImage(String url, final String image, final IServiceListener listener){
        Log.d(TAG,"uploading user image"+ url);
       // StringRequest request = new StringRequest(Request.Method.POST,url,params,new Res)

            //Showing the progress dialog

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        Log.d(TAG, "Succesfully uploaded the image"+ s);
                       listener.onSuccess(0,s);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        Log.e(TAG,"error loading image"+ volleyError.getLocalizedMessage());
                        listener.onError(volleyError.getLocalizedMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                Log.d(TAG,"getting image parameters");


                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("fileToUpload", image);
                params.put("name", "hobistan.jpg");

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void makeForgotPasswordServiceCall(String params) {
        Log.d(TAG,"making forgot password request"+ params);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Config.GET_SIGN_UP_URL, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        forgotPasswordServiceListener.onForgotPassword(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    Log.d(TAG,"error during sign up"+ error.getLocalizedMessage());

                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        forgotPasswordServiceListener.onForgotPasswordError(jsonObject.getString(Config.PARAM_MESSAGE));
                        String status = jsonObject.getString("status");
                        Log.d(TAG, "forgot password status is" + status);
                    } catch (UnsupportedEncodingException e) {
                        forgotPasswordServiceListener.onForgotPasswordError(context.getResources().getString(R.string.error_occured));
                        e.printStackTrace();
                    } catch (JSONException e) {
                        forgotPasswordServiceListener.onForgotPasswordError(context.getResources().getString(R.string.error_occured));
                        e.printStackTrace();
                    }

                } else {
                    forgotPasswordServiceListener.onForgotPasswordError(context.getResources().getString(R.string.error_occured));
                }
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }
}
