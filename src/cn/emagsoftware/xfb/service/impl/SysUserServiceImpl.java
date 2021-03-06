package cn.emagsoftware.xfb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emagsoftware.frame.service.BaseService;
import cn.emagsoftware.utils.SeqUtil;
import cn.emagsoftware.utils.WcfCallUtils;
import cn.emagsoftware.xfb.dao.MemberInfoDAO;
import cn.emagsoftware.xfb.dao.SysLogDAO;
import cn.emagsoftware.xfb.dao.SysUserDAO;
import cn.emagsoftware.xfb.pojo.MemberInfo;
import cn.emagsoftware.xfb.pojo.SysLog;
import cn.emagsoftware.xfb.pojo.SysUser;
import cn.emagsoftware.xfb.service.SysUserService;
import cn.tyiti.xfb.bojo.QuotaScore;
import cn.tyiti.xfb.bojo.ScoreLog;
import cn.tyiti.xfb.constant.CreditModelConstant;
import cn.tyiti.xfb.constant.VerifyStateConstant;
import cn.tyiti.xfb.dao.CreditModelDao;
import cn.tyiti.xfb.dao.MemberInfoDao;
import cn.tyiti.xfb.dao.QuotaScoreDao;
import cn.tyiti.xfb.dao.ScoreLogDao;
import cn.tyiti.xfb.utils.QuotaComputerUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yindongyong
 * Date: 15-4-3
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */

@Service
public class SysUserServiceImpl  extends BaseService implements SysUserService{
    @Autowired
    private SysUserDAO sysUserDao;
    @Autowired
    private SysLogDAO sysLogDAO;
    @Autowired
    private MemberInfoDAO memberInfoDAO;
    @Autowired
    private CreditModelDao creditModelDao;
    @Autowired
    private ScoreLogDao scoreLogDao;
    @Autowired
    private QuotaScoreDao quotaScoreDao;
    @Autowired
    private MemberInfoDao memberInfoDao;
    @Autowired
    private SeqUtil seqUtil;
    @Autowired
    private WcfCallUtils wcfCallUtils;


    @Override
    public SysUser userLogin(SysUser user) throws Exception {

        List<SysUser> list  = sysUserDao.userLogin(user);
        if(list!=null&&list.size()==1){
            return list.get(0);
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean checkLoginName(SysUser user) {
        int rows = sysUserDao.getUserByloginName(user);
        if(rows>0){
            return true;
        }
        return false;   //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addUser(SysUser user) {
        //To change body of implemented methods use File | Settings | File Templates.
        sysUserDao.insert(user);
    }

    @Override
    public void addUser(SysUser user, MemberInfo memberInfo) throws Exception{
        //获取用户序列号 解决事务问题
    	Integer userId = seqUtil.getNextValueBySeqName("t_sys_user");

    	user.setId(userId);
    	//用自定义自增的id，序列表开始
        sysUserDao.insert(user);
        memberInfo.setSysUserid(userId);
        memberInfoDAO.insert(memberInfo);

    	/*
    	 * 调用.net Wcf注册接口
    	 * author:Black
    	 * date:2015-08-05
    	 */
    	wcfCallUtils.callRegister(userId, user.getLoginName(), user.getPassWord(), user.getRealName(), memberInfo.getIdNumber());
    }

    @Override
    public boolean checkRecomCode(SysUser user) throws Exception {

        int rows = sysUserDao.getUserByMyCode(user);
        if(rows>0){
            return true;
        }
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SysUser getUserById(SysUser user) throws Exception {
        return sysUserDao.selectByPrimaryKey(user.getId());  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updatePassWord(SysUser resultUser) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        sysUserDao.updatePassWord(resultUser);
        wcfCallUtils.callSetPassword(resultUser.getId(), resultUser.getLoginName(), resultUser.getNewPassWord());
    }

    @Override
    public SysUser getUserByRecomCode(String recomCode) throws Exception {
        List<SysUser> list = sysUserDao.getUserByRecomCode(recomCode);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public SysUser getPorUserByRecomCode(String recomCode) throws Exception {
        List<SysUser> list = sysUserDao.getPorUserByRecomCode(recomCode);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public SysUser getUserByLoinName(String loginName) throws Exception {
        List<SysUser> list = sysUserDao.getUserByLoinName(loginName);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateRealNameByUsrId(SysUser sysUser) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        sysUserDao.updateRealNameByUsrId(sysUser);
    }

    @Override
    public boolean checkRuralBrokerByUserId(SysUser recomUser) {

        int row = sysUserDao.getRuralBrokerByUserId(recomUser);
        if(row>0){
            return true;
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addSysLog(SysLog sysLog) {
        //To change body of implemented methods use File | Settings | File Templates.
        sysLogDAO.insert(sysLog);
    }

    @Override
    public Integer getCountByRecomCode(String myCode) {
        return sysUserDao.getCountByRecomCode(myCode);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateRecomNum(SysUser recomUser) {
        //To change body of implemented methods use File | Settings | File Templates.
        sysUserDao.updateRecomNum(recomUser);
    }
    @Override
    public Integer getUserByLNCN(SysUser user) {
        return sysUserDao.getUserByLNCN(user); 
    }
    
    @Override
    public SysUser getUserByLoginCard(SysUser user) {
        return sysUserDao.getUserByLoginCard(user); 
    }

	@Override
	public void insertBazaarRegister(Map paramMap) {
		sysUserDao.insertBazaarRegister(paramMap); 
	}

    @Override
    public void updateStatusByLoginName(SysUser sysUser) throws Exception{
    	sysUserDao.updateStatusByLoginName(sysUser);
//    	String loginName = sysUser.getLoginName();
//    	SysUser tempUser = getUserByLoinName(loginName);
    	/*
    	 * 调用.net Wcf注册接口
    	 * author:Black
    	 * date:2015-08-05
    	 */
//    	wcfCallUtils.callSetUserStatus(tempUser.getId(), loginName, String.valueOf(sysUser.getStatus()));
    }

	@Override
	public void updateRecomUser(SysUser recomUser) throws Exception {
		 //更新推荐人推荐数
        sysUserDao.updateRecomNum(recomUser);
        //判断原推荐人数小于5并且授信状态为增信则重新修正推荐人分数
        if(recomUser.getRecomNum() < 5 && VerifyStateConstant.USER_VERIFY_STATE_PASS.equals(recomUser.getVerifyState())){
        	//获取推荐一人的模型分数插入得分表中
        	Integer score =  QuotaComputerUtil.reComputerCredit(creditModelDao.getScoreByCode(CreditModelConstant.USER_RECOM_FRIEND));
        	ScoreLog socreLog = new ScoreLog(recomUser.getId(),score);
        	scoreLogDao.inser(socreLog);
        	//获取得分表中推荐人获得的所有分数
        	Integer scoreSum = scoreLogDao.getScoreSumById(recomUser.getId());
        	//根据分数获取对应的额度
        	QuotaScore quota = quotaScoreDao.getQuotaByScore(scoreSum);
        	//计算额度
        	Integer credits = QuotaComputerUtil.computerCredit(quota, scoreSum);
        	//更新会员额度
        	Map<String,Integer> map = this.getParamMap(recomUser.getId(), credits);
        	memberInfoDao.updateCredit(map);
        	//日志?
        }
	}
	
	private Map<String,Integer> getParamMap(Integer userid,Integer credits){
		Map<String,Integer> map = new HashMap();
		map.put("userid", userid);
		map.put("limit", credits);
		return map;
	}
}
