package cn.mldn.amr.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mldn.amr.vo.Details;

public interface IDetailsService {
	/**
	 * 购买商品清单添加前的数据查询，需要验证权限
	 * @param eid
	 * @return 返回的内容包含如下几种：<br>
	 * <li>key = allTypes、value = ITypeDAO.findAll()</li>
	 * @throws Exception
	 */
	public Map<String,Object> addPre(int eid) throws Exception ;
	/**
	 * 增加购买商品
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean add(Details vo,int eid) throws Exception ;
	/**
	 * 实现已有办公用品的追加购买处理操作
	 * @param eid 雇员编号
 	 * @param rid 办公用品编号
	 * @return
	 * @throws Exception
	 */
	public boolean addAppend(int eid,int rid) throws Exception ;
	
	/**
	 * 购买商品清单添加前的数据查询，需要验证权限
	 * @param eid
	 * @return 返回的内容包含如下几种：<br>
	 * <li>key = allTypes、value = ITypeDAO.findAll()</li>
	 * <li>key = allSubtypes、value = ISubtypeDAO.findAllByType()</li>
	 * <li>key = details、value = IDetailsDAO.findByIdAndPrebuy()</li>
	 * @throws Exception
	 */
	public Map<String,Object> editPre(int eid,int did) throws Exception ;
	/**
	 * 实现数据的更新处理操作。
	 * @param eid
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean edit(int eid,Details vo) throws Exception ;
	/**
	 * 进行指定用户的所有待购商品的列表显示
	 * @param eid
	 * @return
	 * @throws Exception
	 */
	public List<Details> listPrebuy(int eid) throws Exception ;
	/**
	 * 实现商品待购买数量的编辑处理操作
	 * @param eid 当前用户的ID，可以进行权限验证使用
	 * @param dinfo 包含有商品的编号以及修改后的全部数量，key = did、value = amount；
	 * @param ids 所有购买数量为0的商品信息
	 * @return 此时会返回如下的几个数据内容：<br>
	 * <li>key = flag，描述整体操作的成功或者是失败处理！</li>
	 * <li>key = allDetails、value = IDetailsDAO.findAllByPhoto()，取得所有要删除的商品信息</li>
	 * @throws Exception 
	 */
	public Map<String,Object> editAmount(int eid, Map<Integer, Integer> dinfo,
			Set<Integer> ids) throws Exception;
	/**
	 * 从待购商品中删除掉要购买的商品信息
	 * @param eid 雇员编号
	 * @param ids 要删除的待购商品编号
	 * @return 此时会返回如下的几个数据内容：<br>
	 * <li>key = flag，描述整体操作的成功或者是失败处理！</li>
	 * <li>key = allDetails、value = IDetailsDAO.findAllByPhoto()，取得所有要删除的商品信息</li>
	 * @throws Exception
	 */
	public Map<String,Object> rm(int eid,Set<Integer> ids) throws Exception ; 
}
