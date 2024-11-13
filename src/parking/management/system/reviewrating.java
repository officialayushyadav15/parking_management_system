/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
public class reviewrating extends JFrame implements ActionListener{
 

    JTextField parkingLotNameBox, reviewBox;
    JSlider ratingSlider;
    JButton submit, cancel;
    String userUsername;
     
    reviewrating(String userUsername) {
        this.userUsername = userUsername;
        
        setSize(500, 400);
        setLocation(750, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
         
        getContentPane().setBackground(Color.BLACK);
         
        Font labelFont = new Font("Tahoma", Font.PLAIN, 18);
        Font textFieldFont = new Font("Tahoma", Font.PLAIN, 16);
 
        JLabel heading = new JLabel("Review & Rating");
        heading.setBounds(130, 30, 300, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));  
        heading.setForeground(Color.WHITE);  
        add(heading);
         
        JLabel parkingLotLabel = new JLabel("Parking Lot Name");
        parkingLotLabel.setBounds(40, 100, 150, 30);
        parkingLotLabel.setForeground(Color.WHITE);  
        parkingLotLabel.setFont(labelFont);
        add(parkingLotLabel);

        parkingLotNameBox = new JTextField();
        parkingLotNameBox.setBounds(200, 100, 250, 30);
        parkingLotNameBox.setBackground(Color.WHITE);   
        parkingLotNameBox.setForeground(Color.BLACK);   
        parkingLotNameBox.setFont(textFieldFont);
        add(parkingLotNameBox);
         
        JLabel reviewLabel = new JLabel("Review");
        reviewLabel.setBounds(40, 150, 150, 30);
        reviewLabel.setForeground(Color.WHITE);  
        reviewLabel.setFont(labelFont);
        add(reviewLabel);

        reviewBox = new JTextField();
        reviewBox.setBounds(200, 150, 250, 60);
        reviewBox.setBackground(Color.WHITE);  
        reviewBox.setForeground(Color.BLACK);  
        reviewBox.setFont(textFieldFont);
        add(reviewBox);
         
        JLabel ratingLabel = new JLabel("Rating");
        ratingLabel.setBounds(40, 230, 150, 30);
        ratingLabel.setForeground(Color.WHITE);  
        ratingLabel.setFont(labelFont);
        add(ratingLabel);

        ratingSlider = new JSlider(1, 5, 3);
        ratingSlider.setBounds(200, 230, 250, 50);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setBackground(Color.BLACK);   
        ratingSlider.setForeground(Color.WHITE);   
        add(ratingSlider);
         
        submit = new JButton("Submit");
        submit.setBounds(80, 300, 150, 35);
        submit.addActionListener(this);
        submit.setFont(labelFont);
        submit.setBackground(Color.WHITE);   
        submit.setForeground(Color.BLACK);   
        add(submit);

        // Cancel Button
        cancel = new JButton("Cancel");
        cancel.setBounds(260, 300, 150, 35);
        cancel.addActionListener(this);
        cancel.setFont(labelFont);
        cancel.setBackground(Color.WHITE);   
        cancel.setForeground(Color.BLACK);  
        add(cancel);
        
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String parkingLotName = parkingLotNameBox.getText();
            String reviewText = reviewBox.getText();
            int rating = ratingSlider.getValue();
            
            String query = "INSERT INTO review (user_username, parking_lot_name, rating, review_text) " +
                           "VALUES ('" + userUsername + "', '" + parkingLotName + "', '" + rating + "', '" + reviewText + "')";

            try {
                conn c = new conn();
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Review and Rating Submitted Successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) { 
        new reviewrating("Ayush@2003");  
    }

}
