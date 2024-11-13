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
public class generatebill extends JFrame implements ActionListener{


    JComboBox<String> carNumberDropdown;
    JTextField tfName, tfEmail, tfWhatsApp, tfTotalBill, tfPaidStatus;
    JButton btnFetchDetails;
    String username;

    generatebill(String username) {
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
                "BILL DETAILS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(null);
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(30, 30, 300, 30);
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblUsername);

        JTextField tfUsername = new JTextField(username);
        tfUsername.setBounds(350, 30, 400, 30);
        tfUsername.setEditable(false);
        panel.add(tfUsername);

        JLabel lblCarNumber = new JLabel("Select Car Number:");
        lblCarNumber.setBounds(30, 230, 300, 30);
        lblCarNumber.setForeground(Color.WHITE);
        lblCarNumber.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblCarNumber);

        carNumberDropdown = new JComboBox<>();
        carNumberDropdown.setBounds(350, 230, 400, 30);
        panel.add(carNumberDropdown);

        btnFetchDetails = new JButton("Fetch Details");
        btnFetchDetails.setBounds(350, 300, 150, 40);
        btnFetchDetails.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnFetchDetails.setBackground(Color.BLACK);
        btnFetchDetails.setForeground(Color.WHITE);
        btnFetchDetails.setBorder(new LineBorder(Color.WHITE, 1));
        btnFetchDetails.addActionListener(this);
        panel.add(btnFetchDetails);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 80, 300, 30);
        lblName.setForeground(Color.WHITE);
        lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblName);

        tfName = new JTextField();
        tfName.setBounds(350, 80, 400, 30);
        tfName.setEditable(false);
        panel.add(tfName);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 130, 300, 30);
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(350, 130, 400, 30);
        tfEmail.setEditable(false);
        panel.add(tfEmail);

        JLabel lblWhatsApp = new JLabel("WhatsApp Number:");
        lblWhatsApp.setBounds(30, 180, 300, 30);
        lblWhatsApp.setForeground(Color.WHITE);
        lblWhatsApp.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblWhatsApp);

        tfWhatsApp = new JTextField();
        tfWhatsApp.setBounds(350, 180, 400, 30);
        tfWhatsApp.setEditable(false);
        panel.add(tfWhatsApp);

        JLabel lblTotalBill = new JLabel("Total Bill:");
        lblTotalBill.setBounds(30, 370, 300, 30);
        lblTotalBill.setForeground(Color.WHITE);
        lblTotalBill.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblTotalBill);

        tfTotalBill = new JTextField();
        tfTotalBill.setBounds(350, 370, 400, 30);
        tfTotalBill.setEditable(false);
        panel.add(tfTotalBill);

        JLabel lblPaidStatus = new JLabel("Payment Status:");
        lblPaidStatus.setBounds(30, 420, 300, 30);
        lblPaidStatus.setForeground(Color.WHITE);
        lblPaidStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblPaidStatus);

        tfPaidStatus = new JTextField();
        tfPaidStatus.setBounds(350, 420, 400, 30);
        tfPaidStatus.setEditable(false);
        panel.add(tfPaidStatus);

        loadCarNumbers();
        loadUserDetails(); 

        setVisible(true);
    }

    private void loadCarNumbers() {
        try {
            conn c = new conn();
            Statement st = c.s;

            String query = "SELECT car_number FROM records WHERE username = '" + username + "'";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                carNumberDropdown.addItem(rs.getString("car_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadUserDetails() {
        try {
            conn c = new conn();
            Statement st = c.s;

            String query = "SELECT name, email, whatsapp FROM userlogin WHERE username = '" + username + "'";
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                tfName.setText(rs.getString("name"));
                tfEmail.setText(rs.getString("email"));
                tfWhatsApp.setText(rs.getString("whatsapp"));
            } else {
                JOptionPane.showMessageDialog(null, "No user found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnFetchDetails) {
            String selectedCarNumber = (String) carNumberDropdown.getSelectedItem();
            if (selectedCarNumber == null) {
                JOptionPane.showMessageDialog(null, "Please select a car number.");
                return;
            }

            fetchBillDetails(selectedCarNumber);
        }
    }

 private void fetchBillDetails(String carNumber) {
        try {
            conn c = new conn();
            String query = "SELECT (SUM(COALESCE(bill, 0) + COALESCE(report_bill, 0) + COALESCE(cancelbook, 0)) * 1.05) AS total_bill, " +
                           "p.paid_unpaid " +
                           "FROM paybill pb " +
                           "LEFT JOIN paid p ON pb.transaction_id = p.txnid " +
                           "WHERE pb.car_number = ? " +
                           "GROUP BY pb.car_number, p.paid_unpaid";

            PreparedStatement pstmt = c.c.prepareStatement(query);
            pstmt.setString(1, carNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double totalBill = rs.getDouble("total_bill");
                int paidStatus = rs.getInt("paid_unpaid");

                tfTotalBill.setText(String.valueOf(totalBill));
                tfPaidStatus.setText(paidStatus == 1 ? "Paid" : "Unpaid");
            } else {
                JOptionPane.showMessageDialog(null, "No bill found for this car number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new generatebill("Ayush@2003");
    }

}
