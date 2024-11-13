package parking.management.system;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class project extends JFrame implements ActionListener{

    String atype;
    String username;
    project(String atype,String username){
        this.atype = atype;
        this.username = username;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/park7.png"));

        JLabel image = new JLabel();
        add(image);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();

                Image i2 = i1.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                ImageIcon i3 = new ImageIcon(i2);
                image.setIcon(i3);
            }
        });
        
        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);
        
        
        JMenu master = new JMenu("Owner");
master.setForeground(Color.BLUE);
JMenu manage = new JMenu("Manage");
manage.setForeground(Color.BLUE);
JMenu recept = new JMenu("Recept");
recept.setForeground(Color.BLUE);
JMenu noti = new JMenu("Message");
noti.setForeground(Color.BLUE);
 
JMenuItem registerParkingLot = new JMenuItem("Register Parking Lot");
registerParkingLot.setFont(new Font("monospaced", Font.PLAIN, 12));
registerParkingLot.setBackground(Color.WHITE);
ImageIcon iconRegisterParkingLot = new ImageIcon(ClassLoader.getSystemResource("icon/hicon2.jpg"));
Image imageRegisterParkingLot = iconRegisterParkingLot.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
registerParkingLot.setIcon(new ImageIcon(imageRegisterParkingLot));
registerParkingLot.setMnemonic('R');
registerParkingLot.addActionListener(this);
registerParkingLot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
 
master.add(registerParkingLot);
 
JMenuItem dashboardOverview = new JMenuItem("Overview");
dashboardOverview.setFont(new Font("monospaced", Font.PLAIN, 12));
dashboardOverview.setBackground(Color.WHITE);
ImageIcon iconDashboard = new ImageIcon(ClassLoader.getSystemResource("icon/hicon1.jpg"));
Image imageDashboard = iconDashboard.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
dashboardOverview.setIcon(new ImageIcon(imageDashboard));
dashboardOverview.setMnemonic('O');
dashboardOverview.addActionListener(this);
dashboardOverview.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
master.add(dashboardOverview);

JMenuItem viewParkedVehicles = new JMenuItem("View Parked Vehicles");
viewParkedVehicles.setFont(new Font("monospaced", Font.PLAIN, 12));
viewParkedVehicles.setBackground(Color.WHITE);
ImageIcon iconVehicles = new ImageIcon(ClassLoader.getSystemResource("icon/icon6.png"));
Image imageVehicles = iconVehicles.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
viewParkedVehicles.setIcon(new ImageIcon(imageVehicles));
viewParkedVehicles.setMnemonic('V');
viewParkedVehicles.addActionListener(this);
viewParkedVehicles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
manage.add(viewParkedVehicles);

JMenuItem manageParkingSlots = new JMenuItem("Manage Parking Slots");
manageParkingSlots.setFont(new Font("monospaced", Font.PLAIN, 12));
manageParkingSlots.setBackground(Color.WHITE);
ImageIcon iconSlots = new ImageIcon(ClassLoader.getSystemResource("icon/icon12.png"));
Image imageSlots = iconSlots.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
manageParkingSlots.setIcon(new ImageIcon(imageSlots));
manageParkingSlots.setMnemonic('S');
manageParkingSlots.addActionListener(this);
manageParkingSlots.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
manage.add(manageParkingSlots);

JMenuItem reportOverstay = new JMenuItem("Report Overstaying Vehicles");
reportOverstay.setFont(new Font("monospaced", Font.PLAIN, 12));
reportOverstay.setBackground(Color.WHITE);
ImageIcon iconOverstay = new ImageIcon(ClassLoader.getSystemResource("icon/report.png"));
Image imageOverstay = iconOverstay.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
reportOverstay.setIcon(new ImageIcon(imageOverstay));
reportOverstay.setMnemonic('R');
reportOverstay.addActionListener(this);
reportOverstay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
manage.add(reportOverstay);

