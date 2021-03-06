package cn.tyiti.xfb.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.emagsoftware.frame.bean.BaseRspBean;
import cn.emagsoftware.frame.controller.BaseController;
import cn.emagsoftware.utils.Constant;
import cn.emagsoftware.utils.json.JsonUtils;
import cn.emagsoftware.xfb.service.CreditService;
import cn.tyiti.xfb.bojo.ContactInfo;
import cn.tyiti.xfb.bojo.UserInfo;
import cn.tyiti.xfb.service.BlackListService;
import cn.tyiti.xfb.service.ContactInfoService;
import cn.tyiti.xfb.utils.UnicodeUtil;
import cn.tyiti.xfb.utils.blacklist.BlackListQuery;
import cn.tyiti.xfb.utils.blacklist.QlBlackInfoBlackListQuery;
import cn.tyiti.xfb.utils.blacklist.SyBadInfoBlackListQuery;

@Controller
@RequestMapping(value = "/contactInfo")
public class ContactInfoController  extends BaseController {
	@Autowired
	private ContactInfoService contactInfoService;
	@Autowired
	private BlackListService blackListService;
	
	
	@Autowired
	private SyBadInfoBlackListQuery syBadInfoBlackListQuery;
	@Autowired
	private QlBlackInfoBlackListQuery qlBlackInfoBlackListQuery;
	@Autowired
	private BlackListQuery blackListQuery;
	@Autowired
	private CreditService creditService;
	
	
	 	@RequestMapping(value = "/saveContactInfo", method = {RequestMethod.POST, RequestMethod.GET})
	    public String addOrUpdateContactInfo(Map<String, String> model) throws UnsupportedEncodingException {
		 	log.info("联系人操作开始");
		 	BaseRspBean response = new BaseRspBean();
	        List<ContactInfo> contactInfoList = this.getContactInfoListParam();
	        //如果为空不做处理。直接返回。
	        /*if (contactInfoList == null) {
	        	response.setCodeAndMsg(ContactInfoConstant.ERROR_CODE_2000, ContactInfoConstant.ERROR_MESSAGE.get(ContactInfoConstant.ERROR_CODE_2000));
	        	model.put(Constant.RETURN_MESSAGE, JsonUtils.getJSONString(response));
		        return RET_JSP;
	        }*/
	        try {	
	        	
	        	try{
		        	//查询用户是否被自动拒绝
		        	UserInfo userInfo=blackListService.getUserInfoByUserId(this.getUserId());
		        	if(!"6".equals(userInfo.getUserSatus())){
		        		//如果没有被拒绝,判断是否是补件(初次提交黑名单主表里没数据,需要查询,插入,补件时有数据)
		        		if(userInfo.getBlackCount()==null){//是初次提交0,需要先插入主表,再调接口查询插入子表
		        			log.info("调用黑名单开始："+this.getUserId());
		        			blackListService.addData(userInfo);//如果主表插入成功,查询插入子表
		        			blackListQuery.blackListQuery(userInfo.getRealName(), userInfo.getIdCode(), userInfo.getUserId(), userInfo.getLoginName());
	        				syBadInfoBlackListQuery.blackListQuery(userInfo.getRealName(), userInfo.getIdCode(), userInfo.getUserId(), userInfo.getLoginName());
	        				qlBlackInfoBlackListQuery.blackListQuery(userInfo.getRealName(), userInfo.getIdCode(), userInfo.getUserId(), userInfo.getLoginName());
	        				creditService.queryCreditInfo(userInfo.getUserId(),userInfo.getRealName(), userInfo.getIdCode());	
	        				log.info("调用黑名单结束："+this.getUserId());
		        		}
		        		//补件时,第二次查询
//		        		else if("0000".equals(userInfo.getBlackCount())){
//		        			System.out.println("---------------------------------------------33-------");
//		        			
//		        			blackListQuery.blackListQuery(userInfo.getRealName(), userInfo.getIdCode(), userInfo.getUserId(), userInfo.getLoginName());
//		        			syBadInfoBlackListQuery.blackListQuery(userInfo.getRealName(), userInfo.getIdCode(), userInfo.getUserId(), userInfo.getLoginName());
//		        			qlBlackInfoBlackListQuery.blackListQuery(userInfo.getRealName(), userInfo.getIdCode(), userInfo.getUserId(), userInfo.getLoginName());
//		        			creditService.queryCreditInfo(userInfo.getUserId(),userInfo.getRealName(), userInfo.getIdCode());	
//		        		}
		        	}
	        	} catch(Exception ex){
					ex.printStackTrace();
	        		log.error("saveContactInfo校验黑名单查询:"+ex);
	        	}
	        	
        	
        		//只判断第一个，有值则所有的id都有值，反之，都没有值。
        		//如果为true，则执行增加，反之执行更新操作
        		//if (CommonUtils.isEmptyByObj(contactInfoList.get(0).getId())) {
        			//log.info("执行增加逻辑并修改自身与基本认证上两步的状态为审核中");
        			contactInfoService.addContactInfo(contactInfoList);
        		//} else {
        			//contactInfoService.updateContactInfoById(contactInfoList);
        			
        		//}

    	        //成功设置
    			response.setCodeAndMsg(Constant.SUCCESS_CODE,Constant.ERROR_MESSAGE.get(Constant.SUCCESS_CODE));
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	            log.error("联系人操作失败", ex);
	            response.setResultCode(Constant.ERROR_CODE_9999);
	        }
	        /*log.info("联系人结束");
		    response.setResultMessage(ContactInfoConstant.ERROR_MESSAGE.get(response.getResultCode()));*/
		    model.put(Constant.RETURN_MESSAGE, JsonUtils.getJSONString(response));
		    log.debug("BillStageController.billList.response=="+JsonUtils.getJSONString(response));
	        return RET_JSP;
	    }
	 /**
	  * 获取联系人列表
	  * @return
	  */
	 @RequestMapping(value = "/getContactInfo", method = {RequestMethod.POST, RequestMethod.GET})
	 public String getContactInfoList(Map<String, String> model) throws UnsupportedEncodingException {
		 log.info("获取联系人列表开始");
		 Map<String, Object> response = new HashMap<String, Object>();
		 try {
			Integer userId = super.getUserId();
			log.info("<<<<<<<<<<<<<<<requestParam>>>>>>>>>>>>>>>>>>");
        	log.info("请求的联系人信息为：+[id="+userId+"]");
			List<ContactInfo> contactInfoList = contactInfoService.getContactInfoListByUserId(userId);
			/*List<Map<String,Object>> contactList = new ArrayList<Map<String,Object>>();
			if (contactInfoList.isEmpty() || contactInfoList.size() == 0) {
				response.setResultCode(ContactInfoConstant.ERROR_CODE_2001);
			}else {
				for (ContactInfo contactInfo : contactInfoList) {
					Map<String, Object> contactData = new HashMap<String, Object>();
					contactData.put("id", 			contactInfo.getId());
					contactData.put("userId", 		contactInfo.getUserId());
					contactData.put("contactName",  contactInfo.getContactName());
					contactData.put("contactPhone", contactInfo.getContactPhone());
					contactData.put("type", 		contactInfo.getType());	
					contactData.put("relationship", contactInfo.getRelationship());
					contactData.put("verifyState", 	contactInfo.getVerifyState());
					contactList.add(contactData);
				}
			}*/
			//成功设置
			response.put(Constant.SUCCESS_CODE,Constant.ERROR_MESSAGE.get(Constant.SUCCESS_CODE));
			response.put("contactList", contactInfoList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("获取联系人列表失败",e);
            response.put(Constant.ERROR_CODE_9999, Constant.ERROR_MESSAGE.get(Constant.ERROR_CODE_9999));
		}
	     model.put(Constant.RETURN_MESSAGE, JsonUtils.getJSONString(response));
	     log.debug("BillStageController.billList.response=="+JsonUtils.getJSONString(response));
		 return RET_JSP;
	 }
	 	
	 //获取请求数据
	 @SuppressWarnings("rawtypes")
	 private List<ContactInfo> getContactInfoListParam(){
		 List<ContactInfo> contactInfolist = new ArrayList<ContactInfo>();
	        try {
	        	String tempParam= UnicodeUtil.convert(request.getParameter("contactInfo"));
	        	JSONArray contactArray = JSONArray.fromObject(tempParam);
	        	log.info("<<<<<<<<<<<<<<<requestParam>>>>>>>>>>>>>>>>>>");
	        	log.info("请求的联系人信息为："+contactArray.toString());
				Iterator iterator = contactArray.iterator();
				while(iterator.hasNext()){
//					ContactInfo contactInfo = new ContactInfo();
					JSONObject contactObject = JSONObject.fromObject(iterator.next());
					ContactInfo contactInfo = (ContactInfo) JSONObject.toBean(contactObject, ContactInfo.class);
					contactInfo.setUserId(super.getUserId());
					contactInfolist.add(contactInfo);
	        	}
	        } catch (Exception ex) {
	        	log.info("<<<<<<<<<<<<<<<requestParam转化出错>>>>>>>>>>>>>>>>>>");
	            log.error("请求联系人信息转换错误" + ex.getMessage(), ex);
	            contactInfolist = null;
	        }
	        return contactInfolist;
	    }
}
