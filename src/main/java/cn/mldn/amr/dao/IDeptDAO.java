package cn.mldn.amr.dao;

import java.util.List;

import cn.mldn.amr.vo.Dept;

public interface IDeptDAO extends IDAO<Integer, Dept> {
	/**
	 * 根据sflag的内容查询所有的部门数据   
	 * @param sflag
	 * @return
	 * @throws Exception
	 */
	public List<Dept> findAllBySflag(Integer sflag) throws Exception ;
}
