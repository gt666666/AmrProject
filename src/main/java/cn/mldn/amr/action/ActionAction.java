package cn.mldn.amr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.amr.adapter.AbstractActionAdapter;
import cn.mldn.amr.service.IActionService;

@Controller
@RequestMapping("/pages/action/*")
public class ActionAction extends AbstractActionAdapter {
	@Resource
	private IActionService actionService;

	@RequestMapping("/list")
	public ModelAndView list(int gid, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			if (super.isAuth(6, request)) {
				mav.addObject("allActions",
						this.actionService.listByGroups(super.getEid(request), gid)); 
				mav.setViewName(super.getMsg("action.list.page"));
			} else {
				mav.setViewName(super.getMsg("errors.page"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
}
