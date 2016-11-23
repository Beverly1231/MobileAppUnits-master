package com.example.iuliu.aa;




import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.XML;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static com.example.iuliu.aa.R.id.email;

public class MainActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;
    private ProgressDialog pgd;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get Reference to variables
        etEmail = (EditText) findViewById(email);
        etPassword = (EditText) findViewById(R.id.password);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    // Triggers when LOGIN Button clicked
    public void checkLogin(View arg0) {
        if((etEmail.getText().toString().length()>=1)&&(etPassword.getText().toString().length()>=1)){
            // Get text from email and passord field
            final String email = etEmail.getText().toString();
            final String password = etPassword.getText().toString();
            // Initialize  AsyncLogin() class with email and password
            Tasklogin login = new Tasklogin();
            login.read(email, password);
            login.execute();
        }else{
            Toast.makeText(getApplicationContext(),"Invalid Email or password.",Toast.LENGTH_SHORT).show();
        }
    }

    public void regster(View v){
        Intent homeIntent = new Intent(MainActivity.this, RegisterActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }



    public class Tasklogin extends AsyncTask<String, Void, Integer> {
        String lemail;
        String lpassword;

        private void read(String str, String str2) {
            lemail = str;
            lpassword = str2;
        }

        public Boolean getJSONStringFromUrl_GET(String url) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            StringBuilder stringBuilder;
            String line;
            String xmlString = "";
            Boolean bl = null;
            try {
                URL u = new URL(url);
                httpURLConnection = (HttpURLConnection) u.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + '\n');
                }
                xmlString = stringBuilder.toString();
                System.out.println("xmlString: " + xmlString);
                JSONObject jso = XML.toJSONObject(xmlString);
                JSONObject firstjso = jso.getJSONObject("employeess");
                JSONObject secjso = firstjso.getJSONObject("employees");
                System.out.println(firstjso);
                System.out.println(secjso);
                System.out.println("empEmail: " + secjso.getString("empEmail"));
                System.out.println("empPassword: " + secjso.getString("empPassword"));
                final String recEmail = secjso.getString("empEmail");
                final String recPassword = secjso.getString("empPassword");
                System.out.println("InputEmail: " + lemail);
                System.out.println("OutputEmail: " + recEmail);
                System.out.println("InputPassword: " + lpassword);
                System.out.println("OutputPassword: " + recPassword);
                if ((lemail.equals(recEmail)) && (lpassword.equals(recPassword))) {
                    bl = true;
                } else {
                    bl = false;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                //e.printStackTrace();
                System.out.println("Wrong email");
            } finally {
                httpURLConnection.disconnect();
            }
            return bl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgd = ProgressDialog.show(MainActivity.this, "Please wait...", "Processing...", true);
        }


        @Override
        protected Integer doInBackground(String... params) {
            JSONObject jsonObject;
            Integer result = 0;
            try {
                Boolean res = getJSONStringFromUrl_GET("http://192.168.0.100:8080/MainServerREST-master/api/employees/email/" + lemail);
                System.out.println(res);
                if (res == true) {
                    result = 1;
                }else if(res == false) {
                    result = 2;
                }else if(res == null){
                    result = 3;
                }
                //parseInformationFromArray(res);
            } catch (Exception e) {
                //e.printStackTrace();
                result = 3;
                return result;
            }
            return result;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer.equals(1)){
                Toast.makeText(getApplicationContext(),"Log in successful!",Toast.LENGTH_SHORT).show();
                Intent homeIntent = new Intent(MainActivity.this, DisplayScheduleActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }else if(integer.equals(2)){
                Toast.makeText(getApplicationContext(),"Failed: wrong password.",Toast.LENGTH_SHORT).show();
            }else if(integer.equals(3)){
                Toast.makeText(getApplicationContext(),"Failed: wrong Email address.",Toast.LENGTH_SHORT).show();
            }
            pgd.dismiss();
        }
    }


    //--------------------------------------------------------------------------------------------------------
}
