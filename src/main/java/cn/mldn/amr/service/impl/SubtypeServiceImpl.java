package cn.mldn.amr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.amr.dao.ISubtypeDAO;
import cn.mldn.amr.service.ISubtypeService;
import cn.mldn.amr.service.abs.AbstractService;
import cn.mldn.amr.vo.Subtype;

@Service
public class SubtypeServiceImpl extends AbstractService implements ISubtypeService {
	@Resource
	private ISubtypeDAO subtypeDAO;

	@Override
	public List<Subtype> list(int tid) throws Exception {
		return this.subtypeDAO.findAllByType(tid);
	}

	@Override
	public boolean edit(Subtype vo,int eid) throws Exception {
		if (!super.checkAuth(eid, 10)) { 
			return false ;
		} 
		return this.subtypeDAO.doUpdate(vo);
	}

}
