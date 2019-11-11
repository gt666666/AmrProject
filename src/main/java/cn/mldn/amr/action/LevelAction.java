package cn.mldn.amr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.amr.adapter.AbstractActionAdapter;
import cn.mldn.amr.service.ILevelService;

@Controller
@RequestMapping("/pages/level/*")
public class LevelAction extends AbstractActionAdapter {
	@Resource
	private ILevelService levelService;

	@RequestMapping("/checkSalary")
	public ModelAndView checkSalary(int lid, double salary,
			HttpServletResponse response) {
		try {
			super.print(response, this.levelService.checkSalary(salary, lid));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}
}
