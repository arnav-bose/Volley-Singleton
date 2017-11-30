package com.arnav.volleysingleton;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

/**
 * Created by Arnav on 30/11/2017 at 06:00.
 */

public class NetworkHandler {

    private static volatile NetworkHandler networkHandlerSingletonInstance;
    private Context context;
    private RequestQueue mRequestQueue;
    private NetworkResponseListener mNetworkResponseListener;

    public NetworkHandler(Context context) {
        this.context = context;
        //Prevent form the reflection api.
        if (networkHandlerSingletonInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static NetworkHandler getInstance(Context context) {
        if (networkHandlerSingletonInstance == null) { //if there is no instance available... create new one
            synchronized (NetworkHandler.class) {
                if (networkHandlerSingletonInstance == null) {
                    networkHandlerSingletonInstance = new NetworkHandler(context);
                }

            }
        }
        return networkHandlerSingletonInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected NetworkHandler readResolve() {
        return getInstance(context);
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    public StringRequest initializeNetworkRequest(String url, int method, String body){
        Log.d("Volley", "Making Request to: " + url);
        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response != null){
                            Log.d("Volley", response);
                        }
                        if(mNetworkResponseListener != null){
                            mNetworkResponseListener.OnResponseLoaded(response);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null && error.getMessage() != null){
                            Log.d("Volley", error.getMessage());
                        }
                        if(mNetworkResponseListener != null){
                            mNetworkResponseListener.OnResponseFailed(error);
                        }
                    }
                })
        {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = networkResponseToString(response);
                if(mNetworkResponseListener != null){
                    mNetworkResponseListener.OnResponseReceived(responseString);
                }
                return super.parseNetworkResponse(response);
            }
        };

        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        getRequestQueue().add(stringRequest);
        return stringRequest;
    }

    public String networkResponseToString(NetworkResponse networkResponse) {
        try {
            return new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public NetworkResponseListener getNetworkResponseListener() {
        return mNetworkResponseListener;
    }

    public void setNetworkResponseListener(NetworkResponseListener mNetworkResponseListener) {
        this.mNetworkResponseListener = mNetworkResponseListener;
    }
}
