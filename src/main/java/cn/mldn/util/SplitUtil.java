package cn.mldn.util;

import javax.servlet.http.HttpServletRequest;

import cn.mldn.amr.action.abs.AbstractAction;

public class SplitUtil {
	private int cp = 1;
	private int ls = 10;
	private String col;
	private String kw;
	private AbstractAction action;

	public SplitUtil(AbstractAction action) { // 取得公共的Action
		this.action = action;
	}

	public void setCp(String cp) {
		try {
			this.cp = Integer.parseInt(cp);
		} catch (Exception e) {
		}
	}

	public void setLs(String ls) {
		try {
			this.ls = Integer.parseInt(ls);
		} catch (Exception e) {
		}
	}

	public void setCol(String col) {
		if (col == null || "".equals(col)) {
			this.col = this.action.getDefaultColumn();
		} else {
			this.col = col;
		}
	}

	public void setKw(String kw) {
		if ("".equals(kw)) {
			this.kw = null;
		} else {
			this.kw = kw;
		}
	}

	public int getCurrentPage() {
		return cp;
	}

	public int getLineSize() {
		return ls;
	}

	public String getColumn() {
		return col;
	}

	public String getKeyword() {
		return kw;
	}
 
	public void setAttribute(HttpServletRequest request, Object allRecorders,
			String url, String paramA, Object valueA, String paramB,
			Object valueB) {
		request.setAttribute("allRecorders", allRecorders);
		request.setAttribute("url", this.action.getMsg(url));
		request.setAttribute("paramName", paramA);
		request.setAttribute("paramValue", valueA);
		request.setAttribute("paramNameB", paramB);
		request.setAttribute("paramValueB", valueB);
	}
}
