/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.activation.DataSource;
public class sendnotification extends JFrame implements ActionListener{
 

    private JTable carTable;
    private DefaultTableModel tableModel;
    private JButton sendButton;
    private String parkingLotName;

    public static final String ACCOUNT_SID = "AC97b8228a2fbc6bc4e6c891e7736a4bfd";
    public static final String AUTH_TOKEN = "d89a3b761014e34bdcc8f846016df551";
    public static final String FROM_PHONE_NUMBER = "whatsapp:+14155238886";

    public sendnotification(String parkingLotName) {
        this.parkingLotName = parkingLotName;
        setTitle("Send Notification");
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
                "Unregistered Cars", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(new BorderLayout());
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);

        tableModel = new DefaultTableModel(new String[]{"Car Number", "Arrival Time", "Duration", "Cost"}, 0);
        carTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        carTable.setFont(new Font("Arial", Font.PLAIN, 18));
        carTable.setRowHeight(35);

        loadCars();

        JScrollPane scrollPane = new JScrollPane(carTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        sendButton = new JButton("Send Notification");
        sendButton.setFont(new Font("Arial", Font.BOLD, 20));
        sendButton.setPreferredSize(new Dimension(350, 60));
        sendButton.addActionListener(this);
        panel.add(sendButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadCars() {
        String query = "SELECT car_number, arrival_time, time_of_stay FROM book WHERE parking_lot = '" + parkingLotName + "' AND arr = 0";
        try {
            conn c = new conn();
            Statement st = c.s;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String carNumber = rs.getString("car_number");
                String arrivalTime = rs.getString("arrival_time");
                int timeOfStay = rs.getInt("time_of_stay");
                double cost = calculateCost(timeOfStay, parkingLotName);
                tableModel.addRow(new Object[]{carNumber, arrivalTime, timeOfStay, cost});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading cars.");
        }
    }

    private double calculateCost(int timeOfStay, String parkingLotName) {
        double hourlyRate = 0.0;
        try {
            conn c = new conn();
            Statement st = c.s;
            String rateQuery = "SELECT hourly_rate_standard FROM parkinglot WHERE parking_lot_name = '" + parkingLotName + "'";
            ResultSet rs = st.executeQuery(rateQuery);
            if (rs.next()) {
                hourlyRate = rs.getDouble("hourly_rate_standard");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hourlyRate * timeOfStay;
    }

    public void actionPerformed(ActionEvent ae) {
        int row = carTable.getSelectedRow();
        if (row != -1) {
            String carNumber = (String) carTable.getValueAt(row, 0);
            String arrivalTime = (String) carTable.getValueAt(row, 1);
            int duration = (int) carTable.getValueAt(row, 2);
            double cost = (double) carTable.getValueAt(row, 3);
            String whatsapp = getWhatsAppNumber(carNumber);
            String email = getEmail(carNumber);

            String message = String.format("Hello!\n" +
                                           "Welcome to %s Parking.\n" +
                                           "Car Number: %s\n" +
                                           "Arrival Time: %s\n" +
                                           "Duration: %d hours\n" +
                                           "Cost: %.2f\n" +
                                           "Please pay upon arrival.\n" +
                                           "Thank you!",
                                           parkingLotName, carNumber, arrivalTime, duration, cost);

            sendWhatsAppNotification(whatsapp, message);
            sendEmailNotification(email, message);

            JOptionPane.showMessageDialog(null, "Notification sent to " + carNumber);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a car to send notification.");
        }
    }

    private String getWhatsAppNumber(String carNumber) {
        String whatsapp = "";
        try {
            conn c = new conn();
            Statement st = c.s;
            String query = "SELECT whatsapp FROM book WHERE car_number = '" + carNumber + "'";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                whatsapp = rs.getString("whatsapp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return whatsapp;
    }

    private String getEmail(String carNumber) {
        String email = "";
        try {
            conn c = new conn();
            Statement st = c.s;
            String query = "SELECT email FROM book WHERE car_number = '" + carNumber + "'";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                email = rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }

    private void sendWhatsAppNotification(String whatsapp, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        try {
            Message whatsappMessage = Message.creator(
                new PhoneNumber("whatsapp:",whatsapp),
                new PhoneNumber(FROM_PHONE_NUMBER),
                message
            ).create();

            System.out.println("WhatsApp message sent to " + whatsapp);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to send WhatsApp message.");
        }
    }

    private void sendEmailNotification(String email, String messageContent) {
        String host = "smtp.gmail.com";
        String from = "parkingmanagementsystem.pms@gmail.com";
        String password = "fcqx bvrr lubj wgdp"; 

        Properties properties = System.getProperties();
properties.put("mail.smtp.host", host);
properties.put("mail.smtp.port", "587");
properties.put("mail.smtp.auth", "true");
properties.put("mail.smtp.starttls.enable", "true");
properties.put("mail.smtp.ssl.protocols", "TLSv1.3");
properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");


        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(RecipientType.TO, new InternetAddress(email));
            message.setSubject("Parking Notification - " + parkingLotName);
            message.setText(messageContent);

            Transport.send(message);
            System.out.println("Email sent to " + email);
        } catch (MessagingException mex) {
            mex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to send email.");
        }
    }

    public static void main(String[] args) {
        new sendnotification("Cars");
    }
}
