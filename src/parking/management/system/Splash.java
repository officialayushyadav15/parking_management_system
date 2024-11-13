
package parking.management.system;
import javax.swing.*;
import java.awt.*;

public abstract class Splash extends JFrame implements Runnable{
    Thread t;
    Splash(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/park1.png"));
        
       
        Image i2 = i1.getImage().getScaledInstance(1500,900,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image= new JLabel(i3);
         add(image);
        
         setVisible(true);
         for(int i=0 ;i<550; i+=2){
             setSize(i*2,i*3/2);
             setLocation(950-i,670-i);
             try{
                 Thread.sleep(1);
             }
             catch(Exception e){
                 e.printStackTrace();
             }
         }
         t=new Thread(this);
         t.start();
    
    setVisible(true);
    }
    public void run(){
        try{
                 Thread.sleep(1000);
                 setVisible(false);
                 new login();
             }
             catch(Exception e){
                 e.printStackTrace();
             }
    }
    public static void main(String[] args) {
        new Splash() {};
    }
    
}
