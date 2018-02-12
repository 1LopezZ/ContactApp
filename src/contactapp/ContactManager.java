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
public class ContactManager { //APP BACKEND
    private static int editState; 
    //0 FOR CREATE, 1 FOR EDIT, -1 FOR DELETE
    private static int curSelected; //SELECTED INDEX
    private static int curPos; //NUMBER OF ADDED CONTACTS
    private static Contact[] contacts;
    private static String[] names;
    private static int max; //MAXIMUM NUMBER OF CONTACTS
    private static ContactConnector connector;
    
    public ContactManager() { //CLASS INSTANTIATION
        max = 20;
        contacts = new Contact[max];
        names = new String[max];
        curSelected = 0;
        curPos = 0;
        connector = new ContactConnector();
    }
    
    /**
     *
     * @param pos
     */
    public static void setCurSelected(int pos) { //CURSELECTED SETTER
        curSelected = pos;
    }
    
    public static void setEditState(String state) { //EDITSTATE SETTER
        if(state.equals("EDIT")) {
            editState = 1;
        }
        else if(state.equals("CREATE")) {
            editState = 0;
        }
        else {
            editState = -1; //DELETE STATE
        }
    }
    
    public static int getEditState() { //EDIT STATE GETTER
        return editState;
    }
    
    public static String[] makeContact( //UPDATES CONTACT AND NAMES LIST
            String firstName, 
            String lastName, 
            String phoneNo, 
            String email
    ) {
        if(editState == 0) { //CREATING CONTACT
            contacts[curPos] = new Contact(
                    firstName, 
                    lastName, 
                    phoneNo, 
                    email
            );
            names[curPos] = contacts[curPos].getNames();
            curPos = curPos + 1;
        }
        else { //EDITING CONTACT
            if(curSelected != -1) {
                contacts[curSelected].editContact(
                        firstName, 
                        lastName, 
                        phoneNo, 
                        email
                );
                names[curSelected] = contacts[curSelected].getNames();
            }
        }
        return getAllNames();
    }
    
    public static String[] getPrvContacts() { //GET ALL CONTACT FROM DB
        try {
            Contact[] tmp = connector.getContacts();
            for(int i = 0; i < tmp.length; i++) {
                contacts[i] = tmp[i];
                names[i] = contacts[i].getNames();
            }
            curPos = tmp.length;
            return names;
        }
        catch (RuntimeException e) {
            throw e;
        }
    }
    
    public static String[] getAllNames() { //NAMES GETTER
        return names;
    }

    public static String[] getCurData() { //CURSELECTED CONTACT GETTER
        return contacts[curSelected].getAll();
    }

    public static int getCurPos() { //CURPOS GETTER
        return curPos;
    }
    
    public static int getMax() { //MAX GETTER
        return max;
    }

    public static String deleteContact() { //DELETES FROM CONTACTS
        String tmp = names[curSelected];
        for(int i = curSelected; i < curPos-1; i++) {
            names[i] = names[i+1];
            contacts[i] = contacts[i+1];
        }
        contacts[curPos-1] = null;
        names[curPos-1] = null;
        curPos = curPos - 1;
        return "Deleted "+tmp;
    }

    public static String updateCancelled() { //STATUS LABEL FOR MAIN FRAME (CANCEL)
        if(getEditState() == 1) {
            return "Editing contact cancelled.";
        }
        else if(getEditState() == 0){
            return "Adding contact cancelled.";
        }
        else {
            return "Contact App";
        }
    }

    String updateSuccess() { //STATUS LABEL FOR MAIN FRAME (SUCCESS)
        try {
            if(getEditState() == 1) {
                connector.updateContact(contacts[curSelected],curSelected+1);
                return "A contact has been edited.";
            }
            else if(getEditState() == 0){
                connector.postContact(contacts[curPos-1],curPos);
                return "A contact has been added.";
            }
            else {
                connector.deleteContact();
                for(int i = 0; i < 20; i++) {
                    if(contacts[i] != null) {
                        connector.postContact(contacts[i],i+1);
                    }
                }
                return "Contact App";
            }
        }
        catch (RuntimeException e) {
            getPrvContacts();
            return "Error -- Contacts Reset";
        }
    }
    
}
