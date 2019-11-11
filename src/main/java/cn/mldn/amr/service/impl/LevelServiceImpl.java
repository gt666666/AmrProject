package cn.mldn.amr.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.amr.dao.ILevelDAO;
import cn.mldn.amr.service.ILevelService;
import cn.mldn.amr.vo.Level;

@Service
public class LevelServiceImpl implements ILevelService {
	@Resource
	private ILevelDAO levelDAO;

	@Override
	public boolean checkSalary(double salary, int lid) throws Exception {
		Level lel = this.levelDAO.findById(lid);
		if (salary >= lel.getLosal() && salary <= lel.getHisal()) {
			return true;
		}
		return false;
	}

}
