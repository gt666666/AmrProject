package cn.mldn.amr.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.amr.adapter.AbstractActionAdapter;
import cn.mldn.amr.service.IPurchaseService;
import cn.mldn.amr.vo.Purchase;
import cn.mldn.util.SplitUtil;

@Controller
@RequestMapping("/pages/purchase/*")
public class PurchaseAction extends AbstractActionAdapter {
	@Resource
	private IPurchaseService purchaseService;

	@RequestMapping("show")
	public ModelAndView show(int pid, HttpServletRequest request) { // purchase.show.page
		ModelAndView mav = new ModelAndView();
		if (super.isAuth(27, request)) {
			try {
				mav.addObject("purchase", this.purchaseService.getByEmp(
						super.getEid(request), pid));
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("purchase.show.page"));
		} else {
			mav.setViewName(super.getMsg("error.page"));
		}
		return mav;
	}
	@RequestMapping("audit")
	public ModelAndView audit(int pid, int status, HttpServletRequest request,
			HttpServletResponse response) {
		if (super.isAuth(42, request)) {
			try {
				super.print(response, this.purchaseService.editStatus(
						super.getEid(request), pid, status));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			super.print(response, false);
		}
		return null ;
	} 

	@RequestMapping("show5")
	public ModelAndView show5(int pid, HttpServletRequest request) { // purchase.show.page
		ModelAndView mav = new ModelAndView();
		if (super.isAuth(41, request)) {
			try {
				mav.addObject("purchase",
						this.purchaseService.show(super.getEid(request), pid));
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("purchase.show.page"));
		} else {
			mav.setViewName(super.getMsg("error.page"));
		}
		return mav;
	}

	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (super.isAuth(41, request)) {
			SplitUtil split = new SplitUtil(this);
			super.handleSplit(request, split); // 处理分页的参数数据
			try {
				Map<String, Object> map = this.purchaseService.listSimple(
						super.getEid(request), split.getCurrentPage(),
						split.getLineSize());
				mav.addObject("allPurchases", map.get("allPurchases"));
				split.setAttribute(request, map.get("purchaseCount"),
						"purchase.list.action", null, null, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("purchase.list.page"));
		} else {
			mav.setViewName(super.getMsg("error.page"));
		}
		return mav;
	}

	@RequestMapping("apply")
	public ModelAndView apply(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (super.isAuth(27, request)) {
			SplitUtil split = new SplitUtil(this);
			super.handleSplit(request, split); // 处理分页的参数数据
			try {
				Map<String, Object> map = this.purchaseService.listByEmp(
						super.getEid(request), split.getCurrentPage(),
						split.getLineSize());
				mav.addObject("allPurchases", map.get("allPurchases"));
				split.setAttribute(request, map.get("purchaseCount"),
						"purchase.apply.action", null, null, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("purchase.apply.page"));
		} else {
			mav.setViewName(super.getMsg("error.page"));
		}
		return mav;
	}

	@RequestMapping("/add")
	public ModelAndView add(Purchase vo, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (super.isAuth(30, request)) {
			try {
				if (this.purchaseService.add(super.getEid(request), vo)) {
					super.setMsgAndUrl("vo.add.success",
							"purchase.apply.action", mav);
				} else {
					super.setMsgAndUrl("vo.add.failure",
							"purchase.apply.action", mav);
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.setMsgAndUrl("vo.add.failure", "purchase.apply.action",
						mav);
			}
			mav.setViewName(super.getMsg("forward.page"));
		} else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}

	@Override
	public String getFlag() {
		return "购入清单";
	}
}
