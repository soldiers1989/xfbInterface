package cn.tyiti.xfb.service.blacklist.impl;   

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emagsoftware.utils.SeqUtil;
import cn.emagsoftware.xfb.dao.CreditDao;
import cn.tyiti.xfb.bojo.blacklist.BlackListInfo;
import cn.tyiti.xfb.bojo.blacklist.BlistInfo;
import cn.tyiti.xfb.constant.GlobalConstant;
import cn.tyiti.xfb.dao.blacklist.IblackListInfoManageDao;
import cn.tyiti.xfb.dao.blacklist.impl.BlistInfoManageDao;
import cn.tyiti.xfb.service.blacklist.IblackListInfoService;
import cn.tyiti.xfb.utils.DocumentUtil;

/** 
 * 创建时间：2015-11-24 下午9:53:31  
 * 项目名称：xfbManage  
 * @author liminghua  
 * @version 1.0   
 * @since JDK 1.6
 * 文件名称：BlackListInfoService.java  
 * 类说明：  
 */
@Service("blackListInfoService")  
public class BlackListInfoService implements IblackListInfoService {
	
	@Autowired
	private IblackListInfoManageDao blackListInfoManageDao ;
	
	@Autowired
	private BlistInfoManageDao blistInfoManageDao ;
	
    @Autowired
    private SeqUtil seqUtil;
    
	@Autowired
	private CreditDao creditDao ;
    
	/**
	 * 查询成功状态返回码
	 * */
	public final static String QUERY_SUCCESS = "0";
	
	/**
	 *<p>Title:updateBlackListInfo</p>
	 *<p>Description:添加黑名单数据到数据库</p>
	 *@param params resultDoc document文档，  serviceCode接口名称， name姓名， idcode 身份证号， userId， loginName 手机号
	 *@throws Exception
	 *@see com.tyiti.xfb.service.blacklist.IblackListInfoService#updateBlackListInfo(java.util.Map)
	 */
	@Override
	public void insertBlackListInfo(Map<String, Object> params) throws Exception {
		
		Document resultDoc =  (Document)params.get("resultDoc");
		String serviceCode = (String) params.get("serviceCode");
		String name = (String) params.get("name");//姓名
		String idcode = (String) params.get("idcode");//身份证号
		String userId = (String) params.get("userId");//userid
		String loginName = (String) params.get("loginName");//手机号
		String msgId = (seqUtil.getNextValueBySeqName(GlobalConstant.GET_T_BLIST_MESSAGE_SEQ).toString());
		
		BlistInfo blistInfo = new BlistInfo();
		String status = DocumentUtil.getStrByNodePath(resultDoc,"/DATA/MESSAGE/STATUS");//返回查询的 状态 ，为成功 或者 失败
		String value = DocumentUtil.getStrByNodePath(resultDoc,"/DATA/MESSAGE/VALUE");//查询返回 的 查询结果描述字段
		
		blistInfo.setId(msgId);
		blistInfo.setName(name);
		blistInfo.setUserId(userId);
		blistInfo.setLoginName(loginName);
		blistInfo.setIdcode(idcode);
		blistInfo.setStatus(status);
		blistInfo.setQueryInterface(serviceCode);
		//blistInfo.setResultcode(resultcode);
		//blistInfo.setResultdesc(resultdesc);
		blistInfo.setValue(value);
		blistInfoManageDao.insert(blistInfo);
		
		String resultsENText = "";

		if (QUERY_SUCCESS.equals(status) && !"记录为空".equals(value)) {
			resultsENText = DocumentUtil.getDecryptStrByNodePath(resultDoc, "/DATA/RESULTS");

			if (null != resultsENText && !"".equals(resultsENText)) {
				Document resultDoc2 = DocumentHelper.parseText("<RESULTS>" + resultsENText + "</RESULTS>");
				List nodes = resultDoc2.selectNodes("/RESULTS/RESULT");
				//如果节点有内容,列表>0，则更改黑名单主表的关联字段msgId
				if(nodes.size()>0){
					Map  map = new HashMap();
					map.put("modifyField", "blacklist");
					 //map.put("userId", userId);
					 map.put("cardNumber", idcode);
					 map.put("creditCode", msgId);
					creditDao.updateTrxNo(map);
				}
				
				for (Iterator it = nodes.iterator(); it.hasNext();) {
					Element elm = (Element) it.next();
					// System.out.println("id:"+elm.attributeValue("id"));
					// System.out.println(elm.getName());
					BlackListInfo blackListInfo = new BlackListInfo();

					blackListInfo.setMsgId(msgId);
					blackListInfo.setName(name);
					blackListInfo.setIdcode(idcode);
					blackListInfo.setUserId(userId);
					blackListInfo.setLoginName(idcode);
					blackListInfo.setLoginName(loginName);

					Element idtype = elm.element("IDTYPE");
					blackListInfo.setIdtype(idtype.getText());

					Element grade = elm.element("GRADE");
					blackListInfo.setGrade(grade.getText());
					Element sourceid = elm.element("SOURCEID");
					blackListInfo.setSourceid(sourceid.getText());
					Element databuildtime = elm.element("DATABUILDTIME");
					blackListInfo.setDatabuildtime(databuildtime.getText());
					Element moneybound = elm.element("MONEYBOUND");
					blackListInfo.setMoneybound(moneybound.getText());
					Element datastatus = elm.element("DATASTATUS");
					blackListInfo.setDatastatus(datastatus.getText());
					//插入子表数据
					blackListInfoManageDao.insert(blackListInfo);
				}
			}
		}
	}
}
 