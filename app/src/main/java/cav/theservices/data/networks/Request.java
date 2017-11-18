package cav.theservices.data.networks;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.DeviceModel;
import cav.theservices.utils.ConstantManager;

@SuppressWarnings({"deprecation"})
public class Request {

    private static final String TAG = "REQUEST";
    private HttpClient mHttpClient;

    private JSONObject jObj = null;
    private DataManager mDataManager;

    private String BASE_URL;

    public Request(){
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(params,"Android App (The Services)");

        mHttpClient = new DefaultHttpClient(params);
        mDataManager = DataManager.getInstance();
        BASE_URL = mDataManager.getPreferenseManager().getComandServerUrl();
        if (BASE_URL == null || BASE_URL.length() == 0) {
            BASE_URL = "http://45.62.122.33:8080/";
        }
    }

    public void registry(String deviceId,int deviceMode,String deviceName){
        HttpPost post= new HttpPost(BASE_URL+ ConstantManager.URL_REGISTRY);

        post.addHeader("Accept", "application/json");
        post.addHeader("Content-Type", "application/json; charset=utf-8");

        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("deviceID", deviceId));
        nameValuePairs.add(new BasicNameValuePair("deviceMode",String.valueOf(deviceMode)));
        nameValuePairs.add(new BasicNameValuePair("deviceName",deviceName));

        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse fx = mHttpClient.execute(post);
            Log.d(TAG, String.valueOf(fx.getAllHeaders()));
            String response = mHttpClient.execute(post,new BasicResponseHandler());
            Log.d(TAG,"OK");
            Log.d(TAG, response);
            jObj = new JSONObject(response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // оправляем заказ на сервер.
    public void sendDemand(String deviceId,int demandID){
        HttpPost post= new HttpPost(BASE_URL+ ConstantManager.URL_DEMAND);
        post.addHeader("Accept", "application/json");
        List nameValuePairs = new ArrayList(2);
        nameValuePairs.add(new BasicNameValuePair("deviceID", deviceId));
        nameValuePairs.add(new BasicNameValuePair("demandID",String.valueOf(demandID)));


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

    // запрос всех устройств зарегестрированных
    public ArrayList<DeviceModel> getAllDevice(){
        ArrayList<DeviceModel> rec = new ArrayList<>();
        HttpPost post= new HttpPost(BASE_URL+ ConstantManager.URL_ALLDEVICE);
        post.addHeader("Accept", "application/json");
        List nameValuePairs = new ArrayList(1);

        return rec;
    }


}