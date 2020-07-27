package dao.interfaces;

import java.util.List;

import dto.UserAccountDTO;

public interface IUserAccountDAO {
	List<UserAccountDTO> findAll();
	
	UserAccountDTO findOneById(int id);
	
	UserAccountDTO findOneByUserNameAndPassword(String userName, String password);

	UserAccountDTO findOneByUserName(String userName);
	
	int update(UserAccountDTO dto);
	
	void insert(UserAccountDTO dto);
}
