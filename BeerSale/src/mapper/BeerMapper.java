package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import dto.BeerDTO;

public class BeerMapper implements IRowMapper<BeerDTO>{

	@Override
	public BeerDTO mapRow(ResultSet resultSet) {
		try {
			BeerDTO beer = new BeerDTO();
			beer.setId(resultSet.getInt("id"));
			beer.setName(resultSet.getString("name"));
			beer.setBrand(resultSet.getString("brand"));
			beer.setOriginBrand(resultSet.getString("origin_brand"));
			beer.setOrigin(resultSet.getString("origin"));
			beer.setIngredient(resultSet.getString("ingredient"));
			beer.setCapacity(resultSet.getString("capacity"));
			beer.setCount(resultSet.getInt("count"));
			beer.setCost(resultSet.getInt("cost"));
			beer.setRegId(resultSet.getInt("reg_id"));
			beer.setRegDate(resultSet.getString("reg_date"));
			beer.setUpdtId(resultSet.getInt("updt_id"));
			beer.setUpdtDate(resultSet.getString("updt_date"));
			try {
				beer.setDelId(resultSet.getInt("del_id"));
				beer.setDelDate(resultSet.getString("del_date"));
			}catch (Exception e) {
			}
			return beer;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
