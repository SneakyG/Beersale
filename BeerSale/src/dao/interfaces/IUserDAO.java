package dao.interfaces;

import dto.UserDTO;

public interface IUserDAO {

	void insert(UserDTO dto);
	
	UserDTO findOneByUserAccountId(int id);
}
