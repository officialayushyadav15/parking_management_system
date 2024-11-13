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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class billdetails extends JFrame implements ActionListener{




    private JTable billTable;
    private DefaultTableModel tableModel;
    private String username;
    private JButton refreshButton;

    billdetails(String username) {
        this.username = username;
        setTitle("Bill Details");
        setBounds(500, 250, 900, 700);
        setLayout(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 900, 700);
        add(layeredPane);

        ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("icon/park.jpg"));
        Image resizedImage = backgroundImage.getImage().getScaledInstance(900, 700, Image.SCALE_DEFAULT);
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
        imageLabel.setBounds(0, 0, 900, 700);
        layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);

        JPanel panel = new JPanel();
        panel.setBounds(15, 40, 855, 580);
        panel.setBackground(Color.BLACK);
        panel.setOpaque(true);
        panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2),
                "Bill DETAILS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(new BorderLayout());
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);

        tableModel = new DefaultTableModel(new String[]{"Car Number", "Payment Option", "Booked Cancelled", "Parking Lot", "Time of Stay", "Entry Time", "Exit Time", "Payment"}, 0);
        billTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            billTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        billTable.setFont(new Font("Arial", Font.PLAIN, 16));
        billTable.setRowHeight(30);

        loadBillDetails();

        JScrollPane scrollPane = new JScrollPane(billTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 18));
        refreshButton.setPreferredSize(new Dimension(300, 50));
        refreshButton.addActionListener(this);
        panel.add(refreshButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadBillDetails() {
        String query = "SELECT r.car_number, r.payment_option, r.booked_cancelled, r.parking_lot, r.time_of_stay, r.entry_time, r.exit_time, p.paid_unpaid " +
                "FROM records r LEFT JOIN paid p ON r.id = p.txnid WHERE r.username = '" + username + "'";
        try {
            conn c = new conn();
            Statement st = c.s;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String carNumber = rs.getString("car_number");
                String paymentOption = rs.getString("payment_option");
                String bookedCancelled = rs.getString("booked_cancelled");
                String parkingLot = rs.getString("parking_lot");
                int timeOfStay = rs.getInt("time_of_stay");
                String entryTime = rs.getString("entry_time");
                String exitTime = rs.getString("exit_time");
                int paidUnpaid = rs.getInt("paid_unpaid");

                String paymentStatus = (paidUnpaid == 0) ? "Unpaid" : "Paid";

                if (entryTime == null && exitTime == null) {
                    entryTime = "NIL";
                    exitTime = "NIL";
                } else if (entryTime == null) {
                    entryTime = "Car not arrived";
                } else if (exitTime == null) {
                    exitTime = "Still in parking lot";
                } else if ("cancelled".equals(bookedCancelled)) {
                    entryTime = "Cancelled";
                    exitTime = "Cancelled";
                }

                tableModel.addRow(new Object[]{carNumber, paymentOption, bookedCancelled, parkingLot, timeOfStay, entryTime, exitTime, paymentStatus});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading booking details.");
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == refreshButton) {
            tableModel.setRowCount(0);
            loadBillDetails();
        }
    }

    public static void main(String[] args) {
        new billdetails("Ayush@2003");
    }

}
