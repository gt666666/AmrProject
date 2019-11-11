package cn.mldn.amr.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import cn.mldn.amr.dao.IActionDAO;
import cn.mldn.amr.dao.abs.AbstractDAO;
import cn.mldn.amr.vo.Action;

@Component
public class ActionImpl extends AbstractDAO implements IActionDAO {

	@Override

	public boolean doCreate(Action vo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doUpdate(Action vo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Action findById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Action> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Action> findAllSplit(String column, String keyWord,
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
	public List<Action> findAllByGroups(Integer gid) throws Exception {
		return super.getSession().selectList(
				"cn.mldn.amr.mapping.ActionNS.findAllByGroups", gid);
	}

	@Override
	public Action findByIdAndDept(Integer did, Integer actid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("did", did);
		map.put("actid", actid);
		return super.getSession().selectOne(
				"cn.mldn.amr.mapping.ActionNS.findByIdAndDept", map); 
	} 
}
