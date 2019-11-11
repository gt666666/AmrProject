package cn.mldn.amr.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.amr.dao.IResDAO;
import cn.mldn.amr.dao.ITakeDAO;
import cn.mldn.amr.service.ITakeService;
import cn.mldn.amr.service.abs.AbstractService;
import cn.mldn.amr.vo.Res;
import cn.mldn.amr.vo.Take;

@Service
public class TakeServiceImpl extends AbstractService implements ITakeService {
	@Resource
	private ITakeDAO takeDAO;
	@Resource
	private IResDAO resDAO;

	@Override
	public boolean add(int eid, Take vo) throws Exception {
		if (!super.checkAuth(eid, 22, 34, 40)) {
			return false;
		}
		// 取得当前的领取记录中的id
		Integer tkid = this.takeDAO.findByResAndEmp(eid, vo.getRes().getRid());
		if (tkid == null || tkid.equals(0)) { // 此时没有添加过
			vo.setGeid(eid); // 设置领取者
			vo.setAmount(1);
			return this.takeDAO.doCreate(vo);
		} else { // 如果存在了
			return this.takeDAO.doUpdateAmount(tkid, 1);
		}
	}

	@Override
	public Map<String, Object> listUnget(int eid) throws Exception {
		if (!super.checkAuth(eid, 22, 34, 40)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 1、取得所有的待领取数据信息
		List<Take> allT = this.takeDAO.findAllByEmpUnGet(eid);
		if (allT.size() > 0) {
			// 2、对集合进行处理
			Map<Integer, Take> allTakes = new HashMap<Integer, Take>();
			Iterator<Take> iter = allT.iterator();
			while (iter.hasNext()) {
				Take t = iter.next();
				allTakes.put(t.getRes().getRid(), t);
			}
			// 3、查询所有指定id的商品信息
			map.put("allTakes", allTakes);
			map.put("allRess", this.resDAO.findAllByRids(allTakes.keySet()));
		}
		return map;
	}

	@Override
	public boolean edit(int eid, Map<Integer, Integer> map) throws Exception {
		if (map.size() == 0) {
			return false;
		}
		if (!super.checkAuth(eid, 22, 34, 40)) {
			return false;
		}
		Set<Integer> tkids = new HashSet<Integer>();
		Iterator<Map.Entry<Integer, Integer>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Integer> me = iter.next();
			Integer tkid = me.getKey();
			Integer amount = me.getValue();
			if (amount > 0) {
				this.takeDAO.doUpdateAmountByEmp(tkid, eid, amount);
			} else {
				tkids.add(tkid);
			}
		}
		if (tkids.size() > 0) { // 有0的数据
			this.takeDAO.doRemoveByEmp(tkids);
		}
		return false;
	}

	@Override
	public boolean rm(int eid, Set<Integer> ids) throws Exception {
		if (ids.size() == 0) {
			return false;
		}
		if (!super.checkAuth(eid, 22, 34, 40)) {
			return false;
		}
		return this.takeDAO.doRemoveByEmp(ids);
	}

	@Override
	public boolean editSubmit(int eid) throws Exception {
		if (!super.checkAuth(eid, 22, 34, 40)) {
			return false;
		}
		// 1、 需要知道那些用品是等待领取的
		List<Take> all = this.takeDAO.findAllByEmpUnGet(eid);
		Iterator<Take> iter = all.iterator();
		while (iter.hasNext()) {
			Take vo = iter.next();
			vo.setStatus(0);
			this.takeDAO.doUpdateSubmit(vo);
		}
		return true;
	}

	@Override
	public Map<String, Object> listByEmp(int eid, int currentPage, int lineSize)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Take> allTakes = this.takeDAO.findAllByEmp(eid, currentPage,
				lineSize);
		Set<Integer> rids = new HashSet<Integer>();
		Iterator<Take> iter = allTakes.iterator();
		while (iter.hasNext()) {
			Take take = iter.next();
			rids.add(take.getRes().getRid());
		}
		List<Res> allRes = this.resDAO.findAllByRids(rids);
		Map<Integer, Res> resMap = new HashMap<Integer, Res>();
		Iterator<Res> iterRes = allRes.iterator();
		while (iterRes.hasNext()) {
			Res res = iterRes.next();
			resMap.put(res.getRid(), res);
		}
		map.put("allTakes", allTakes);
		map.put("resMap", resMap);
		map.put("takeCount", this.takeDAO.getAllCountByEmp(eid));
		// 这个时候只是领取记录数据，但是不包含对应的商品数据
		return map;
	}

	@Override
	public Map<String, Object> list(int eid, String column, String keyWord,
			int currentPage, int lineSize) throws Exception {
		if (!super.checkAuth(eid, 28)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Take> allTakes = this.takeDAO.findAllSplit(column, keyWord,
				currentPage, lineSize);
		Set<Integer> rids = new HashSet<Integer>();
		Iterator<Take> iter = allTakes.iterator();
		while (iter.hasNext()) {
			Take take = iter.next();
			rids.add(take.getRes().getRid());
		}
		List<Res> allRes = this.resDAO.findAllByRids(rids);
		Map<Integer, Res> resMap = new HashMap<Integer, Res>();
		Iterator<Res> iterRes = allRes.iterator();
		while (iterRes.hasNext()) {
			Res res = iterRes.next();
			resMap.put(res.getRid(), res);
		}
		map.put("allTakes", allTakes);
		map.put("resMap", resMap);
		map.put("takeCount", this.takeDAO.getAllCount(column, keyWord));
		// 这个时候只是领取记录数据，但是不包含对应的商品数据
		return map;
	}

	@Override
	public boolean editAudit(int eid, Take vo) throws Exception {
		if (!super.checkAuth(eid, 28)) {
			return false;
		}
		vo.setGdate(new Date());
		if (vo.getStatus().equals(1)) { // 审核通过
			// 1、判断数量够不够
			Take take = this.takeDAO.findById(vo.getTkid()); // 取得具体的申请记录信息
			// 2、取出指定资源的信息
			Res res = this.resDAO.findById(take.getRes().getRid());
			if (res.getAmount() - take.getAmount() < 0) { // 用品不够
				return false;
			}
			if (this.takeDAO.doUpdateStatus(vo)) { // 审核状态更新成功
				return this.resDAO.doUpdateAmount(res.getRid(),
						0 - take.getAmount());
			}
		} else {
			return this.takeDAO.doUpdateStatus(vo);
		}
		return false;
	}

	@Override
	public boolean editRflag(int eid, int tkid) throws Exception {
		if (!super.checkAuth(eid, 22, 34, 40)) {
			return false;
		}
		// 1、取得相应的领取记录信息
		Take take = this.takeDAO.findById(tkid); // 取得具体的申请记录信息
		// 2、归还处理
		return this.takeDAO.doUpdateStatus(tkid, 3, null); // 归还申请
	}

	@Override
	public boolean editRdate(int eid, int tkid) throws Exception {
		if (!super.checkAuth(eid, 28)) {
			return false;
		}
		Take take = this.takeDAO.findById(tkid); // 取得具体的申请记录信息
		// 2、取出指定资源的信息
		Res res = this.resDAO.findById(take.getRes().getRid());
		if (this.takeDAO.doUpdateStatus(tkid, 4, new Date())) {
			return this.resDAO.doUpdateAmount(res.getRid(), take.getAmount());
		}
		return false;
	}

}
