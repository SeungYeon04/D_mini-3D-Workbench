package DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemDAO {
	/** 싱글톤 코드 */
	
	private ItemDAO() {} //싱글톤 어디서든 만들어서 동시에 하는 걸 방지 publicX privateO
	
	private static ItemDAO instance = new ItemDAO(); //객체생성에서 private static로 빌려쓰게 해줌 
	
	public static ItemDAO getinstance() {
		return instance;
	}
	
	//버튼 누르면 getAllItems() 얘 호출  ArrayList<Item> 
	// 1. 전체 상품 조회 및 리스트 저장 (버튼하고 연결될 스크립트) 
	public ArrayList<Item> getAllItems() throws Exception  {
		ArrayList<Item> list = new ArrayList<Item>();
		
		Connection conn = null; 
		Statement stmt = null; 
		ResultSet rs = null; 
		String sql = "select * from item"; 
		
		conn = DBconnect.connect(); //다른코드에서 가져오기 권한 
		stmt = conn.createStatement(); 
		rs = stmt.executeQuery(sql); //셀렉트문 던지고 rs에 넣기 
		
		Item item = new Item(); //null값이면 객체생성된 게 아님 빈객체.  
		
		while (rs.next()) {
			int id = rs.getInt("id");
			String item_name = rs.getString("item_name"); 
			int item_stock = rs.getInt("item_stock"); 
			int item_price = rs.getInt("item_price"); 
			
			//get 값가져오기 set 값세팅하는거 
			item.setId(id);
			item.setItem_name(item_name);
			item.setItem_stock(item_stock); 
			item.setItem_price(item_price); 
			list.add(item); //item 빈데다가 저장하기 
			item = new Item(); //다시 아이템 비워두기. 다른 새 아이템 와도 
			
		}
		
		rs.close(); 
		conn.close();
		stmt.close(); 
		
		return list;
	}
	
	// 2. 전체 상품명 조회 및 리스트 저장 (이름만) 
	public ArrayList<String> getAllItemNames() throws Exception { //이름만 갖고오니 리스트<스트링>
		ArrayList<String> list = new ArrayList<String>(); 
		ArrayList<Item> ilist = new ArrayList<Item>(); 
		
		ilist = getAllItems(); //예외처리는 메소드로 throws Exception 
		
		for(Item item : ilist) { //ilist에서 item객체 받아오라?
			list.add(item.getItem_name()); 
		}
		
		return list;
	}
	
	/** 깜짝문제 1. 재고량전체출력 getAllStocks() 2. 제품단가전체출력 getAllPrice() */
	public ArrayList<Integer> getAllStocks() throws Exception {
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Item> ilist = new ArrayList<Item>();
		
		ilist = getAllItems(); 
		
		for(Item item : ilist) {
			list.add(item.getItem_stock()); 
		}
		
		return list; 
	}
	
	public ArrayList<Integer> getAllPrice() throws Exception {
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Item> ilist = new ArrayList<Item>();
		
		ilist = getAllItems(); 
		
		for(Item item : ilist) {
			list.add(item.getItem_price()); 
		}
		
		return list;
	}
	
	//3. 사용자가 선택한 상품의 단가만 조회 
	//쿼리만 날리면 될 것을 싶어도, 언어와 연동을 위해 필요하다하심 (item_price + "원")
	public String getPrice(String item_name) throws SQLException { //특정상품의 단가라서 item_name 반환 
		Connection conn = null;
		PreparedStatement pstmt = null; //Statement는 완성된 쿼리만 실행될 수 있음. 매개변수로 미완성 완성시키는 거면 PreparedStatement 선언 
		ResultSet rs = null;
		int item_price = 0;
		
		String sql = "select item_price from item where item_name=?"; //=? 변수 받아오는 부분 item_name 받아오는 미완성쿼리 item_name=item_name
		
		//JPA 라고 직접 라이브러리로 가져와서 쓸 건데 아직 지방은 직접 만듦. JPA는 다음 학기에 언급할 거임. 
		conn = DBconnect.connect();
		pstmt = conn.prepareStatement(sql); //쿼리실행권한을 가진 앤 얘다. 미완성된 거 담아두고 아래서 완성시켜놓고 보내자 
		pstmt.setString(1, item_name); //1번째 ?에 값 넣어준다 item_name반환 
		rs = pstmt.executeQuery(); //위에서 다 지정해서 실행만 해도 잘 됨. 
		
		while(rs.next()) {
			item_price = rs.getInt("item_price"); 
		}
		
		DBconnect.close();
		return item_price + "원"; 
	}
	
	//4. 사용자가 선택한 상품의 재고량만 조회 
	public String getStock(String item_name) throws SQLException { 
		Connection conn = null;
		PreparedStatement pstmt = null; //Statement는 완성된 쿼리만 실행될 수 있음. 매개변수로 미완성 완성시키는 거면 PreparedStatement 선언 
		ResultSet rs = null;
		int item_stock = 0;
		
		String sql = "select item_stock from item where item_name=?"; //=? 변수 받아오는 부분 item_name 받아오는 미완성쿼리 item_name=item_name
		
		//JPA 라고 직접 라이브러리로 가져와서 쓸 건데 아직 지방은 직접 만듦. JPA는 다음 학기에 언급할 거임. 
		conn = DBconnect.connect();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, item_name); 
		rs = pstmt.executeQuery(); //위에서 다 지정해서 실행만 해도 잘 됨. 
		
		while(rs.next()) {
			item_stock = rs.getInt("item_stock"); 
		}
		
		DBconnect.close();
		return item_stock + "개"; //여기서 원, 개 처리하면 나중에 숫자로 업데이트 어려워서 없애버림 
	}
	
	
	//5. 사용자가 선택한 상품의 구매수량을 반영하여 재고량 업데이트 
	public void updateStock(String total, String count, String item_name) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null; //Statement는 완성된 쿼리만 실행될 수 있음. 매개변수로 미완성 완성시키는 거면 PreparedStatement 선언 
		ResultSet rs = null;
		
		String sql = "update item set item_stock=?-? where item_name=?"; //total - count and인가 item_name=item_name

		conn = DBconnect.connect();
		pstmt = conn.prepareStatement(sql); 
		
		//?에 값을 채워준다. 
		pstmt.setString(1, total); 
		pstmt.setString(2, count); 
		pstmt.setString(3, item_name); 
		int r = pstmt.executeUpdate(); //완성된 쿼리 실행 얘는 select아니니 업데이트 

		if(r>0) {
			System.out.println("재고량 업데이트 성공 (0!=)");
		}
		
		DBconnect.close();
	}
	
	//6. 관리자모드 상품추가 
	public boolean insertItem(Item item) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pstmt = null; //Statement는 완성된 쿼리만 실행될 수 있음. 매개변수로 미완성 완성시키는 거면 PreparedStatement 선언 
		ResultSet rs = null;
		
		String sql = "insert into item(item_name, item_stock, item_price) values(?,?,?)";
		
		conn = DBconnect.connect();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, item.getItem_name()); 
		pstmt.setInt(2, item.getItem_stock()); 
		pstmt.setInt(3, item.getItem_price()); 
		int r = pstmt.executeUpdate();
		
		if(r > 0) {
			System.out.println("상품등록성공");
		}
		
		DBconnect.close();
		return true; 
	}
	
	//7. 관리자모드 상품정보수정 
	public boolean updateItem(Item item) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null; //Statement는 완성된 쿼리만 실행될 수 있음. 매개변수로 미완성 완성시키는 거면 PreparedStatement 선언 
		ResultSet rs = null;
		
		//값을 바꿔라 where id가 이거인 것에 대해서 
		String sql = "update item set item_name=?, item_stock=?, item_price=? where id=?";

		conn = DBconnect.connect();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, item.getItem_name()); 
		pstmt.setInt(2, item.getItem_stock()); 
		pstmt.setInt(3, item.getItem_price()); 
		pstmt.setInt(4, item.getId());
		int r = pstmt.executeUpdate();

		if(r > 0) {
			System.out.println("상품수정성공");
		}
		
		DBconnect.close();
		
		return true; 
	}
	
	//수정?
	public boolean deleteItem(int id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null; //Statement는 완성된 쿼리만 실행될 수 있음. 매개변수로 미완성 완성시키는 거면 PreparedStatement 선언 
		ResultSet rs = null;
		
		//값을 바꿔라 where id가 이거인 것에 대해서 
		String sql = "delete from item where id=?";

		conn = DBconnect.connect();
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id); 
		int r = pstmt.executeUpdate();
		
		if(r > 0) {
			System.out.println("상품삭제성공");
		}
		
		DBconnect.close();
		
		return true; 
	}
	
	
	
	
}
	
