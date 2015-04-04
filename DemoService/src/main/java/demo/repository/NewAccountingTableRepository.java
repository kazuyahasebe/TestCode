package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import demo.entity.NewAccountingTable;

@Repository
public interface NewAccountingTableRepository extends
		CrudRepository<NewAccountingTable, Integer> {
	
	/*getTableListAction
	 * EntryTableAction
	 */
	@Transactional
	@Query("select nat from NewAccountingTable nat where nat.useFlg = ?1 and nat.enableFlg = ?2")
	public List<NewAccountingTable> findByUseFlgAndEnableFlg(int useFlg , int enableFlg);
	
	/*
	 * EntryTableChangeFlgAction
	 */
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update NewAccountingTable nat  set nat.useFlg = ?1 where nat.useFlg = ?2 ")
	public  int updateByUseFlg(int newUseFlg,int useFlg);
	
	

}

//String str = "select * from shuwapm.ACCOUNTING_TABLE_TEST where use_flg = ? and enable_flg = ? ";
//<EntryTableChangeFlgAction>String sqlstr = "UPDATE shuwapm.ACCOUNTING_TABLE_TEST  SET use_flg = 1 WHERE use_flg = ?" ;
//<GetTableListAction>String str = "select * from shuwapm.ACCOUNTING_TABLE_TEST where use_flg = ? and enable_flg = ?";