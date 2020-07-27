package dao;

import java.util.List;

import dao.interfaces.IUserDAO;
import dto.UserDTO;
import mapper.UserMapper;

public class UserDAO extends AbstractDAO<UserDTO> implements IUserDAO {

	@Override
	public void insert(UserDTO user) {
		String sql = "INSERT INTO user(name,user_account_id,email,phone_number,money,reg_id,reg_date,updt_id,updt_date) VALUES(?,?,?,?,0,?,now(),?,now())";
		insert(sql, user.getName(),user.getUserAccountId(),user.getEmail(),user.getPhoneNumber(),user.getRegId(),user.getUpdtId());
	}

	@Override
	public UserDTO findOneByUserAccountId(int id) {
		String sql = "SELECT * FROM user WHERE user_account_id = "
					+"(SELECT id FROM user_account WHERE id = ?)";
		List<UserDTO> list = query(sql, new UserMapper(),id);
		return list.isEmpty() ? null : list.get(0);
	}

}