JMenuItem viewArrivals = new JMenuItem("View Expected Arrivals");
viewArrivals.setFont(new Font("monospaced", Font.PLAIN, 12));
viewArrivals.setBackground(Color.WHITE);
ImageIcon iconArrivals = new ImageIcon(ClassLoader.getSystemResource("icon/icon_arrivals.jpeg"));
Image imageArrivals = iconArrivals.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
viewArrivals.setIcon(new ImageIcon(imageArrivals));
viewArrivals.setMnemonic('A');
viewArrivals.addActionListener(this);
viewArrivals.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
master.add(viewArrivals);

JMenuItem updateSlots = new JMenuItem("Update Slot Assignments");
updateSlots.setFont(new Font("monospaced", Font.PLAIN, 12));
updateSlots.setBackground(Color.WHITE);
ImageIcon iconUpdateSlots = new ImageIcon(ClassLoader.getSystemResource("icon/icon4.png"));
Image imageUpdateSlots = iconUpdateSlots.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
updateSlots.setIcon(new ImageIcon(imageUpdateSlots));
updateSlots.setMnemonic('U');
updateSlots.addActionListener(this);
updateSlots.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
manage.add(updateSlots);

JMenuItem generateReceipts = new JMenuItem("Generate Receipts");
generateReceipts.setFont(new Font("monospaced", Font.PLAIN, 12));
generateReceipts.setBackground(Color.WHITE);
ImageIcon iconReceipts = new ImageIcon(ClassLoader.getSystemResource("icon/icon12.png"));
Image imageReceipts = iconReceipts.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
generateReceipts.setIcon(new ImageIcon(imageReceipts));
generateReceipts.setMnemonic('G');
generateReceipts.addActionListener(this);
generateReceipts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
recept.add(generateReceipts);

JMenuItem sendNotifications = new JMenuItem("Send Notifications (WhatsApp/Email)");
sendNotifications.setFont(new Font("monospaced", Font.PLAIN, 12));
sendNotifications.setBackground(Color.WHITE);
ImageIcon iconNotifications = new ImageIcon(ClassLoader.getSystemResource("icon/noti.jpg"));
Image imageNotifications = iconNotifications.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
sendNotifications.setIcon(new ImageIcon(imageNotifications));
sendNotifications.setMnemonic('T');
sendNotifications.addActionListener(this);
sendNotifications.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
noti.add(sendNotifications);
 
JMenu user = new JMenu("User");
user.setForeground(Color.BLUE);
 
JMenuItem searchSlot = new JMenuItem("Search Parking Slot");
searchSlot.setFont(new Font("monospaced", Font.PLAIN, 12));
searchSlot.setBackground(Color.WHITE);
ImageIcon icon13 = new ImageIcon(ClassLoader.getSystemResource("icon/search.png")); // Add the search parking slot icon
Image image13 = icon13.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
searchSlot.setIcon(new ImageIcon(image13));
searchSlot.setMnemonic('S');
 searchSlot.addActionListener(this);
 searchSlot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
user.add(searchSlot);
 
JMenuItem viewSlot = new JMenuItem("View Parking Slot");
viewSlot.setFont(new Font("monospaced", Font.PLAIN, 12));
viewSlot.setBackground(Color.WHITE);
ImageIcon icon14 = new ImageIcon(ClassLoader.getSystemResource("icon/icon6.png")); // Add the view parking slot icon
Image image14 = icon14.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
viewSlot.setIcon(new ImageIcon(image14));
viewSlot.setMnemonic('V');
 viewSlot.addActionListener(this);
 viewSlot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
user.add(viewSlot);
 
JMenuItem booking = new JMenuItem("Booking");
booking.setFont(new Font("monospaced", Font.PLAIN, 12));
booking.setBackground(Color.WHITE);
ImageIcon icon90 = new ImageIcon(ClassLoader.getSystemResource("icon/booking.png"));
Image image90 = icon90.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
booking.setIcon(new ImageIcon(image90));
booking.setMnemonic('K');
 booking.addActionListener(this);
 booking.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
user.add(booking);
 
JMenuItem cancelBooking = new JMenuItem("Cancel Booking");
cancelBooking.setFont(new Font("monospaced", Font.PLAIN, 12));
cancelBooking.setBackground(Color.WHITE);
ImageIcon icon120 = new ImageIcon(ClassLoader.getSystemResource("icon/cance.png"));
Image image120 = icon120.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
cancelBooking.setIcon(new ImageIcon(image120));
cancelBooking.setMnemonic('C');
 cancelBooking.addActionListener(this);
 cancelBooking.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
