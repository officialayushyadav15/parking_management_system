/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;





public class booking extends JFrame implements ActionListener{



    public static final String ACCOUNT_SID = "AC97b8228a2fbc6bc4e6c891e7736a4bfd";
    public static final String AUTH_TOKEN = "d89a3b761014e34bdcc8f846016df551";
    public static final String FROM_PHONE_NUMBER = "whatsapp:+14155238886";
    JTextField tfCarNumber, tfWhatsApp, tfEmail, tfLocation, tfName, tfTimeOfStay;
    JComboBox<String> paymentOption, tfVehicleType;
    JSpinner hoursSpinner, minutesSpinner;
    JComboBox<String> amPmCombo;
    JButton book, cancel;
    String username;

    booking(String username) {
        this.username = username;

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
                "BOOK PARKING SLOT", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(null);
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);

        tfCarNumber = addFormElement(panel, "Car Number", 30);
        tfWhatsApp = addFormElement(panel, "WhatsApp", 74);
        tfEmail = addFormElement(panel, "Email", 162);
        tfLocation = addFormElement(panel, "Parking Lot Name", 206);
        tfName = addFormElement(panel, "Name", 294);
        tfTimeOfStay = addFormElement(panel, "Time of Stay (in hours)", 426);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(30, 338, 300, 30);
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblUsername);

        JTextField tfUsername = new JTextField(username);
        tfUsername.setBounds(350, 338, 400, 30);
        tfUsername.setEditable(false);
        panel.add(tfUsername);

        JLabel lblVehicleType = new JLabel("Vehicle Type:");
        lblVehicleType.setBounds(30, 118, 300, 30);
        lblVehicleType.setForeground(Color.WHITE);
        lblVehicleType.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblVehicleType);

        tfVehicleType = new JComboBox<>(new String[]{"Car", "Bicycle", "Cycle"});
        tfVehicleType.setBounds(350, 118, 300, 30);
        panel.add(tfVehicleType);

        JLabel lblHours = new JLabel("Hours:");
        lblHours.setBounds(30, 250, 100, 30);
        lblHours.setForeground(Color.WHITE);
        lblHours.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblHours);

        hoursSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 12, 1));
        hoursSpinner.setBounds(150, 250, 50, 30);
        panel.add(hoursSpinner);

        JLabel lblMinutes = new JLabel("Minutes:");
        lblMinutes.setBounds(230, 250, 100, 30);
        lblMinutes.setForeground(Color.WHITE);
        lblMinutes.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblMinutes);

        minutesSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        minutesSpinner.setBounds(350, 250, 50, 30);
        panel.add(minutesSpinner);

        JLabel lblAmPm = new JLabel("AM/PM:");
        lblAmPm.setBounds(430, 250, 100, 30);
        lblAmPm.setForeground(Color.WHITE);
        lblAmPm.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblAmPm);

        amPmCombo = new JComboBox<>(new String[]{"AM", "PM"});
        amPmCombo.setBounds(540, 250, 70, 30);
        panel.add(amPmCombo);

        JLabel lblPaymentOption = new JLabel("Payment Option:");
        lblPaymentOption.setBounds(30, 382, 200, 30);
        lblPaymentOption.setForeground(Color.WHITE);
        lblPaymentOption.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(lblPaymentOption);

        paymentOption = new JComboBox<>(new String[]{"Cash on Delivery", "Online"});
        paymentOption.setBounds(350, 382, 200, 30);
        panel.add(paymentOption);

        book = new JButton("Book");
        book.setBounds(200, 480, 150, 40);
        book.setFont(new Font("Tahoma", Font.BOLD, 16));
        book.setBackground(Color.BLACK);
        book.setForeground(Color.WHITE);
        book.setBorder(new LineBorder(Color.WHITE, 1));
        book.addActionListener(this);
        panel.add(book);

        cancel = new JButton("Cancel");
        cancel.setBounds(400, 480, 150, 40);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 16));
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setBorder(new LineBorder(Color.WHITE, 1));
        cancel.addActionListener(this);
        panel.add(cancel);

        setVisible(true);
    }

    private JTextField addFormElement(JPanel panel, String label, int yPosition) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(30, yPosition, 300, 30);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(jLabel);

        JTextField textField = new JTextField();
        textField.setBounds(350, yPosition, 400, 30);
        panel.add(textField);
        return textField;
    }

    

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == book) {
            String carNumber = tfCarNumber.getText();
            String vehicleType = (String) tfVehicleType.getSelectedItem();
            String whatsapp = tfWhatsApp.getText();
            String payment = (String) paymentOption.getSelectedItem();
            String email = tfEmail.getText();
            String location = tfLocation.getText();
            String name = tfName.getText();
            String timeOfStayString = tfTimeOfStay.getText();

            if (carNumber.isEmpty() || vehicleType.isEmpty() || whatsapp.isEmpty() ||
                payment.isEmpty() || email.isEmpty() || location.isEmpty() ||
                name.isEmpty() || timeOfStayString.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            int timeOfStay;
            try {
                timeOfStay = Integer.parseInt(timeOfStayString);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Time of stay must be a number.");
                return;
            }

            int hours = (int) hoursSpinner.getValue();
            int minutes = (int) minutesSpinner.getValue();
            String arrivalTime = String.format("%02d:%02d:00", hours % 12, minutes);
            if (amPmCombo.getSelectedItem().equals("PM")) {
                hours += 12;
            }

            try {
                conn c = new conn();
                Statement st = c.s;

           
                String checkCarQuery = "SELECT car_number FROM book WHERE car_number = '" + carNumber + "'";
                ResultSet carCheckResult = st.executeQuery(checkCarQuery);

                if (carCheckResult.next()) {
                    JOptionPane.showMessageDialog(null, "This car is already booked.");
                    return;
                }

                String getTotalSlotsQuery = "SELECT total_slots FROM parkinglot WHERE parking_lot_name = '" + location + "'";
                ResultSet rsParkingLot = st.executeQuery(getTotalSlotsQuery);
                int totalSlots = 0;
                if (rsParkingLot.next()) {
                    totalSlots = rsParkingLot.getInt("total_slots");
                } else {
                    JOptionPane.showMessageDialog(null, "Parking lot not found.");
                    return;
                }

                String countBookedSlotsQuery = "SELECT COUNT(*) AS booked_count FROM records WHERE parking_lot = '" + location + "' AND booked_cancelled = 'booked' AND exit_time IS NULL";
                ResultSet rsBookedSlots = st.executeQuery(countBookedSlotsQuery);
                int bookedSlots = 0;
                if (rsBookedSlots.next()) {
                    bookedSlots = rsBookedSlots.getInt("booked_count");
                }

                if (bookedSlots < totalSlots) {
                    String insertBookingQuery = String.format(
                        "INSERT INTO book (car_number, vehicle_type, whatsapp, email, parking_lot, name, time_of_stay, arrival_time, payment_option, username) " +
                        "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %d, '%s', '%s', '%s')",
                        carNumber, vehicleType, whatsapp, email, location, name, timeOfStay, arrivalTime, payment, username
                    );

                    String insertRecordQuery = String.format(
                        "INSERT INTO records (car_number, vehicle_type, whatsapp, email, parking_lot, name, time_of_stay, arrival_time, payment_option, username, booked_cancelled) " +
                        "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %d, '%s', '%s', '%s','booked')",
                        carNumber, vehicleType, whatsapp, email, location, name, timeOfStay, arrivalTime, payment, username
                    );

                    st.executeUpdate(insertBookingQuery);
                    st.executeUpdate(insertRecordQuery);
                    
                    String message = String.format("Hello %s,\n" +
                                               "Your booking is confirmed!\n" +
                                               "Parking Lot: %s\n" +
                                               "Arrival Time: %s\n" +
                                               "Time of Stay: %d hours\n" +
                                               "Payment Option: %s\n" +
                                               "Thank you for choosing our parking service.",
                                               name, location, arrivalTime, timeOfStay, payment);

                sendWhatsAppNotification(whatsapp, message);


                    JOptionPane.showMessageDialog(null, "Booking successful!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Parking lot is full.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            dispose();
        }
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





    public static void main(String[] args) {
         new booking("Ayush@2003");
    }

}
