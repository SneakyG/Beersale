package dao.interfaces;

import java.util.List;

import mapper.IRowMapper;
// maybe you made framework
public interface IAbstractDAO<T> {
	List<T> query(String sql, IRowMapper<T> rowMapper, Object... parameters);

	void insert(String sql, Object... parameters);

	int update(String sql, Object... parameters);
	
	int calculate(String sql, Object... parameters);
	
	List<String> queryString(String sql, Object... parameters);
}
