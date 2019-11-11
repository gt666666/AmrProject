package cn.mldn.amr.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.amr.dao.IDetailsDAO;
import cn.mldn.amr.dao.IEmpDAO;
import cn.mldn.amr.dao.IPurchaseDAO;
import cn.mldn.amr.dao.IResDAO;
import cn.mldn.amr.service.IPurchaseService;
import cn.mldn.amr.service.abs.AbstractService;
import cn.mldn.amr.vo.Details;
import cn.mldn.amr.vo.Emp;
import cn.mldn.amr.vo.Purchase;
import cn.mldn.amr.vo.Res;
import cn.mldn.util.MathUtil;

@Service
public class PurchaseServiceImpl extends AbstractService implements
		IPurchaseService {
	@Resource
	private IDetailsDAO detailsDAO;
	@Resource
	private IPurchaseDAO purchaseDAO;
	@Resource
	private IEmpDAO empDAO;
	@Resource
	private IResDAO resDAO;

	@Override
	public boolean add(int eid, Purchase vo) throws Exception {
		if (!super.checkAuth(eid, 30)) {
			return false;
		}
		// 1、取出所有当前用户所保存的待购入商品信息
		List<Details> allDetails = this.detailsDAO.findAllPrebuy(eid); // 所有的待购入商品
		if (allDetails.size() == 0) { // 现在没有任何要购买的商品
			return false; // 无法进行购入操作的编写；
		}
		// 2、如果存在有待购入数据，则计算总额
		double sum = 0.0;
		Iterator<Details> iter = allDetails.iterator();
		while (iter.hasNext()) {
			Details details = iter.next(); // 取出每一个待购入数据得到单价和数量
			sum += details.getPrice() * details.getAmount(); // 每个商品的总价累积
		}
		if (sum < 0.0) { // 如果买东西还给你钱，科学
			return false;
		}
		// 3、创建清单的完整数据
		vo.setEmp(new Emp());
		vo.getEmp().setEid(eid); // 设置申请人的雇员编号
		vo.setPubdate(new Date());// 当前日期为申请日期
		vo.setStatus(0); // 待审核状态
		vo.setTotal(MathUtil.round(sum, 2)); // 总额
		// 4、保存清单数据
		if (this.purchaseDAO.doCreate(vo)) { // 创建清单时直接会取得增长后的ID数据
			return this.detailsDAO.doUpdateByPurchase(vo.getPid(), eid);
		}
		return false;
	}

	@Override
	public Map<String, Object> listByEmp(int eid, int currentPage, int lineSize)
			throws Exception {
		if (!super.checkAuth(eid, 27)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allPurchases",
				this.purchaseDAO.findAllByEmp(eid, currentPage, lineSize));
		map.put("purchaseCount", this.purchaseDAO.getAllCountByEmp(eid));
		return map;
	}

	@Override
	public Purchase getByEmp(int eid, int pid) throws Exception {
		if (!super.checkAuth(eid, 27)) {
			return null;
		}
		Purchase p = this.purchaseDAO.findByIdAndEmp(pid, eid);
		if (p != null) { // 已经查询到了申请单的数据
			p.setAllDetails(this.detailsDAO.findAllByPruchase(pid));
			p.setEmp(this.empDAO.findById(p.getEmp().getEid()));
		}
		return p;
	}

	@Override
	public Map<String, Object> listSimple(int eid, int currentPage, int lineSize)
			throws Exception {
		if (!super.checkAuth(eid, 41)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allPurchases",
				this.purchaseDAO.findAllSimpleSplit(currentPage, lineSize));
		map.put("purchaseCount", this.purchaseDAO.getAllCountSimple());
		return map;
	}

	@Override
	public Purchase show(int eid, int pid) throws Exception {
		if (!super.checkAuth(eid, 41)) {
			return null;
		}
		Purchase p = this.purchaseDAO.findById(pid);
		if (p != null) { // 已经查询到了申请单的数据
			p.setAllDetails(this.detailsDAO.findAllByPruchase(pid));
			p.setEmp(this.empDAO.findById(p.getEmp().getEid()));
		}
		return p;
	}

	@Override
	public boolean editStatus(int eid, int pid, int status) throws Exception {
		if (!super.checkAuth(eid, 42, 4)) {
			return false; 
		}
		// 1、进行订单的审核操作，未必通过
		if (this.purchaseDAO.doUpdateStatus(pid, status, eid)) {
			if (status == 1) { // 表示审核通过
				// 2、需要取出所有的该订单下的待购商品信息
				List<Details> allDetails = this.detailsDAO
						.findAllByPruchase(pid);
				Iterator<Details> iter = allDetails.iterator();
				while (iter.hasNext()) {
					Details details = iter.next();
					if (details.getRes() == null
							|| details.getRes().getRid() == null) {
						Res vo = new Res(); // 需要将待购入的订单信息保存到用品表
						vo.setType(details.getType());
						vo.setSubtype(details.getSubtype());
						vo.setTitle(details.getTitle());
						vo.setPrice(details.getPrice());
						vo.setIndate(new Date()); // 当前日期为购入日期
						vo.setPhoto(details.getPhoto());
						vo.setRflag(details.getRflag());
						vo.setAmount(details.getAmount());
						this.resDAO.doCreate(vo) ;
					} else {	// 如果现在rid存在，进行数量的追加
						this.resDAO.doUpdateAmount(details.getRes().getRid(), details.getAmount()) ;
					}
				}
			}
		}
		return true ;
	} 
}
