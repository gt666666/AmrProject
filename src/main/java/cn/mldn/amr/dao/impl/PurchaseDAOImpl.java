package cn.mldn.amr.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import cn.mldn.amr.dao.IPurchaseDAO;
import cn.mldn.amr.dao.abs.AbstractDAO;
import cn.mldn.amr.vo.Purchase;

@Component
public class PurchaseDAOImpl extends AbstractDAO implements IPurchaseDAO {

	@Override
	public boolean doCreate(Purchase vo) throws Exception {
		return super.getSession().insert(
				"cn.mldn.amr.mapping.PurchaseNS.doCreate", vo) > 0;
	}

	@Override
	public boolean doUpdate(Purchase vo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Purchase findById(Integer id) throws Exception {
		return super.getSession().selectOne(
				"cn.mldn.amr.mapping.PurchaseNS.findById", id);
	}

	@Override
	public List<Purchase> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Purchase> findAllSplit(String column, String keyWord,
			Integer currentPage, Integer lineSize) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAllCount(String column, String keyWord) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Purchase> findAllByEmp(Integer eid, Integer currentPage,
			Integer lineSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eid", eid);
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);
		return super.getSession().selectList(
				"cn.mldn.amr.mapping.PurchaseNS.findAllByEmp", map);
	}

	@Override
	public Integer getAllCountByEmp(Integer eid) throws Exception {
		return super.getSession().selectOne(
				"cn.mldn.amr.mapping.PurchaseNS.getAllCountByEmp", eid);
	}

	@Override
	public Purchase findByIdAndEmp(Integer pid, Integer eid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eid", eid);
		map.put("pid", pid);
		return super.getSession().selectOne(
				"cn.mldn.amr.mapping.PurchaseNS.findByIdAndEmp", map);
	}

	@Override
	public List<Purchase> findAllSimpleSplit(Integer currentPage,
			Integer lineSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);
		return super.getSession().selectList(
				"cn.mldn.amr.mapping.PurchaseNS.findAllSimpleSplit", map);
	}

	@Override
	public Integer getAllCountSimple() throws Exception {
		return super.getSession().selectOne(
				"cn.mldn.amr.mapping.PurchaseNS.getAllCountSimple");
	}

	@Override
	public boolean doUpdateStatus(Integer pid, Integer status,Integer eid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		map.put("status", status);
		map.put("meid", eid); 
		return super.getSession().update(
				"cn.mldn.amr.mapping.PurchaseNS.doUpdateStatus", map) > 0;
	}

}
