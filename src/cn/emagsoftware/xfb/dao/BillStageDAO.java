package cn.emagsoftware.xfb.dao;

import cn.emagsoftware.xfb.pojo.BillStage;

import java.util.List;
import java.util.Map;

public interface BillStageDAO{
    int deleteByPrimaryKey(Integer id);

    void insert(BillStage record);

    void insertSelective(BillStage record);

    BillStage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillStage record);

    int updateByPrimaryKey(BillStage record);

    Map<String,Object> billList(BillStage billStage, long startNumber, long pageSize);

    int batchCreateBill(List<BillStage> list);

    void updateStatusAndAmount(BillStage billStage);

    void updateBillByOrder(BillStage billStage);

    List<BillStage> getBillByOrder(BillStage bill);

	List<BillStage> getBillByOrderId(Integer orderId);

	BillStage getBillDetail(Integer billId);
	/**
	 * 通过orderId获取账单并且状态包括0,1,3的个数
	 * @param orderId
	 * @return
	 */
	int getBillFlagByOrderId(Integer orderId);
	
	/**
	 * 根据流水号获取总金额（流水表）
	 * @param BillStage
	 * @author shenwu
	 * @return
	 */
	BillStage selTotalAllByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 根据流水号获取总金额（账单表）
	 * @param BillStage
	 * @author shenwu
	 * @return
	 */
	BillStage selTotalAllByBillNo(String billNo) throws Exception;

	/**
	 * 查询7日待还数量
	 * @Description:
	 * @author: liminghua
	 * @param @param orderId
	 * @param @return
	 * @return int
	 * @throws
	 */
	int gettSevenBillNumByOwnerUserId(Integer orderId);

}
