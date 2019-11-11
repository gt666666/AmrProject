package cn.mldn.amr.service;

import java.util.List;

import cn.mldn.amr.vo.Type;

public interface ITypeService {
	/**
	 * 商品的分类列表
	 * @return
	 * @throws Exception
	 */
	public List<Type> list() throws Exception ;
	/**
	 * 修改商品分类信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Type vo,int eid) throws Exception ;
}
