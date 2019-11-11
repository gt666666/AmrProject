package cn.mldn.amr.dao;

import java.util.List;

import cn.mldn.amr.vo.Action;

public interface IActionDAO extends IDAO<Integer, Action> {
	/**
	 * 取得指定权限组对应的所有权限数据
	 * @param gid 权限组编号
	 * @return 该权限组对应的所有权限信息 
	 * @throws Exception
	 */
	public List<Action> findAllByGroups(Integer gid) throws Exception ;
	/**
	 * 根据部门与权限查询指定的权限数据 
	 * @param did
	 * @param actid
	 * @return
	 * @throws Exception
	 */
	public Action findByIdAndDept(Integer did,Integer actid) throws Exception ;
}
