/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
public class reportuser extends JFrame implements ActionListener {


    JTextField parkingLotNameBox;
    JButton report, cancel;
    String userUsername; 
    reportuser(String userUsername) {
        this.userUsername = userUsername;

        setSize(500, 300);
        setLocation(750, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
 
        getContentPane().setBackground(Color.BLACK);
 
        Font labelFont = new Font("Tahoma", Font.PLAIN, 18);
 
        JLabel heading = new JLabel("Report Parking Lot");
        heading.setBounds(130, 30, 300, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));  
        heading.setForeground(Color.WHITE);  
        add(heading);
 
        JLabel parkingLotLabel = new JLabel("Parking Lot Name");
        parkingLotLabel.setBounds(40, 100, 150, 30);
        parkingLotLabel.setForeground(Color.WHITE);   
        parkingLotLabel.setFont(labelFont);
        add(parkingLotLabel);

        parkingLotNameBox = new JTextField();
        parkingLotNameBox.setBounds(200, 100, 250, 30);
        parkingLotNameBox.setBackground(Color.WHITE);   
        parkingLotNameBox.setForeground(Color.BLACK);   
        parkingLotNameBox.setFont(labelFont);
        add(parkingLotNameBox);
 
        report = new JButton("Report");
        report.setBounds(80, 150, 150, 35);
        report.addActionListener(this);
        report.setFont(labelFont);
        report.setBackground(Color.WHITE);   
        report.setForeground(Color.BLACK);   
        add(report);
 
        cancel = new JButton("Cancel");
        cancel.setBounds(260, 150, 150, 35);
        cancel.addActionListener(this);
        cancel.setFont(labelFont);
        cancel.setBackground(Color.WHITE);   
        cancel.setForeground(Color.BLACK);  
        add(cancel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == report) {
            String parkingLotName = parkingLotNameBox.getText();

            int confirmation = JOptionPane.showConfirmDialog(null,
                    "Do you want to report the parking lot: " + parkingLotName + "?",
                    "Confirm Report", JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                reportParkingLot(parkingLotName);
            }
        } else {
            setVisible(false);
        }
    }

    private void reportParkingLot(String parkingLotName) {
        try {
            conn c = new conn();
            String updateRatingQuery = "UPDATE review SET rating = GREATEST(rating - 0.1, 0) WHERE parking_lot_name = '" + parkingLotName + "'";
            c.s.executeUpdate(updateRatingQuery);
            JOptionPane.showMessageDialog(null, "Reported successfully");
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reporting the parking lot.");
        }
    }

    public static void main(String[] args) {
        
        new reportuser("Ayush@2003"); 
    }

}
