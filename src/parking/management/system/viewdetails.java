/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class viewdetails extends JFrame implements ActionListener {
    

    JTextField usernamebox;
    JButton viewDetails, cancel;
    String role;  
 
    viewdetails(String role) {
        this.role = role;

        setSize(400, 300);
        setLocation(760, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
 
        getContentPane().setBackground(Color.BLACK);
 
        Font labelFont = new Font("Tahoma", Font.PLAIN, 18);
        Font textFieldFont = new Font("Tahoma", Font.PLAIN, 16);
 
        JLabel heading = new JLabel("View Details");
        heading.setBounds(80, 30, 300, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 35));
        heading.setForeground(Color.WHITE);
        add(heading);
 
        JLabel username = new JLabel("Username");
        username.setBounds(40, 125, 150, 30);
        username.setForeground(Color.WHITE);
        username.setFont(labelFont);
        add(username);

        usernamebox = new JTextField();
        usernamebox.setBounds(130, 125, 200, 30);
        usernamebox.setBackground(Color.WHITE);
        usernamebox.setForeground(Color.BLACK);
        usernamebox.setFont(textFieldFont);
        add(usernamebox);
 
        viewDetails = new JButton("View Details");
        viewDetails.setBounds(40, 200, 140, 30);
        viewDetails.addActionListener(this);
        viewDetails.setFont(labelFont);
        viewDetails.setBackground(Color.WHITE);
        viewDetails.setForeground(Color.BLACK);
        add(viewDetails);
 
        cancel = new JButton("Cancel");
        cancel.setBounds(200, 200, 140, 30);
        cancel.addActionListener(this);
        cancel.setFont(labelFont);
        cancel.setBackground(Color.WHITE);
        cancel.setForeground(Color.BLACK);
        add(cancel);

        setVisible(true);
    }
 
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewDetails) {
            String username = usernamebox.getText();

            String query = "";
            if (role.equals("owner")) {
                query = "SELECT * FROM ownerlogin WHERE username = '" + username + "'";
            } else if (role.equals("user")) {
                query = "SELECT * FROM userlogin WHERE username = '" + username + "'";
            }

            try {
                conn c = new conn();
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) { 
                    String phone = rs.getString("phone");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String whatsapp = rs.getString("whatsapp");
                    String vacancies = role.equals("admin") ? rs.getString("vacancies") : "N/A";
                    String location = role.equals("admin") ? rs.getString("location") : "N/A";
 
                    String details = "Username: " + username + "\n"
                            + "Name: " + name + "\n"
                            + "Phone: " + phone + "\n"
                            + "Email: " + email + "\n"
                            + "WhatsApp: " + whatsapp + "\n"
                            + (role.equals("admin") ? "Vacancies: " + vacancies + "\n" + "Location: " + location + "\n" : "");

                    JOptionPane.showMessageDialog(null, details);
                } else {
                    JOptionPane.showMessageDialog(null, "User not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) { 
        new viewdetails("user");
    }

}
