package demo.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ACCOUNTING_TABLE_TEST database table.
 * 
 */
@Entity
public class Test implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String comment;

	@Column(name="enable_flg")
	private int enableFlg;

	@Column(name="table_no")
	private String tableNo;

	@Column(name="use_flg")
	private int useFlg;

	public Test() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getEnableFlg() {
		return this.enableFlg;
	}

	public void setEnableFlg(int enableFlg) {
		this.enableFlg = enableFlg;
	}

	public String getTableNo() {
		return this.tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	public int getUseFlg() {
		return this.useFlg;
	}

	public void setUseFlg(int useFlg) {
		this.useFlg = useFlg;
	}

}