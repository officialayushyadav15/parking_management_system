
package parking.management.system;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
public class signupo extends JFrame implements ActionListener{
    JButton create,back;
    JTextField nameField,emailField,usernameField,phoneField,whatsappField,numberv,loc;
    JPasswordField passwordField,cnfpasswordField;
    signupo(){

      
    setBounds(450, 150, 1000, 800);
    getContentPane().setBackground(Color.BLACK);
    setLayout(null);
 
    ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/park.jpg"));
    Image i5 = i4.getImage().getScaledInstance(1000, 800, Image.SCALE_DEFAULT);
    ImageIcon i6 = new ImageIcon(i5);
    JLabel image = new JLabel(i6);
    image.setBounds(0, 0, 1000, 800);
    add(image);
 
    JPanel panel = new JPanel();
    panel.setBounds(50, 50, 900, 670);
    panel.setBackground(Color.BLACK);
    panel.setOpaque(true);  
    panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2),
            "CREATE ACCOUNT AS OWNER", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
    panel.setLayout(null);

    image.add(panel);  
    ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icon/logo1.png"));
    Image logoImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
    ImageIcon logoScaledIcon = new ImageIcon(logoImage);
    JLabel logoLabel = new JLabel(logoScaledIcon);
    logoLabel.setBounds(400, 20, 100, 100);  
    panel.add(logoLabel);  
    JLabel heading = new JLabel("Name");
    heading.setBounds(160, 150, 200, 30);
    heading.setForeground(Color.WHITE);
    heading.setFont(new Font("Tahoma", Font.BOLD, 20));
    panel.add(heading);

    nameField = new JTextField();
    nameField.setBounds(410, 150, 300, 30);
    panel.add(nameField);

    JLabel email = new JLabel("E-mail ID");
    email.setBounds(160, 200, 200, 30);
    email.setForeground(Color.WHITE);
    email.setFont(new Font("Tahoma", Font.BOLD, 20));
    panel.add(email);

    emailField = new JTextField();
    emailField.setBounds(410, 200, 300, 30);
    panel.add(emailField);

    JLabel username = new JLabel("Username");
    username.setBounds(160, 250, 200, 30);
    username.setForeground(Color.WHITE);
    username.setFont(new Font("Tahoma", Font.BOLD, 20));
    panel.add(username);

    usernameField = new JTextField();
    usernameField.setBounds(410, 250, 300, 30);
    panel.add(usernameField);

    JLabel password = new JLabel("Password");
    password.setBounds(160, 300, 200, 30);
    password.setForeground(Color.WHITE);
    password.setFont(new Font("Tahoma", Font.BOLD, 20));
    panel.add(password);

    passwordField = new JPasswordField();
    passwordField.setBounds(410, 300, 300, 30);
    panel.add(passwordField);

    JLabel cnfpassword = new JLabel("Confirm Password");
    cnfpassword.setBounds(160, 350, 200, 30);
    cnfpassword.setForeground(Color.WHITE);
    cnfpassword.setFont(new Font("Tahoma", Font.BOLD, 20));
    panel.add(cnfpassword);

    cnfpasswordField = new JPasswordField();
    cnfpasswordField.setBounds(410, 350, 300, 30);
    panel.add(cnfpasswordField);

    JLabel phone = new JLabel("Phone Number");
    phone.setBounds(160, 400, 200, 30);
    phone.setForeground(Color.WHITE);
    phone.setFont(new Font("Tahoma", Font.BOLD, 20));
    panel.add(phone);

    phoneField = new JTextField();
    phoneField.setBounds(410, 400, 300, 30);
    panel.add(phoneField);

    JLabel whatsapp = new JLabel("Whatsapp Number");
    whatsapp.setBounds(160, 450, 200, 30);
    whatsapp.setForeground(Color.WHITE);
    whatsapp.setFont(new Font("Tahoma", Font.BOLD, 20));
    panel.add(whatsapp);

    whatsappField = new JTextField();
    whatsappField.setBounds(410, 450, 300, 30);
    panel.add(whatsappField);

    JLabel number = new JLabel("Number of Vacancies");
    number.setBounds(160, 500, 200, 30);
    number.setForeground(Color.WHITE);
    number.setFont(new Font("Tahoma", Font.BOLD, 20));
    panel.add(number);

    numberv = new JTextField();
    numberv.setBounds(410, 500, 300, 30);
    panel.add(numberv);

    JLabel location = new JLabel("Location");
    location.setBounds(160, 550, 200, 30);
    location.setForeground(Color.WHITE);
    location.setFont(new Font("Tahoma", Font.BOLD, 20));
    panel.add(location);

    loc = new JTextField();
    loc.setBounds(410, 550, 300, 30);
    panel.add(loc);

    create = new JButton("Create");
    create.setBounds(250, 600, 150, 40);
    create.addActionListener(this);
    create.setFont(new Font("Tahoma", Font.BOLD, 16));
    panel.add(create);

    back = new JButton("Back");
    back.setBounds(500, 600, 150, 40);
    back.addActionListener(this);
    back.setFont(new Font("Tahoma", Font.BOLD, 16));
    panel.add(back);

    setVisible(true);

    }
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource()==back){
            setVisible(false);
            new login();
            
        }else if (ae.getSource()==create){
            String susername = usernameField.getText();
            String sname = nameField.getText();
            String semail = emailField.getText();
            String sphone = phoneField.getText();
            String swhatsapp = whatsappField.getText();
            String snumber = numberv.getText();
            String sloc = loc.getText();
            String spass = new String(passwordField.getPassword());
            String scnf = new String(cnfpasswordField.getPassword());
            
            if (susername.isEmpty() || sname.isEmpty() || semail.isEmpty() || sphone.isEmpty() || swhatsapp.isEmpty() || spass.isEmpty() || scnf.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
            } else if (!spass.equals(scnf)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match");
            } else {
                try{
                conn c= new conn();
                String query = "insert into ownerlogin values('"+sphone+"','"+susername+"','"+sname+"','"+semail+"','"+swhatsapp+"','"+sloc+"','"+spass+"','"+spass+"')";
                c.s.executeUpdate(query);
            }catch(Exception e){
                 e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Account created successfully");
                setVisible(false);
                new login();}
            }
        }
    }
    
    public static void main(String[] args){
        new signupo();
    }
}
