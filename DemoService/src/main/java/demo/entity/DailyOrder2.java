package demo.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;


/**
 * The persistent class for the DailyOrder2 database table.
 * 
 */
@Entity
public class DailyOrder2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="order_datetime")
	public Date orderDatetime;

	@Column(name="visitor_code")
	private String visitorCode;

	@Column(name="table_no")
	private String tableNo;
	
	@Column(name="menu_category_id")
	private int menuCategoryId;
	
	@Column(name="menu_id")
	private int menuId;
	
	@Column(name="menu_name")
	private String menuName;
	
	@Column(name="menu_price")
	private int menuPrice;
	
	@Column(name="amount")
	private int amount;
	
	@Column(name="total_price")
	private int totalPrice;
	
	@Column(name="payment")
	private int payment;
	
	public DailyOrder2() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVisitorCode() {
		return visitorCode;
	}

	public void setVisitorCode(String visitorCode) {
		this.visitorCode = visitorCode;
	}

	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	public int getMenuCategoryId() {
		return menuCategoryId;
	}

	public void setMenuCategoryId(int menuCategoryId) {
		this.menuCategoryId = menuCategoryId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(int menuPrice) {
		this.menuPrice = menuPrice;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}



}