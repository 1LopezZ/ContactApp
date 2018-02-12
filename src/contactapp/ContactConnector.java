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
    
    static String hostName;
    
    public ContactConnector() {
        hostName = "https://node-postgres-contacts-obnyymjatr.now.sh";
        //LINK TO NODE.JS APP IN THE CLOUD
        //SERVER IS DEPLOYED USING ZEIT'S NOW
    }
    
    public static void postContact(Contact contact, int pos) {
        //ADDING CONTACT TO DB
        try {
            Contact[] res = null;
            URL url = new URL(hostName+"/postContact");
            //HTTP REQUEST LINK FOR POSING CONTACTS
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            System.out.println("POSTING CONTACT...");
            OutputStream os = conn.getOutputStream();
            os.write(contact.getQuery(pos).getBytes());
            //PASSING PARAMETERS OF CONTACT TO INTO HTTP STREAM
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("ERROR");
                throw new java.lang.RuntimeException("UNSUCCESSFUL PUT");
            }
            else {
                System.out.println("SUCCESS");
            }
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new java.lang.RuntimeException("SERVER OFFLINE");
        }
    }

    public static Contact[] getContacts() {
        //GETTING ALL CONTACTS FROM DB
        try {
            Contact[] res = null;
            URL url = new URL(hostName+"/getContacts");
            //HTTP REQUEST LINK FOR GETTING CONTACTS
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            System.out.println("GETTING CONTACTS...");
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new java.lang.RuntimeException("UNSUCCESSFUL GET");
            }
            else {
                System.out.println("PROCESSING CONTACTS...");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //CREATING A READER FOR INPUT FROM HTTP
                res = getRes(in);
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new java.lang.RuntimeException("SERVER OFFLINE");
        }
    }

    public static Contact[] getRes(BufferedReader in) {
        //GETTING RESULTS GIVEN INPUT STREAM
        JSONObject res = null;
        Contact[] contacts;
        try {
            //STORING RESULTS INTO JSONOBJECT
            String inputLine = in.readLine();
            while (inputLine != null) {
                res = new JSONObject(inputLine);
                System.out.println(inputLine);
                inputLine = in.readLine();
            }
            in.close();
            //ITERATES THROUGH ALL VALUES FROM CONTACTS
            //CREATES CONTACTS AND STORED INTO CONTACT ARRAY
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
        //CREATES A STRING QUERY TO BE PASSED AS HTTP REQUEST
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = param.keySet().iterator();
        while(itr.hasNext()){
            //DELIMITED BY = AND &
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
        //DELETING CONTACT FROM DB
        System.out.println("DELETING OLD CONTACTS...");
        try {
            Contact[] res = null;
            URL url = new URL(hostName+"/resetContacts");
            //HTTP REQUEST LINK FOR DELETING CONTACT
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new java.lang.RuntimeException("UNSUCCESSFUL DELETE");
            }
            else {
                System.out.println("SUCCESS");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new java.lang.RuntimeException("SERVER OFFLINE");
        }
    }

    void updateContact(Contact contact, int pos) throws RuntimeException {
        //UPDATING CONTACT IN DB
        try {
            Contact[] res = null;
            URL url = new URL(hostName+"/updateContact");
            //HTTP REQUEST FOR UPDATING CONTACT
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            System.out.println("UPDATING CONTACT...");
            OutputStream os = conn.getOutputStream();
            os.write(contact.getQuery(pos).getBytes());
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new java.lang.RuntimeException("UNSUCCESSFUL UPDATE");
            }
            else {
                System.out.println("SUCCESS");
            }
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new java.lang.RuntimeException("SERVER OFFLINE");
        }
    }
}
