package cn.tyiti.xfb.dao.blacklist.impl;   

import org.springframework.stereotype.Repository;

import cn.emagsoftware.frame.dao.BaseDao;
import cn.tyiti.xfb.bojo.blacklist.BlackListInfo;
import cn.tyiti.xfb.dao.blacklist.IblackListInfoManageDao;

/** 
 * 创建时间：2015-11-24 下午9:36:29  
 * 项目名称：xfbManage  
 * @author liminghua  
 * @version 1.0   
 * @since JDK 1.6
 * 文件名称：BlackListInfoManageDao.java  
 * 类说明：  
 */
@Repository("blackListInfoManageDao")
public class BlackListInfoManageDao extends BaseDao implements IblackListInfoManageDao {

	/**
	 *<p>Title:insert</p>
	 *<p>Description:插入数据</p>
	 *@param blackListInfo
	 *@throws Exception
	 *@see cn.tyiti.xfb.dao.blacklist.IblackListInfoManageDao#insert(cn.tyiti.xfb.bojo.blacklist.BlackListInfo)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void insert(BlackListInfo blackListInfo) throws Exception {
		this.getSqlMapClientTemplate().insert("t_blacklist_result.insert_blacklist",blackListInfo);
	}

}
 