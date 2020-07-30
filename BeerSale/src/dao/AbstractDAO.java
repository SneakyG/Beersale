package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import dao.interfaces.IAbstractDAO;
import mapper.IRowMapper;

public class AbstractDAO<T> implements IAbstractDAO<T> {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	@Override
	public List<T> query(String sql, IRowMapper<T> rowMapper, Object... parameters) {
		List<T> list = new ArrayList<>();
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			setParameters(statement, parameters);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add(rowMapper.mapRow(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public void insert(String sql, Object... parameters) {
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			setParameters(statement, parameters);
			statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int update(String sql, Object... parameters) {
		int id = 0;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			setParameters(statement, parameters);
			id = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	
	@Override
	public int calculate(String sql, Object... parameters) {
		try {
			int min = 0;
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			setParameters(statement, parameters);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				min = resultSet.getInt(1);
			}
			return min;
		} catch (SQLException e) {
			return 0;
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return 0;
			}
		}
	}

	private void setParameters(PreparedStatement statement, Object[] parameters) {
		try {
			for (int i = 1; i <= parameters.length; i++) {
				Object parameter = parameters[i - 1];
				if (parameter instanceof Integer) {
					statement.setInt(i, (int) parameter);
				} else if (parameter instanceof String) {
					statement.setString(i, (String) parameter);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> queryString(String sql, Object... parameters) {
		List<String> list = new ArrayList<>();
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			setParameters(statement, parameters);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
