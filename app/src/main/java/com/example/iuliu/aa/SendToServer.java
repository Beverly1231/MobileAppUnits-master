package com.example.iuliu.aa;

import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by 46014 on 2016/11/7.
 */

public class SendToServer {

    public static void main(String[] args) throws MalformedURLException, IOException {

        SendToServer cnt = new SendToServer();
        Employees emp = new Employees(5,"Thor", "Svensson", "ThorSven", "12345", "ts@gmail.com","110",5,true);
        String postUrl = "http://localhost:8080/MainServerREST/api/entities.employees/";// put in your url

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(postUrl);
        StringEntity postingString = new StringEntity(cnt.convertToJSON(emp));//gson.tojson() converts your pojo to json
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(post);
        System.out.println(response.toString());
    }

    public void saveToJSON(Object o) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(o);
        try {
            FileWriter writeTofile = new FileWriter("src/home_commands.json");
            writeTofile.write(jsonString);
            writeTofile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String convertToJSON(Object c) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(c);

        System.out.println(jsonString);
        return jsonString;

    }
}
