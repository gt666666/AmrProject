package cn.mldn.amr.action.abs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.amr.vo.Action;
import cn.mldn.amr.vo.Emp;
import cn.mldn.amr.vo.Groups;
import cn.mldn.util.SplitUtil;

public abstract class AbstractAction {
	@Resource
	private MessageSource msgSource; // 自动匹配注入

	@InitBinder
	public void initBinder(WebDataBinder binder) { // 转换器
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 将自定义的转换器进行配置，表示以后如果发现有Date类型，就使用sdf对象进行转换，并且允许数据为null
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
				sdf, true));
	}

	/**
	 * 实现信息的输出操作
	 * 
	 * @param response
	 * @param obj
	 */
	public void print(HttpServletResponse response, Object obj) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * 取得保存在session中的雇员编号
	 * 
	 * @param request
	 * @return
	 */
	public Integer getEid(HttpServletRequest request) {
		return ((Emp) request.getSession().getAttribute("emp")).getEid();
	}

	/**
	 * 验证在session中是否存在有指定的权限数据
	 * 
	 * @param actid
	 *            权限编号，唯一的，不可变的
	 * @return
	 */
	public boolean isAuth(int actid, HttpServletRequest request) { // 是否具备有指定的权限
		Emp emp = this.getEmp(request); // 取得Emp对象
		Iterator<Groups> iterGup = emp.getDept().getAllGroups().iterator();
		while (iterGup.hasNext()) {
			Groups gup = iterGup.next();
			Iterator<Action> iterAct = gup.getAllActions().iterator();
			while (iterAct.hasNext()) {
				Action act = iterAct.next();
				if (act.getActid().equals(actid)) {
					return true;
				}
			}
		}
		return false; 
	}
	/**
	 * 取得在session中出现的登录操作，并且这里面可以存在有相应的权限处理
	 *
	 * @param request
	 * @return
	 */
	public Emp getEmp(HttpServletRequest request) {
		return (Emp) request.getSession().getAttribute("emp");
	}


	/**
	 * 取得文件名称，如果没有上传，则返回的是“nophoto.png”文件
	 * 
	 * @param file
	 *            接收上传文件
	 * @return
	 */
	public String createSingleFileName(MultipartFile file) { // 创建文件名称
		if (file == null) {
			return "nophoto.png";
		}
		if (file.getSize() <= 0) { // 没有上传文件
			return "nophoto.png";
		}
		return UUID.randomUUID() + "." + this.getFileExt(file.getContentType());
	}
	/**
	 * 取得文件的后缀
	 *
	 * @return
	 */
	public String getFileExt(String contentType) {
		if ("image/bmp".equals(contentType)) {
			return "bmp";
		} else if ("image/gif".equals(contentType)) {
			return "gif";
		} else if ("image/jpeg".equals(contentType)) {
			return "jpg";
		} else if ("image/png".equals(contentType)) {
			return "png";
		}
		return null;
	}
	/**
	 * 删除文件信息
	 * @param request
	 * @param fileName
	 */
	public void deleteFile(HttpServletRequest request, String fileName) {
		if (!"nophoto.png".equals(fileName)) {
			String filePath = request.getServletContext().getRealPath(
					this.getSaveFileDiv())
					+ fileName;
			File file = new File(filePath);
			if (file.exists()) {
				file.delete() ;
			}
		}
	}

	/**
	 * 文件的保存处理操作
	 * 
	 * @param file
	 *            包含所有的文件信息
	 * @param request
	 *            取得绝对路径
	 * @return
	 */
	public boolean saveUploadFile(MultipartFile file,
			HttpServletRequest request, String fileName) {
		String filePath = request.getServletContext().getRealPath(
				this.getSaveFileDiv())
				+ fileName;
		if (file.getSize() > 0) {
			OutputStream output = null;
			InputStream input = null;
			try {
				output = new FileOutputStream(filePath);
				input = file.getInputStream();
				byte data[] = new byte[2048];
				int temp = 0;
				while ((temp = input.read(data)) != -1) {
					output.write(data, 0, temp);
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}



	/**
	 * 设置所需要的msg与url数据
	 * 
	 * @param msgKey
	 * @param urlKey
	 * @param mav
	 */
	public void setMsgAndUrl(String msgKey, String urlKey, ModelAndView mav) {
		mav.addObject("msg", this.getMsg(msgKey));
		mav.addObject("url", this.getMsg(urlKey));
	}

	/**
	 * 设置操作资源文件数据的读取处理
	 * 
	 * @param key
	 *            资源文件的key信息
	 * @return
	 */
	public String getMsg(String key) {
		try {
			return this.msgSource.getMessage(key,
					new Object[] { this.getFlag() }, Locale.getDefault());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 传递任意多个参数
	 * 
	 * @param key
	 * @param arg
	 * @return
	 */
	public String getMsg(String key, Object... arg) {
		try {
			return this.msgSource.getMessage(key, arg, Locale.getDefault());
		} catch (Exception e) {
			return null;
		}
	}

	@ExceptionHandler
	public String exceptionHandle(Exception e) { // 专门处理异常
		if (e instanceof MaxUploadSizeExceededException) { // 现在超过了最大的上传文件大小
			System.out.println("**** 进行一些错误的记录处理操作 *******");
		}
		return "/errors.jsp";
	}

	public void handleSplit(HttpServletRequest request, SplitUtil split) {
		split.setCp(request.getParameter("cp"));
		split.setLs(request.getParameter("ls"));
		split.setKw(request.getParameter("kw"));
		split.setCol(request.getParameter("col"));
		// 将这些内容传递到JSP页面上
		request.setAttribute("currentPage", split.getCurrentPage());
		request.setAttribute("lineSize", split.getLineSize());
		request.setAttribute("column", split.getColumn());
		request.setAttribute("keyWord", split.getKeyword());
		request.setAttribute("columnData", this.getColumnData());
	}

	/**
	 * 返回CRUD操作时的执行标记
	 * 
	 * @return
	 */
	public abstract String getFlag();

	/**
	 * 取得上传文件的路径
	 * 
	 * @return
	 */
	public abstract String getSaveFileDiv();

	/**
	 * 得到分页的默认列
	 * 
	 * @return
	 */
	public abstract String getDefaultColumn();

	/**
	 * 实现页面的模糊查询列表显示，格式“标签:字段|标签:字段|标签:字段|”
	 * 
	 * @return
	 */
	public abstract String getColumnData();
}
