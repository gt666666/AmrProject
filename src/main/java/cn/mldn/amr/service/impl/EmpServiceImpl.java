package cn.mldn.amr.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.amr.dao.IActionDAO;
import cn.mldn.amr.dao.IDeptDAO;
import cn.mldn.amr.dao.IEmpDAO;
import cn.mldn.amr.dao.IGroupsDAO;
import cn.mldn.amr.dao.ILevelDAO;
import cn.mldn.amr.service.IEmpService;
import cn.mldn.amr.service.abs.AbstractService;
import cn.mldn.amr.vo.Dept;
import cn.mldn.amr.vo.Emp;
import cn.mldn.amr.vo.Groups;
import cn.mldn.amr.vo.Level;

@Service
public class EmpServiceImpl extends AbstractService implements IEmpService {
	
	@Resource
	private IEmpDAO empDAO ;
	@Resource
	private ILevelDAO levelDAO;
	@Resource
	private IDeptDAO deptDAO ;
	@Resource
	private IGroupsDAO groupsDAO ;
	@Resource 
	private IActionDAO actionDAO ;
	@Override
	public boolean login(Emp vo) throws Exception {
		if (this.empDAO.findLogin(vo)) {
			// 取得所有对应的权限组数据
			List<Groups> allGups = this.groupsDAO.findAllByDept(vo.getDept().getDid()) ;
			Iterator<Groups> iter = allGups.iterator() ;
			while (iter.hasNext()) {
				Groups g = iter.next() ;
				g.setAllActions(this.actionDAO.findAllByGroups(g.getGid())); 
			}
			vo.getDept().setAllGroups(allGups); 
			return true ;
		}
		return false ; 
	}
	@Override
	public Map<String, Object> addPre() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allLevels", this.levelDAO.findAll());
		map.put("allDepts", this.deptDAO.findAllBySflag(0)); 
		return map;
	}
	@Override
	public boolean add(Emp vo, int eid) throws Exception {
		if (!super.checkAuth(eid, 12)) { // 没有此权限
			return false;
		}
		if (vo.getDept().getDid().equals(1)) {	// 管理部门
			return false ; 
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
		vo.setAflag(0); // 普通的管理员编号就是2
		return this.empDAO.doCreate(vo);
	}
	@Override
	public Map<String, Object> editPre(int eid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allLevels", this.levelDAO.findAll());
		map.put("allDepts", this.deptDAO.findAllBySflag(0));
		map.put("emp", this.empDAO.findById(eid)) ;
		return map;
	}
	@Override
	public boolean edit(Emp vo, int eid) throws Exception {
		if (!super.checkAuth(eid, 15)) { // 没有此权限
			return false;
		}
		if (vo.getDept().getDid().equals(1)) {	// 管理部门
			return false ; 
		}
		// 验证工资等级
		Level lev = this.levelDAO.findById(vo.getLevel().getLid());
		if (vo.getSalary() > lev.getHisal() || vo.getSalary() < lev.getLosal()) {
			return false;
		}
		return this.empDAO.doUpdate(vo); 
	}
	@Override
	public Map<String, Object> list(int eid,String column, String keyWord,
			int currentPage, int lineSize) throws Exception {
		if (!super.checkAuth(eid, 13)) {
			return null ;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allEmps", this.empDAO.findAllEmp(column, keyWord,
				currentPage, lineSize));
		map.put("empCount", this.empDAO.getAllEmpCount(column, keyWord));
		return map; 
	} 

}
