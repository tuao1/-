package com.example.tuao.myapp.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetRequest {
    private String result;

    public String getResult() {
        return result;
    }

    public void sendRequest(URL url){

                //联网请求
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream in=connection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    result=response.toString();
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader!=null){
                        try {
                            reader.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }

    }

