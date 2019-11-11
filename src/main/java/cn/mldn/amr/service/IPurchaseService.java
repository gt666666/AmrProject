package cn.mldn.amr.service;

import java.util.Map;

import cn.mldn.amr.vo.Purchase;

public interface IPurchaseService {
	/**
	 * 购入申请单创建
	 * @param eid 负责处理的雇员编号
	 * @param vo 申请单的信息
	 * @return
	 * @throws Exception
	 */
	public boolean add(int eid,Purchase vo) throws Exception ;
	/**
	 * 实现指定雇员所有申请单的数据查询
	 * @param eid 雇员编号
	 * @param currentPage
	 * @param lineSize
	 * @return 返回的内容包括如下结果：<br>
	 * <li>key = allPurchases、value = IPurchaseDAO.findAllByEmp()</li>
	 * <li>key = purchaseCount、value = IPurchaseDAO.getAllCountByEmp()</li>
	 * @throws Exception
	 */
	public Map<String, Object> listByEmp(int eid, int currentPage, int lineSize)
			throws Exception;
	/**
	 * 查询一个购入申请单的详细内容
	 * @param eid
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public Purchase getByEmp(int eid,int pid) throws Exception ;
	/**
	 * 列出全部的购入申请单信息
	 * @param eid
	 * @param currentPage
	 * @param lineSize
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> listSimple(int eid, int currentPage, int lineSize)
			throws Exception;
	/**
	 * 查看一个订单的详情数据
	 * @param eid
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public Purchase show(int eid,int pid) throws Exception ; 
	/**
	 * 实现购入单的审核处理
	 * @param eid 要处理的雇员编号，只能够是财务部的经理
	 * @param pid
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public boolean editStatus(int eid,int pid,int status) throws Exception ;
}
