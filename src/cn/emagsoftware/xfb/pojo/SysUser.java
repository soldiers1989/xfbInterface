package cn.emagsoftware.xfb.pojo;

import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable  {
	
	public SysUser(){};
	
	public SysUser(String loginName,String idNumber){
		this.loginName = loginName;
		this.memberInfo = new MemberInfo();
		memberInfo.setIdNumber(idNumber);
	}
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 6301781217267221242L;

	private Integer id;
    
    private String uid;

    private String loginName;

    private String realName;

    private String passWord;

    private String newPassWord;

    private String oldPassword;

    private Integer status;

    private String myCode;

    private String recomCode;

    private Date createTime;

    private Integer createUserid;

    private Date updateTime;

    private Integer updateUserid;

    private String description;
    
    private String type;


    private  String  SMSCode;

    private Integer recomNum;
    
    //授信状态
    private String verifyState;
    
    private MemberInfo memberInfo;
    
    //会员类型：A1:上班族 A2:学生
    private String hasVerify;
    
    public String getVerifyState() {
		return verifyState;
	}

	public void setVerifyState(String verifyState) {
		this.verifyState = verifyState;
	}

	public String getHasVerify() {
		return hasVerify;
	}

	public void setHasVerify(String hasVerify) {
		this.hasVerify = hasVerify;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public String getSMSCode() {
        return SMSCode;
    }

    public void setSMSCode(String SMSCode) {
        this.SMSCode = SMSCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMyCode() {
        return myCode;
    }

    public void setMyCode(String myCode) {
        this.myCode = myCode == null ? null : myCode.trim();
    }

    public String getRecomCode() {
        return recomCode;
    }

    public void setRecomCode(String recomCode) {
        this.recomCode = recomCode == null ? null : recomCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(Integer createUserid) {
        this.createUserid = createUserid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserid() {
        return updateUserid;
    }

    public void setUpdateUserid(Integer updateUserid) {
        this.updateUserid = updateUserid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }


    public Integer getRecomNum() {
        return recomNum;
    }

    public void setRecomNum(Integer recomNum) {
        this.recomNum = recomNum;
    }
}
