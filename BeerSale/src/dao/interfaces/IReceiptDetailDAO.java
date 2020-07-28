package dao.interfaces;

import java.util.List;

import dto.ReceiptDetailDTO;

public interface IReceiptDetailDAO {
	List<ReceiptDetailDTO> findAll();

	List<ReceiptDetailDTO> findByReceiptId(int id);

	ReceiptDetailDTO findByReceiptIdAndBeerId(int receiptId, int beerId);

	List<String> findBeerNameByBeerIdAndReceiptId(int receiptId);

	int totalByReceiptId(int receiptId);

	void insert(ReceiptDetailDTO receiptDetail);

	int update(ReceiptDetailDTO receiptDetail);
}
