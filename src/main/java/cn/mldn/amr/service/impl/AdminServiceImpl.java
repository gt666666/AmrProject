package cn.mldn.amr.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.amr.dao.IEmpDAO;
import cn.mldn.amr.dao.ILevelDAO;
import cn.mldn.amr.service.IAdminService;
import cn.mldn.amr.service.abs.AbstractService;
import cn.mldn.amr.vo.Dept;
import cn.mldn.amr.vo.Emp;
import cn.mldn.amr.vo.Level;

@Service
public class AdminServiceImpl extends AbstractService implements IAdminService {
	@Resource
	private IEmpDAO empDAO;
	@Resource
	private ILevelDAO levelDAO;

	@Override
	public boolean add(Emp vo, Integer eid) throws Exception {
		if (!super.checkAuth(eid, 2)) { // 没有此权限
			return false;
		}
		// 要判断增加的雇员编号不存在
		if (this.empDAO.findById(vo.getEid()) != null) { // 该雇员编号已存在
			return false;
		}
		// 验证工资等级
		Level lev = this.levelDAO.findById(vo.getLevel().getLid());
		if (vo.getSalary() > lev.getHisal() || vo.getSalary() < lev.getLosal()) {
			return false;
		}
		vo.setHeid(eid); // 设置增加该雇员的管理员编号
		vo.setAflag(2); // 普通的管理员编号就是2
		vo.setDept(new Dept());
		vo.getDept().setDid(1);
		return this.empDAO.doCreate(vo);
	}

	@Override
	public Map<String, Object> addPre() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allLevels", this.levelDAO.findAll());
		return map;
	}

	@Override
	public boolean checkEid(int eid) throws Exception {
		return this.empDAO.findById(eid) != null;
	}

	@Override
	public Map<String, Object> list(int eid,String column, String keyWord,
			int currentPage, int lineSize) throws Exception {
		if (!super.checkAuth(eid, 3)) {
			return null ;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allEmps", this.empDAO.findAllAdmin(column, keyWord,
				currentPage, lineSize));
		map.put("empCount", this.empDAO.getAllAdminCount(column, keyWord));
		return map;
	}

}
