/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class viewexpectedarrival extends JFrame implements ActionListener{




    JButton search, cancel;
    JTable resultTable;
    DefaultTableModel tableModel;
    String parkingLotName;  

    viewexpectedarrival(String parkingLotName) {
        this.parkingLotName = parkingLotName;  
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
                "EXPECTED ARRIVALS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(null);

        image.add(panel); 
        JLabel lblParkingLot = new JLabel("Parking Lot: " + parkingLotName);
        lblParkingLot.setBounds(30, 30, 400, 30);
        lblParkingLot.setForeground(Color.WHITE);
        lblParkingLot.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblParkingLot); 
        String[] columnNames = {"Car Number", "Arrival Time", "Stay", "Email", "WhatsApp"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        resultTable.setForeground(Color.WHITE);
        resultTable.setBackground(Color.BLACK);
        resultTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
        resultTable.setGridColor(Color.WHITE);
        resultTable.setShowGrid(true);
        resultTable.setAutoCreateRowSorter(true); 
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(30, 100, 680, 250);
        panel.add(scrollPane); 
        search = new JButton("Load Arrivals");
        search.setBounds(200, 380, 150, 40);
        search.setFont(new Font("Tahoma", Font.BOLD, 16));
        search.setBackground(Color.BLACK);
        search.setForeground(Color.WHITE);
        search.setBorder(new LineBorder(Color.WHITE, 1));
        search.addActionListener(this);
        panel.add(search); 
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

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) { 
            String query = "SELECT car_number, arrival_time, time_of_stay , email, whatsapp " +
                    "FROM book WHERE parking_lot = ?";

            try {
                conn c = new conn();  
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, parkingLotName);  
                ResultSet rs = pst.executeQuery(); 
                tableModel.setRowCount(0); 
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No cars expected at this parking lot.");
                } else { 
                    while (rs.next()) {
                        String carNumber = rs.getString("car_number");
                        String arrivalTime = rs.getString("arrival_time");
                        int time_of_stay = rs.getInt("time_of_stay");
                       
                        String email = rs.getString("email");
                        String whatsapp = rs.getString("whatsapp"); 
                        tableModel.addRow(new Object[]{carNumber, arrivalTime, time_of_stay, email, whatsapp});
                    }
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error fetching expected arrivals: " + e.getMessage());
            }
        } else if (ae.getSource() == cancel) {
            dispose(); 
        }
    }



    public static void main(String[] args) {
        new viewexpectedarrival("Cars");  
    }

}
