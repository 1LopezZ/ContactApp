/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lopezz
 */
class Contact {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    
    public Contact(
            String firstName, 
            String lastName, 
            String phoneNo, 
            String email
    ) {
        //INSTANTIATING CONTACTS
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email = email;
    }
    
    public void editContact(
            String firstName, 
            String lastName, 
            String phoneNo, 
            String email
    ) {
        //EDITING ALL CONTACTS VALUES
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public String getNames() {
        //RETURN CONTACT NAMES FOR NAMES LIST
        return lastName + ", " + firstName;
    }
    
    public String[] getAll() {
        //RETURN CONTACT VALUES TO BE DISPLAYED ON FRAME
        String[] tmp = new String[4];
        tmp[0] = firstName;
        tmp[1] = lastName;
        tmp[2] = phoneNo;
        tmp[3] = email;
        return tmp;
    }
    
    public String getQuery(int pos) {
        StringBuilder result = new StringBuilder();
        try {
            //CREATES A STRING QUERY TO BE PASSED AS HTTP REQUEST
            result.append(URLEncoder.encode("id", "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(String.valueOf(pos), "UTF-8"));
            result.append("&");
            result.append(URLEncoder.encode("firstName", "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(firstName, "UTF-8"));
            result.append("&");
            result.append(URLEncoder.encode("lastName", "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(lastName, "UTF-8"));
            result.append("&");
            result.append(URLEncoder.encode("phoneNo", "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(phoneNo, "UTF-8"));
            result.append("&");
            result.append(URLEncoder.encode("email", "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(email, "UTF-8"));
            return result.toString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Contact.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
