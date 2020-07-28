package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import dto.UserDTO;

public class UserMapper implements IRowMapper<UserDTO> {

	@Override
	public UserDTO mapRow(ResultSet resultSet) {
		try {
			UserDTO dto = new UserDTO();
			dto.setId(resultSet.getInt("id"));
			dto.setName(resultSet.getString("name"));
			dto.setEmail(resultSet.getString("email"));
			dto.setPhoneNumber(resultSet.getInt("phone_number"));
			dto.setRegDate(resultSet.getString("reg_date"));
			return dto;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
