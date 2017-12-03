package com.arnav.volleysingleton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button buttonRequest;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        buttonRequest.setOnClickListener(this);
    }

    private void initComponents() {
        buttonRequest = (Button) findViewById(R.id.buttonRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonRequest: {
                requestHotOffers();
                break;
            }
        }
    }

    private void requestHotOffers() {
        NetworkHandler networkHandler = NetworkHandler.getInstance(MainActivity.this);
        networkHandler.setNetworkResponseListener(new NetworkResponseListener() {
            @Override
            public void OnResponseFailed(VolleyError error) {

            }

            @Override
            public void OnResponseReceived(String response) {

            }

            @Override
            public void OnResponseLoaded(String response) {

            }
        });
        StringRequest stringRequest = networkHandler.initializeNetworkRequest(url, Request.Method.GET, "");
        addStringRequestToList(stringRequest);
    }
}
