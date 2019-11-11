package cn.mldn.amr.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.amr.adapter.AbstractActionAdapter;
import cn.mldn.amr.service.IResService;
import cn.mldn.util.SplitUtil;

@Controller
@RequestMapping("/pages/res/*")
public class ResAction extends AbstractActionAdapter {
	@Resource
	private IResService resService;

	@RequestMapping("/list")
	public ModelAndView checkSalary(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		SplitUtil split = new SplitUtil(this);
		super.handleSplit(request, split); // 处理分页的参数数据
		try {
			Map<String, Object> map = this.resService.list(split.getColumn(),
					split.getKeyword(), split.getCurrentPage(),
					split.getLineSize());
			mav.addObject("allRess", map.get("allRess"));
			split.setAttribute(request, map.get("resCount"), "res.list.action",
					null, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName(super.getMsg("res.list.page"));
		return mav; 
	}
}
