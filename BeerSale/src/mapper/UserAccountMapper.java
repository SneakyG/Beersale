package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import dto.UserAccountDTO;

public class UserAccountMapper implements IRowMapper<UserAccountDTO> {

	@Override
	public UserAccountDTO mapRow(ResultSet resultSet) {
		try {
			UserAccountDTO dto = new UserAccountDTO();
			dto.setId(resultSet.getInt("id"));
			dto.setUserName(resultSet.getString("username"));
			dto.setPassword(resultSet.getString("password"));
			dto.setStatus(resultSet.getInt("status"));
			dto.setMoney(resultSet.getInt("money"));
			dto.setRoleId(resultSet.getInt("role_id"));
			dto.setRegDate(resultSet.getString("reg_date"));
			dto.setUpdtDate(resultSet.getString("updt_date"));
			try {
				dto.setRegDate(resultSet.getString("reg_id"));
				dto.setUpdtDate(resultSet.getString("updt_id"));
			} catch (Exception e) {
			}
			return dto;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
