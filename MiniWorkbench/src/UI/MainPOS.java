package UI;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainPOS extends JFrame {

	public POS_pos pos = null; 
	public POS_StockManagrment sm = null; 
	
	public static void main(String[] args) {
	MainPOS main = new MainPOS(); 
	main.setTitle("POS 시스템"); 
	
	main.pos = new POS_pos();
	main.sm = new POS_StockManagrment(); 
	
	JTabbedPane jtp = new JTabbedPane(); 
	jtp.add("POS", main.pos);
	jtp.add("재고관리", main.sm); 
	main.add(jtp); 
	
	main.setSize(550, 400); 
	main.setVisible(true); 
	
	
	}	
}
