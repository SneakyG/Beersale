package dao;

import java.util.List;

import dao.interfaces.IReceiptDAO;
import dto.ReceiptDTO;
import mapper.ReceiptMapper;

public class ReceiptDAO extends AbstractDAO<ReceiptDTO> implements IReceiptDAO {

	@Override
	public List<ReceiptDTO> findAll() {
		String sql = "SELECT * FROM receipt";
		List<ReceiptDTO> list = query(sql, new ReceiptMapper());
		return list;
	}

	@Override
	public ReceiptDTO findOne(int id) {
		String sql = "SELECT * FROM receipt WHERE id = ?";
		List<ReceiptDTO> list = query(sql, new ReceiptMapper(), id);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public int update(ReceiptDTO receipt) {
		String sql = "UPDATE receipt SET status = ?, total = ?, updt_id = ?, updt_date = now() WHERE id = ?";
		int result = update(sql, receipt.getStatus(), receipt.getTotal(),receipt.getUpdtId(),receipt.getId());
		return result;
	}

	@Override
	public void insert(ReceiptDTO receipt) {
		String sql = "INSERT INTO receipt(user_id,total,status,reg_id,reg_date,updt_id,updt_date) VALUES(?,?,?,?,now(),?,now())";
		insert(sql, receipt.getUserId(),receipt.getTotal(),receipt.getStatus(),receipt.getRegId(),receipt.getUpdtId());
	}

}