user.add(cancelBooking);
 
JMenuItem rate = new JMenuItem("Review and Rate");
rate.setFont(new Font("monospaced", Font.PLAIN, 12));
rate.setBackground(Color.WHITE);
ImageIcon icon110 = new ImageIcon(ClassLoader.getSystemResource("icon/review.png"));
Image image110 = icon110.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
rate.setIcon(new ImageIcon(image110));
rate.setMnemonic('T');
 rate.addActionListener(this);
 rate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
user.add(rate);
 
JMenuItem reportParking = new JMenuItem("Report Parking");
reportParking.setFont(new Font("monospaced", Font.PLAIN, 12));
reportParking.setBackground(Color.WHITE);
ImageIcon icon100 = new ImageIcon(ClassLoader.getSystemResource("icon/report.png"));
Image image100 = icon100.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
reportParking.setIcon(new ImageIcon(image100));
reportParking.setMnemonic('P');
 reportParking.addActionListener(this);
 reportParking.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
user.add(reportParking);



        JMenu info = new JMenu("Information");
        info.setForeground(Color.RED);
        
        
        JMenuItem updateinformation = new JMenuItem("Update Password");
        updateinformation.setFont(new Font("monospaced", Font.PLAIN, 12));
        updateinformation.setBackground(Color.WHITE);
        ImageIcon icon5 = new ImageIcon(ClassLoader.getSystemResource("icon/icon4.png"));
        Image image5 = icon5.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        updateinformation.setIcon(new ImageIcon(image5));
        updateinformation.setMnemonic('U');
        updateinformation.addActionListener(this);
        updateinformation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        info.add(updateinformation);
        
        JMenuItem viewinformation = new JMenuItem("View Information");
        viewinformation.setFont(new Font("monospaced", Font.PLAIN, 12));
        viewinformation.setBackground(Color.WHITE);
        ImageIcon icon6 = new ImageIcon(ClassLoader.getSystemResource("icon/icon6.png"));
        Image image6 = icon6.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        viewinformation.setIcon(new ImageIcon(image6));
        viewinformation.setMnemonic('L');
        viewinformation.addActionListener(this);
        viewinformation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        info.add(viewinformation);
        
        JMenu report = new JMenu("Bill");
        report.setForeground(Color.RED);
         
JMenuItem billdetails = new JMenuItem("Bill Details");
billdetails.setFont(new Font("monospaced", Font.PLAIN, 12));
billdetails.setBackground(Color.WHITE);
ImageIcon icon8 = new ImageIcon(ClassLoader.getSystemResource("icon/icon6.png"));
Image image8 = icon8.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
billdetails.setIcon(new ImageIcon(image8));
billdetails.setMnemonic('B');
 billdetails.addActionListener(this);
 billdetails.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
report.add(billdetails);

        
        JMenuItem generatebill = new JMenuItem("Generate Bill");
        generatebill.setFont(new Font("monospaced", Font.PLAIN, 12));
        generatebill.setBackground(Color.WHITE);
        ImageIcon icon9 = new ImageIcon(ClassLoader.getSystemResource("icon/icon7.png"));
        Image image9 = icon9.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        generatebill.setIcon(new ImageIcon(image9));
        generatebill.setMnemonic('G');
        generatebill.addActionListener(this);
        generatebill.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        report.add(generatebill);
         
JMenuItem paybill = new JMenuItem("Pay Bill");
paybill.setFont(new Font("monospaced", Font.PLAIN, 12));
paybill.setBackground(Color.WHITE);
ImageIcon icon7 = new ImageIcon(ClassLoader.getSystemResource("icon/icon4.png"));
Image image7 = icon7.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
paybill.setIcon(new ImageIcon(image7));
paybill.setMnemonic('R');
 paybill.addActionListener(this);
 paybill.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
report.add(paybill);
         
JMenu utility = new JMenu("Utility");
utility.setForeground(Color.BLUE);
 
