package cn.mldn.amr.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.mldn.amr.vo.Take;

public interface ITakeDAO extends IDAO<Integer, Take> {
	/**
	 * 根据指定的雇员编号与用品编号进行数据的取得
	 * @param eid
	 * @param rid 
	 * @return
	 * @throws Exception
	 */
	public Integer findByResAndEmp(Integer eid, Integer rid) throws Exception;
	/**
	 * 重复添加的时候实现待领数量的变更
	 * @param tkid
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public boolean doUpdateAmount(Integer tkid , Integer amount) throws Exception ;
	/**
	 * 根据指定的雇员编号查询出所有的雇员的待领取记录信息
	 * @param eid 
	 * @return
	 * @throws Exception
	 */
	public List<Take> findAllByEmpUnGet(Integer eid) throws Exception ;
	/**
	 * 修改指定雇员的领取记录数量
	 * @param eid
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public boolean doUpdateAmountByEmp(Integer tkid, Integer eid,Integer amount) throws Exception ;
	/**
	 * 删除指定雇员的待领记录信息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean doRemoveByEmp(Set<Integer> ids) throws Exception ;
	/**
	 * 进行用户的状态更新
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean doUpdateSubmit(Take vo) throws Exception ;
	/**
	 * 查询出一个雇员的所有领取记录信息
	 * @param eid
	 * @param currentPage
	 * @param lineSize
	 * @return
	 * @throws Exception
	 */
	public List<Take> findAllByEmp(Integer eid,Integer currentPage,Integer lineSize) throws Exception ;
	/**
	 * 统计出一个雇员所领取的商品总和
	 * @param eid
	 * @return
	 * @throws Exception
	 */
	public Integer getAllCountByEmp(Integer eid) throws Exception ;
	/**
	 * 实现数据的审核处理操作
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean doUpdateStatus(Take vo) throws Exception ;
	
	public boolean doUpdateStatus(Integer tkid,Integer status,Date rdate) throws Exception ;
}
