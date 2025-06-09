package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DB.Item;
import DB.ItemDAO;

public class POS_StockManagrment extends JPanel implements ActionListener {

	JLabel jl; 
	JButton refresh; 
	JButton register; //등록 
	JButton update; 
	JButton delete; 
	
	//이 두 개를 연결해서 데이터를 뿌릴 것 
	JTable jt;
	DefaultTableModel model;
	
	//
	
	public POS_StockManagrment() {
		setLayout(null); 
		
		jl = new JLabel("재고현황"); 
		jl.setSize(100,40); 
		jl.setLocation(60, 20); //이정도 떨어진 위치~ 가로60 세로20 떨궈 붙이기 
		//jl.setBounds(60,20,100,40); //위랑 비교해서 이래도 됨 
		
		refresh = new JButton("상품새로고침"); 
		refresh.setBounds(10,70,150,40); //내용물 40 + 위치 20 더하기 | 70 + 60 = 130
		
		register = new JButton("등록"); 
		register.setBounds(10,130,150,40);
		
		update = new JButton("수정"); 
		update.setBounds(10,190,150,40);
		
		delete = new JButton("삭제"); 
		delete.setBounds(10,250,150,40);
		
		
		String title[] = {"ID", "상품명", "재고량", "단가"}; 
		model = new DefaultTableModel(title, 0); //JTable은 공간도 붙이는데 이거 쓴거
		jt = new JTable(model); 
		JScrollPane sp = new JScrollPane(jt); //화면에 뿌려줄 건 이거 
		
		sp.setBounds(200,20,300,280);
		
		refresh.addActionListener(this);
		register.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		
		add(jl);
		add(refresh);
		add(register); 
		add(update); 
		add(delete); 
		add(sp); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		DefaultTableModel model = (DefaultTableModel) jt.getModel(); //DefaultTableModel객체형으로 형변환 
		
		
		if(obj == refresh) {
			System.out.println("전체데이터 조회 버튼 눌림");
			
			try {
				loadDB(model);
			} catch (Exception e1) {
				System.out.println("db전체조회오류");
			}
		} else if (obj == register) {
			System.out.println("상품 등록 버튼 눌림");
		} else if (obj == update) {
			System.out.println("상품 수정 버튼 눌림");
		} else if (obj == delete) {
			System.out.println("상품 삭제 버튼 눌림");
		}
	}
	
	public void loadDB(DefaultTableModel model) throws Exception {
		//인스턴스 객체 리턴받기 - getAllItems() 실행 - return 
		//ArrayList -> Item 
		
		ArrayList<Item> list = ItemDAO.getinstance().getAllItems();
		
		for(Item item: list) {
			String id = String.valueOf(item.getId()); 
			String item_name = item.getItem_name();
			String item_stock = String.valueOf(item.getItem_stock());
			String item_price = String.valueOf(item.getItem_price()); 
			
			Vector<String> in = new Vector<String>(); //필드별로 한줄씩 읽어다가 백터에 넣고 모델의 한행으로 차지하게 넣기 
			in.add(id);
			in.add(item_name);
			in.add(item_stock);
			in.add(item_price);
			
			model.addRow(in);
		}
		
	}
}
