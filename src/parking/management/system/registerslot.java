/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class registerslot extends JFrame implements ActionListener {
 
    JTextField tfOwner, tfParkingLot, tfAddress, tfLandmark, tfTotalSlots, tfEVSlots;
    JTextField tfRateStandard, tfRateEV;
    JButton register, cancel;

    registerslot() {
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
        panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2),
                "REGISTER PARKING LOT", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(null);

        image.add(panel); 
 
        tfOwner = addFormElement(panel, "Owner Username", 30);
        tfParkingLot = addFormElement(panel, "Parking Lot Name", 70);
        tfAddress = addFormElement(panel, "Address", 110);
        tfLandmark = addFormElement(panel, "Landmark", 150);
        tfTotalSlots = addFormElement(panel, "Total Slots", 190);
        tfEVSlots = addFormElement(panel, "EV Charging Slots", 230);
        tfRateStandard = addFormElement(panel, "Hourly Rate (Standard)", 270);
        tfRateEV = addFormElement(panel, "Hourly Rate (EV)", 310);
 
        register = new JButton("Register");
        register.setBounds(200, 380, 150, 40);
        register.setFont(new Font("Tahoma", Font.BOLD, 16));
        register.setBackground(Color.BLACK);
        register.setForeground(Color.WHITE);
        register.setBorder(new LineBorder(Color.WHITE, 1));
        register.addActionListener(this);
        panel.add(register);
 
        cancel = new JButton("Cancel");
        cancel.setBounds(400, 380, 150, 40);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 16));
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setBorder(new LineBorder(Color.WHITE, 1));
        cancel.addActionListener(this);
        panel.add(cancel);

        setVisible(true);
    }

    private JTextField addFormElement(JPanel panel, String label, int yPosition) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(30, yPosition, 200, 30);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(jLabel);

        JTextField textField = new JTextField();
        textField.setBounds(250, yPosition, 400, 30);
        panel.add(textField);
        return textField;  
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == register) { 
            String owner = tfOwner.getText();
            String parkingLot = tfParkingLot.getText();
            String address = tfAddress.getText();
            String landmark = tfLandmark.getText();
            String totalSlots = tfTotalSlots.getText();
            String evSlots = tfEVSlots.getText();
            String rateStandard = tfRateStandard.getText();
            String rateEV = tfRateEV.getText();
 
            if (owner.isEmpty() || parkingLot.isEmpty() || address.isEmpty() || totalSlots.isEmpty() ||
                evSlots.isEmpty() || rateStandard.isEmpty() || rateEV.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            String query = "INSERT INTO parkinglot (owner_username, parking_lot_name, address, landmark, total_slots, ev_charging_slots, " +
                           "hourly_rate_standard, hourly_rate_ev) VALUES ('" +
                           owner + "', '" + parkingLot + "', '" + address + "', '" + landmark + "', " + 
                           totalSlots + ", " + evSlots + ", " + rateStandard + ", " + rateEV + ")";

            try {
                conn c= new conn();
                c.s.executeUpdate(query);
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Parking Lot Registered Successfully!");
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error registering parking lot: " + e.getMessage());
            }
        } else if (ae.getSource() == cancel) {
            dispose();  
        }
    }

    public static void main(String[] args) {
        new registerslot();
    }
}
