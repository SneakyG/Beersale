package dao.interfaces;

import java.util.List;

import mapper.IRowMapper;

public interface IAbstractDAO<T> {
	List<T> query(String sql, IRowMapper<T> rowMapper, Object... parameters);

	void insert(String sql, Object... parameters);

	int update(String sql, Object... parameters);
}
