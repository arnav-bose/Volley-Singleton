package com.arnav.volleysingleton;

import com.android.volley.VolleyError;

/**
 * Created by Arnav on 30/11/2017 at 06:20.
 */

public interface NetworkResponseListener {

    void OnResponseFailed(VolleyError error);

    void OnResponseReceived(String response);

    void OnResponseLoaded(String response);

}
