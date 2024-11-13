/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
public class updatepassword extends JFrame implements ActionListener{
    

    JTextField usernamebox, currentPasswordBox, newPasswordBox, confirmPasswordBox;
    JButton update, cancel;
    String role;  
    updatepassword(String role) {
        this.role = role;

        setSize(500, 450);
        setLocation(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
 
        getContentPane().setBackground(Color.BLACK);
 
        Font labelFont = new Font("Tahoma", Font.PLAIN, 18);
        Font textFieldFont = new Font("Tahoma", Font.PLAIN, 16);
 
        JLabel heading = new JLabel("Update Password");
        heading.setBounds(100, 30, 300, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30)); 
        heading.setForeground(Color.WHITE);
        add(heading);
 
        JLabel username = new JLabel("Username");
        username.setBounds(40, 100, 150, 30);
        username.setForeground(Color.WHITE);
        username.setFont(labelFont);
        add(username);

        usernamebox = new JTextField();
        usernamebox.setBounds(230, 100, 200, 30);
        usernamebox.setBackground(Color.WHITE);
        usernamebox.setForeground(Color.BLACK);
        usernamebox.setFont(textFieldFont);
        add(usernamebox);
 
        JLabel currentPassword = new JLabel("Current Password");
        currentPassword.setBounds(40, 150, 150, 30);
        currentPassword.setForeground(Color.WHITE);
        currentPassword.setFont(labelFont);
        add(currentPassword);

        currentPasswordBox = new JPasswordField();
        currentPasswordBox.setBounds(230, 150, 200, 30);
        currentPasswordBox.setBackground(Color.WHITE);
        currentPasswordBox.setForeground(Color.BLACK);
        currentPasswordBox.setFont(textFieldFont);
        add(currentPasswordBox);
 
        JLabel newPassword = new JLabel("New Password");
        newPassword.setBounds(40, 200, 150, 30);
        newPassword.setForeground(Color.WHITE);
        newPassword.setFont(labelFont);
        add(newPassword);

        newPasswordBox = new JPasswordField();
        newPasswordBox.setBounds(230, 200, 200, 30);
        newPasswordBox.setBackground(Color.WHITE);
        newPasswordBox.setForeground(Color.BLACK);
        newPasswordBox.setFont(textFieldFont);
        add(newPasswordBox);
 
        JLabel confirmPassword = new JLabel("Confirm Password");
        confirmPassword.setBounds(40, 250, 150, 30);
        confirmPassword.setForeground(Color.WHITE);
        confirmPassword.setFont(labelFont);
        add(confirmPassword);

        confirmPasswordBox = new JPasswordField();
        confirmPasswordBox.setBounds(230, 250, 200, 30);
        confirmPasswordBox.setBackground(Color.WHITE);
        confirmPasswordBox.setForeground(Color.BLACK);
        confirmPasswordBox.setFont(textFieldFont);
        add(confirmPasswordBox);
 
        update = new JButton("Update");
        update.setBounds(70, 320, 150, 35);
        update.addActionListener(this);
        update.setFont(labelFont);
        update.setBackground(Color.WHITE);
        update.setForeground(Color.BLACK);
        add(update);
 
        cancel = new JButton("Cancel");
        cancel.setBounds(250, 320, 150, 35);
        cancel.addActionListener(this);
        cancel.setFont(labelFont);
        cancel.setBackground(Color.WHITE);
        cancel.setForeground(Color.BLACK);
        add(cancel);

        setVisible(true);
    }
 
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            String username = usernamebox.getText();
            String currentPassword = currentPasswordBox.getText();
            String newPassword = newPasswordBox.getText();
            String confirmPassword = confirmPasswordBox.getText();
 
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "New passwords do not match.");
                return;
            }

            String query = "";
            if (role.equals("admin")) {
                query = "SELECT * FROM ownerlogin WHERE username = '" + username + "' AND password = '" + currentPassword + "'";
            } else if (role.equals("owner")) {
                query = "SELECT * FROM userlogin WHERE username = '" + username + "' AND password = '" + currentPassword + "'";
            }

            try {
                conn c = new conn();
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) { 
                    String updateQuery = "";
                    if (role.equals("owner")) {
                        updateQuery = "UPDATE ownerlogin SET password = '" + newPassword + "' WHERE username = '" + username + "'";
                    } else if (role.equals("user")) {
                        updateQuery = "UPDATE userlogin SET password = '" + newPassword + "' WHERE username = '" + username + "'";
                    }

                    c.s.executeUpdate(updateQuery);
                    JOptionPane.showMessageDialog(null, "Password updated successfully.");
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid current password.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) { 
        new updatepassword("owner");   
    }
}
