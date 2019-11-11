package cn.mldn.amr.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.amr.adapter.AbstractActionAdapter;
import cn.mldn.amr.service.ITakeService;
import cn.mldn.amr.vo.Take;
import cn.mldn.util.SplitUtil;

@Controller
@RequestMapping("/pages/res/*")
public class TakeAction extends AbstractActionAdapter {
	@Resource
	private ITakeService takeService;

	@RequestMapping("editRflag")
	public ModelAndView editRflag(int tkid, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			super.print(response,
					this.takeService.editRflag(super.getEid(request), tkid));
		} catch (Exception e) {
			e.printStackTrace();
			super.print(response, false);
		}
		return null;
	}

	@RequestMapping("editAudit")
	public ModelAndView editAudit(Take take, HttpServletRequest request,
			HttpServletResponse response) {
		if (super.isAuth(28, request)) {
			try {
				super.print(response,
						this.takeService.editAudit(super.getEid(request), take));
			} catch (Exception e) {
				e.printStackTrace();
				super.print(response, false);
			}
		} else {
			super.print(response, false);
		}
		return null;
	}

	@RequestMapping("editRdate")
	public ModelAndView editRdate(int tkid, HttpServletRequest request,
			HttpServletResponse response) {
		if (super.isAuth(28, request)) {
			try {
				super.print(response,
						this.takeService.editRdate(super.getEid(request), tkid));
			} catch (Exception e) {
				e.printStackTrace();
				super.print(response, false);
			} 
		} else {
			super.print(response, false);
		}
		return null;
	}

	@RequestMapping("preget")
	public ModelAndView preget(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			Map<String, Object> map = this.takeService.listUnget(super
					.getEid(request));
			mav.addAllObjects(map); // 设置一组Map
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName(super.getMsg("res.preget.list.page"));
		return mav;
	}

	@RequestMapping("audit")
	public ModelAndView audit(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		SplitUtil split = new SplitUtil(this);
		super.handleSplit(request, split); // 处理分页的参数数据
		try {
			Map<String, Object> map = this.takeService.list(
					super.getEid(request), split.getColumn(),
					split.getKeyword(), split.getCurrentPage(),
					split.getLineSize());
			split.setAttribute(request, map.get("takeCount"),
					"res.audit.list.action", null, null, null, null);
			mav.addAllObjects(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName(super.getMsg("res.audit.list.page"));
		return mav;
	}

	@RequestMapping("emp_list")
	public ModelAndView empList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		SplitUtil split = new SplitUtil(this);
		super.handleSplit(request, split); // 处理分页的参数数据
		try {
			Map<String, Object> map = this.takeService.listByEmp(
					super.getEid(request), split.getCurrentPage(),
					split.getLineSize());
			split.setAttribute(request, map.get("takeCount"),
					"emp.list.action", null, null, null, null);
			mav.addAllObjects(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName(super.getMsg("res.emp.list.page"));
		return mav;
	}

	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			if (this.takeService.editSubmit(super.getEid(request))) {
				super.setMsgAndUrl("res.get.success", "res.emp.list.action",
						mav);
			} else {
				super.setMsgAndUrl("res.get.failure", "res.emp.list.action",
						mav);
			}
		} catch (Exception e) {
			super.setMsgAndUrl("res.get.failure", "res.emp.list.action", mav);
			e.printStackTrace();
		}
		mav.setViewName(super.getMsg("forward.page"));
		return mav;
	}

	@RequestMapping("/rmTake")
	public ModelAndView rmTake(String data, HttpServletResponse response,
			HttpServletRequest request) {
		String result[] = data.split("\\|");
		Set<Integer> set = new HashSet<Integer>();
		for (int x = 0; x < result.length; x++) {
			set.add(Integer.parseInt(result[x]));
		}
		try {
			super.print(response,
					this.takeService.rm(super.getEid(request), set));
		} catch (Exception e) {
			e.printStackTrace();
			super.print(response, false);
		}
		return null;
	}

	@RequestMapping("/editTake")
	public ModelAndView editTake(String data, HttpServletResponse response,
			HttpServletRequest request) {
		String result[] = data.split("\\|");
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int x = 0; x < result.length; x++) {
			String temp[] = result[x].split(":");
			map.put(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
		}
		try {
			super.print(response,
					this.takeService.edit(super.getEid(request), map));
		} catch (Exception e) {
			e.printStackTrace();
			super.print(response, false);
		}
		return null;
	}

	@RequestMapping("/addTake")
	public ModelAndView checkSalary(Take vo, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			super.print(response,
					this.takeService.add(super.getEid(request), vo));
		} catch (Exception e) {
			e.printStackTrace();
			super.print(response, false);
		}
		return null;
	}

}
