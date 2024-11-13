/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
public class registerlogin extends JFrame implements ActionListener{
    


    JTextField usernamebox, passwordbox;
    JButton login, cancel;

    registerlogin() {
        setSize(500, 350);
        setLocation(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        
        Font labelFont = new Font("Tahoma", Font.PLAIN, 18);
        Font textFieldFont = new Font("Tahoma", Font.PLAIN, 16);

        JLabel heading = new JLabel("User Login");
        heading.setBounds(160, 30, 200, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));
        heading.setForeground(Color.WHITE);
        add(heading);
        
        JLabel username = new JLabel("Username");
        username.setBounds(40, 100, 150, 30);
        username.setForeground(Color.WHITE);
        username.setFont(labelFont);
        add(username);

        usernamebox = new JTextField();
        usernamebox.setBounds(130, 100, 300, 30);
        usernamebox.setBackground(Color.WHITE);
        usernamebox.setForeground(Color.BLACK);
        usernamebox.setFont(textFieldFont);
        add(usernamebox);
        
        JLabel password = new JLabel("Password");
        password.setBounds(40, 150, 150, 30);
        password.setForeground(Color.WHITE);
        password.setFont(labelFont);
        add(password);

        passwordbox = new JPasswordField();
        passwordbox.setBounds(130, 150, 300, 30);
        passwordbox.setBackground(Color.WHITE);
        passwordbox.setForeground(Color.BLACK);
        passwordbox.setFont(textFieldFont);
        add(passwordbox);
        
        login = new JButton("Login");
        login.setBounds(80, 220, 150, 35);
        login.addActionListener(this);
        login.setFont(labelFont);
        login.setBackground(Color.WHITE);
        login.setForeground(Color.BLACK);
        add(login);

        cancel = new JButton("Cancel");
        cancel.setBounds(260, 220, 150, 35);
        cancel.addActionListener(this);
        cancel.setFont(labelFont);
        cancel.setBackground(Color.WHITE);
        cancel.setForeground(Color.BLACK);
        add(cancel);
        
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String username = usernamebox.getText();
            String password = passwordbox.getText();
            String query = "SELECT * FROM ownerlogin WHERE username = '" + username + "' AND password = '" + password + "'";
            
            try {
                conn c = new conn();
                ResultSet rs = c.s.executeQuery(query);
                
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successful");
                    setVisible(false);
                    new registerslot();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Login");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new registerlogin();
    }
}
