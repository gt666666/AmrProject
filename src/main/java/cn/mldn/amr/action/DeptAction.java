package cn.mldn.amr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.amr.adapter.AbstractActionAdapter;
import cn.mldn.amr.service.IDeptService;
import cn.mldn.amr.vo.Dept;

@Controller
@RequestMapping("/pages/dept/*")
public class DeptAction extends AbstractActionAdapter {
	@Resource
	private IDeptService deptService;
	@RequestMapping("edit")
	public ModelAndView edit(Dept vo, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (super.isAuth(7, request)) {
				super.print(response,
						this.deptService.edit(super.getEid(request), vo));
			} else {
				super.print(response, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ; 
	}
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		try {
			if (super.isAuth(4, request)) {

				mav.addObject("allDepts",
						this.deptService.list(super.getEid(request)));
				mav.setViewName(super.getMsg("dept.list.page"));
			} else {
				System.out.println(333);
				mav.setViewName(super.getMsg("errors.page"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
}
