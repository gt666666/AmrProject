package cn.mldn.amr.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import cn.mldn.amr.dao.IDetailsDAO;
import cn.mldn.amr.dao.abs.AbstractDAO;
import cn.mldn.amr.vo.Details;

@Component
public class DetailsDAOImpl extends AbstractDAO implements IDetailsDAO {

	@Override
	public boolean doCreate(Details vo) throws Exception {
		return super.getSession().insert(
				"cn.mldn.amr.mapping.DetailsNS.doCreate", vo) > 0;
	}

	@Override
	public boolean doUpdate(Details vo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Details findById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Details> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Details> findAllSplit(String column, String keyWord,
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
	public List<Details> findAllPrebuy(Integer eid) throws Exception {
		return super.getSession().selectList(
				"cn.mldn.amr.mapping.DetailsNS.findAllPrebuy", eid);
	}

	@Override
	public boolean doUpdateAmount(Details vo) throws Exception {
		return super.getSession().update(
				"cn.mldn.amr.mapping.DetailsNS.doUpdateAmount", vo) > 0;
	}

	@Override
	public boolean doRemoveByAmount(Set<Integer> ids) throws Exception {
		return super.getSession().delete(
				"cn.mldn.amr.mapping.DetailsNS.doRemoveBatchByAmount",
				ids.toArray()) > 0;
	}

	@Override
	public List<Details> findAllByPhoto(Set<Integer> ids) throws Exception {
		return super.getSession().selectList(
				"cn.mldn.amr.mapping.DetailsNS.findAllByPhoto", ids.toArray());
	}

	@Override
	public Details findByIdAndPrebuy(Integer eid, Integer id) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pdid", id);
		map.put("eid", eid);
		return super.getSession().selectOne(
				"cn.mldn.amr.mapping.DetailsNS.findByIdAndPrebuy", map);
	}

	@Override
	public boolean doUpdatePrebuy(Details vo) throws Exception {
		return super.getSession().update(
				"cn.mldn.amr.mapping.DetailsNS.doUpdatePrebuy", vo) > 0;
	}

	@Override
	public boolean doUpdateByPurchase(Integer pid, Integer eid)
			throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pid", pid);
		map.put("eid", eid);
		return super.getSession().update(
				"cn.mldn.amr.mapping.DetailsNS.doUpdateByPurchase", map) > 0;
	}

	@Override
	public List<Details> findAllByPruchase(Integer pid) throws Exception {
		return super.getSession().selectList(
				"cn.mldn.amr.mapping.DetailsNS.findAllByPruchase", pid);
	}

	@Override
	public Details findByDetailsExists(Integer eid, Integer rid)
			throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("eid", eid);
		map.put("rid", rid);
		return super.getSession().selectOne(
				"cn.mldn.amr.mapping.DetailsNS.findByDetailsExists", map);
	}

	@Override
	public boolean doUpdateAppendAmount(int did) throws Exception {
		return super.getSession().update(
				"cn.mldn.amr.mapping.DetailsNS.doUpdateAppendAmount", did) > 0;
	}

}
