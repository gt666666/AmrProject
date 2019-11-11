package cn.mldn.amr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.amr.adapter.AbstractActionAdapter;
import cn.mldn.amr.service.IEmpService;
import cn.mldn.amr.vo.Emp;
import cn.mldn.util.MD5Code;

@Controller
public class LoginAction extends AbstractActionAdapter {
	@Resource
	private IEmpService empService;

	@RequestMapping("/logout")
	public ModelAndView logout(Emp vo, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		request.getSession().invalidate(); 
		super.setMsgAndUrl("logout.success", "login.page", mav);
		mav.setViewName(super.getMsg("forward.page"));
		return mav ; 
	}
	
	@RequestMapping("/login")
	public ModelAndView login(Emp vo, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		vo.setPassword(new MD5Code().getMD5ofStr(vo.getPassword()));
		try {
			if (this.empService.login(vo)) {
				request.getSession().setAttribute("emp", vo);
				super.setMsgAndUrl("login.success", "index.page", mav);
			} else {
				super.setMsgAndUrl("login.failure", "login.page", mav);
			} 
			mav.setViewName(super.getMsg("forward.page"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}


}
