package cn.mldn.amr.service;

import java.util.List;

import cn.mldn.amr.vo.Groups;

public interface IGroupsService {
	/**
	 * 查看一个部门对应的所有权限数据
	 * @param eid 
	 * @param did
	 * @return
	 * @throws Exception
	 */
	public List<Groups> listByDept(int eid, int did) throws Exception;
}
