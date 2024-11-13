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
import javax.swing.border.TitledBorder;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.SQLException;
import java.sql.Statement;
public class cancelbooking extends JFrame implements ActionListener{


    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private String username;
    private JButton cancel;

    cancelbooking(String username) {
        this.username = username;
        setTitle("Cancel Booking");
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
                "CANCEL BOOKING", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(new BorderLayout());
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);

        tableModel = new DefaultTableModel(new String[]{"Car Number", "Arrival Time", "Duration"}, 0);
        bookingTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            bookingTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        bookingTable.setFont(new Font("Arial", Font.PLAIN, 16));
        bookingTable.setRowHeight(30);
        loadBookings();

        JScrollPane scrollPane = new JScrollPane(bookingTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        cancel = new JButton("Cancel Selected Booking");
        cancel.setFont(new Font("Arial", Font.BOLD, 18));
        cancel.setPreferredSize(new Dimension(300, 50));
        cancel.addActionListener(this);
        panel.add(cancel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadBookings() {
        String query = "SELECT car_number, arrival_time, time_of_stay FROM book WHERE username = '" + username + "'";
        try {
            conn c = new conn();
            Statement st = c.s;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String carNumber = rs.getString("car_number");
                String arrivalTime = rs.getString("arrival_time");
                int timeOfStay = rs.getInt("time_of_stay");
                tableModel.addRow(new Object[]{carNumber, arrivalTime, timeOfStay});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading bookings.");
        }
    }

    public void actionPerformed(ActionEvent ae) {
        int row = bookingTable.getSelectedRow();
        if (row != -1) {
            String carNumber = (String) bookingTable.getValueAt(row, 0);
            try {
                conn c = new conn();
                Statement st = c.s;
                String query = "SELECT arr FROM book WHERE car_number = '" + carNumber + "'";
                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    int arrStatus = rs.getInt("arr");
                    if (arrStatus == 1) {
                        JOptionPane.showMessageDialog(null, "Cannot cancel booking. Car has already checked in.");
                        return;
                    }
                }

                int confirmation = JOptionPane.showConfirmDialog(null,
                        "Do you want to confirm the cancellation of booking for car number: " + carNumber + "?",
                        "Confirm Cancellation", JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    cancelBooking(carNumber);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error checking booking status.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a booking to cancel.");
        }
    }

    private void cancelBooking(String carNumber) {
        try {
            conn c = new conn();
            Statement st = c.s;

            String query = "SELECT arr FROM book WHERE car_number = '" + carNumber + "'";
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                int arrStatus = rs.getInt("arr");
                if (arrStatus == 1) {
                    JOptionPane.showMessageDialog(null, "Can't delete as car already entered.");
                    return;
                }
            }

            String deleteBookingQuery = "DELETE FROM book WHERE car_number = '" + carNumber + "'";
            st.executeUpdate(deleteBookingQuery);

            String updateRecordQuery = "UPDATE records SET booked_cancelled = 'cancelled' WHERE car_number = '" + carNumber + "'";
            st.executeUpdate(updateRecordQuery);

            String getCurrentSlotsQuery = "SELECT current_slots FROM count WHERE parking_lot_name = (SELECT parking_lot_name FROM book WHERE car_number = '" + carNumber + "')";
            ResultSet rsCount = st.executeQuery(getCurrentSlotsQuery);

            if (rsCount.next()) {
                int currentSlots = rsCount.getInt("current_slots");
                String updateCountQuery = "UPDATE count SET current_slots = " + (currentSlots - 1) + " WHERE parking_lot_name = (SELECT parking_lot_name FROM book WHERE car_number = '" + carNumber + "')";
                st.executeUpdate(updateCountQuery);
            }

            JOptionPane.showMessageDialog(null, "Successfully cancelled booking for car number: " + carNumber);
            tableModel.setRowCount(0);
            loadBookings();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error cancelling booking.");
        }
    }

    public static void main(String[] args) {
        new cancelbooking("Ayush@2003");
    }
}
