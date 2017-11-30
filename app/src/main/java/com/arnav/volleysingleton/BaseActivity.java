package com.arnav.volleysingleton;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

/**
 * Created by Arnav on 30/11/2017 at 07:06.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private ArrayList<StringRequest> arrayListStringRequests;

    public void addStringRequestToList(StringRequest stringRequest){
        if(arrayListStringRequests == null){
            arrayListStringRequests = new ArrayList<>();
        }
        arrayListStringRequests.add(stringRequest);
        Log.d("Volley", "Array List Request Size: " + String.valueOf(arrayListStringRequests.size()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelPageRequests();
        Log.d("Volley", "Cancelling string requests");
    }

    private void cancelPageRequests(){
        if(arrayListStringRequests != null){
            for(int i = 0; i < arrayListStringRequests.size(); i++){
                StringRequest stringRequest = arrayListStringRequests.get(i);
                if(stringRequest != null){
                    stringRequest.cancel();
                }
            }
        }
    }
}
