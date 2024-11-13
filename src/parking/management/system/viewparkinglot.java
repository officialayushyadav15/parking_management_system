/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class viewparkinglot extends JFrame implements ActionListener{



    JTextField tfParkingLot;
    JButton view, cancel;
    JTextArea resultArea;

    viewparkinglot() {
        setBounds(500, 250, 800, 600);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);
 
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/park.jpg"));
        Image i5 = i4.getImage().getScaledInstance(1500, 900, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image = new JLabel(i6);
        image.setBounds(0, 0, 1000, 800);
        add(image); 
        JPanel panel = new JPanel();
        panel.setBounds(20, 40, 745, 460);
        panel.setBackground(Color.BLACK);
        panel.setOpaque(true);
        panel.setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.LineBorder(new Color(255, 255, 255), 2),
                "VIEW PARKING LOT", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(null);
        image.add(panel); 
        JLabel lblParkingLot = new JLabel("Parking Lot Name");
        lblParkingLot.setBounds(30, 50, 200, 30);
        lblParkingLot.setForeground(Color.WHITE);
        lblParkingLot.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblParkingLot);

        tfParkingLot = new JTextField();
        tfParkingLot.setBounds(250, 50, 400, 30);
        panel.add(tfParkingLot); 
        view = new JButton("View");
        view.setBounds(200, 120, 150, 40);
        view.setFont(new Font("Tahoma", Font.BOLD, 16));
        view.setBackground(Color.BLACK);
        view.setForeground(Color.WHITE);
        view.setBorder(new javax.swing.border.LineBorder(Color.WHITE, 1));
        view.addActionListener(this);
        panel.add(view);
 
        cancel = new JButton("Cancel");
        cancel.setBounds(400, 120, 150, 40);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 16));
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setBorder(new javax.swing.border.LineBorder(Color.WHITE, 1));
        cancel.addActionListener(this);
        panel.add(cancel); 
        resultArea = new JTextArea();
        resultArea.setBounds(30, 200, 650, 200);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setBackground(Color.WHITE);
        resultArea.setEditable(false);
        panel.add(resultArea);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == view) { 
            String parkingLotName = tfParkingLot.getText();

            if (parkingLotName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a parking lot name.");
                return;
            }

            try { 
                conn c = new conn();
                String query = "SELECT pl.address, pl.landmark, pl.total_slots, pl.ev_charging_slots, pl.hourly_rate_standard, pl.hourly_rate_ev, " +
                               "co.current_slots, ol.name, ol.phone, ol.email, ol.whatsapp " +
                               "FROM parkinglot pl " +
                               "LEFT JOIN count co ON pl.parking_lot_name = co.parking_lot_name " +
                               "JOIN ownerlogin ol ON pl.owner_username = ol.username " +
                               "WHERE pl.parking_lot_name = '" + parkingLotName + "'";
                ResultSet rs = c.s.executeQuery(query);
 
                if (rs.next()) {
                    String address = rs.getString("address");
                    String landmark = rs.getString("landmark");
                    int totalSlots = rs.getInt("total_slots");
                    int evSlots = rs.getInt("ev_charging_slots");
                    double rateStandard = rs.getDouble("hourly_rate_standard");
                    double rateEV = rs.getDouble("hourly_rate_ev");
                    int currentSlots = rs.getInt("current_slots"); 
                    String ownerName = rs.getString("name");
                    String ownerPhone = rs.getString("phone");
                    String ownerEmail = rs.getString("email");
                    String ownerWhatsApp = rs.getString("whatsapp");

                    String result = "Owner Name: " + ownerName + "\n" +
                                    "Phone: " + ownerPhone + "\n" +
                                    "Email: " + ownerEmail + "\n" +
                                    "WhatsApp: " + ownerWhatsApp + "\n" +
                                    "Address: " + address + "\n" +
                                    "Landmark: " + landmark + "\n" +
                                    "Total Slots: " + totalSlots + "\n" +
                                    "EV Charging Slots: " + evSlots + "\n" +
                                    "Hourly Rate (Standard): $" + rateStandard + "\n" +
                                    "Hourly Rate (EV): $" + rateEV + "\n" +
                                    "Available Slots: " + currentSlots;
                    resultArea.setText(result);
                } else {
                    JOptionPane.showMessageDialog(null, "No parking lot found with the name: " + parkingLotName);
                    resultArea.setText("");
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error fetching parking lot details: " + e.getMessage());
            }

        } else if (ae.getSource() == cancel) {
            dispose();  
        }
    }



    public static void main(String[] args) {
        new viewparkinglot();
    }
}

