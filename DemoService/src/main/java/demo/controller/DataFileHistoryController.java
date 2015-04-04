package demo.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import demo.repository.DailyOrder2Repository;
import demo.repository.NewAccountingTableRepository;
import demo.repository.NewDataFileHistoryRepository;

@Controller
@RequestMapping("/dataFileHistory")
public class DataFileHistoryController {

	public Timestamp updateTimestamp;
	public String fileNameMenuCtgry;
	public String fileNameTblList;
	public String fileNameMenu;
	@Autowired
	public ApplicationContext applicationContext;

	@Autowired
	public NewDataFileHistoryRepository newDataFileHistoryRepository;

	// request related

	@RequestMapping(value = "/loadDataList", method = RequestMethod.GET)
	@ResponseBody
	public  List<Map<String,String>> loadDataList(@RequestParam("fileNameMenuCtgry")String fileName){
		Pageable pageable = new PageRequest(0, 1);
		//1.Data_file_historyテーブルのupdate_timestampを降順で並び替え、その先頭のレコード1件を取得したものをresultに代入します。
		Page<NewDataFileHistory>result = newDataFileHistoryRepository.findByOrderByUpdateTimestampDesc( pageable);
		
		List<Map<String,String>>list = new ArrayList<Map<String,String>>();
		
		//2.historyに１で取得したresultに繰り返し取り出して、3-(a)から3-(b)の処理を繰り返し実行します。
		for (NewDataFileHistory history:result){
			Map<String,String>map = new HashMap<String,String>();
		//3-(a).mapのkeyにDataFileHistoryのentityを格納します。
			map.put("fn_tbl_list", history.getFileNameTblList());
			map.put("fn_menu_ctgry", history.getFileNameMenuCtgry());
			map.put("fn_menu", history.getFileNameMenu());

			// HashMapをArrayListに格納する
			//4. 3で登録したmapをlistに格納します。
			list.add(map); 
		}
		//5. listをJSONでエンコードした結果をJSON形式の文字列で返します。
		 return list;
		}
//===================================================================================
	@RequestMapping(value = "/readFileMenu", method = RequestMethod.GET)
	public @ResponseBody List<Map<String,String>> readFileMenu(@RequestParam("fileNameMenu")String fileName){
		Pageable pageable = new PageRequest(0, 1);
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Page<NewDataFileHistory> result = newDataFileHistoryRepository.findByOrderByUpdateTimestampDesc2(pageable);
		//1./var/www/html/DataFiles/ディレクトリのfileNameファイルをSJIS形式で読み込みます。
		try{
			InputStreamReader fr = new InputStreamReader(new FileInputStream(
					"/var/www/html/DataFiles/" + fileName), "SJIS");
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			StringTokenizer token;
			//2. 1で読み込んだファイルを１行ずつ読み込みます。
			//3. 2で読み込んだファイルがnullでなければ、4-(a),4-(b)、4-(c)を繰り返し実行します。
			while((line = br.readLine()) != null) {
				
				//4-(a). ”,”で区切って、分割した文字を出力します。
				token = new StringTokenizer(line,",");
				
				String value = token.nextToken();
				String value2 = token.nextToken();
				String value3 = token.nextToken();
				String value4 = token.nextToken();
				System.out.println(value);
				System.out.println(value2);
				System.out.println(value3);
				System.out.println(value4);
				
				Map<String,String> map = new HashMap<String,String>();
				
		//4-(b).keyをmenu_idに、valueをvalue、
				map.put("menu_id",value);
				
		//　keyをmenu_category_idに、valueをvalue2、
				map.put("menu_category_id", value2);
				
		//　keyをmenu_nameに、valueをvalue3、
				map.put("menu_name",value3);
		//　keyをmenu_priceに、valueをvalue4としてmapに登録します。
				map.put("menu_price",value4);
		//4-(c). 4-(b)で登録したmapをlistに格納します。
				list.add(map);
			}
       	//5. listをJSONでエンコードした結果を、JSON形式で返します。
			return list;
		//6. listの要素をすべて取り出し、例外オブジェクトをキャッチし、エラー内容を表示します。
		}catch(FileNotFoundException e){
			System.err.println("ファイルが見つかりません");
		}catch(IOException e){
			System.err.println("読み込めませんでした");
		}
		return null;
	}


