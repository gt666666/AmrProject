package cn.mldn.amr.service;

import java.util.Map;

import cn.mldn.amr.vo.Emp;

public interface IEmpService {
	/**
	 * 此处实现用户的登录，必须保证密码经过了MD5加密处理，本操作要执行如下调用：<br>
	 * <li>执行IEmpDAO.findLogin()方法进行登录判断。</li>
	 * @param vo 
	 * @return 登录成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean login(Emp vo) throws Exception;
	/**
	 * 雇员增加前的查询处理操作
	 * @return 返回的内容包含有如下信息：<br>
	 * <li>key = allDepts、value = IDeptDAO.findAllBySflag()</li>
	 * <li>key = allLevels、value = ILevelDAO.findAll()</li>
	 * @throws Exception
	 */
	public Map<String,Object> addPre() throws Exception ;
	/**
	 * 雇员增加处理操作，需要进行一系列的验证，包含如下的几个操作：<br>
	 * <li>需要验证当前雇员的权限是否符合要求；</li>
	 * <li>验证工资范围是否符合要求</li>
	 * <li>需要配置好雇员中的heid数据</li>
	 * <li>所有雇员的aflag内容是0</li>
	 * @param vo 
	 * @param eid 增加雇员的雇员编号
	 * @return 增加成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean add(Emp vo,int eid) throws Exception ;
	/**
	 * 实现所有管理员数据的列表显示处理操作
	 * @param column
	 * @param keyWord
	 * @param currentPage
	 * @param lineSize
	 * @return 返回集合包括如下内容：<br>
	 * <li>key = allEmps、value = IEmpDAO.findAllEmp()</li>
	 * <li>key = empCount、value = IEmpDAO.getAllEmpCount()</li>
	 * @throws Exception 
	 */ 
	public Map<String, Object> list(int eid,String column, String keyWord,
			int currentPage, int lineSize) throws Exception;
	
	/**
	 * 雇员增加前的查询处理操作
	 * @return 返回的内容包含有如下信息：<br>
	 * <li>key = allDepts、value = IDeptDAO.findAllBySflag()</li>
	 * <li>key = allLevels、value = ILevelDAO.findAll()</li>
	 * <li>key = emp、value = IEmpDAO.findById()</li>
	 * @throws Exception
	 */
	public Map<String,Object> editPre(int eid) throws Exception ;
	/**
	 * 编辑雇员的信息
	 * @param vo
	 * @param eid
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Emp vo,int eid) throws Exception ;
}
