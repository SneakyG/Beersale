package dao.interfaces;

import dto.UserDTO;

public interface IUserDAO {
	
	UserDTO findOneByUserAccountId(int id);

	void insert(UserDTO user);

}
