/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;


import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class searchparkinglot extends JFrame implements ActionListener {

 
    JTextField tfLandmark;
    JButton search, cancel;
    JTable resultTable;
    DefaultTableModel tableModel;

    searchparkinglot() {
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
                "SEARCH PARKING LOT", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(null);
        image.add(panel); 
 
        JLabel lblLandmark = new JLabel("Enter Landmark");
        lblLandmark.setBounds(30, 30, 200, 30);
        lblLandmark.setForeground(Color.WHITE);
        lblLandmark.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblLandmark);

        tfLandmark = new JTextField();
        tfLandmark.setBounds(250, 30, 400, 30);
        panel.add(tfLandmark);
 
        search = new JButton("Search");
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
 
        String[] columnNames = {"Parking Lot Name", "Hourly Rate (Standard)", "Hourly Rate (EV)", "Rating"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel) {
 
            @Override
            public Class<?> getColumnClass(int column) {
                return String.class;  
            }
        };
        resultTable.setForeground(Color.WHITE);
        resultTable.setBackground(Color.BLACK);
        resultTable.setFont(new Font("Tahoma", Font.PLAIN, 16)); 
        resultTable.setGridColor(Color.WHITE);
        resultTable.setShowGrid(true);
        resultTable.setAutoCreateRowSorter(true);  
 
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < columnNames.length; i++) {
            resultTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
 
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(30, 100, 680, 250);
        panel.add(scrollPane);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            
            String landmark = tfLandmark.getText();
 
            if (landmark.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a landmark.");
                return;
            }

            String query = "SELECT pl.parking_lot_name, pl.hourly_rate_standard, pl.hourly_rate_ev, " +
                    "COALESCE(AVG(r.rating), 0) AS avg_rating " +
                    "FROM parkinglot pl " +
                    "LEFT JOIN review r ON pl.parking_lot_name = r.parking_lot_name " +
                    "WHERE pl.landmark = ? " +
                    "GROUP BY pl.parking_lot_name, pl.hourly_rate_standard, pl.hourly_rate_ev";

            try {
                conn c = new conn();
                PreparedStatement pst = c.c.prepareStatement(query);  
                pst.setString(1, landmark);  
                ResultSet rs = pst.executeQuery();
 
                tableModel.setRowCount(0);
 
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No parking slots available at this landmark.");
                } else { 
                    while (rs.next()) {
                        String lotName = rs.getString("parking_lot_name");
                        String rateStandard = rs.getString("hourly_rate_standard");
                        String rateEV = rs.getString("hourly_rate_ev");
                        String avgRating = String.valueOf(rs.getDouble("avg_rating"));
 
                        tableModel.addRow(new Object[]{lotName, rateStandard, rateEV, avgRating});
                    }
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error fetching parking lot data: " + e.getMessage());
            }
        } else if (ae.getSource() == cancel) {
            dispose();  
        }
    }


    public static void main(String[] args) {
        new searchparkinglot();
    }
}

