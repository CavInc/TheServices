package cav.theservices.data.networks;

import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.DeviceModel;
import cav.theservices.data.models.LangDataModel;
import cav.theservices.data.models.ServiceEditModel;
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
            //BASE_URL = "http://45.62.122.33:8080/";
           // BASE_URL = "http://192.168.1.23:5000/";
          //  BASE_URL = "http://192.168.56.33:8081/";
            BASE_URL = "http://kempir.com/scx/";
        }
    }

    public void registry(String deviceId,int deviceMode,String deviceName){
        HttpPost post= new HttpPost(BASE_URL+ ConstantManager.URL_REGISTRY);

        post.addHeader("Accept", "application/json");
        //post.addHeader("Content-Type", "application/json; charset=utf-8"); // при urldecode и php работает криво

        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("deviceID", deviceId));
        nameValuePairs.add(new BasicNameValuePair("deviceMode",String.valueOf(deviceMode)));
        nameValuePairs.add(new BasicNameValuePair("deviceName",deviceName));

        try {
            //post.setEntity(new StringEntity(nameValuePairs,"utf-8"));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
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
    public void sendDemand(String deviceId,int demandID,String comment){
        HttpPost post= new HttpPost(BASE_URL+ ConstantManager.URL_DEMAND);
        post.addHeader("Accept", "application/json");
       // post.addHeader("Content-Type", "application/json; charset=utf-8");
        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("deviceID", deviceId));
        nameValuePairs.add(new BasicNameValuePair("demandID",String.valueOf(demandID)));
        nameValuePairs.add(new BasicNameValuePair("demandComment",comment));


        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }

        try {
            String response = mHttpClient.execute(post,new BasicResponseHandler());
            Log.d(TAG,"OK");
            Log.d(TAG, response);
            jObj = new JSONObject(response);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

    // запрос всех устройств зарегестрированных
    public ArrayList<DeviceModel> getAllDevice(){
        ArrayList<DeviceModel> rec = new ArrayList<>();
        HttpPost post= new HttpPost(BASE_URL+ ConstantManager.URL_ALLDEVICE);
        post.addHeader("Accept", "application/json");
       // post.addHeader("Content-Type", "application/json; charset=utf-8");
        List nameValuePairs = new ArrayList(1);

        try {
            HttpResponse response = mHttpClient.execute(post);
            Header[] header = response.getAllHeaders();
            for (int i=0;i<header.length;i++){
                Log.d(TAG, String.valueOf(header[i]));
            }

            String result = EntityUtils.toString(response.getEntity());
            Log.d(TAG,result);

            jObj = new JSONObject(result);
            if (jObj.has("status") && jObj.getString("status").equals("ok")){
                //JSONObject jdata= (JSONObject)jObj.get("devices");
                JSONArray jarr = jObj.getJSONArray("devices");
                for (int i = 0;i < jarr.length();i++){
                    JSONObject lx =  (JSONObject) jarr.get(i);
                    Log.d(TAG,lx.getString("deviceID"));
                    Log.d(TAG,lx.getString("deviceName"));
                    mDataManager.getDB().addDevices(lx.getString("deviceID"),lx.getInt("deviceMode"),lx.getString("deviceName"));
                    rec.add(new DeviceModel(lx.getString("deviceID"),lx.getInt("deviceMode"),lx.getString("deviceName")));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rec;
    }

    // сервис на сервер.
    public void sendService(ServiceEditModel data){
        HttpPost post= new HttpPost(BASE_URL+ ConstantManager.URL_SERVICE);
        post.addHeader("Accept", "application/json");
        //post.addHeader("Content-Type", "application/json; charset=utf-8");

        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("serviceID",String.valueOf(data.getId())));
        nameValuePairs.add(new BasicNameValuePair("servicePrice",String.valueOf(data.getPrice())));

        ArrayList<LangDataModel> rec_data = data.getData();
        nameValuePairs.add(new BasicNameValuePair("serviceRec",getJSONLangDataModel(rec_data)));

        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }

        try {
            HttpResponse response = mHttpClient.execute(post);
            Header[] header = response.getAllHeaders();
            for (int i=0;i<header.length;i++){
                Log.d(TAG, String.valueOf(header[i]));
            }

            String result = EntityUtils.toString(response.getEntity());
            Log.d(TAG,result);
            jObj = new JSONObject(result);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String getJSONLangDataModel (ArrayList<LangDataModel>  data){
        JSONObject bot = new JSONObject();
        JSONArray bt = new JSONArray();
        try {
            int i =0;
            for (LangDataModel l : data) {
                JSONObject rx = new JSONObject();
                rx.put("langID",l.getLang());
                rx.put("title",l.getTitle());
                rx.put("body",l.getBody());
                bt.put(rx);
                i+=1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bt.toString();
    }

    // Запрос всех сервисов
    public ArrayList<ServiceEditModel> getAllService(){
        ArrayList<ServiceEditModel> rec = new ArrayList<>();
        HttpPost post= new HttpPost(BASE_URL+ ConstantManager.URl_ALLSERVICE);
        post.addHeader("Accept", "application/json");
        post.addHeader("Content-Type", "application/json; charset=utf-8");


        try {
            HttpResponse response = mHttpClient.execute(post);
            Header[] header = response.getAllHeaders();
            for (int i=0;i<header.length;i++){
                Log.d(TAG, String.valueOf(header[i]));
            }

            String result = EntityUtils.toString(response.getEntity());
            Log.d(TAG,result);

            jObj = new JSONObject(result);

           ;

            if (jObj.has("status") && jObj.getString("status").equals("ok")){
                //JSONObject jdata= (JSONObject)jObj.get("devices");
                JSONArray jarr = jObj.getJSONArray("services");
                for (int i = 0;i < jarr.length();i++){
                    JSONObject lx =  (JSONObject) jarr.get(i);
                    Log.d(TAG,lx.toString());
                    ArrayList<LangDataModel> spec = new ArrayList<>();
                    JSONArray jspec = lx.getJSONArray("serviceSpec");
                    for (int j = 0; j< jspec.length();j++){
                        Log.d(TAG,jspec.get(j).toString());
                        JSONObject lm = (JSONObject) jspec.get(j);
                        spec.add(new LangDataModel(
                                lm.getInt("langID"),
                                lm.getString("title"),
                                lm.getString("body")
                        ));
                    }
                    rec.add(new ServiceEditModel(lx.getInt("serviceID"),
                            Float.parseFloat(lx.getString("servicePrice")),"","",spec));
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rec;
    }

    // запрос новых заявок


}