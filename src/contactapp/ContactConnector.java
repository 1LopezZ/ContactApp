/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactapp;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author lopezz
 */
public class ContactConnector {
    public static void main(String[] params) {
        System.out.println("RUNNING");
        System.out.println(getContacts());
    }
    
    public static void postContact(Contact contact, int pos) {
        try {
            Contact[] res = null;
            URL url = new URL("http://localhost:3000/postContact");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            System.out.println("POSTING CONTACT...");
            OutputStream os = conn.getOutputStream();
            os.write(contact.getQuery(pos).getBytes());
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("ERROR");
            }
            else {
                System.out.println("SUCCESS");
            }
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Contact[] getContacts() {

        try {
            Contact[] res = null;
            URL url = new URL("http://localhost:3000/getContacts");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            System.out.println("GETTING CONTACTS...");
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            else {
                System.out.println("PROCESSING CONTACTS...");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                res = getRes(in);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Contact[] getRes(BufferedReader in) {
        JSONObject res = null;
        Contact[] contacts;
        try {
            String inputLine = in.readLine();
            while (inputLine != null) {
                res = new JSONObject(inputLine);
                System.out.println(inputLine);
                inputLine = in.readLine();
            }
            in.close();
            if(res != null) {
                JSONArray array = res.getJSONArray("contacts");
                contacts = new Contact[array.length()];
                for(int i = 0 ; i < array.length() ; i++){
                    JSONObject tmp = array.getJSONObject(i);
                    String firstname = tmp.getString("firstname");
                    String lastname = tmp.getString("lastname");
                    String phoneno = tmp.getString("phoneno");
                    String email = tmp.getString("email");
                    contacts[i] = new Contact(firstname,lastname,phoneno,email);
                    System.out.println(contacts[i].getNames());
                }
                return contacts;
            }
            else {
                return null;
            }
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        } 
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

    void deleteContact() {
        System.out.println("DELETING OLD CONTACTS...");
        try {
            Contact[] res = null;
            URL url = new URL("http://localhost:3000/resetContacts");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("ERROR");
            }
            else {
                System.out.println("SUCCESS");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR");
        }
    }

    void updateContact(Contact contact, int pos) {
        try {
            Contact[] res = null;
            URL url = new URL("http://localhost:3000/updateContact");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            System.out.println("UPDATING CONTACT...");
            OutputStream os = conn.getOutputStream();
            os.write(contact.getQuery(pos).getBytes());
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("ERROR");
            }
            else {
                System.out.println("SUCCESS");
            }
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
