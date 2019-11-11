package cn.mldn.amr.dao;

import java.util.List;

import cn.mldn.amr.vo.Emp;

public interface IEmpDAO extends IDAO<Integer, Emp> {
	/**
	 * 登录之后要求取出真实姓名以及用户的照片
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean findLogin(Emp vo) throws Exception;
	/**
	 * 进行所有管理员数据的查询
	 * @param column
	 * @param keyWord
	 * @param currentPage
	 * @param lineSize
	 * @return
	 * @throws Exception
	 */
	public List<Emp> findAllAdmin(String column, String keyWord,
			Integer currentPage, Integer lineSize) throws Exception;
	/**
	 * 统计所有管理员的个数 
	 * @param column
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public Integer getAllAdminCount(String column, String keyWord)
			throws Exception;
	/**
	 * 查询所有的雇员信息
	 * @param column
	 * @param keyWord
	 * @param currentPage
	 * @param lineSize
	 * @return
	 * @throws Exception
	 */
	public List<Emp> findAllEmp(String column, String keyWord,
			Integer currentPage, Integer lineSize) throws Exception;
	/**
	 * 统计出所有雇员的信息
	 * @param column
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public Integer getAllEmpCount(String column, String keyWord)
			throws Exception;
}
