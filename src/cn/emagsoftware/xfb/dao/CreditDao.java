package cn.emagsoftware.xfb.dao;

import java.util.List;
import java.util.Map;

import cn.emagsoftware.xfb.pojo.CollectInfo;
import cn.emagsoftware.xfb.pojo.CreditInfo;
import cn.emagsoftware.xfb.pojo.LoanInfo;

public interface CreditDao {

	//查询个人征信
	void addCreditInfo(CreditInfo creditInfo) throws Exception; 
	
	//保存查询结果集
	void saveLoanInfo(LoanInfo loanInfo) throws Exception;

	//查询用户id
	String getUserIdByTrxNo(String trxNo) throws Exception; 
	//查询用户id
	String getCardNumberByTrxNo(String trxNo) throws Exception; 
	
	//修改黑名单表标识位trxNo
	int updateTrxNo(Map<String, String> map) throws Exception;
	
	//收集本地数据库返回给91平台
	List<CollectInfo> queryLoanInfos(Map<String, String> map) throws Exception;
}
