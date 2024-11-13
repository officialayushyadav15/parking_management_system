/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*; 
public class PaytmPaymentInterface extends JFrame implements ActionListener{



    String meter;
    JButton back;
    int transactionId;

    PaytmPaymentInterface(String meter, int transactionId) {
        this.meter = meter;
        this.transactionId = transactionId;

        setLayout(new BorderLayout());

        try {
            ImageIcon scannerIcon = new ImageIcon(ClassLoader.getSystemResource("icon/scanner.jpg"));
            Image scaledImage = scannerIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            JLabel scannerLabel = new JLabel(new ImageIcon(scaledImage));
            add(scannerLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            System.out.println("Error loading image.");
        }

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);

        back = new JButton("Back");
        back.addActionListener(this);
        topPanel.add(back, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        setSize(800, 600);
        setLocation(400, 150);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        updatePaymentStatus();
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new paybill("Ayush@2003");
    }

    public void updatePaymentStatus() {
        try {
            conn c = new conn();
            String query = "UPDATE paid SET paid_unpaid = 1 WHERE txnid = ?";
            PreparedStatement pstmt = c.c.prepareStatement(query);
            pstmt.setInt(1, transactionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PaytmPaymentInterface("123456", 1);
    }
}

