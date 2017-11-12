package cav.theservices.data.networks;

import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cav.theservices.data.managers.DataManager;
import cav.theservices.utils.ConstantManager;

@SuppressWarnings({"deprecation"})
public class Request {

    private static final String TAG = "REQUEST";
    private HttpClient mHttpClient;

    private JSONObject jObj = null;
    private DataManager mDataManager;

    private String BASE_URL;

    public Request(){
        mHttpClient = new DefaultHttpClient();
        mDataManager = DataManager.getInstance();
        BASE_URL = mDataManager.getPreferenseManager().getComandServerUrl();
        if (BASE_URL == null || BASE_URL.length() == 0) {
            BASE_URL = "http://45.62.122.33:8080/";
        }
    }

    public void registry(String deviceId,int deviceMode,String deviceName){
        HttpPost post= new HttpPost(BASE_URL+ ConstantManager.URL_REGISTRY);
        post.addHeader("Accept", "application/json");
        List nameValuePairs = new ArrayList(2);
        nameValuePairs.add(new BasicNameValuePair("deviceID", deviceId));
        nameValuePairs.add(new BasicNameValuePair("deviceMode",String.valueOf(deviceMode)));
        nameValuePairs.add(new BasicNameValuePair("deviceName",deviceName));

        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            String response = mHttpClient.execute(post,new BasicResponseHandler());
            Log.d(TAG,"OK");
            Log.d(TAG, response);
            jObj = new JSONObject(response);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}