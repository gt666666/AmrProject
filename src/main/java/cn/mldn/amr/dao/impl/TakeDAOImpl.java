package cn.mldn.amr.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import cn.mldn.amr.dao.ITakeDAO;
import cn.mldn.amr.dao.abs.AbstractDAO;
import cn.mldn.amr.vo.Take;

@Component
public class TakeDAOImpl extends AbstractDAO implements ITakeDAO {

	@Override
	public boolean doCreate(Take vo) throws Exception {
		return super.getSession().insert("cn.mldn.amr.mapping.TakeNS.doCreate",
				vo) > 0;
	}

	@Override
	public boolean doUpdate(Take vo) throws Exception {
		return false;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Take findById(Integer id) throws Exception {
		return super.getSession().selectOne(
				"cn.mldn.amr.mapping.TakeNS.findById", id); 
	}

	@Override
	public List<Take> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Take> findAllSplit(String column, String keyWord,
			Integer currentPage, Integer lineSize) throws Exception {
		return super.listHandle(column, keyWord, currentPage, lineSize,
				"cn.mldn.amr.mapping.TakeNS.findAllSplit");
	}

	@Override
	public Integer getAllCount(String column, String keyWord) throws Exception {
		return super.countHandle(column, keyWord,
				"cn.mldn.amr.mapping.TakeNS.getAllCount");
	}

	@Override
	public Integer findByResAndEmp(Integer eid, Integer rid) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("eid", eid);
		map.put("rid", rid);
		return super.getSession().selectOne(
				"cn.mldn.amr.mapping.TakeNS.findByResAndEmp", map);
	}

	@Override
	public boolean doUpdateAmount(Integer tkid, Integer amount)
			throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("tkid", tkid);
		map.put("amount", amount);
		return super.getSession().update(
				"cn.mldn.amr.mapping.TakeNS.doUpdateAmount", map) > 0;
	}

	@Override
	public List<Take> findAllByEmpUnGet(Integer eid) throws Exception {
		return super.getSession().selectList(
				"cn.mldn.amr.mapping.TakeNS.findAllByEmpUnGet", eid);
	}

	@Override
	public boolean doUpdateAmountByEmp(Integer tkid, Integer eid, Integer amount)
			throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("tkid", tkid);
		map.put("geid", eid);
		map.put("amount", amount);
		return super.getSession().update(
				"cn.mldn.amr.mapping.TakeNS.doUpdateAmountByEmp", map) > 0;
	}

	@Override
	public boolean doRemoveByEmp(Set<Integer> ids) throws Exception {
		return super.getSession().delete(
				"cn.mldn.amr.mapping.TakeNS.doRemoveByEmp", ids.toArray()) > 0;
	}

	@Override
	public boolean doUpdateSubmit(Take vo) throws Exception {
		return super.getSession().update(
				"cn.mldn.amr.mapping.TakeNS.doUpdateSubmit", vo) > 0;
	}

	@Override
	public List<Take> findAllByEmp(Integer eid, Integer currentPage,
			Integer lineSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("geid", eid);
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);
		return super.getSession().selectList(
				"cn.mldn.amr.mapping.TakeNS.findAllByEmp", map);
	}

	@Override
	public Integer getAllCountByEmp(Integer eid) throws Exception {
		return super.getSession().selectOne(
				"cn.mldn.amr.mapping.TakeNS.getAllCountByEmp", eid);
	}

	@Override
	public boolean doUpdateStatus(Take vo) throws Exception {
		return super.getSession().update(
				"cn.mldn.amr.mapping.TakeNS.doUpdateStatus", vo) > 0;
	}

	@Override
	public boolean doUpdateStatus(Integer tkid, Integer status,Date rdate)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("tkid", tkid) ;
		map.put("status", status) ;
		map.put("rdate", rdate) ; 
		return super.getSession().update("cn.mldn.amr.mapping.TakeNS.doUpdateStatus2",map) > 0;
	} 

}