	public static void main(String[] args) {
		try {
			InputStreamReader fr = new InputStreamReader(new FileInputStream(
					"C:\\ShuwaPM_Tools32\\DataFiles\\menu_ver20130922.csv"), "SJIS"); // ファイルの読み込み
			BufferedReader br = new BufferedReader(fr); // ファイルを一行ずつ読み込む
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();

			// 読み込んだファイルを１行ずつ処理する
			String line;
			StringTokenizer token;
			//9. 8で読み込んだファイルから１行ずつ読み込みます。
			//10. 9で読み込んだファイルがnullでなければ、11-(a),11-(b),11-(c)を繰り返し実行します。
			while ((line = br.readLine()) != null) {
				
			//読み込んだ文字を”,”で区切る
				token = new StringTokenizer(line, ",");
				//11-(a).  ”,”で区切って、分割した文字を出力します。	
				String value = token.nextToken();
				String value2 = token.nextToken();
				String value3 = token.nextToken();
				String value4 = token.nextToken();
				System.out.println(value);
				System.out.println(value2);
				System.out.println(value3);
				System.out.println(value4);
				
				//11-(b).keyをmenu_idに、valueをvalue、
				//　      keyをmenu_category_idに、valueをvalue2、
				//      　keyをmenu_nameに、valueをvalue3、
				//　      keyをmenu_priceに、valueをvalue4としてmapに登録します。
				Map<String, String> map = new HashMap<String, String>();
				map.put("menu_id", value);
				map.put("menu_category_id", value2);
				map.put("menu_name", value3);
				map.put("menu_price", value4);
				
				// 11-(b)で登録したmapをlistに格納します。		
				list.add(map); // HashMapをArrayListに格納する
			}
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.size());
			}
		} catch (FileNotFoundException e) {
			System.err.println("ファイルが見つかりません");
		} catch (IOException e) {
			System.err.println("読み込めませんでした");
		}
		// TODO JSONで返却
	}

				
		


	//==============================================================
	
	public @ResponseBody String readFileMenuCtgry(Pageable pageable){
		//1./var/www/html/DataFiles/ディレクトリのfileNameをSJIS形式で読み込みます。
		//2.１で読み込んだファイルから１行ずつデータを読み込みます。
		//3. ２で読み込んだファイルがnullでなければ、4-(a),4-(b)、4-(c)を繰り返し実行します。
		//4-(a).   ”,”で区切って、分割した文字を出力します。
		//4-(b). keyをcategory_idに、valueをvalue、
		//　　　keyをcategory_nameに、valueをvalue2としてmapに登録します。
		//4-(c). 4-(b)で登録したmapをlistに格納します。
		//listをJSONでエンコードした結果を、JSON形式で返します。
		//5. listの要素をすべて取り出し、例外オブジェクトをキャッチし、エラー内容を表示します。
		newDataFileHistoryRepository.findByOrderByUpdateTimestampDesc3(pageable);
		return String.valueOf(fileNameMenuCtgry);
	}
	
	public @ResponseBody String readFileTblNo(){
		//1.data_file_historyテーブルからfile_name_tblListがfileNameのレコードを取得し、historyに代入します。
		//2.１で取得したhistory.fileNameTblListとfileNameを比較します。
		//3./var/www/html/DataFiles/ディレクトリのfileNameファイルを読み込みます。
		//4. 3で読み込んだファイルから１行ずつデータを読み込みます。
		//5. 4で読み込んだファイルがnullでなければ,4-(a),4-(b),4-(c)を繰り返し実行します。
		//4-(a).   ”,”で区切って、分割した文字を出力します。
		//4-(b). keyをidに、valueをvalue、
		//　　　keyをtable_noに、valueをvalue2としてmapに登録します。
		//4-(c).4-(b)で登録したmapをlistに格納します。
		// 9. listをJSONでエンコードした結果を、JSON形式のもj列で返します。
		//10. listの要素をすべて取り出し、例外オブジェクトをキャッチし、エラー内容を表示します。
		newDataFileHistoryRepository.findByFileNameTblList(fileNameTblList);
		return String.valueOf(fileNameTblList);
	}
}
