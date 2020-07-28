package dao;

import java.util.List;

import dao.interfaces.IBeerDAO;
import dto.BeerDTO;
import mapper.BeerMapper;

public class BeerDAO extends AbstractDAO<BeerDTO> implements IBeerDAO {

	@Override
	public List<BeerDTO> findAll() {
		String sql = "SELECT * FROM beer";
		List<BeerDTO> list = query(sql, new BeerMapper());
		return list;
	}

	@Override
	public BeerDTO findOne(int id) {
		String sql = "SELECT * FROM beer WHERE id = ?";
		List<BeerDTO> list = query(sql, new BeerMapper(), id);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public void insert(BeerDTO beer) {
		String sql = "INSERT INTO beer(name,brand,origin_brand,origin,ingredient,capacity,count,cost,reg_id,reg_date,updt_id,updt_date)"
				+" VALUES(?,?,?,?,?,?,?,?,?,now(),?,now())";
		insert(sql, beer.getName(),beer.getBrand(),beer.getOriginBrand(),beer.getOrigin(),beer.getIngredient(),beer.getCapacity(),beer.getCount(),
				beer.getCost(),beer.getRegId(),beer.getUpdtId());
	}

	@Override
	public int update(BeerDTO beer) {
		String sql = "UPDATE beer SET name = ?, brand = ?, origin_brand = ?, origin = ?, ingredient = ?, capacity = ?, cost = ?, count = ?,"
				+ " updt_id = ?, updt_date = now() WHERE id = ?";
		return update(sql, beer.getName(), beer.getBrand(), beer.getOriginBrand(), beer.getOrigin(), beer.getIngredient(), beer.getCapacity(), beer.getCost(),
				beer.getCount(), beer.getUpdtId(), beer.getId());
		
	}

	@Override
	public int delete(int id) {
		String sql = "DELETE FROM beer WHERE id = ?";
		int result = update(sql, id);
		return result;
	}

	@Override
	public List<BeerDTO> findAllByBrand(String brand) {
		String sql = "SELECT * FROM beer WHERE brand like '%"+ brand + "%'";
		List<BeerDTO> list = query(sql, new BeerMapper());
		return list;
	}

	@Override
	public BeerDTO findOneByName(String name) {
		String sql = "SELECT * FROM beer WHERE name = ?";
		List<BeerDTO> list = query(sql, new BeerMapper(), name);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public int findMinCost() {
		String sql = "SELECT MIN(cost) cost FROM beer";
		return calculate(sql);
	}

	@Override
	public List<BeerDTO> findAllByName(String name) {
		String sql = "SELECT * FROM beer WHERE name like '%"+ name + "%'";
		List<BeerDTO> list = query(sql, new BeerMapper());
		return list;
	}
	
}
