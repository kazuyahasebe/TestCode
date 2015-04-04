package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import demo.entity.DailyOrder2;

@Repository
public interface DailyOrder2Repository extends
		CrudRepository<DailyOrder2, Integer> {
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update  DailyOrder2 do set do.payment = ?1 where do.tableNo = ?2 and do.payment = ?3")
	public int updateByTableNo(int paymentNew , String tableNo,int payment);
	
	/*
	 * TestController
	 */
	@Transactional
	@Query("select do.visitorCode,sum(do.amount) as amount ,sum(do.totalPrice) as totalPrice from " +
	             "DailyOrder2 do where do.tableNo = ?1 and do.payment = ?2 " +
			     "group by do.visitorCode")	
	public List<DailyOrder2>  groupBytableNoAndPaymentGroupByVisitorCode(String tableNo,int payment);
	
	/*
	 * EntryOrder
	 */
	@Transactional
	@Query("select count( do )  from DailyOrder2 do where do.tableNo = ?1 and do.payment = ?2")
	public int countByTableNoAndPayment( String tableNo,int payment);
	
	/*
	 * EntryOrder
	 */
	@Transactional
	@Query("select do  from DailyOrder2 do where do.tableNo = ?1 and do.payment = ?2")
	public List<DailyOrder2> findByTableNoAndPayment3( String tableNo,int payment);
	
	/*
	 * TotalPrice
	 */
	@Transactional
	@Query("select do from DailyOrder2 do where do.tableNo = ?1 and do.payment = ?2")
	public List<DailyOrder2>  findByTableNoAndPayment2(String tableNo,int payment);

	
}

//String sqlStr = "select visitor_code,sum(amount) as totalAmnt,sum(total_price) as totalPrice from shuwapm.DAILY_ORDER where table_no = ? and payment = ? group by visitor_code";
//String sqlStr = "select distinct visitor_code from DAILY_ORDER " where table_no = ? " + "and payment = ?";
//String sqlStr = "select sum(total_price) as totalPrice, sum(amount) as totalAmnt from DAILY_ORDER where table_no = ? and payment = ?";
//String sqlStr = "select sum(total_price) as totalPrice, sum(amount) as totalAmnt from DAILY_ORDER where table_no = ? and payment = ?";
