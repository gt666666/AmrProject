package cn.mldn.amr.dao;

import java.util.List;

import cn.mldn.amr.vo.Purchase;

public interface IPurchaseDAO extends IDAO<Integer, Purchase> {
	/**
	 * 实现购入单数据的更新处理操作
	 * @param pid 购入单编号
	 * @param status 状态，如果通过则设置为1，如果不通过则设置为2
	 * @return
	 * @throws Exception
	 */
	public boolean doUpdateStatus(Integer pid,Integer status,Integer eid) throws Exception ;
	
	/**
	 * 列出指定用户的所有的申请记录
	 * @param eid
	 * @param currentPage
	 * @param lineSize
	 * @return
	 * @throws Exception
	 */
	public List<Purchase> findAllByEmp(Integer eid, Integer currentPage,
			Integer lineSize) throws Exception;
	/**
	 * 所有申请记录的个数统计
	 * @param eid
	 * @return
	 * @throws Exception
	 */
	public Integer getAllCountByEmp(Integer eid) throws Exception;
	/**
	 * 根据订单编号与雇员的编号查询此雇员的自己的申请记录的详细内容
	 * @param pid
	 * @param eid
	 * @return
	 * @throws Exception
	 */
	public Purchase findByIdAndEmp(Integer pid,Integer eid) throws Exception ;
	/**
	 * 进行全部申请单的列表显示
	 * @param currentPage
	 * @param lineSize
	 * @return
	 * @throws Exception
	 */
	public List<Purchase> findAllSimpleSplit(Integer currentPage,Integer lineSize) throws Exception ;
	/**
	 * 统计出全部申请单的个数
	 * @return
	 * @throws Exception
	 */
	public Integer getAllCountSimple() throws Exception ;
}
