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
		String sql = "SELECT * FROM receipt_detail WHERE receipt_id = " + "(SELECT id FROM receipt WHERE id = ?)";
		List<ReceiptDetailDTO> list = query(sql, new ReceiptDetailMapper(), id);
		return list;
	}

	@Override
	public ReceiptDetailDTO findByReceiptIdAndBeerId(int receiptId, int beerId) {
		String sql = "SELECT * FROM receipt_detail WHERE receipt_id = ? AND beer_id = ?";
		List<ReceiptDetailDTO> list = query(sql, new ReceiptDetailMapper(), receiptId, beerId);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public void insert(ReceiptDetailDTO receiptDetail) {
		String sql = "INSERT INTO receipt_detail(beer_id,user_id,receipt_id,count,cost,reg_id,reg_date,updt_id,updt_date) VALUES (?,?,?,?,?,?,now(),?,now())";
		insert(sql, receiptDetail.getBeerId(), receiptDetail.getUserId(), receiptDetail.getReceiptId(),
				receiptDetail.getCount(), receiptDetail.getCost(), receiptDetail.getRegId(), receiptDetail.getUpdtId());
	}

	@Override
	public int update(ReceiptDetailDTO receiptDetail) {
		String sql = "UPDATE receipt_detail SET beer_id = ?,count = ?, cost = ?,updt_id = ?,updt_date = now() WHERE id = ?";
		return update(sql, receiptDetail.getBeerId(), receiptDetail.getCount(), receiptDetail.getCost(),
				receiptDetail.getUpdtId(), receiptDetail.getId());
	}

	@Override
	public int totalByReceiptId(int receiptId) {
		String sql = "SELECT SUM(cost) FROM receipt_detail WHERE receipt_id = ?";
		return calculate(sql, receiptId);
	}

	@Override
	public List<String> findBeerNameByBeerIdAndReceiptId(int receiptId) {
		String sql = "SELECT name FROM beer WHERE id IN "
				+ "(SELECT beer_id FROM receipt_detail WHERE receipt_id = ?)";
		List<String> lst = queryString(sql, receiptId);
		return lst;
	}

}
