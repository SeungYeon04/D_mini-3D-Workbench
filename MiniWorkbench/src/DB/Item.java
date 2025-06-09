package DB;
public class Item { //데이터클래스, Spring가면 테이블클래스 엔티티클래스라 함. 
	// DB의 테이블데이터 받을 공간들 
	private int id; 
	private String item_name;
	private int item_stock; 
	private int item_price;
	
	//getter setter 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public int getItem_stock() {
		return item_stock;
	}
	public void setItem_stock(int item_stock) {
		this.item_stock = item_stock;
	}
	public int getItem_price() {
		return item_price;
	}
	public void setItem_price(int item_price) {
		this.item_price = item_price;
	} 
}
