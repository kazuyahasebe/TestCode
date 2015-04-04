package demo.repository;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import demo.entity.NewDataFileHistory;

@Repository
public interface NewDataFileHistoryRepository extends
		CrudRepository<NewDataFileHistory, Integer> {
	
	/*
	 * LoadDataList
	 */
	@Transactional
	@Query("SELECT nd FROM NewDataFileHistory nd ORDER BY nd.updateTimestamp DESC ")
	public Page<NewDataFileHistory> findByOrderByUpdateTimestampDesc(Pageable pageable);

	/*
	 * ReadFileMenu
	 */
	@Transactional
	@Query("SELECT nd.fileNameMenu FROM NewDataFileHistory nd ORDER BY updateTimestamp DESC ")
	public Page<NewDataFileHistory>findByOrderByUpdateTimestampDesc2(Pageable pageable);
	
	/*
	 * ReadFileMenuCtgry
	 */
	@Transactional
	@Query("SELECT nd.fileNameMenuCtgry FROM NewDataFileHistory nd ORDER BY updateTimestamp DESC ")
	public Page<NewDataFileHistory>findByOrderByUpdateTimestampDesc3(Pageable pageable);
	
	/*
	 * select file_name_tbl_list from data_file_history where file_name_tbl_list=
	 */
	@Transactional
	@Query("SELECT nd.fileNameTblList FROM NewDataFileHistory nd WHERE nd.fileNameTblList=?1")
	public List<NewDataFileHistory> findByFileNameTblList(String fileNameTblList);
}
