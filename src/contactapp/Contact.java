/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactapp;

/**
 *
 * @author lopezz
 */
class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    
    public Contact(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
    public void editContact(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getNames() {
        return lastName + ", " + firstName;
    }
    
    public String[] getAll() {
        String[] tmp = new String[4];
        tmp[0] = firstName;
        tmp[1] = lastName;
        tmp[2] = phoneNumber;
        tmp[3] = email;
        return tmp;
    }
}
