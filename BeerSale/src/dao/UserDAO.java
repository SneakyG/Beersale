package dao;

import java.util.List;

import dao.interfaces.IUserDAO;
import dto.UserDTO;
import mapper.UserMapper;

public class UserDAO extends AbstractDAO<UserDTO> implements IUserDAO {

	@Override
	public void insert(UserDTO user) {
		String sql = "INSERT INTO user(name,user_account_id,email,phone_number,reg_id,reg_date,updt_id,updt_date) VALUES(?,?,?,?,?,now(),?,now())";
		insert(sql, user.getName(),user.getUserAccountId(),user.getEmail(),user.getPhoneNumber(),user.getRegId(),user.getUpdtId());
	}

	@Override
	public UserDTO findOneByUserAccountId(int userAccountId) {
		String sql = "SELECT * FROM user WHERE user_account_id = "
					+"(SELECT id FROM user_account WHERE id = ?)";
		List<UserDTO> list = query(sql, new UserMapper(),userAccountId);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public int update(UserDTO user) {
		String sql = "UPDATE user SET name = ?, email = ?, phone_number = ?, updt_id = ?, updt_date = now() WHERE id = ?";
		return update(sql, user.getName(), user.getEmail(), user.getPhoneNumber(), user.getUpdtId(), user.getId());
	}

}
