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
public class ContactManager {
    private static int editState;
    private static int curSelected;
    private static int curPos; //number of added contacts
    private static Contact[] contacts;
    private static String[] names;
    private static int max;
    
    public ContactManager() {
        max = 5;
        contacts = new Contact[max];
        names = new String[max];
        curSelected = 0;
        curPos = 0;
    }
    
    /**
     *
     * @param pos
     */
    public static void setCurSelected(int pos) {
        curSelected = pos;
    }
    
    public static void setEditState(String state) {
        if(state.equals("EDIT")) {
            editState = 1;
        }
        else if(state.equals("CREATE")) {
            editState = 0;
        }
        else {
            editState = -1;
        }
    }
    
    public static int getEditState() {
        return editState;
    }
    
    public static String[] makeContact(String firstName, String lastName, String phoneNo, String email) {
        if(editState == 0) { //CREATING
            contacts[curPos] = new Contact(firstName, lastName, phoneNo, email);
            names[curPos] = contacts[curPos].getNames();
            curPos = curPos + 1;
        }
        else { //EDITING
            if(curSelected != -1) {
                contacts[curSelected].editContact(firstName, lastName, phoneNo, email);
                names[curSelected] = contacts[curSelected].getNames();
            }
        }
        return getAllNames();
    }
    
    public static String[] getAllNames() {
        return names;
    }

    public static String[] getCurData() {
        return contacts[curSelected].getAll();
    }

    public static int getCurPos() {
        return curPos;
    }
    
    public static int getMax() {
        return max;
    }

    public static String deleteContact() {
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
    
}
