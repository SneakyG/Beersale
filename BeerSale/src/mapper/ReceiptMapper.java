package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import dto.ReceiptDTO;

public class ReceiptMapper implements IRowMapper<ReceiptDTO> {

	@Override
	public ReceiptDTO mapRow(ResultSet resultSet) {
		try {
			ReceiptDTO receipt = new ReceiptDTO();
			receipt.setId(resultSet.getInt("id"));
			receipt.setUserId(resultSet.getInt("user_id"));
			receipt.setStatus(resultSet.getInt("status"));
			receipt.setTotal(resultSet.getInt("total"));
			receipt.setRegDate(resultSet.getString("reg_date"));
			receipt.setUpdtId(resultSet.getInt("updt_id"));
			receipt.setUpdtDate(resultSet.getString("updt_date"));
			return receipt;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
