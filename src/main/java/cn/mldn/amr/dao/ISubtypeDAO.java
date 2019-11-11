package cn.mldn.amr.dao;

import java.util.List;

import cn.mldn.amr.vo.Subtype;

public interface ISubtypeDAO extends IDAO<Integer, Subtype> {
	/**
	 * 根据type列出所有的subtype数据
	 * @param tid
	 * @return
	 * @throws Exception
	 */
	public List<Subtype> findAllByType(Integer tid) throws Exception ;
}
 