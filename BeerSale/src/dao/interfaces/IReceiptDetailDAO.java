package dao.interfaces;

import java.util.List;

import dto.ReceiptDetailDTO;

public interface IReceiptDetailDAO {
	List<ReceiptDetailDTO> findAll();

	List<ReceiptDetailDTO> findByReceiptId(int id);
}
