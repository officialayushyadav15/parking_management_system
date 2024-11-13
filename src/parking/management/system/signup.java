
package parking.management.system;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.sql.DriverManager;
public class signup extends JFrame implements ActionListener{
    JButton create,back;  
    JTextField nameField,emailField,usernameField,phoneField,whatsappField;
    JPasswordField passwordField,cnfpasswordField;
    signup(){

        setBounds(450, 150, 1000, 800);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);
 
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/park.jpg"));
        Image i5 = i4.getImage().getScaledInstance(1500, 900, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image = new JLabel(i6);
        image.setBounds(0, 0, 1000, 800);
        add(image);
 
        JPanel panel = new JPanel();
        panel.setBounds(60, 60, 860, 645);
        panel.setBackground(Color.BLACK);
        panel.setOpaque(true);  
        panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 2),
                "CREATE ACCOUNT AS USER", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel.setLayout(null);

        image.add(panel);  
        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icon/logo1.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon logoScaledIcon = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(logoScaledIcon);
        logoLabel.setBounds(330, 30, 200, 200);  
        panel.add(logoLabel);  
 
        JLabel heading = new JLabel("Name");
        heading.setBounds(150, 220, 200, 30);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(heading);

        nameField = new JTextField();
        nameField.setBounds(370, 220, 350, 30);
        panel.add(nameField);

        JLabel email = new JLabel("E-mail ID");
        email.setBounds(150, 270, 200, 30);
        email.setForeground(Color.WHITE);
        email.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(email);

        emailField = new JTextField();
        emailField.setBounds(370, 270, 350, 30);
        panel.add(emailField);

        JLabel username = new JLabel("Username");
        username.setBounds(150, 320, 200, 30);
        username.setForeground(Color.WHITE);
        username.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(username);

        usernameField = new JTextField();
        usernameField.setBounds(370, 320, 350, 30);
        panel.add(usernameField);

        JLabel password = new JLabel("Password");
        password.setBounds(150, 370, 200, 30);
        password.setForeground(Color.WHITE);
        password.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(password);

        passwordField = new JPasswordField();
        passwordField.setBounds(370, 370, 350, 30);
        panel.add(passwordField);

        JLabel cnfpassword = new JLabel("Confirm Password");
        cnfpassword.setBounds(150, 420, 200, 30);
        cnfpassword.setForeground(Color.WHITE);
        cnfpassword.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(cnfpassword);

        cnfpasswordField = new JPasswordField();
        cnfpasswordField.setBounds(370, 420, 350, 30);
        panel.add(cnfpasswordField);

        JLabel phone = new JLabel("Phone Number");
        phone.setBounds(150, 470, 200, 30);
        phone.setForeground(Color.WHITE);
        phone.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(phone);

        phoneField = new JTextField();
        phoneField.setBounds(370, 470, 350, 30);
        panel.add(phoneField);

        JLabel whatsapp = new JLabel("Whatsapp Number");
        whatsapp.setBounds(150, 520, 200, 30);
        whatsapp.setForeground(Color.WHITE);
        whatsapp.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(whatsapp);

        whatsappField = new JTextField();
        whatsappField.setBounds(370, 520, 350, 30);
        panel.add(whatsappField);
        
        create = new JButton("Create");
        create.setBounds(250, 570, 150, 40);
        create.addActionListener(this);
        create.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(create);
        
        back = new JButton("Back");
        back.setBounds(450, 570, 150, 40);
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
            String spass = new String(passwordField.getPassword());
            String scnf = new String(cnfpasswordField.getPassword());
 
            if (susername.isEmpty() || sname.isEmpty() || semail.isEmpty() || sphone.isEmpty() || swhatsapp.isEmpty() || spass.isEmpty() || scnf.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
            } else if (!spass.equals(scnf)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match");
            } else {
                try{
                conn c= new conn();
                String query = "insert into userlogin values('"+sphone+"','"+susername+"','"+sname+"','"+semail+"','"+swhatsapp+"','"+spass+"')";
                c.s.executeUpdate(query);
            }catch(Exception e){
                 e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Account created successfully");
                setVisible(false);
                new login();  
            }
            
        }
        }
    }
    
    public static void main(String[] args){
        new signup();
    }
}
