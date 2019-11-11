package cn.mldn.amr.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.mldn.amr.dao.IResDAO;
import cn.mldn.amr.service.IResService;
import cn.mldn.amr.service.abs.AbstractService;

@Component
public class ResServiceImpl extends AbstractService implements IResService {
	@Resource
	private IResDAO resDAO;

	@Override
	public Map<String, Object> list(String column, String keyWord,
			int currentPage, int lineSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allRess", this.resDAO.findAllSplit(column, keyWord,
				currentPage, lineSize));
		map.put("resCount", this.resDAO.getAllCount(column, keyWord));
		return map;
	}

}
