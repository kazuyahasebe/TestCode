package demo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the ACCOUNTING_TABLE_TEST database table.
 * 
 */
@Entity
public class NewDataFileHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="file_name_tbl_list")
	private String fileNameTblList;

	@Column(name="file_name_menu_ctgry")
	private String fileNameMenuCtgry;

	@Column(name="file_name_menu")
	private String fileNameMenu;
	
	@Column(name="update_timestamp")
	private Timestamp updateTimestamp;

	public NewDataFileHistory() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileNameTblList() {
		return fileNameTblList;
	}

	public void setFileNameTblList(String fileNameTblList) {
		this.fileNameTblList = fileNameTblList;
	}

	public String getFileNameMenuCtgry() {
		return fileNameMenuCtgry;
	}

	public void setFileNameMenuCtgry(String fileNameMenuCtgry) {
		this.fileNameMenuCtgry = fileNameMenuCtgry;
	}

	public String getFileNameMenu() {
		return fileNameMenu;
	}

	public void setFileNameMenu(String fileNameMenu) {
		this.fileNameMenu = fileNameMenu;
	}

	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Timestamp updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}


}