package cn.mldn.amr.service.abs;

import javax.annotation.Resource;

import cn.mldn.amr.dao.IActionDAO;
import cn.mldn.amr.dao.IEmpDAO;
import cn.mldn.amr.vo.Emp;

public abstract class AbstractService {
	@Resource
	private IEmpDAO empDAO ;
	@Resource
	private IActionDAO actionDAO ;
	/**
	 * 检测当前的雇员编号是否具备有指定的权限处理
	 * @param eid 雇员编号
	 * @param actid 权限ID
	 * @return 如果具备有此权限则返回true，否则返回false
	 */
	public boolean checkAuth(int eid,int ... actid) throws Exception {
		Emp emp = this.empDAO.findById(eid) ;	// 根据雇员编号查询出指定的雇员信息
		if (emp.getAflag().equals(1) || emp.getAflag().equals(2)) {	// 如果现在是管理员
			return true ;
		}
		// 需要根据部门编号查询指定的权限数据，如果可以查询出来就表示具备此权限
		for (int x : actid) {
			if (this.actionDAO.findByIdAndDept(emp.getDept().getDid(), x) != null) {
				return true ;
			}
		}
		return false ;
	}
	/**
	 * 检测当前用户的用户名、权限编号、级别
	 * @param eid
	 * @param actid
	 * @param lid
	 * @return
	 * @throws Exception
	 */
	public boolean checkAuth(int eid,int actid,int lid) throws Exception {
		Emp emp = this.empDAO.findById(eid) ;	// 根据雇员编号查询出指定的雇员信息
		if (emp.getAflag().equals(1) || emp.getAflag().equals(2)) {	// 如果现在是管理员
			return true ;
		}
		// 需要根据部门编号查询指定的权限数据，如果可以查询出来就表示具备此权限 
		if(emp.getLevel().getLid().equals(lid)) {
			return this.actionDAO.findByIdAndDept(emp.getDept().getDid(), actid) != null ;
		}
		return false ;
	}
}
