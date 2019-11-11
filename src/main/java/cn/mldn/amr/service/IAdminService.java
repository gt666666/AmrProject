package cn.mldn.amr.service;

import java.util.Map;

import cn.mldn.amr.vo.Emp;

public interface IAdminService {
	/**
	 * 此处实现管理员数据的增加处理操作
	 * @param vo 管理员数据的VO对象，本质上就是Emp类对象，增加的管理员标记都为2
	 * @param eid 操作的管理员编号
	 * @return 增加成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean add(Emp vo, Integer eid) throws Exception;
	/**
	 * 实现数据增加前的查询处理
	 * @return 包含如下数据：<br>
	 * <li>key = allLevels、value = ILevelDAO.findAll()
	 * @throws Exception
	 */
	public Map<String,Object> addPre() throws Exception ;
	/**
	 * 检测雇员编号是否存在
	 * @param eid 雇员编号
	 * @return 存在返回true，否则返回false
	 * @throws Exception
	 */
	public boolean checkEid(int eid) throws Exception ;
	/**
	 * 实现所有管理员数据的列表显示处理操作
	 * @param column
	 * @param keyWord
	 * @param currentPage
	 * @param lineSize
	 * @return 返回集合包括如下内容：<br>
	 * <li>key = allEmps、value = IAdminDAO.findAllAdmin()</li>
	 * <li>key = empCount、value = IAdminDAO.getAllAdminCount()</li>
	 * @throws Exception
	 */ 
	public Map<String, Object> list(int eid,String column, String keyWord,
			int currentPage, int lineSize) throws Exception;
}
