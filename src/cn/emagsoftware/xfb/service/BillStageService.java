package cn.emagsoftware.xfb.service;

import cn.emagsoftware.xfb.pojo.BillStage;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yindongyong
 * Date: 15-4-7
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */
public interface BillStageService {

    public BillStage getBillById(BillStage bill)throws Exception;
    public  int  batchCreateBill(List<BillStage> list)throws Exception;

    public Map<String, Object> billList(BillStage billStage, long startNumber, long pageSize)throws Exception;

    public void  updateBill(BillStage billStage)throws Exception;

    public void updateBillByOrder(BillStage billStage)throws Exception;

    public BillStage getBillByOrder(BillStage bill)throws Exception;
    
	public List<BillStage> getBillByOrderId(Integer id);
	
	public BillStage getBillDetail(Integer billId);
	/**
	 * 通过orderId获取账单并且状态包括0,1,3的个数
	 * @param orderId
	 * @return
	 */
	public int getBillFlagByOrderId(Integer orderId);
	
	
	/**
	 * <p>Title:gettSevenBillNumByOwnerUserId</p>
	 * <p>Description:查询七日待还帐单数量</p>
	 * @param orderId 
	 * @return number
	 */
	public int gettSevenBillNumByOwnerUserId(Integer orderId);
	
	/**
	 * 根据流水号获取总金额(流水表)
	 * @param BillStage
	 * @author shenwu
	 * @return
	 */
	public BillStage selTotalAllByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 根据流水号获取总金额（账单表）
	 * @param BillStage
	 * @author shenwu
	 * @return
	 */
	public BillStage selTotalAllByBillNo(String billNo) throws Exception;
}
