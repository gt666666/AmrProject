package cn.mldn.amr.dao.abs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public abstract class AbstractDAO {
	@Resource
	private SqlSessionFactory factory ;
	public SqlSessionFactory getFactory() {
		return factory;
	}
	public SqlSession getSession() {
		return this.factory.openSession() ;
	}
	
	public <E> List<E> listHandle(String column, String keyWord,
			Integer currentPage, Integer lineSize,String namespace) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", column);
		if (keyWord != null) {
			map.put("keyWord", "%" + keyWord + "%");
		}
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);
		return this.getSession().selectList(namespace, map) ; 
	}
	 
	public Integer countHandle(String column,String keyWord,String namespace) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", column);
		if (keyWord != null) {
			map.put("keyWord", "%" + keyWord + "%");
		}
		return this.getSession().selectOne(namespace,map ) ;
	}
}
