/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class overview extends JFrame implements ActionListener{
    



    JTextField parkingLotBox, passwordBox;
    JButton viewOverview, cancel;
    JTextArea resultArea;
 
    overview() {
        setSize(500, 500);
        setLocation(760, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
 
        getContentPane().setBackground(Color.BLACK);
 
        Font labelFont = new Font("Tahoma", Font.PLAIN, 18);
        Font textFieldFont = new Font("Tahoma", Font.PLAIN, 16);
 
        JLabel heading = new JLabel("Parking Overview");
        heading.setBounds(120, 30, 300, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));
        heading.setForeground(Color.WHITE);
        add(heading);
 
        JLabel parkingLotLabel = new JLabel("Parking Lot Name");
        parkingLotLabel.setBounds(30, 100, 180, 30);
        parkingLotLabel.setForeground(Color.WHITE);
        parkingLotLabel.setFont(labelFont);
        add(parkingLotLabel);

        parkingLotBox = new JTextField();
        parkingLotBox.setBounds(200, 100, 200, 30);
        parkingLotBox.setBackground(Color.WHITE);
        parkingLotBox.setForeground(Color.BLACK);
        parkingLotBox.setFont(textFieldFont);
        add(parkingLotBox);
 
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(30, 150, 180, 30);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(labelFont);
        add(passwordLabel);

        passwordBox = new JPasswordField();
        passwordBox.setBounds(200, 150, 200, 30);
        passwordBox.setBackground(Color.WHITE);
        passwordBox.setForeground(Color.BLACK);
        passwordBox.setFont(textFieldFont);
        add(passwordBox);
 
        viewOverview = new JButton("View Overview");
        viewOverview.setBounds(50, 220, 150, 30);
        viewOverview.addActionListener(this);
        viewOverview.setFont(labelFont);
        viewOverview.setBackground(Color.WHITE);
        viewOverview.setForeground(Color.BLACK);
        add(viewOverview);
 
        cancel = new JButton("Cancel");
        cancel.setBounds(220, 220, 150, 30);
        cancel.addActionListener(this);
        cancel.setFont(labelFont);
        cancel.setBackground(Color.WHITE);
        cancel.setForeground(Color.BLACK);
        add(cancel);
 
        resultArea = new JTextArea();
        resultArea.setBounds(30, 280, 400, 150);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setBackground(Color.WHITE);
        resultArea.setEditable(false);
        add(resultArea);

        setVisible(true);
    }
 
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewOverview) {
            String parkingLotName = parkingLotBox.getText();
            String password = passwordBox.getText();

            try {
                conn c = new conn();
 
                String queryParkingLot = "SELECT owner_username FROM parkinglot WHERE parking_lot_name = '" + parkingLotName + "'";
                ResultSet rsParkingLot = c.s.executeQuery(queryParkingLot);

                if (rsParkingLot.next()) {
                    String ownerUsername = rsParkingLot.getString("owner_username");
 
                    String queryOwnerLogin = "SELECT * FROM ownerlogin WHERE username = '" + ownerUsername + "' AND password = '" + password + "'";
                    ResultSet rsOwnerLogin = c.s.executeQuery(queryOwnerLogin);

                    if (rsOwnerLogin.next()) {
                        String queryTotalSlots = "SELECT total_slots FROM count WHERE parking_lot_name = '" + parkingLotName + "'";
                        ResultSet rsTotalSlots = c.s.executeQuery(queryTotalSlots);

                        if (rsTotalSlots.next()) {
                            int totalSlots = rsTotalSlots.getInt("total_slots");

                            String queryBookedSlots = "SELECT COUNT(*) AS booked_count FROM records WHERE parking_lot = '" + parkingLotName + "' AND booked_cancelled = 'booked' AND exit_time IS NULL";
                            ResultSet rsBookedSlots = c.s.executeQuery(queryBookedSlots);

                            int bookedSlots = 0;
                            if (rsBookedSlots.next()) {
                                bookedSlots = rsBookedSlots.getInt("booked_count");
                            }

                            int emptySlots = totalSlots - bookedSlots;

                            String details = "Parking Lot Name: " + parkingLotName + "\n"
                                    + "Total Slots: " + totalSlots + "\n"
                                    + "Booked Slots: " + bookedSlots + "\n"
                                    + "Empty Slots: " + emptySlots + "\n";

                            resultArea.setText(details);
                        } else {
                            resultArea.setText("Parking lot details not found.");
                        }
                    } else {
                        resultArea.setText("Incorrect password.");
                    }
                } else {
                    resultArea.setText("Parking lot not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }




    public static void main(String[] args) {
        new overview();
    }

}

