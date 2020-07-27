package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import dto.ReceiptDetailDTO;

public class ReceiptDetailMapper implements IRowMapper<ReceiptDetailDTO> {

	@Override
	public ReceiptDetailDTO mapRow(ResultSet resultSet) {
		try {
			ReceiptDetailDTO receiptDetailDTO = new ReceiptDetailDTO();
			receiptDetailDTO.setId(resultSet.getInt("id"));
			receiptDetailDTO.setUserId(resultSet.getInt("user_id"));
			receiptDetailDTO.setBeerId(resultSet.getInt("beer_id"));
			receiptDetailDTO.setReceiptId(resultSet.getInt("receipt_id"));
			receiptDetailDTO.setCost(resultSet.getInt("cost"));
			receiptDetailDTO.setCount(resultSet.getInt("count"));
			receiptDetailDTO.setRegDate(resultSet.getString("reg_date"));
			receiptDetailDTO.setUpdtDate(resultSet.getString("updt_date"));
			return receiptDetailDTO;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
