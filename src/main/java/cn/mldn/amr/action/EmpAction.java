package cn.mldn.amr.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.amr.adapter.AbstractActionAdapter;
import cn.mldn.amr.service.IAdminService;
import cn.mldn.amr.service.IEmpService;
import cn.mldn.amr.vo.Emp;
import cn.mldn.util.MD5Code;
import cn.mldn.util.SplitUtil;

@Controller
@RequestMapping("/pages/emp/*")
public class EmpAction extends AbstractActionAdapter {
	@Resource
	private IAdminService adminService; // 实现用户ID的检测
	@Resource
	private IEmpService empService;

	@RequestMapping("checkEid")
	public ModelAndView checkEid(Integer eid, HttpServletResponse response) {
		try {
			super.print(response, !this.adminService.checkEid(eid));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("editPre")
	public ModelAndView editPre(int eid,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			Map<String, Object> map = this.empService.editPre(eid);
			mav.addObject("allLevels", map.get("allLevels"));
			mav.addObject("allDepts", map.get("allDepts"));
			mav.addObject("emp", map.get("emp"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName(super.getMsg("emp.edit.page"));
		return mav;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(Emp vo, MultipartFile pic,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (super.isAuth(15, request)) {
			if (pic != null && pic.getSize() > 0) {	// 有新的图片上传
				if ("nophoto.png".equals(vo.getPhoto())) {	// 原始没有图片
					vo.setPhoto(super.createSingleFileName(pic)); // 创建文件名称
				}
			}
			if (vo.getPassword() == null || "".equals(vo.getPassword())) {
				vo.setPassword(null);	// 与动态SQL对应
			} else {
				vo.setPassword(new MD5Code().getMD5ofStr(vo.getPassword())); // 密码加密
			}
			mav.setViewName(super.getMsg("forward.page"));
			try {
				if (this.empService.edit(vo, super.getEid(request))) {
					super.saveUploadFile(pic, request, vo.getPhoto());// 保存文件
					super.setMsgAndUrl("vo.edit.success", "emp.list.action", mav);
				} else {
					super.setMsgAndUrl("vo.edit.failure", "emp.list.action", mav);
				}
			} catch (Exception e) {
				super.setMsgAndUrl("vo.edit.failure", "emp.list.action", mav);
				e.printStackTrace();
			}
		} else {
			mav.setViewName(super.getMsg("error.page"));
		}
		return mav;
	}

	@RequestMapping("addPre")
	public ModelAndView addPre(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (super.isAuth(11, request)) {

			try {
				Map<String, Object> map = this.empService.addPre();
				mav.addObject("allLevels", map.get("allLevels"));
				mav.addObject("allDepts", map.get("allDepts"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("emp.add.page"));
		} else {
			mav.setViewName(super.getMsg("error.page"));
		}
		return mav;
	}

	@RequestMapping("add")
	public ModelAndView add(Emp vo, MultipartFile pic,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (super.isAuth(12, request)) {
			vo.setPhoto(super.createSingleFileName(pic)); // 创建文件名称
			vo.setHeid(super.getEid(request)); // 取得创建者雇员编号
			vo.setPassword(new MD5Code().getMD5ofStr(vo.getPassword())); // 密码加密
			mav.setViewName(super.getMsg("forward.page"));
			try {
				if (this.empService.add(vo, super.getEid(request))) {
					super.saveUploadFile(pic, request, vo.getPhoto());// 保存文件
					super.setMsgAndUrl("vo.add.success", "emp.add.action", mav);
				} else {
					super.setMsgAndUrl("vo.add.failure", "emp.add.action", mav);
				}
			} catch (Exception e) {
				super.setMsgAndUrl("vo.add.failure", "emp.add.action", mav);
				e.printStackTrace();
			}
		} else {
			mav.setViewName(super.getMsg("error.page"));
		}
		return mav;
	}

	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (super.isAuth(13, request)) {
			SplitUtil split = new SplitUtil(this);
			super.handleSplit(request, split); // 处理分页的参数数据
			try {
				Map<String, Object> map = this.empService.list(super.getEid(request),
						split.getColumn(), split.getKeyword(),
						split.getCurrentPage(), split.getLineSize());
				mav.addObject("allEmps", map.get("allEmps"));
				split.setAttribute(request, map.get("empCount"),
						"emp.list.action", null, null, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("emp.list.page"));
		} else {
			mav.setViewName(super.getMsg("error.page"));
		}
		return mav;
	}

	@Override
	public String getFlag() {
		return "雇员";
	}

	@Override
	public String getSaveFileDiv() {
		return "/upload/emp/";
	}

	@Override
	public String getDefaultColumn() {
		return "name";
	}

	@Override
	public String getColumnData() {
		return "雇员姓名:name|雇员编号:eid|雇员电话:phone";
	}

}
