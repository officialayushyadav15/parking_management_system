/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class reportoverstayingvehicles extends JFrame implements ActionListener{



    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JButton reportButton;
    private String parkingLotName;

    reportoverstayingvehicles(String parkingLotName) {
        this.parkingLotName = parkingLotName;
        setTitle("Report Overstaying Vehicles");
        setBounds(500, 250, 900, 700);
        setLayout(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 900, 700);
        add(layeredPane);

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/park.jpg"));
        Image i5 = i4.getImage().getScaledInstance(900, 700, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image = new JLabel(i6);
        image.setBounds(0, 0, 900, 700);
        layeredPane.add(image, JLayeredPane.DEFAULT_LAYER);

        JPanel panel = new JPanel();
        panel.setBounds(15, 40, 855, 580);
        panel.setBackground(Color.BLACK);
        panel.setOpaque(true);
        panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2),
                "OVERSTAYING VEHICLES", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(new BorderLayout());
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);
 
        tableModel = new DefaultTableModel(new String[]{"Car Number", "Vehicle Type", "Entry Time", "Allowed Stay Time"}, 0);
        reportTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  
            }
        };
 
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            reportTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
 
        reportTable.setFont(new Font("Arial", Font.PLAIN, 16));
        reportTable.setRowHeight(30);
 
        loadOverstayingVehicles();
 
        JScrollPane scrollPane = new JScrollPane(reportTable);
        panel.add(scrollPane, BorderLayout.CENTER);
 
        reportButton = new JButton("Report Selected Vehicle");
        reportButton.setFont(new Font("Arial", Font.BOLD, 18));
        reportButton.setPreferredSize(new Dimension(300, 50));
        reportButton.addActionListener(this);
        panel.add(reportButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadOverstayingVehicles() {
    String query = "SELECT car_number, vehicle_type, entry_time, time_of_stay " +
                   "FROM records WHERE booked_cancelled = 'booked' AND " +
                   "TIME_TO_SEC(ADDTIME(entry_time, SEC_TO_TIME(time_of_stay * 3600))) < TIME_TO_SEC(CURTIME()) " +
                   "AND car_number NOT IN (SELECT car_number FROM reportbooker)";
    try {
        conn c = new conn();
        Statement st = c.s;
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            String carNumber = rs.getString("car_number");
            String vehicleType = rs.getString("vehicle_type");
            Time entryTime = rs.getTime("entry_time");
            int timeOfStay = rs.getInt("time_of_stay");
            tableModel.addRow(new Object[]{carNumber, vehicleType, entryTime, timeOfStay});
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading overstaying vehicles.");
    }
}


    @Override
    public void actionPerformed(ActionEvent ae) {
        int row = reportTable.getSelectedRow();
        if (row != -1) {
            String carNumber = (String) reportTable.getValueAt(row, 0);

            try {
                conn c = new conn();
                String reportQuery = "INSERT INTO reportbooker (car_number, report_time) VALUES (?, CURRENT_TIMESTAMP)";
                PreparedStatement pst = c.c.prepareStatement(reportQuery); // Use c.c for Connection object
                pst.setString(1, carNumber);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Vehicle reported successfully.");
 
                tableModel.removeRow(row);

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error reporting vehicle.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a vehicle to report.");
        }
    }

    public static void main(String[] args) {
        String parkingLotName = JOptionPane.showInputDialog("Enter the parking lot name:");
        if (parkingLotName != null && !parkingLotName.trim().isEmpty()) {
            new reportoverstayingvehicles(parkingLotName);
        } else {
            JOptionPane.showMessageDialog(null, "Parking lot name is required.");
        }
    }


}

