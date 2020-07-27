package dao;

import java.util.List;

import dao.interfaces.IUserAccountDAO;
import dto.UserAccountDTO;
import mapper.UserAccountMapper;

public class UserAccountDAO extends AbstractDAO<UserAccountDTO> implements IUserAccountDAO {

	@Override
	public List<UserAccountDTO> findAll() {
		String sql = "SELECT * FROM user_account";
		List<UserAccountDTO> list = query(sql, new UserAccountMapper());
		return list;
	}
	
	@Override
	public UserAccountDTO findOneByUserNameAndPassword(String userName, String password) {
		String sql = "SELECT * FROM user_account WHERE username = ? AND password = ?";
		List<UserAccountDTO> list = query(sql, new UserAccountMapper(), userName,password);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public UserAccountDTO findOneByUserName(String userName) {
		String sql = "SELECT * FROM user_account WHERE username = ?";
		List<UserAccountDTO> list = query(sql, new UserAccountMapper(), userName);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public void insert(UserAccountDTO dto) {
		String sql = "INSERT INTO user_account(username,password,role_id,status,money,reg_date,updt_date)"
					+ " VALUES(?,?,2,1,0,now(),now())";
		insert(sql, dto.getUserName(), dto.getPassword());
	}

	@Override
	public UserAccountDTO findOneById(int id) {
		String sql = "SELECT * FROM user_account WHERE id = ?";
		List<UserAccountDTO> list = query(sql, new UserAccountMapper(), id);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public int update(UserAccountDTO dto) {
		String sql = "UPDATE user_account SET password = ?,status = ?, money = ?, updt_date = now(), updt_id = ? WHERE id = ?";
		return update(sql, dto.getPassword(),dto.getStatus(), dto.getMoney(), dto.getUpdtId(),dto.getId());
	}

	

}