JMenuItem viewInformation = new JMenuItem("View Booking History");
viewInformation.setFont(new Font("monospaced", Font.PLAIN, 12));
viewInformation.setBackground(Color.WHITE);
ImageIcon icon11 = new ImageIcon(ClassLoader.getSystemResource("icon/icon12.png"));
Image image11 = icon11.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
viewInformation.setIcon(new ImageIcon(image11));
viewInformation.setMnemonic('V');  // Shortcut for "View Information"
 viewInformation.addActionListener(this);
 viewInformation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
utility.add(viewInformation);

        
        JMenu mexit = new JMenu("Exit");
        mexit.setForeground(Color.RED);
        
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.setFont(new Font("monospaced", Font.PLAIN, 12));
        exit.setBackground(Color.WHITE);
        ImageIcon icon12 = new ImageIcon(ClassLoader.getSystemResource("icon/icon11.png"));
        Image image12 = icon12.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        exit.setIcon(new ImageIcon(image12));
        exit.setMnemonic('W');
        exit.addActionListener(this);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        mexit.add(exit);
        
        if (atype.equals("owner")) {
            mb.add(master);
            mb.add(noti);
            mb.add(manage);
            mb.add(recept);
        } else {
            mb.add(user);
            mb.add(utility);
            
            mb.add(report);
        }
            
            
            
            
     
        
       mb.add(info);
        mb.add(mexit);
        setLayout(new FlowLayout());
        setVisible(true);
        
        
        
    }
    public String getParkingLotName(String username) {
    String parkingLotName = null;

    if ("owner".equals(atype)) {
        try {
            conn c = new conn();
            String query = "SELECT parking_lot_name FROM parkinglot WHERE owner_username = '" + username + "'";
            ResultSet rs = c.s.executeQuery(query);
            
            if (rs.next()) {
                parkingLotName = rs.getString("parking_lot_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    return parkingLotName;
}

    
     public void actionPerformed(ActionEvent ae) {
        String msg = ae.getActionCommand();
        

        if (msg.equals("Exit")) {
            setVisible(false);
            new login();
        }
        else if (msg.equals("View Booking History")){
            new viewbookinghistory(username);
        }
        else if (msg.equals("Pay Bill")){
            new paybill(username);
        }
        else if (msg.equals("Generate Bill")){
            new generatebill(username);
        }
        else if (msg.equals("Bill Details")){
            new billdetails(username);
        }
        else if (msg.equals("View Information")){
            new viewdetails(atype);
        }
        else if (msg.equals("Update Password")){
            new updatepassword(atype);
        }
        else if (msg.equals("Report Parking")){
            new reportuser(username);
        }
        else if (msg.equals("Review and Rate")){
            new reviewrating(username);
        }
        else if (msg.equals("Cancel Booking")){
            new cancelbooking(username);
        }
        else if (msg.equals("Booking")){
            new booking(username);
        }
        else if (msg.equals("View Parking Slot")){
            new viewparkinglot();
        }
        else if (msg.equals("Search Parking Slot")){
            new searchparkinglot();
        }
        else if (msg.equals("Send Notifications (WhatsApp/Email)")){
            new sendnotification(getParkingLotName(username));
        }
        else if (msg.equals("Generate Receipts")){
            new generatereceit(username);
        }
        else if (msg.equals("Update Slot Assignments")){
            new updateslotassignments(username);
        }
        else if (msg.equals("View Expected Arrivals")){
            new viewexpectedarrival(getParkingLotName(username));
        }
        else if (msg.equals("Report Overstaying Vehicles")){
            new reportoverstayingvehicles(getParkingLotName(username));
        }
        else if (msg.equals("Manage Parking Slots")){
            new viewparkedvehicles(getParkingLotName(username));
        }
        else if (msg.equals("View Parked Vehicles")){
            new viewparkedvehicles(getParkingLotName(username));
        }
        else if (msg.equals("Overview")){
            new overview();
        }
        else if (msg.equals("Register Parking Lot")){
            new registerslot();
        }
     }
    public static void main(String[] args){
        new project("","");
    }
}
    