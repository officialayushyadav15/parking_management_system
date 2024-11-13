/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class updateslotassignments extends JFrame implements ActionListener{


    JTextField carNumberBox;
    JSpinner hoursSpinner, minutesSpinner;
    JButton updateSlot, cancel;
    String userUsername;
 
    updateslotassignments(String userUsername) {
        this.userUsername = userUsername;

        setSize(500, 350);
        setLocation(750, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
 
        getContentPane().setBackground(Color.BLACK);
 
        Font labelFont = new Font("Tahoma", Font.PLAIN, 18);
 
        JLabel heading = new JLabel("Update Slot Assignments");
        heading.setBounds(90, 30, 350, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 25));
        heading.setForeground(Color.WHITE);
        add(heading);
 
        JLabel carNumberLabel = new JLabel("Car Number:");
        carNumberLabel.setBounds(40, 100, 150, 30);
        carNumberLabel.setForeground(Color.WHITE);
        carNumberLabel.setFont(labelFont);
        add(carNumberLabel);

        carNumberBox = new JTextField();
        carNumberBox.setBounds(200, 100, 250, 30);
        carNumberBox.setBackground(Color.WHITE);
        carNumberBox.setForeground(Color.BLACK);
        carNumberBox.setFont(labelFont);
        add(carNumberBox);
 
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setBounds(40, 150, 150, 30);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(labelFont);
        add(timeLabel);
 
        hoursSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        hoursSpinner.setBounds(200, 150, 60, 30);
        hoursSpinner.setFont(labelFont);
        add(hoursSpinner);
 
        minutesSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        minutesSpinner.setBounds(270, 150, 60, 30);
        minutesSpinner.setFont(labelFont);
        add(minutesSpinner);
 
        updateSlot = new JButton("Update Slot");
        updateSlot.setBounds(80, 220, 150, 35);
        updateSlot.addActionListener(this);
        updateSlot.setFont(labelFont);
        updateSlot.setBackground(Color.WHITE);
        updateSlot.setForeground(Color.BLACK);
        add(updateSlot);
 
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
        if (ae.getSource() == updateSlot) {
            String carNumber = carNumberBox.getText();
            int hours = (int) hoursSpinner.getValue();
            int minutes = (int) minutesSpinner.getValue();
            LocalTime entryTime = LocalTime.of(hours, minutes);
            String formattedEntryTime = entryTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            int confirmation = JOptionPane.showConfirmDialog(null,
                    "Do you want to update the slot for car number: " + carNumber + "?",
                    "Confirm Update", JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                updateSlotAssignment(carNumber, formattedEntryTime);
            }
        } else {
            setVisible(false);
        }
    }

    private void updateSlotAssignment(String carNumber, String entryTime) {
        try {
            conn c = new conn(); 
            String query = "SELECT arr, parking_lot FROM book WHERE car_number = '" + carNumber + "'";
            ResultSet rs = c.s.executeQuery(query);

            if (rs.next()) {
                int arrStatus = rs.getInt("arr");
                String parkingLotName = rs.getString("parking_lot");

                if (arrStatus == 0) {
                    String updateQuery = "UPDATE book SET entry_time = '" + entryTime + "', arr = 1 WHERE car_number = '" + carNumber + "'";
                    String updateQuery3 = "UPDATE records SET entry_time = '" + entryTime + "' WHERE car_number = '" + carNumber + "'";
                    c.s.executeUpdate(updateQuery);
                    c.s.executeUpdate(updateQuery3);
                    JOptionPane.showMessageDialog(null, "Slot assignment updated successfully.");
                } else if (arrStatus == 1) {
                    String deleteQuery = "DELETE FROM book WHERE car_number = '" + carNumber + "'";
                    String updateQuery2 = "UPDATE records SET exit_time = '" + entryTime + "' WHERE car_number = '" + carNumber + "'";
                    String updateCountQuery = "UPDATE count SET current_slots = current_slots - 1 WHERE parking_lot_name = '" + parkingLotName + "'";
                    c.s.executeUpdate(updateQuery2);
                    c.s.executeUpdate(deleteQuery);
                    c.s.executeUpdate(updateCountQuery);
                    JOptionPane.showMessageDialog(null, "Slot assignment deleted successfully.");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Car number not found.");
            }

            setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating slot assignment.");
        }
    }


    public static void main(String[] args) { 
        new updateslotassignments("Ayush@2003");  
    }

}
