package com.example.iuliu.aa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import static com.example.iuliu.aa.R.id.email;


/**
 * Created by 46014 on 2016/11/15.
 */

public class RegisterActivity extends AppCompatActivity {
    private ProgressDialog pgd;
    private EditText ReUsername;
    private EditText RePassword;
    private EditText ReEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button bkbtn = (Button)findViewById(R.id.btnLinkToLoginScreen);
        Button regbtn = (Button) findViewById(R.id.btnRegister);
        ReEmail = (EditText) findViewById(R.id.registerEmail);
        RePassword = (EditText) findViewById(R.id.registerPassword);
        ReUsername = (EditText) findViewById(R.id.registerName);
        bkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Taskreg regist = new Taskreg();
                regist.read(ReEmail.getText().toString(),RePassword.getText().toString(),ReUsername.getText().toString());
                regist.execute();
            }
        });
    }

    public class Taskreg extends AsyncTask<String, Void, Integer> {
        String emails;
        String pswd;
        String username;

        private void read(String email, String pwd, String user) {
            emails = email;
            pswd = pwd;
            username = user;
        }

        public Boolean getJSONStringFromUrl_POST(String url) {
            Gson gson = new Gson();
            Gson gson2 = new GsonBuilder().create();
            Managers mng = new Managers(1,"Binx","Chenx","binchenm","33333","44@gmail.com","55555");
            Employees emp = new Employees(0,"first","last",username,pswd,emails,"number",mng,true);
            String json = gson2.toJson(emp);
            System.out.println(json.toString());
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            StringBuilder stringBuilder;
            String line;
            String xmlString = "";
            Boolean bl = null;
            try {
                String xml = XML.toString(json);
                System.out.println(xml);
                URL u = new URL(url);
                httpURLConnection = (HttpURLConnection) u.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
                wr.write(xml);
                wr.flush();

                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                stringBuilder = new StringBuilder();
                //while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(bufferedReader.readLine() + '\n');
                //}
                xmlString = stringBuilder.toString();
                System.out.println("xmlString: " + xmlString);
                JSONObject jso = XML.toJSONObject(xmlString);
                if(xmlString != null){
                    bl = true;
                }else{
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
            pgd = ProgressDialog.show(RegisterActivity.this, "Please wait...", "Processing...", true);
        }


        @Override
        protected Integer doInBackground(String... params) {
            JSONObject jsonObject;
            Integer result = 0;;
            try {
                Boolean res = getJSONStringFromUrl_POST("http://194.47.46.112:8080/MainServerREST-master/api/employees/");
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
                Intent homeIntent = new Intent(RegisterActivity.this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }else if(integer.equals(2)){
                Toast.makeText(getApplicationContext(),"Failed: wrong password.",Toast.LENGTH_SHORT).show();
            }else if(integer.equals(3)){
                Toast.makeText(getApplicationContext(),"Failed: wrong Email address.",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
