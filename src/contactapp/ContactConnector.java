/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactapp;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 *
 * @author lopezz
 */
public class ContactConnector {
    public static void main(String[] params) {
        System.out.println("RUNNING");
        System.out.println(getUsers());
    }
    
    public static String getUsers() {
        try {
            System.out.println("CONNECTING TO LOCAL HOST...");
            URL url = new URL("http://localhost:3000/users/getID");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            System.out.println("GETTING ID ...");
            OutputStream os = conn.getOutputStream();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                os.flush();
                os.close();
                return "Error";
            }
            else {
                System.out.println("PROCESSING ID ...");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String[] res = getRes(in);
                os.flush();
                os.close();
            }
            return "Success";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
    
    public static String[] getRes(BufferedReader in) {
        ArrayList res = new ArrayList();
        try {
            String inputLine = in.readLine();
            while (inputLine != null) {
                res.add(inputLine);
                System.out.println(inputLine);
                inputLine = in.readLine();
            }
            in.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return (String[]) res.toArray();
    }
    
    public static String createQueryString(HashMap param) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = param.keySet().iterator();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = param.get(key);

            if (first) {
                first = false;
            }
            else {
                result.append("&");
            }
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}
