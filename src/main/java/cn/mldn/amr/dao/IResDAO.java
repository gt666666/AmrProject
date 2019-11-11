package cn.mldn.amr.dao;

import java.util.List;
import java.util.Set;

import cn.mldn.amr.vo.Res;

public interface IResDAO extends IDAO<Integer, Res> {
	/**
	 * 实现已有商品数量的更新处理操作
	 * @param rid
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public boolean doUpdateAmount(Integer rid,Integer amount) throws Exception ;
	/**
	 * 根据指定的编号范围查询出所有的商品信息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<Res> findAllByRids(Set<Integer> ids) throws Exception ;
}
