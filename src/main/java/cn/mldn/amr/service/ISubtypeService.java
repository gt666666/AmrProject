package cn.mldn.amr.service;

import java.util.List;

import cn.mldn.amr.vo.Subtype;

public interface ISubtypeService {
	/**
	 * 商品的分类列表
	 * @return
	 * @throws Exception
	 */
	public List<Subtype> list(int tid) throws Exception ;
	/**
	 * 修改商品分类信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Subtype vo,int eid) throws Exception ;  
}
