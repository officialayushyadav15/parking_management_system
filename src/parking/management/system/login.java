
package parking.management.system;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
public class login extends JFrame implements ActionListener{
    JButton login,cancel,signup,signupo;
    JTextField usernamebox, passwordbox;
    Choice logginas;
    login(){
    super("Login Page");
    getContentPane().setBackground(Color.BLACK);
    setLayout(null);

    Font labelFont = new Font("Arial", Font.BOLD, 20); 
    Font textFieldFont = new Font("Arial", Font.PLAIN, 20); 

    JLabel username = new JLabel("Username");
    username.setBounds(280, 350, 150, 30);
    username.setForeground(Color.WHITE); 
    username.setFont(labelFont); 
    add(username);

    usernamebox = new JTextField();
    usernamebox.setBounds(430, 350, 250, 30);
    usernamebox.setBackground(Color.LIGHT_GRAY);  
    usernamebox.setForeground(Color.BLACK);  
    usernamebox.setFont(textFieldFont); 
    add(usernamebox);

    JLabel password = new JLabel("Password");
    password.setBounds(280, 400, 150, 30);
    password.setForeground(Color.WHITE);  
    password.setFont(labelFont);
    add(password);

    passwordbox = new JPasswordField();
    passwordbox.setBounds(430, 400, 250, 30);
    passwordbox.setBackground(Color.LIGHT_GRAY);  
    passwordbox.setForeground(Color.BLACK); 
    passwordbox.setFont(textFieldFont);
    add(passwordbox);

    JLabel loginas = new JLabel("Login as");
    loginas.setBounds(280, 450, 150, 30);
    loginas.setForeground(Color.WHITE);  
    loginas.setFont(labelFont); 
    add(loginas);

    logginas = new Choice();
    logginas.add("OWNER");
    logginas.add("USER");
    logginas.setBounds(430, 450, 250, 30);
    logginas.setFont(textFieldFont); 
    add(logginas);

    login = new JButton("Login");
    login.setBounds(320, 520, 150, 35);
    login.addActionListener(this);
    login.setFont(labelFont);
    add(login);

    cancel = new JButton("Cancel");
    cancel.setBounds(495, 520, 150, 35);
    cancel.addActionListener(this);
    cancel.setFont(labelFont); 
    add(cancel);

    signup = new JButton("Create User Account");
    signup.setBounds(350, 570, 260, 35);
    signup.addActionListener(this);
    signup.setFont(labelFont); 
    add(signup);
    
    signupo = new JButton("Create Owner Account");
    signupo.setBounds(350, 620, 260, 35);
    signupo.addActionListener(this);
    signupo.setFont(labelFont); 
    add(signupo);

    ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/park.jpg"));
    Image i5 = i4.getImage().getScaledInstance(1500, 900, Image.SCALE_DEFAULT);
    ImageIcon i6 = new ImageIcon(i5);
    JLabel image = new JLabel(i6);
    image.setBounds(0, 0, 1000, 800);
    add(image);

    ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icon/logo1.png"));
    Image logoImage = logoIcon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT);
    ImageIcon logoScaledIcon = new ImageIcon(logoImage);
    JLabel logoLabel = new JLabel(logoScaledIcon);
    logoLabel.setBounds(60, 60, 850, 330); 
    image.add(logoLabel); 
    
    
    JPanel panel = new JPanel();
        panel.setBounds(90, 90, 800, 585);
        panel.setBackground(Color.BLACK);
        panel.setOpaque(true); 
        panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2), 
             "LOGIN", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(null);
        
        image.add(panel); 

    setSize(1000, 800);
    setLocation(450, 150);
    setVisible(true);
}
    public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == login) {
        String susername = usernamebox.getText();
        String spassword = passwordbox.getText();
        String userType = logginas.getSelectedItem(); 
        
        try {
            conn c = new conn();
            String query = "";
            
            if (userType.equals("USER")) {
                query = "SELECT * FROM userlogin WHERE username = '" + susername + "' AND password = '" + spassword + "'";
            } else if (userType.equals("OWNER")) {
                query = "SELECT * FROM ownerlogin WHERE username = '" + susername + "' AND password = '" + spassword + "'";
            }
            
            ResultSet rs = c.s.executeQuery(query);
            
            if (rs.next()) {
                setVisible(false);
                if (userType.equals("USER")) {
                    new project("user",susername);
                } else if (userType.equals("OWNER")) {
                    new project("owner",susername);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Login");
                usernamebox.setText("");
                passwordbox.setText("");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else if (ae.getSource() == cancel) {
        setVisible(false);
    } else if (ae.getSource() == signup) {
        setVisible(false);
        new signup();
    } else if (ae.getSource() == signupo) {
        setVisible(false);
        new signupo();
    }
}


    
    public static void main(String[] args) {
        new login();
    }
}
