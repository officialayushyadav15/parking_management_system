/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class paybill extends JFrame implements ActionListener{




    JComboBox<String> carNumberDropdown;
    JButton btnFetchDetails, btnPayNow;
    String username;

    paybill(String username) {
        this.username = username;

        setBounds(500, 250, 900, 600);
        setLayout(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 900, 600);
        add(layeredPane);

        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icon/park.jpg"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(900, 700, Image.SCALE_DEFAULT);
        ImageIcon background = new ImageIcon(backgroundImage);
        JLabel imageLabel = new JLabel(background);
        imageLabel.setBounds(0, 0, 900, 700);
        layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);

        JPanel panel = new JPanel();
        panel.setBounds(15, 40, 855, 480);
        panel.setBackground(Color.BLACK);
        panel.setOpaque(true);
        panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2),
                "PAYMENT DETAILS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(null);
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);

        JLabel lblCarNumber = new JLabel("Select Unpaid Car Number:");
        lblCarNumber.setBounds(30, 100, 300, 30);
        lblCarNumber.setForeground(Color.WHITE);
        lblCarNumber.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblCarNumber);

        carNumberDropdown = new JComboBox<>();
        carNumberDropdown.setBounds(350, 100, 400, 30);
        panel.add(carNumberDropdown);

        btnPayNow = new JButton("Pay Now");
        btnPayNow.setBounds(350, 220, 150, 40);
        btnPayNow.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnPayNow.setBackground(Color.BLACK);
        btnPayNow.setForeground(Color.WHITE);
        btnPayNow.setBorder(new LineBorder(Color.WHITE, 1));
        btnPayNow.addActionListener(e -> {
            String selectedCarNumber = (String) carNumberDropdown.getSelectedItem();
            if (selectedCarNumber == null) {
                JOptionPane.showMessageDialog(null, "Please select a car number.");
                return;
            }
 
            int transactionId = fetchTransactionId(selectedCarNumber);
            if (transactionId == -1) {
                JOptionPane.showMessageDialog(null, "Transaction ID not found for this car number.");
                return;
            }
 
            updatePaymentStatus(transactionId);
 
            setVisible(false);
            new PaytmPaymentInterface(selectedCarNumber, transactionId);
        });
        panel.add(btnPayNow);

        loadUnpaidCarNumbers(); 

        setVisible(true);
    }

    private void loadUnpaidCarNumbers() {
        try {
            conn c = new conn();
            PreparedStatement pstmt = c.c.prepareStatement(
                "SELECT pb.car_number " +
                "FROM paybill pb " +
                "JOIN paid p ON pb.transaction_id = p.txnid " +
                "WHERE p.paid_unpaid = 0 AND pb.username = ?"
            );
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                carNumberDropdown.addItem(rs.getString("car_number"));  
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int fetchTransactionId(String carNumber) {
        int transactionId = -1;  
        try {
            conn c = new conn(); 
            String query = "SELECT pb.transaction_id FROM paybill pb " +
                           "JOIN paid p ON pb.transaction_id = p.txnid " +
                           "WHERE pb.car_number = ? AND p.paid_unpaid = 0";
            PreparedStatement pstmt = c.c.prepareStatement(query);
            pstmt.setString(1, carNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                transactionId = rs.getInt("transaction_id");  
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionId;
    }
 
    private void updatePaymentStatus(int transactionId) {
        try {
            conn c = new conn();
            String query = "UPDATE paid SET paid_unpaid = 1 WHERE txnid = ?";
            PreparedStatement pstmt = c.c.prepareStatement(query);
            pstmt.setInt(1, transactionId); 
            pstmt.executeUpdate();  
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating payment status.");
        }
    }

    public void actionPerformed(ActionEvent ae) { 
    }

    private void fetchBillDetails(String carNumber) {
        try {
            conn c = new conn();
            String query = "SELECT total_bill FROM paybill WHERE car_number = ?";
            PreparedStatement pstmt = c.c.prepareStatement(query);
            pstmt.setString(1, carNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double totalBill = rs.getDouble("total_bill");
                JOptionPane.showMessageDialog(null, "Total Bill for " + carNumber + ": " + totalBill);
            } else {
                JOptionPane.showMessageDialog(null, "No unpaid bill found for this car number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new paybill("Ayush@2003");
    }
}
