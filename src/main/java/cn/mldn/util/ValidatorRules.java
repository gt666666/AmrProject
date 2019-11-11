package cn.mldn.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ValidatorRules {
	private Object obj ;
	private String rules[];
	private HttpServletRequest request;
	/**
	 * @param obj 当前要执行操作的Action程序类
	 * @param rules 所有的验证规则
	 * @param request 进行参数的接收处理
	 */
	public ValidatorRules(Object obj , String rules[], HttpServletRequest request) {
		this.obj = obj ;
		this.rules = rules;
		this.request = request;
	}
	/**
	 * 实现具体的验证处理操作
	 * @return
	 */
	public Map<String,String> validate() {
		Map<String,String> map = new HashMap<String,String>() ;
		for (int x = 0 ; x < this.rules.length ; x ++) {
			String temp [] = this.rules[x].split(":") ;	// 按照竖线拆分
			String paramValue = this.request.getParameter(temp[0]) ;
			switch(temp[1]) {
				case "rand" : {
					if (ValidatorUtil.isString(paramValue)) {	// 数据现在不为空
						String rand = (String) this.request.getSession().getAttribute("rand") ;
						if (!paramValue.equalsIgnoreCase(rand)) {	// 验证码验证失败
							map.put(temp[0], MessageUtil.getMessage(this.obj, "invalidate.code.error.msg")) ;
						}
					} else {
						map.put(temp[0], MessageUtil.getMessage(this.obj, "invalidate.code.error.msg")) ;
					}
				}
				case "string" : {
					if (!ValidatorUtil.isString(paramValue)) {	// 验证失败
						map.put(temp[0], MessageUtil.getMessage(this.obj, "invalidate.string.error.msg")) ;
					}
					break ;
				}
				case "int" : {
					if (!ValidatorUtil.isInt(paramValue)) {	// 验证失败
						map.put(temp[0], MessageUtil.getMessage(this.obj, "invalidate.int.error.msg")) ;
					}
					break ;
				}
				case "double" : {
					if (!ValidatorUtil.isDouble(paramValue)) {	// 验证失败
						map.put(temp[0], MessageUtil.getMessage(this.obj, "invalidate.double.error.msg")) ;
					}
					break ;
				}
				case "date" : {
					if (!ValidatorUtil.isDate(paramValue)) {	// 验证失败
						map.put(temp[0], MessageUtil.getMessage(this.obj, "invalidate.date.error.msg")) ;
					}
					break ;
				}
				case "datetime" : {
					if (!ValidatorUtil.isDate(paramValue)) {	// 验证失败
						map.put(temp[0], MessageUtil.getMessage(this.obj, "invalidate.datetime.error.msg")) ;
					}
					break ;
				}
			}
		}
		return map ;
	} 
}
