/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
public class viewparkedvehicles extends JFrame{




    private JTable parkedVehiclesTable;
    private DefaultTableModel tableModel;
    private String parkingLotName;

    viewparkedvehicles(String parkingLotName) {
        this.parkingLotName = parkingLotName;
        setTitle("View Parked Vehicles");
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
                "PARKED VEHICLES", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(new BorderLayout());
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER); 
        tableModel = new DefaultTableModel(new String[]{"Car Number", "Vehicle Type", "Entry Time", "Time of Stay"}, 0);
        parkedVehiclesTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  
            }
        }; 
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            parkedVehiclesTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        } 
        parkedVehiclesTable.setFont(new Font("Arial", Font.PLAIN, 16));
        parkedVehiclesTable.setRowHeight(30); 
        loadParkedVehicles(); 
        JScrollPane scrollPane = new JScrollPane(parkedVehiclesTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadParkedVehicles() {
        String query = "SELECT car_number, vehicle_type, entry_time, time_of_stay " +
                       "FROM records WHERE booked_cancelled = 'booked' " +
                       "AND entry_time IS NOT NULL AND exit_time IS NULL";
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
            JOptionPane.showMessageDialog(null, "Error loading parked vehicles.");
        }
    }

    public static void main(String[] args) {
        String parkingLotName = JOptionPane.showInputDialog("Enter the parking lot name:");
        if (parkingLotName != null && !parkingLotName.trim().isEmpty()) {
            new viewparkedvehicles(parkingLotName);
        } else {
            JOptionPane.showMessageDialog(null, "Parking lot name is required.");
        }
    }

}
