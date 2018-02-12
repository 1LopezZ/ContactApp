/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactapp;

import javax.swing.JOptionPane;

/**
 *
 * @author lopezz
 */
public class ContactApp { //APP BASE CLASS

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
        // TODO code application logic here
        try { //INSTANTIATE MAIN FRAMS AND MANAGER
            ContactManager manager = new ContactManager();
            ContactFrame frame = new ContactFrame(manager);
            frame.setVisible(true);
        }
        catch (RuntimeException e) { //CATCHING SERVER ERRORS
            System.out.println("SERVER CURRENTLY DOWN");
            System.out.println("CLOSING");
            JOptionPane.showMessageDialog( //CREATING ERROR BOX
                    null, 
                    "Server down; please try again later.", 
                    "Contact App", 
                    JOptionPane.INFORMATION_MESSAGE
            );            System.exit(1);
        }
    }  
}
