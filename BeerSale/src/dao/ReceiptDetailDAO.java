package dao;

import java.util.List;

import dao.interfaces.IReceiptDetailDAO;
import dto.ReceiptDetailDTO;
import mapper.ReceiptDetailMapper;

public class ReceiptDetailDAO extends AbstractDAO<ReceiptDetailDTO> implements IReceiptDetailDAO {

	@Override
	public List<ReceiptDetailDTO> findAll() {
		String sql = "SELECT * FROM receipt_detail";
		List<ReceiptDetailDTO> list = query(sql, new ReceiptDetailMapper());
		return list;
	}

	@Override
	public List<ReceiptDetailDTO> findByReceiptId(int id) {
		String sql = "SELECT * FROM receipt_detail WHERE receipt_id = " 
					+ "(SELECT id FROM receipt WHERE id = ?)";
		List<ReceiptDetailDTO> list = query(sql, new ReceiptDetailMapper(), id);
		return list;
	}

}
