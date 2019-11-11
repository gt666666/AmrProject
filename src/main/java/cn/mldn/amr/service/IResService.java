package cn.mldn.amr.service;

import java.util.Map;

public interface IResService {
	/**
	 * 数据的分页显示处理
	 * @param column 
	 * @param keyWord
	 * @param currentPage
	 * @param lineSize
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> list(String column, String keyWord,
			int currentPage, int lineSize) throws Exception;
}
