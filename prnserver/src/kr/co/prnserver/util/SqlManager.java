package kr.co.prnserver.util;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public abstract class SqlManager {
	
	 private SqlMapClient sc = null;
	 
	 public SqlManager(){
		  try{
			   sc = SqlMapClientBuilder.buildSqlMapClient(
			            Resources.getResourceAsReader(
			              "/config/prn-oracle.xml"));         //sql설정이 들어가 있는 SqlMapConfig파일 위치 지정
			                                                  //classes 폴더에 있으면 SqlMapClient.xml로 바로 지정 해도 됨
		  }catch(java.io.IOException ie){
		   ie.printStackTrace();
		  }
	 }


	public SqlMapClient getSqlMap(){
	  return sc; 
	}
}