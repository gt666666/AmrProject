package cn.mldn.amr.action;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.amr.adapter.AbstractActionAdapter;
import cn.mldn.amr.service.ISubtypeService;
import cn.mldn.amr.service.ITypeService;
import cn.mldn.amr.vo.Subtype;
import cn.mldn.amr.vo.Type;

@Controller
@RequestMapping("/pages/type/*")
public class TypeAction extends AbstractActionAdapter {
	@Resource
	private ITypeService typeService;
	@Resource
	private ISubtypeService subtypeService;
	@RequestMapping("getSubtype") 
	public ModelAndView getSubtype(int tid,HttpServletResponse response) {
		try {
			List<Subtype> all = this.subtypeService.list(tid) ;
			Iterator<Subtype> iter = all.iterator() ;
			JSONArray array = new JSONArray() ;
			while (iter.hasNext()) {
				Subtype sub = iter.next() ;
				JSONObject temp = new JSONObject() ;
				temp.put("stid", sub.getStid()) ;
				temp.put("title", sub.getTitle()) ;
				array.add(temp) ;
			}
			super.print(response, array);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}

	@RequestMapping("/listSubtype")
	public ModelAndView listSubtype(int tid, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			mav.addObject("allSubtypes", this.subtypeService.list(tid));
			mav.setViewName(super.getMsg("subtype.list.page"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping("editSubtype")
	public ModelAndView editSubtype(Subtype vo, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (super.isAuth(10, request)) {
				super.print(response,
						this.subtypeService.edit(vo, super.getEid(request)));
			} else {
				super.print(response, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("edit")
	public ModelAndView edit(Type vo, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (super.isAuth(9, request)) {
				super.print(response,
						this.typeService.edit(vo, super.getEid(request)));
			} else {
				super.print(response, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			mav.addObject("allTypes", this.typeService.list());
			mav.setViewName(super.getMsg("type.list.page"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
}
