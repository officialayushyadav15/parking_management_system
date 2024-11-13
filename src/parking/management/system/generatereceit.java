/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class generatereceit extends JFrame implements ActionListener {


    String username;
    JComboBox<String> carComboBox;
    JButton receiptButton;
    JTextArea area;

    generatereceit(String username) {
        this.username = username;

        setSize(500, 800);
        setLocation(550, 30);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();

        JLabel heading = new JLabel("Payment Receipt");
        JLabel selectCarLabel = new JLabel("Select Car Number:");

        carComboBox = new JComboBox<>();
        populateCarNumbers();

        area = new JTextArea(50, 15);
        area.setText("\n\n\t--------Select a Car and---------\n\t Generate Receipt Button to get\n\tthe payment receipt.");
        area.setFont(new Font("Serif", Font.ITALIC, 18));

        JScrollPane pane = new JScrollPane(area);

        receiptButton = new JButton("Generate Receipt");
        receiptButton.addActionListener(this);

        panel.add(heading);
        panel.add(selectCarLabel);
        panel.add(carComboBox);
        add(panel, "North");
        add(pane, "Center");
        add(receiptButton, "South");

        setVisible(true);
    }

    private void populateCarNumbers() {
        try {
            conn c = new conn();
            String query = "SELECT car_number FROM paybill WHERE parking_lot = (SELECT parking_lot_name FROM parkinglot WHERE owner_username = '" + username + "')";
            ResultSet rs = c.s.executeQuery(query);
            
            while (rs.next()) {
                carComboBox.addItem(rs.getString("car_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving car numbers: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            conn c = new conn();
            String carNumber = (String) carComboBox.getSelectedItem();
            
            area.setText("\t  ParkSmart pvt. Limited\n\t       ParkSmart Bill \n\tFOR CAR NUMBER: " + carNumber + "\n\n\n");

            String userQuery = "SELECT u.name, u.email, u.phone, u.whatsapp FROM userlogin u JOIN paybill p ON u.username = p.username WHERE p.car_number = '" + carNumber + "'";
            ResultSet rs = c.s.executeQuery(userQuery);
            
            if (rs.next()) {
                area.append("\n    Customer Name: " + rs.getString("name"));
                area.append("\n    Email: " + rs.getString("email"));
                area.append("\n    Number: " + rs.getString("phone"));
                area.append("\n    WhatsApp Number: " + rs.getString("whatsapp"));
                area.append("\n---------------------------------------------------");
            }
            
            String parkingQuery = "SELECT pl.parking_lot_name, pl.address, pl.landmark, pl.hourly_rate_standard, pl.total_slots FROM parkinglot pl JOIN paybill p ON pl.parking_lot_name = p.parking_lot WHERE p.car_number = '" + carNumber + "'";
            rs = c.s.executeQuery(parkingQuery);
            
            if (rs.next()) {
                area.append("\n    Parking lot name: " + rs.getString("parking_lot_name")); 
                area.append("\n    Address: " + rs.getString("address")); 
                area.append("\n    Landmark: " + rs.getString("landmark")); 
                area.append("\n    Total slots: " + rs.getString("total_slots")); 
                area.append("\n---------------------------------------------------");
            }
            
            String taxQuery = "SELECT hourly_rate_standard, hourly_rate_ev FROM parkinglot";
            rs = c.s.executeQuery(taxQuery);
            
            if (rs.next()) {
                area.append("\n    Cost Per Hour: " + rs.getString("hourly_rate_standard")); 
                area.append("\n    Cost Per Hour EV: " + rs.getString("hourly_rate_ev")); 
                area.append("\n---------------------------------------------------");
            }
            
            double billAmount = 0;
            double serviceTax = 0;
            double extra = 0;
            
            String totalQuery2 = "SELECT SUM(COALESCE(bill, cancelbook)) AS total_bill FROM paybill WHERE car_number = '" + carNumber + "'";
            rs = c.s.executeQuery(totalQuery2);
            
            if (rs.next()) {
                billAmount = Double.parseDouble(rs.getString("total_bill"));
                area.append("\n    BILL: " + billAmount);
                serviceTax = billAmount * 0.05;
                area.append("\n    Service Tax: " + serviceTax);
            }

            String totalQuery3 = "SELECT SUM(COALESCE(report_bill, 0)) AS t_bill FROM paybill WHERE car_number = '" + carNumber + "'";
            rs = c.s.executeQuery(totalQuery3);

            if (rs.next()) {
                extra = Double.parseDouble(rs.getString("t_bill")); 
                area.append("\n    Extra: " + extra); 
            }

            double totalPayable = billAmount + serviceTax + extra;
            area.append("\n    Total Payable: " + totalPayable);
            area.append("\n");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating receipt: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new generatereceit("Ayush@15092003");
    }
}
