package cn.mldn.amr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.amr.adapter.AbstractActionAdapter;
import cn.mldn.amr.service.IGroupsService;

@Controller
@RequestMapping("/pages/groups/*")
public class

GroupsAction extends AbstractActionAdapter {
	@Resource
	private IGroupsService groupsService;

	@RequestMapping("/list")
	public ModelAndView list(int did, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		System.out.println(111);
		try {
			System.out.println("**************** " + super.isAuth(6, request));
			if (super.isAuth(6, request)) {
				mav.addObject("allGroups",
						this.groupsService.listByDept(super.getEid(request),
						did)); 
				mav.setViewName(super.getMsg("groups.list.page"));
			} else {
				System.out.println(2222);
				mav.setViewName(super.getMsg("errors.page"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
}
