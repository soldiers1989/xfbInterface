package cn.emagsoftware.xfb.dao.impl;

import cn.emagsoftware.frame.dao.BaseDao;
import cn.emagsoftware.xfb.dao.BillStageDAO;
import cn.emagsoftware.xfb.pojo.BillStage;
import cn.emagsoftware.xfb.pojo.OrderInfo;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("billStageDAO")
public class BillStageDAOImpl extends BaseDao implements BillStageDAO {

    public BillStageDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(Integer id) {
        BillStage _key = new BillStage();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("t_bill_stage.deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(BillStage record) {
        getSqlMapClientTemplate().insert("t_bill_stage.insert", record);
    }

    public void insertSelective(BillStage record) {
        getSqlMapClientTemplate().insert("t_bill_stage.insertSelective", record);
    }

    public BillStage selectByPrimaryKey(Integer id) {
        BillStage _key = new BillStage();
        _key.setId(id);
        BillStage record = (BillStage) getSqlMapClientTemplate().queryForObject("t_bill_stage.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByPrimaryKeySelective(BillStage record) {
        int rows = getSqlMapClientTemplate().update("t_bill_stage.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(BillStage record) {
        int rows = getSqlMapClientTemplate().update("t_bill_stage.updateByPrimaryKey", record);
        return rows;
    }

    @Override
    public Map<String, Object> billList(BillStage billStage, long startNumber, long pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageSize", pageSize);
        map.put("pageStart", startNumber);
        map.put("orderId", billStage.getOrderId());
        List<BillStage> billStageList =  (List<BillStage>) this.getSqlMapClientTemplate().queryForList("t_bill_stage.getBillListByOrderId", billStage);
        Object total = this.getSqlMapClientTemplate().queryForObject("t_bill_stage.getBillListByOrderId_count", billStage);
        map.put("billList", billStageList);
        map.put("billTotal", null == total ? 0 : (Integer) total);
        return map;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int batchCreateBill(List<BillStage> list) {

        this.getSqlMapClientTemplate().insert("t_bill_stage.betchInsert",list);
        return list.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 账单还款后更新账单状态和还款额
     * */
    @Override
    public void updateStatusAndAmount(BillStage billStage) {
        //To change body of implemented methods use File | Settings | File Templates.
        this.getSqlMapClientTemplate().update("t_bill_stage.updateStatusAndAmount",billStage);
    }

    @Override
    public void updateBillByOrder(BillStage billStage) {
        //To change body of implemented methods use File | Settings | File Templates.
        this.getSqlMapClientTemplate().update("t_bill_stage.updateStatusByOrder",billStage);
    }

    @Override
    public List<BillStage> getBillByOrder(BillStage bill) {

        return   this.getSqlMapClientTemplate().queryForList("t_bill_stage.getBillByOrder",bill);  //To change body of implemented methods use File | Settings | File Templates.
    }

	@Override
	public List<BillStage> getBillByOrderId(Integer orderId) {
		return   this.getSqlMapClientTemplate().queryForList("t_bill_stage.getBillByOrderId",orderId);
	}
	
	@Override
	public BillStage getBillDetail(Integer billId) {
		// TODO Auto-generated method stub
		return (BillStage) this.getSqlMapClientTemplate().queryForObject("t_bill_stage.getBillDetail", billId);
	}

	@Override
	public int getBillFlagByOrderId(Integer orderId) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("t_bill_stage.getBillFlagByOrderId", orderId);
	}
	
	/**
	 * 根据流水号获取总金额（流水表）
	 */
	@Override
	public BillStage selTotalAllByOrderNo(String orderNo) {
		return (BillStage) this.getSqlMapClientTemplate().queryForObject("t_bill_stage.selTotalAllByOrderNo", orderNo);
	}
	
	/**
	 * 根据流水号获取总金额（账单表）
	 */
	@Override
	public BillStage selTotalAllByBillNo(String billNo) {
		return (BillStage) this.getSqlMapClientTemplate().queryForObject("t_bill_stage.selTotalAllByBillNo", billNo);
	}
	/*七日内待还数量
	 * (non-Javadoc)
	 * @see cn.emagsoftware.xfb.dao.BillStageDAO#gettSevenBillNumByOwnerUserId(java.lang.Integer)
	 */
	@Override
	public int gettSevenBillNumByOwnerUserId(Integer orderId) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("t_bill_stage.gettSevenBillNumByOwnerUserId", orderId);
	}
	
}
