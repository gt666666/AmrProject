package cn.mldn.amr.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;
import cn.mldn.amr.dao.IEmpDAO;
import cn.mldn.amr.dao.abs.AbstractDAO;
import cn.mldn.amr.vo.Emp;
@Component
public class EmpDAOImpl extends AbstractDAO implements IEmpDAO {

	@Override
	public boolean doCreate(Emp vo) throws Exception {
		return super.getSession().insert("cn.mldn.amr.mapping.EmpNS.doCreate",
				vo) > 0;
	}

	@Override
	public boolean doUpdate(Emp vo) throws Exception {
		return super.getSession().update("cn.mldn.amr.mapping.EmpNS.doUpdate", vo) > 0 ;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Emp findById(Integer id) throws Exception {
		return super.getSession().selectOne(
				"cn.mldn.amr.mapping.EmpNS.findById", id);
	}

	@Override
	public List<Emp> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> findAllSplit(String column, String keyWord,
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
	public boolean findLogin(Emp vo) throws Exception {
		Emp rvo = super.getSession().selectOne(
				"cn.mldn.amr.mapping.EmpNS.findLogin", vo);
		if (rvo != null) { // 现在已经得到了所需要的数据记录
			vo.setName(rvo.getName()); // 取得用户的姓名
			vo.setPhoto(rvo.getPhoto()); // 取得照片
			vo.setAflag(rvo.getAflag()); // 取得管理员标记
			vo.setDept(rvo.getDept()); // 取出did数据返回
			vo.setLevel(rvo.getLevel());
			return true;
		}
		return false;
	}

	@Override
	public List<Emp> findAllAdmin(String column, String keyWord,
			Integer currentPage, Integer lineSize) throws Exception {
		return super.listHandle(column, keyWord, currentPage, lineSize,
				"cn.mldn.amr.mapping.EmpNS.findAllAdmin");
	}

	@Override
	public Integer getAllAdminCount(String column, String keyWord)
			throws Exception {
		return super.countHandle(column, keyWord,
				"cn.mldn.amr.mapping.EmpNS.getAllAdminCount");
	} 
	@Override
	public List<Emp> findAllEmp(String column, String keyWord,
			Integer currentPage, Integer lineSize) throws Exception {
		return super.listHandle(column, keyWord, currentPage, lineSize,
				"cn.mldn.amr.mapping.EmpNS.findAllEmp");
	}

	@Override
	public Integer getAllEmpCount(String column, String keyWord)
			throws Exception {
		return super.countHandle(column, keyWord,
				"cn.mldn.amr.mapping.EmpNS.getAllEmpCount");
	} 
}
