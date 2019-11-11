package cn.mldn.amr.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.amr.dao.IDetailsDAO;
import cn.mldn.amr.dao.IResDAO;
import cn.mldn.amr.dao.ISubtypeDAO;
import cn.mldn.amr.dao.ITypeDAO;
import cn.mldn.amr.service.IDetailsService;
import cn.mldn.amr.service.abs.AbstractService;
import cn.mldn.amr.vo.Details;
import cn.mldn.amr.vo.Emp;
import cn.mldn.amr.vo.Res;

@Service
public class DetailsServiceImpl extends AbstractService implements
		IDetailsService {
	@Resource
	private IDetailsDAO detailsDAO;
	@Resource
	private ITypeDAO typeDAO;
	@Resource
	private ISubtypeDAO subtypeDAO ;
	@Resource
	private IResDAO resDAO ;

	@Override
	public Map<String, Object> addPre(int eid) throws Exception {
		if (!super.checkAuth(eid, 25)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allTypes", this.typeDAO.findAll());
		return map;
	}

	@Override
	public boolean add(Details vo, int eid) throws Exception {
		if (!super.checkAuth(eid, 25)) {
			return false;
		}
		vo.setEmp(new Emp());
		vo.getEmp().setEid(eid);
		vo.setAmount(1);
		return this.detailsDAO.doCreate(vo);
	}

	@Override
	public List<Details> listPrebuy(int eid) throws Exception {
		if (!super.checkAuth(eid, 25)) {
			return null;
		}
		return this.detailsDAO.findAllPrebuy(eid);
	}

	@Override
	public Map<String, Object> editAmount(int eid,
			Map<Integer, Integer> dinfo, Set<Integer> ids) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		if (!super.checkAuth(eid, 25)) {
			flag = false;
		}
		if (flag) {
			// 1、如果要想修改数量，则需要的是Details对象，则必须对Map集合进行迭代输出
			Iterator<Map.Entry<Integer, Integer>> iter = dinfo.entrySet()
					.iterator();
			while (iter.hasNext()) {
				Map.Entry<Integer, Integer> me = iter.next();
				Details vo = new Details();
				vo.setDid(me.getKey());
				vo.setAmount(me.getValue());
				vo.setEmp(new Emp());
				vo.getEmp().setEid(eid);
				if (!this.detailsDAO.doUpdateAmount(vo)) { // 有错误
					flag = false;
				}
			}
			// 2、需要删除全部商品数量为0的信息
			if (flag) {
				if (ids.size() > 0) {
					// 要取得所有删除的图片数据，同时可以针对于雇员信息进行验证处理
					List<Details> allDetails = this.detailsDAO.findAllByPhoto(ids);
					Iterator<Details> iterD = allDetails.iterator();
					while (iterD.hasNext()) {
						Details details = iterD.next();
						if (!details.getEmp().getEid().equals(eid)) { // 要删除的不是自己的
							flag = false;
							break;
						}
					}
					if (flag) {
						flag = this.detailsDAO.doRemoveByAmount(ids);
						if (flag) {
							map.put("allDetails", allDetails) ;
						}
					}
				} 
			}
		}
		map.put("flag", flag) ;
		return map;
	}

	@Override
	public Map<String, Object> rm(int eid, Set<Integer> ids) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		boolean flag = true ;
		if (!super.checkAuth(eid, 25)) {
			flag = false ;
		}
		if (ids.size() == 0) {
			flag = false ;
		}
		if (flag) {
			List<Details> allDetails = this.detailsDAO.findAllByPhoto(ids);
			Iterator<Details> iterD = allDetails.iterator();
			while (iterD.hasNext()) {
				Details details = iterD.next();
				if (!details.getEmp().getEid().equals(eid)) { // 要删除的不是自己的
					flag = false;
					break;
				}
			}
			if (flag) {
				flag = this.detailsDAO.doRemoveByAmount(ids) ;
				if (flag) {
					map.put("allDetails", allDetails) ;
				}
			}
		}
		map.put("flag", flag) ;
		return map ;
	}

	@Override
	public Map<String, Object> editPre(int eid, int did) throws Exception {
		if (!super.checkAuth(eid, 25)) {
			return null;
		}
		Details details = this.detailsDAO.findByIdAndPrebuy(eid, did) ;
		if (details == null) {
			return null ;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allTypes", this.typeDAO.findAll());
		map.put("allSubtypes", this.subtypeDAO.findAllByType(details.getType().getTid())) ;
		map.put("details", details) ;
		return map;
	}

	@Override
	public boolean edit(int eid, Details vo) throws Exception {
		if (!super.checkAuth(eid, 25)) {
			return false;
		}
		return this.detailsDAO.doUpdatePrebuy(vo);
	}

	@Override
	public boolean addAppend(int eid, int rid) throws Exception {
		if (!super.checkAuth(eid, 25)) {
			return false;
		}
		// 判断此时的数据是否已经存在
		Details details = this.detailsDAO.findByDetailsExists(eid, rid) ;
		if (details == null) {	// 数据不在
			// 2、需要取得指定编号的办公用品追加
			Res res = this.resDAO.findById(rid) ;
			if (res != null) {	// 现在已经有了相应的商品信息
				Details vo = new Details() ;
				vo.setRes(res);
				vo.setType(res.getType());
				vo.setSubtype(res.getSubtype());
				vo.setTitle(res.getTitle());
				vo.setEmp(new Emp());
				vo.getEmp().setEid(eid);
				vo.setPrice(res.getPrice());
				vo.setAmount(1);
				vo.setPhoto(res.getPhoto());
				vo.setRflag(res.getRflag());
				return this.detailsDAO.doCreate(vo) ; 
			}
		} else {
			return this.detailsDAO.doUpdateAppendAmount(details.getDid()) ;
		}
		return false;
	} 

}
