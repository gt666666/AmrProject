package cn.mldn.amr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.amr.dao.IDeptDAO;
import cn.mldn.amr.service.IDeptService;
import cn.mldn.amr.service.abs.AbstractService;
import cn.mldn.amr.vo.Dept;

@Service
public class DeptServiceImpl extends AbstractService implements IDeptService {
	@Resource
	private IDeptDAO deptDAO;

	@Override
	public List<Dept> list(int eid) throws Exception {
		if (!super.checkAuth(eid, 4)) {
			return null;
		}
		return this.deptDAO.findAll(); 
	}

	@Override
	public boolean edit(int eid, Dept vo) throws Exception {
		if (!super.checkAuth(eid, 7)) {
			return false;
		}
		return this.deptDAO.doUpdate(vo) ;
	} 

}
