package cn.tyiti.xfb.controller.blacklistmanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.emagsoftware.frame.controller.BaseController;
import cn.emagsoftware.utils.json.JsonUtils;
import cn.tyiti.xfb.bojo.UserInfo;
import cn.tyiti.xfb.service.BlackListService;
import cn.tyiti.xfb.utils.blacklist.BlackListQuery;
import cn.tyiti.xfb.utils.blacklist.QlBlackInfoBlackListQuery;
import cn.tyiti.xfb.utils.blacklist.SyBadInfoBlackListQuery;

/**
 * 
 *<p>Title: BlackListController</p>
 *<p>Description:神州融黑名单查询控制器</p>
 *<p>Company: 天尧信息</p>
 *@author: liminghua
 *@date： 日期：2015-11-24 时间：下午2:32:04
 */
@Controller
@RequestMapping(value = "/blackList")
public class BlackListController  extends BaseController  {

	Logger log = Logger.getLogger(BlackListController.class);
	@Autowired
	private SyBadInfoBlackListQuery syBadInfoBlackListQuery;
	
	@Autowired
	private QlBlackInfoBlackListQuery qlBlackInfoBlackListQuery;
	@Autowired
	private BlackListService blackListService;
	
	@Autowired
	private BlackListQuery blackListQuery;

	/**
	 * 
	 * <p>Title:BlackListcheck</p>
	 * <p>Description:通过神州融接口SyBadInfoAction查询黑名单</p>
	 * @param request name姓名， idcode 身份证号，用户userId,loginName 手机号
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/syBadInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public String SyBadInfoAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 姓名
		String name = request.getParameter("name");
		// 身份证号
		String idcode = request.getParameter("idcode");
		
		//用户id
		String userId = request.getHeader("userId");
		//手机号
		String loginName = request.getParameter("loginName");
		
		String msg = syBadInfoBlackListQuery.blackListQuery(name,idcode,userId,loginName );
		
		map.put("code", msg);
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(JsonUtils.getJSONString(map));
		return null;
	}
	/**
	 * <p>Title:BlackListAction</p>
	 * <p>Description:通过神州融接口BlackListAction查询黑名单</p>
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/blackList", method = {RequestMethod.POST, RequestMethod.GET})
	public String BlackListAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 姓名
		String name = request.getParameter("name").trim();
		// 身份证号
		String idcode = request.getParameter("idcode").trim();
		//用户id
		String userId = request.getHeader("userId").trim();
		//手机号
		String loginName = request.getParameter("loginName").trim();
		
		String msg = blackListQuery.blackListQuery(name,idcode,userId,loginName );
		
		//String msg = blackListQuery.blackListQuery("安吉", "320106198107173214","1999","13777777777");
		map.put("code", msg);
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(JsonUtils.getJSONString(map));
		return null;
	}
	/**
	 * 
	 * <p>Title:BlackListcheck</p>
	 * <p>Description:通过神州融接口qlBlackInfoAction查询黑名单</p>
	 * @param request 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/qlBlackInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public String QlBlackInfoAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 姓名
		String name = request.getParameter("name");
		// 身份证号
		String idcode = request.getParameter("idcode");
		//用户id
		String userId = request.getHeader("userId");
		//手机号
		String loginName = request.getParameter("loginName");
		//此查询手机号不能为空
		String msg = qlBlackInfoBlackListQuery.blackListQuery(name,idcode,userId,loginName );
		
		//String msg = qlBlackInfoBlackListQuery.blackListQuery(name,idcode);
		map.put("code", msg);
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(JsonUtils.getJSONString(map));
		return null;
	}
	
	@RequestMapping(value = "/queryAndInsert", method = {RequestMethod.POST, RequestMethod.GET})
	public String queryAndInsert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfo user=new UserInfo();
		List<UserInfo> list=new ArrayList<UserInfo>();
		
		user.setIdCode(request.getParameter("idCode"));
		user.setRealName(request.getParameter("realName"));
		user.setLoginName(request.getParameter("loginName"));
		
		list.add(user);
		blackListService.queryAndInsert(list);
		return null;
	}
}
