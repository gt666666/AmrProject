package cn.mldn.amr.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.amr.adapter.AbstractActionAdapter;
import cn.mldn.amr.service.IDetailsService;
import cn.mldn.amr.vo.Details;
import cn.mldn.amr.vo.Emp;

@Controller
@RequestMapping("/pages/res/*")
public class DetailsAction extends AbstractActionAdapter {
	@Resource
	private IDetailsService detailsService;
	@RequestMapping("append") 
	public ModelAndView append(int rid, HttpServletRequest request,
			HttpServletResponse response) {
		if (super.isAuth(25, request)) {
			try {
				super.print(response, this.detailsService.addAppend(
						super.getEid(request), rid));
			} catch (Exception e) {
				e.printStackTrace();
				super.print(response, false); 
			}
		} else {
			super.print(response, false);
		}
		return null;
	}

	@RequestMapping("addPre")
	public ModelAndView addPre(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			Map<String, Object> map = this.detailsService.addPre(super
					.getEid(request));
			mav.addObject("allTypes", map.get("allTypes"));
			mav.setViewName(super.getMsg("details.add.page"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping("editPre")
	public ModelAndView editPre(int did, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			Map<String, Object> map = this.detailsService.editPre(
					super.getEid(request), did);
			mav.addObject("allTypes", map.get("allTypes"));
			mav.addObject("allSubtypes", map.get("allSubtypes"));
			mav.addObject("details", map.get("details"));
			mav.setViewName(super.getMsg("details.edit.page"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping("prebuy")
	public ModelAndView prebuy(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (super.isAuth(25, request)) {
			try {
				mav.addObject("allDetails",
						this.detailsService.listPrebuy(super.getEid(request)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("details.list.page"));
		} else {
			mav.setViewName(super.getMsg("error.page"));
		}
		return mav;
	}

	@RequestMapping("rm")
	public ModelAndView rm(String deletestr, HttpServletResponse response,
			HttpServletRequest request) {
		if (super.isAuth(25, request)) {
			Set<Integer> deleteSet = new HashSet<Integer>();
			if (!(deletestr == null || "".equals(deletestr))) {
				String deleteResult[] = deletestr.split("\\|");
				for (int x = 0; x < deleteResult.length; x++) {
					deleteSet.add(Integer.parseInt(deleteResult[x]));
				}
				try {
					Map<String, Object> map = this.detailsService.rm(
							super.getEid(request), deleteSet);
					List<Details> allDetails = (List<Details>) map
							.get("allDetails");
					if (allDetails != null) { // 有数据删除
						Iterator<Details> iter = allDetails.iterator();
						while (iter.hasNext()) {
							super.deleteFile(request, iter.next().getPhoto());
						}
					}
					super.print(response, map.get("flag"));
				} catch (Exception e) {
					e.printStackTrace();
					super.print(response, false);
				}
			}
		} else {
			super.print(response, false);
		}
		return null;
	}

	@RequestMapping("editAmount")
	public ModelAndView editAmount(String updatestr, String deletestr,
			HttpServletResponse response, HttpServletRequest request) {
		if (super.isAuth(25, request)) {
			// 1、针对于修改的数量进行拆分处理操作
			String updateResult[] = updatestr.split("\\|");
			Map<Integer, Integer> updateMap = new HashMap<Integer, Integer>();
			for (int x = 0; x < updateResult.length; x++) {
				String temp[] = updateResult[x].split(":");
				updateMap.put(Integer.parseInt(temp[0]),
						Integer.parseInt(temp[1]));
			}
			// 2、针对于要删除的数据进行拆分处理
			Set<Integer> deleteSet = new HashSet<Integer>();
			if (!(deletestr == null || "".equals(deletestr))) {
				String deleteResult[] = deletestr.split("\\|");
				for (int x = 0; x < deleteResult.length; x++) {
					deleteSet.add(Integer.parseInt(deleteResult[x]));
				}
			}
			try {
				Map<String, Object> map = (Map<String, Object>) this.detailsService
						.editAmount(super.getEid(request), updateMap, deleteSet);
				List<Details> allDetails = (List<Details>) map
						.get("allDetails");
				if (allDetails != null) { // 有数据删除
					Iterator<Details> iter = allDetails.iterator();
					while (iter.hasNext()) {
						super.deleteFile(request, iter.next().getPhoto());
					}
				}
				super.print(response, map.get("flag"));
			} catch (Exception e) {
				e.printStackTrace();
				super.print(response, false);
			}
		} else {
			super.print(response, false);
		}
		return null;
	}

	@RequestMapping("add")
	public ModelAndView add(Details vo, MultipartFile pic,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (super.isAuth(25, request)) {
			vo.setPhoto(super.createSingleFileName(pic)); // 创建文件名称
			try {
				if (this.detailsService.add(vo, super.getEid(request))) {
					super.saveUploadFile(pic, request, vo.getPhoto());// 保存文件
					super.setMsgAndUrl("vo.add.success", "details.list.action",
							mav);
				} else {
					super.setMsgAndUrl("vo.add.failure", "details.list.action",
							mav);
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.setMsgAndUrl("vo.add.failure", "details.list.action", mav);
			}
			mav.setViewName(super.getMsg("forward.page"));
		} else {
			mav.setViewName(super.getMsg("error.page"));
		}
		return mav;
	}

	@RequestMapping("edit")
	public ModelAndView edit(Details vo, MultipartFile pic,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (super.isAuth(25, request)) {
			if (pic != null && pic.getSize() > 0) { // 有新的图片上传
				if ("nophoto.png".equals(vo.getPhoto())) {
					vo.setPhoto(super.createSingleFileName(pic)); // 创建文件名称
				}
			}
			vo.setEmp(new Emp());
			vo.getEmp().setEid(super.getEid(request));
			try {
				if (this.detailsService.edit(super.getEid(request), vo)) {
					super.saveUploadFile(pic, request, vo.getPhoto());// 保存文件
					super.setMsgAndUrl("vo.edit.success",
							"details.list.action", mav);
				} else {
					super.setMsgAndUrl("vo.edit.failure",
							"details.list.action", mav);
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.setMsgAndUrl("vo.edit.failure", "details.list.action",
						mav);
			}
			mav.setViewName(super.getMsg("forward.page"));
		} else {
			mav.setViewName(super.getMsg("error.page"));
		}
		return mav;
	}

	@Override
	public String getSaveFileDiv() {
		return "/upload/res/";
	}

	@Override
	public String getFlag() {
		return "待购办公用品";
	}
}
