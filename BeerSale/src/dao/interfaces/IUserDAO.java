package dao.interfaces;

import dto.UserDTO;

public interface IUserDAO {

	UserDTO findOneByUserAccountId(int userAccountId);

	void insert(UserDTO user);

	int update(UserDTO user);
}
