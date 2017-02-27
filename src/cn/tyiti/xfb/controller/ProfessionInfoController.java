/**
 * @(#)ProfessionInfoController.java	1.0	2015-8-12
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
import cn.tyiti.xfb.bojo.JobInfo;
import cn.tyiti.xfb.bojo.ProfessionInfo;
import cn.tyiti.xfb.bojo.StudentInfo;
import cn.tyiti.xfb.bojo.VerifyLogInfo;
import cn.tyiti.xfb.service.MemberInfoService;
import cn.tyiti.xfb.service.ProfessionInfoService;


/**
 * 提升额度-获取职业信息/提升额度-提交职业信息Controller.
 * 
 * @version 1.0 2015-8-12
 * @author Black
 */
@Controller
@RequestMapping(value = "/professionInfo")
public class ProfessionInfoController extends BaseController {
	@Autowired
	private ProfessionInfoService professionInfoService;
	
	@Autowired
	private MemberInfoService memberInfoServiceII;
	
	/**
     * 获取职业信息
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/getJobInfo", method = {RequestMethod.POST})
    public String getJobInfo(Map<String, String> model) {
    	JobInfo jobInfo = this.getParamConvertEntity(JobInfo.class);
        try {
        	jobInfo = professionInfoService.getProfessionInfo(jobInfo);	
    		//成功设置
        	jobInfo.setCodeAndMsg(Constant.SUCCESS_CODE,Constant.ERROR_MESSAGE
                    .get(Constant.SUCCESS_CODE));
        } catch (Exception ex) {
            log.error("CustomerController.getStateInfo", ex);
            jobInfo.setCodeAndMsg(Constant.ERROR_CODE_9999, Constant.ERROR_MESSAGE.get(Constant.ERROR_CODE_9999));
        }
    	model.put(Constant.RETURN_MESSAGE, JsonUtils.getJSONString(jobInfo));
        return RET_JSP;
    }
    
	/**
     * 提交职业信息
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveJobInfo", method = {RequestMethod.POST})
    public String saveJobInfo(Map<String, String> model) {
    	JobInfo jobInfo = this.getParamConvertEntity(JobInfo.class);
    	BaseRspBean response = new BaseRspBean();
        try {
            professionInfoService.saveProfessionInfo(jobInfo);
            
            //授信拦截地址
            log.info("授信拦截地址>>>>>>>>>>>>>>>>>>>>用户ID="+jobInfo.getUserId());
            String uId = jobInfo.getUserId().toString();
            String ct = jobInfo.getCity();
            String city = null;
            if(!"".equals(ct) || ct == null){
            	city = ct.substring(0,ct.length()-1);
            }
            
            ProfessionInfo pfi = memberInfoServiceII.getPartnerBaseInfo(uId);
            String locationProvince = pfi.getLocationProvince();
            String partnerAddr = pfi.getPartnerAddr();
            String sysVersion = this.request.getHeader("sysVersion");
        	log.info("获取版本号为：>>>>>>>>>>>>>>>>>>>>>"+sysVersion);
        	if("V1.2.0".equals(sysVersion) || "V1.2.0" == sysVersion) {//兼容V1.2.0
        		log.info("此用户使用的是新版本V1.2.0,执行V1.2.0操作>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        		String geolocation = pfi.getGeolocation();
                if(!"".equals(geolocation) || geolocation != null){
                	if("".equals(city) || "".equals(locationProvince) || "".equals(partnerAddr) || city==null || locationProvince == null || partnerAddr == null){
                		log.info("商户地址>>>>>>>>>>>"+locationProvince+"与"+"个人信息住址>>>>>>>>>>>"+city+"职业信息地址>>>>>>>>>"+partnerAddr+"地理位置"+geolocation+",为空，已添加至审核日志表");
                		updateUserStatus(uId);//更新状态
                	}else {
                    	if(locationProvince.indexOf(city)<0 && partnerAddr.indexOf(city)<0 && geolocation.indexOf(city)<0){
                    		log.info("商户地址>>>>>>>>>>>"+locationProvince+"与"+"个人信息住址>>>>>>>>>>>"+city+"职业信息地址>>>>>>>>>"+partnerAddr+"地理位置"+geolocation+",不一致，已添加至审核日志表");
                    		updateUserStatus(uId);//更新状态
                    	}
                	}
                }else {
                	ExtractionOfPublic(city, locationProvince, partnerAddr, uId);
                }
        	}else {//兼容V1.1.4
        		log.info("此用户使用的是老版本V1.1.4,执行V1.1.4操作>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        		ExtractionOfPublic(city, locationProvince, partnerAddr, uId);
        	}
            
    		//成功设置
            response.setCodeAndMsg(Constant.SUCCESS_CODE,Constant.ERROR_MESSAGE
                    .get(Constant.SUCCESS_CODE));
        } catch (Exception ex) {
            log.error("CustomerController.getStateInfo", ex);
            response.setCodeAndMsg(Constant.ERROR_CODE_9999, Constant.ERROR_MESSAGE.get(Constant.ERROR_CODE_9999));
        }
    	model.put(Constant.RETURN_MESSAGE, JsonUtils.getJSONString(response));
        return RET_JSP;
    }

	/**
     * 获取学生信息
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/getStudentInfo", method = {RequestMethod.POST})
    public String getStudentInfo(Map<String, String> model) {
    	StudentInfo studentInfo = this.getParamConvertEntity(StudentInfo.class);
        try {
        	studentInfo = professionInfoService.getProfessionInfo(studentInfo);	
    		//成功设置
        	studentInfo.setCodeAndMsg(Constant.SUCCESS_CODE,Constant.ERROR_MESSAGE
                    .get(Constant.SUCCESS_CODE));
        } catch (Exception ex) {
            log.error("CustomerController.getStateInfo", ex);
            studentInfo.setCodeAndMsg(Constant.ERROR_CODE_9999, Constant.ERROR_MESSAGE.get(Constant.ERROR_CODE_9999));
        }
    	model.put(Constant.RETURN_MESSAGE, JsonUtils.getJSONString(studentInfo));
        return RET_JSP;
    }
    
	/**
     * 提交学生信息
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveStudentInfo", method = {RequestMethod.POST})
    public String saveStudentInfo(Map<String, String> model) {
    	StudentInfo studentInfo = this.getParamConvertEntity(StudentInfo.class);
    	BaseRspBean response = new BaseRspBean();
        try {
            professionInfoService.saveProfessionInfo(studentInfo);	
    		//成功设置
            response.setCodeAndMsg(Constant.SUCCESS_CODE,Constant.ERROR_MESSAGE
                    .get(Constant.SUCCESS_CODE));
        } catch (Exception ex) {
            log.error("CustomerController.getStateInfo", ex);
            response.setCodeAndMsg(Constant.ERROR_CODE_9999, Constant.ERROR_MESSAGE.get(Constant.ERROR_CODE_9999));
        }
    	model.put(Constant.RETURN_MESSAGE, JsonUtils.getJSONString(response));
        return RET_JSP;
    }
    
    /**
     * 提取公共部分
     * @author shenwu
     * @param city locationProvince partnerAddr uId
     */
    public void ExtractionOfPublic(String city, String locationProvince, String partnerAddr, String uId){
    	if("".equals(city) || "".equals(locationProvince) || "".equals(partnerAddr) || city==null || locationProvince == null || partnerAddr == null){
    		log.info("商户地址>>>>>>>>>>>"+locationProvince+"与"+"个人信息住址>>>>>>>>>>>"+city+"职业信息地址>>>>>>>>>"+partnerAddr+",为空，已添加至审核日志表");
    		updateUserStatus(uId);//更新状态
    	}else {
        	if(locationProvince.indexOf(city)<0 && partnerAddr.indexOf(city)<0){
        		log.info("商户地址>>>>>>>>>>>"+locationProvince+"与"+"个人信息住址>>>>>>>>>>>"+city+"职业信息地址>>>>>>>>>"+partnerAddr+",不一致，已添加至审核日志表");
        		updateUserStatus(uId);//更新状态
        	}
    	}
    }
    
    /**
     * 更新状态
     * @author shenwu
     * @throws Exception
     */
    public void updateUserStatus(String uId) {
    	try {
    		log.info(">>>>>>>>>>>>>>>>>更新状态开始>>>>>>>>>>>>>>>>>");
    		VerifyLogInfo vli = new VerifyLogInfo();
    		vli.setUserid(uId);
    		memberInfoServiceII.saveVerifyLogInfo(vli);
    		log.info("系统自动拦截,修改用户"+uId+"状态为6");
    		Integer userId = Integer.valueOf(uId);
    		memberInfoServiceII.updateUserStatus(userId);
    		log.info(">>>>>>>>>>>>>>>>>更新状态结束>>>>>>>>>>>>>>>>>");
		} catch (Exception e) {
			log.error("CustomerController.updateUserStatus", e);
			log.info("更新状态出现异常，请查看CustomerController.updateUserStatus此方法");
		}
    }
    
}
