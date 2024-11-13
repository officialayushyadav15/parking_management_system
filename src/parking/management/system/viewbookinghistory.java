/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder; 
import java.awt.*;
import java.sql.*;
public class viewbookinghistory extends JFrame{


    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private String username;
 
    public viewbookinghistory(String username) {
        this.username = username;
        setTitle("Booking History");
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
                "Booking History", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(new BorderLayout());
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);
 
        tableModel = new DefaultTableModel(new String[]{
                "Car Number", "Vehicle Type", "WhatsApp", "Payment Option", "Email", "Name", "Status"
        }, 0);
        
        bookingTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  
            }
        };
 
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < bookingTable.getColumnCount(); i++) {
            bookingTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
 
        bookingTable.setFont(new Font("Arial", Font.PLAIN, 16)); 
        bookingTable.setRowHeight(35);  
        loadBookingHistory(); 
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadBookingHistory() {
        String query = "SELECT car_number, vehicle_type, whatsapp, payment_option, email, name, booked_cancelled " +
                       "FROM records WHERE username = '" + username + "'";
        try {
            conn c = new conn();
            Statement st = c.s;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String carNumber = rs.getString("car_number");
                String vehicleType = rs.getString("vehicle_type");
                String whatsapp = rs.getString("whatsapp");
                String paymentOption = rs.getString("payment_option");
                String email = rs.getString("email");
                String name = rs.getString("name");
                String status = rs.getString("booked_cancelled");

                tableModel.addRow(new Object[]{carNumber, vehicleType, whatsapp, paymentOption, email, name, status});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading booking history.");
        }
    }


    public static void main(String[] args) {
        new viewbookinghistory("Ayush@2003");
    }

}
