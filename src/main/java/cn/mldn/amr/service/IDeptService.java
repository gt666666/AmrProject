package cn.mldn.amr.service;

import java.util.List;

import cn.mldn.amr.vo.Dept;

public interface IDeptService {
	public List<Dept> list(int eid) throws Exception ; 
	/**
	 * 进行指定用户的权限的确认，而后实现修改处理操作
	 * @param eid
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean edit(int eid,Dept vo) throws Exception ;
}
