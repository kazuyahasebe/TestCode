package demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.entity.NewAccountingTable;
import demo.entity.DailyOrder2;
import demo.entity.NewDataFileHistory;
import demo.repository.NewAccountingTableRepository;
import demo.repository.DailyOrder2Repository;

@Controller
@RequestMapping("/demo")
public class DemoController {

	public int payment;
	public int enableFlg;
	
	@Autowired
	public ApplicationContext applicationContext;

	@Autowired
	public  DailyOrder2Repository dailyOrder2Repository;

	// request related
	
	// request related

		@RequestMapping(value = "/update", method = RequestMethod.GET)
		public @ResponseBody String update(@RequestParam("tableNo") String tableNo) {
			//1.Daily_Orderテーブルからtable_noがtableNoで、paymentが０のレコードのpaymentに１をセットします。
			 dailyOrder2Repository.updateByTableNo(1, tableNo, 0);
			//2.ok.jspを返します。
			return "ok.jsp";
		}
		/*
		 * EntryOrder
		 */
		@RequestMapping(value="/entryOrder",method = RequestMethod.GET)
	public @ResponseBody String entryOrder(
			@RequestParam("OrderList") String OrderList,@RequestParam("tableNo") String tableNo) {
			
			//１．Daily_Orderテーブルの指定したTable_noとpaymentが０の件数を取得する。
			long count=dailyOrder2Repository.countByTableNoAndPayment(tableNo,0);
		
			//２．tableNoに現在日時を”-”で連結させたものをvisitorCodeとします。
			String date = getCurrentDateTime();
			String visitorCode = (tableNo+"-"+date);
			// 3.１で取得した件数が０ではない場合、Daily_Orderテーブルからtable_noがTableNoで、かつ、paymentが０の、
			      // リストを取得して、その先頭のレコードのvisitorCodeを取得し、visitorCodeに代入する
			if(count !=0){
				List<DailyOrder2>result = dailyOrder2Repository.findByTableNoAndPayment3(tableNo, 0);
				for(DailyOrder2 dailyOrder2:result){
				visitorCode = dailyOrder2.getVisitorCode();
				}
				
				//4.OrderListをJSONでデコードした結果をmapのListとしして取得します。
				ObjectMapper mapper = new ObjectMapper();
				 List<Map<String, Object>> jsonOrderList = null;
				try {
					jsonOrderList = mapper.readValue(OrderList,List.class);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//5.MAPにjsonOrderListの要素を繰り返し取り出して、5-(a)から5-(b)の処理を繰り返し実行します。
			for(Map<String, Object> map : jsonOrderList){
				DailyOrder2 dailyOrder = new DailyOrder2();
				//5-(a).mapから取り出してDailyOrderのentityに代入する。
				dailyOrder.setTableNo(tableNo);
				dailyOrder.orderDatetime =new java.sql.Date(System.currentTimeMillis());
				dailyOrder.setVisitorCode(visitorCode);
				dailyOrder.setMenuCategoryId(Integer.parseInt((String)map.get("menuCtgry")));
				dailyOrder.setMenuId(Integer.parseInt((String)map.get("menuId")));
				dailyOrder.setMenuName((String)map.get("menuName"));
				dailyOrder.setMenuPrice(Integer.parseInt((String)map.get("menuPrice")));
				dailyOrder.setAmount(((BigDecimal)map.get("amnt")).intValue());
				dailyOrder.setTotalPrice(((BigDecimal)map.get("totalPrice")).intValue());
				dailyOrder.setPayment(0);
				
				//5-(b).５-(a)で代入した値をorder_listテーブルに挿入する。
				dailyOrder2Repository.save(dailyOrder);
			}

			}
			
			//7.例外が発生した場合、処理をする。
			//8.ok.jspを返す。
			return "ok.jsp";
	}
	/*○
	 * TotalPrice
	 */
		@RequestMapping(value="/totalPrice",method=RequestMethod.GET)
	public @ResponseBody String totalPrice(@RequestParam("tableNo")String tableNo,
			@RequestParam("totalPrice")int totalPrice,@RequestParam("totalAmnt")int totalAmnt) {
		//1. Daily_Orderテーブルからtable_noがtableNoでpaymentが０のレコードの
			//total_priceの総合計をtotalPrice、amountの総合計をtotalAmntとして取得する。
		List<DailyOrder2> result =dailyOrder2Repository.findByTableNoAndPayment2(tableNo,0);
		for(DailyOrder2 dailyOrder2:result){
			
			//２．１で取得した先頭のレコードのtotalpriceをtotalPriceに代入する
			totalPrice += dailyOrder2.getTotalPrice();
			//３．１で取得した先頭のレコードのtotalAmntをtotalAmntに代入する
			totalAmnt += dailyOrder2.getAmount();
		}
		//４．price.jspを返す。
		
		//５．totalPriceに１０を代入する。
		//６．totalAmntに５を代入する。
		//７．price.jspを返す。
		return "price.jsp";
	}
	
	/*
	 * Test
	 */
		@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody String test(@RequestParam("tableNo") String tableNo) {
		//１.Daily_Orderテーブルからtable_noがtableNoでpaymentが１のレコードをvisitor_codeごとに、
		 //total_priceの総合計をtotalPrice、amountの総合計をtotalAmntとして取得します。
		dailyOrder2Repository.groupBytableNoAndPaymentGroupByVisitorCode(tableNo,1);
		
		//2.price.jspを返す。
		return "price.jsp";
		//3.totalPriceに１０を代入する。
		//６．totalAmntに５を代入する。
		//７．price.jspを返す。
		
	}
	
		private String getCurrentDateTime() {
			Calendar Cal = new GregorianCalendar(new Locale("ja", "JP"));
			Cal.setTime(new Date());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String Date = df.format(Cal.getTime());

			// TODO 自動生成されたメソッド・スタブ
			return Date;
		}
}
