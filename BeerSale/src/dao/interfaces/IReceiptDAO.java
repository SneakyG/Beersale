package dao.interfaces;

import java.util.List;

import dto.ReceiptDTO;

public interface IReceiptDAO {
	
	List<ReceiptDTO> findAll();
	
	ReceiptDTO findOne(int id);
	
	int update(ReceiptDTO receipt);
}
