package demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.entity.NewAccountingTable;
import demo.entity.DailyOrder2;
import demo.entity.NewDataFileHistory;
import demo.repository.NewAccountingTableRepository;
import demo.repository.DailyOrder2Repository;

@Controller
@RequestMapping("/accounting")
public class AccountingController {

	public String tableNo;
	public String orderList;
	@Autowired
	public ApplicationContext applicationContext;

	@Autowired
	public NewAccountingTableRepository newAccountingTableRepository;


	/*○
	 * EntryTable
	 */
	@RequestMapping(value ="/entryTable", method = RequestMethod.GET)
	public @ResponseBody Iterator<NewAccountingTable> entryTable(@RequestParam("useFlg")int useFlg){
		//1.ACCOUNTING_TABLE_TESTテーブルからuse_flgが０でenable_flgが１のレコードを取得しresultsに代入します。
		List<NewAccountingTable>results = newAccountingTableRepository.findByUseFlgAndEnableFlg(useFlg,1);
		
		//2.１のresultsをJSONに変換します。
		Iterator<NewAccountingTable> itr = results.iterator();
		
		//3. 2で変換したJSON形式の文字列を返します。
		return itr;
	}
	
	
	/*○
	 * GetTableList
	 */
	@RequestMapping(value ="/update", method = RequestMethod.GET)
	public @ResponseBody Iterator<NewAccountingTable> update( @RequestParam("useFlg")int useFlg) {
		//1.ACCOUNTING_TABLE_TESTテーブルからenable_flgが１、
		//　use_flgがnullの場合、use_flgが１、use_flgがnullでない場合,
		//   use_flgがuseFlgのレコードを取得し、resultsに代入します。
		List<NewAccountingTable> results =newAccountingTableRepository.findByUseFlgAndEnableFlg(useFlg,1);
		
		//2.resultsをJSONでエンコードした結果をjsonに代入します。
		Iterator<NewAccountingTable>json = results.iterator();
		
		//3. 2で変換したJSON形式の文字列を返します。
		return json;
	}
	
	/*○
	 * EntryTableChangeFlg
	 */
	@RequestMapping(value ="/entryTableChangeFlg", method = RequestMethod.GET)
	public @ResponseBody String entryTableChangeFlg(@RequestParam("useFlg") int useFlg ) {
		//1.ACCOUNTING_TABLE_TESTテーブルのuse_flgが、0の値のレコードのuse_flgに１をセットします。
		newAccountingTableRepository.updateByUseFlg(1,useFlg);
		return null;
	}
	
}
