package dao.interfaces;

import java.util.List;

import dto.BeerDTO;

public interface IBeerDAO {
	
	List<BeerDTO> findAll();
	
	BeerDTO findOne(int id);
	
	boolean findOneByName(String name);
	
	List<BeerDTO> findAllByBrand(String brand);
	
	void insert(BeerDTO beer);
	
	int update(BeerDTO beer);
	
	int delete(int id);
}
