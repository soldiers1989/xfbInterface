/**
 * @(#)TradePasswordController.java	1.0	2015-8-12
 * Copyright 2014 [天尧], Inc. All rights reserved.
 * Website: http://www.tyiti.com/
 */
package cn.tyiti.xfb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.emagsoftware.frame.bean.BaseRspBean;
import cn.emagsoftware.frame.controller.BaseController;
import cn.emagsoftware.utils.Constant;
import cn.emagsoftware.utils.json.JsonUtils;
import cn.emagsoftware.xfb.pojo.SysUser;
import cn.emagsoftware.xfb.service.SysUserService;
import cn.tyiti.xfb.bojo.MemberInfo;
import cn.tyiti.xfb.service.TradePasswordService;


/**
 * 
 * 交易密码Controller.
 * 
 * @version 1.0 2015-9-15
 * @author Black
 */
@Controller
@RequestMapping(value = "/tradePassword")
public class TradePasswordController extends BaseController {
	@Autowired
	private TradePasswordService tradePasswordService;

    @Autowired
    private SysUserService sysUserService;
	/**
	 * 
	 * 交易密码设置
	 * @author Black
	 * @date 2015-9-15 上午9:25:20
	 *
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/setTradePassword", method = {RequestMethod.POST})
    public String setTradePassword(Map<String, String> model) {
    	MemberInfo memberInfo = this.getParamConvertEntity(MemberInfo.class);
    	BaseRspBean reponse = new BaseRspBean();
        try {
        	/* 判断用户ID是否为空
        	 * null：直接返回错误信息
        	 * 
        	 */
        	if(memberInfo.getUserId() == 0){
        		reponse.setCodeAndMsg("2110", "头消息为空， 请重试！");
        	}else {
        		if (memberInfo.getCardNumber()!=null&&!memberInfo.getCardNumber().equals("")) {
        			String loginName = request.getParameter("loginName");
        			String cardId = request.getParameter("cardNumber");
        			Integer userId = memberInfo.getUserId();
        			SysUser user = sysUserService.getUserByLoginCard(new SysUser(loginName,cardId));
        			if (user!=null) {
        				if(user.getId().equals(userId)&&user.getId()!=0){
        					int result = tradePasswordService.setTradePassword(memberInfo);
        					if(result == 1){
        						//成功设置
        						reponse.setCodeAndMsg(Constant.SUCCESS_CODE,Constant.ERROR_MESSAGE
        								.get(Constant.SUCCESS_CODE));
        					} else {
        						reponse.setCodeAndMsg("2111", "交易密码设置失败， 请重试！");
        					}
        				}else {
        					reponse.setCodeAndMsg("2112", "交易密码设置失败， 请重试！");
        				}
					}else{
						reponse.setCodeAndMsg("2113", "用户不存在， 请重试！");
					}
        		}else {
        			int result = tradePasswordService.setTradePassword(memberInfo);
        			if(result == 1){
        				//成功设置
        				reponse.setCodeAndMsg(Constant.SUCCESS_CODE,Constant.ERROR_MESSAGE
        						.get(Constant.SUCCESS_CODE));
        			} else {
        				reponse.setCodeAndMsg("2114", "交易密码设置失败， 请重试！");
        			}
        		}
			}
        } catch (Exception ex) {
            log.error("TradePasswordController.setTradePassword", ex);
            reponse.setCodeAndMsg(Constant.ERROR_CODE_9999, Constant.ERROR_MESSAGE.get(Constant.ERROR_CODE_9999));
        }
    	model.put(Constant.RETURN_MESSAGE, JsonUtils.getJSONString(reponse));
        return RET_JSP;
    }

    /**
     * 
     * 交易密码验证
     * @author Black
     * @date 2015-9-15 上午9:31:14
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/validTradePassword", method = {RequestMethod.POST})
    public String validTradePassword(Map<String, String> model) {
    	MemberInfo memberInfo = this.getParamConvertEntity(MemberInfo.class);
    	BaseRspBean reponse = new BaseRspBean();
        try {
        	MemberInfo resultMemberInfo = tradePasswordService.getMemberInfo(memberInfo);
        	//校验交易密码是否正确
        	if(resultMemberInfo.getTradePassword().equalsIgnoreCase(memberInfo.getTradePassword())){
        		//成功设置
            	reponse.setCodeAndMsg(Constant.SUCCESS_CODE,Constant.ERROR_MESSAGE
                        .get(Constant.SUCCESS_CODE));
        	} else {
        		reponse.setCodeAndMsg("2111", "交易密码错误");
        	}
        } catch (Exception ex) {
            log.error("TradePasswordController.validTradePassword", ex);
            reponse.setCodeAndMsg(Constant.ERROR_CODE_9999, Constant.ERROR_MESSAGE.get(Constant.ERROR_CODE_9999));
        }
    	model.put(Constant.RETURN_MESSAGE, JsonUtils.getJSONString(reponse));
        return RET_JSP;
    }

    /**
     * 
     * 交易密码找回
     * @author Black
     * @date 2015-9-15 上午9:31:14
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/retrieveTradePassword", method = {RequestMethod.POST})
    public String retrieveTradePassword(Map<String, String> model) {
    	MemberInfo memberInfo = this.getParamConvertEntity(MemberInfo.class);
    	BaseRspBean reponse = new BaseRspBean();
        try {
        	MemberInfo resultMemberInfo = tradePasswordService.getMemberInfo(memberInfo);
        	//校验身份证号码
        	if(resultMemberInfo.getCardNumber().equalsIgnoreCase(memberInfo.getCardNumber())){
        		//成功设置
            	reponse.setCodeAndMsg(Constant.SUCCESS_CODE,Constant.ERROR_MESSAGE
                        .get(Constant.SUCCESS_CODE));
        	} else {
        		reponse.setCodeAndMsg("2112", "身份证号码错误");
        	}
        } catch (Exception ex) {
            log.error("TradePasswordController.retrieveTradePassword", ex);
            reponse.setCodeAndMsg(Constant.ERROR_CODE_9999, Constant.ERROR_MESSAGE.get(Constant.ERROR_CODE_9999));
        }
    	model.put(Constant.RETURN_MESSAGE, JsonUtils.getJSONString(reponse));
        return RET_JSP;
    }
    
}
