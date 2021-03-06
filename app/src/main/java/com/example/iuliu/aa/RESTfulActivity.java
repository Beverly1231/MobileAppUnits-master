package com.example.iuliu.aa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceActivity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.*;
import org.json.JSONStringer;
import org.json.XML;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class RESTfulActivity extends FragmentActivity {

    private ProgressDialog pgd;
    private TextView txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restful);
        txtview = (TextView) findViewById(R.id.textView);

    }

    public void BacktoCar(View v){
        Intent intent = new Intent (RESTfulActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    public void Send(View v){
        System.out.println("123432435345");
        new Tasklogin().execute();
    }

    public void Get(View v){
        RetrieveFromServer cnt = new RetrieveFromServer();
    }

    public class Tasklogin extends AsyncTask<String, Void, Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgd = ProgressDialog.show(RESTfulActivity.this,"Please wait...","Processing...",true);
        }


        @Override
        protected Integer doInBackground(String... params) {
            Map<String,String> param = new HashMap<>();
            JSONObject jsonObject;
            try{
                getJSONStringFromUrl_GET("http://192.168.1.9:8080/MainServerREST-master/api/employees/email/33@mail.com");
                //System.out.println(res);
                //parseInformationFromArray(res);
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            pgd.dismiss();
        }
    }


    //--------------------------------------------------------------------------------------------------------

    /**
     * Call service api with GET method and then return result form service as json string
     * @param url
     * @return
     */
    public String getJSONStringFromUrl_GET(String url){
        Gson gson = new Gson();
        JsonArray jsonArray = null;
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder;
        String line;
        String xmlString = "";
        try {
            URL u = new URL(url);
            httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("GET");
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            stringBuilder = new StringBuilder();
            Employees emp = new Employees();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
            xmlString = stringBuilder.toString();
            System.out.println(xmlString);
            //JsonObject obj = XML.toJSONObject(xmlString);
            JSONObject obj = XML.toJSONObject(xmlString);
            System.out.println(obj);
            emp = gson.fromJson(obj.toString(),Employees.class);
            //emp = gson.fromJson(obj.toString(),Employees.class);
            System.out.println(emp);

            JsonElement Firstname = (JsonObject)obj.get("empFirstname");
            JsonElement lastName = (JsonObject) obj.get("empLastname");
            System.out.println("Found the information(example) " + "FirstName = " +Firstname + " LastName = " + lastName);
            //convert2emp(bufferedReader);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }

        return xmlString;
    }

    public void convert2emp(BufferedReader brd){
        Gson gson = new Gson();
        JsonArray jsonArray = null;
        jsonArray = new JsonParser().parse(brd).getAsJsonArray();
        for (int i = 0; i< jsonArray.size();i++){
            JsonElement str = jsonArray.get(i);
            Employees emp = gson.fromJson(str,Employees.class);
            System.out.println(emp);
            System.out.println(str);
        }
    }


    /**
     * Convert json string to json object
     * @param jsonString
     * @return
     */

    public JSONObject convertJSONString2Obj(String jsonString) {
        JSONObject jObj = null;
        try {
            Log.w("convertJSONString2Obj", "JsonString=" + jsonString);
            jObj = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jObj;
    }

    /**
     * Get json string from URL with method POST
     * @param serviceUrl
     * @param params post data
     * @return json string
     */
    public String getJSONStringWithParam_POST(String serviceUrl, Map<String, String> params)
            throws IOException
    {
        JSONArray jsonArray = null;
        String jsonString = null;
        HttpURLConnection conn = null;
        String line;

        URL url;
        try
        {
            url = new URL(serviceUrl);
        }
        catch (MalformedURLException e)
        {
            throw new IllegalArgumentException("invalid url: " + serviceUrl);
        }

        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext())
        {
            Map.Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }

        String body = bodyBuilder.toString();
        Log.w("getJSONStringWithParam", "param=>" + body);
        byte[] bytes = body.getBytes();
        try {

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();

            Log.w("getJSONStringWithParam", "Response Status = " + status);
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }

            BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();


            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line + '\n');
            }

            jsonString = stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return jsonString;
    }


    public String getJSONStringWithParam_POST(String serviceUrl,String params)
            throws IOException
    {
        JSONArray jsonArray = null;
        String jsonString = null;
        HttpURLConnection conn = null;
        String line;

        URL url;
        try
        {
            url = new URL(serviceUrl);
        }
        catch (MalformedURLException e)
        {
            throw new IllegalArgumentException("invalid url: " + serviceUrl);
        }

        Log.w("getJSONStringWithParam", "param=>" + params);
        byte[] bytes = params.getBytes();
        try {

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write (bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();

            Log.w("getJSONStringWithParam", "Response Status = " + status);
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }

            BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();


            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line + '\n');
            }

            jsonString = stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return jsonString;
    }
}




