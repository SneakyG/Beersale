package dao.interfaces;

import java.util.List;

import dto.BeerDTO;

public interface IBeerDAO {

	List<BeerDTO> findAll();

	BeerDTO findOne(int id);

	int findMinCost();

	BeerDTO findOneByName(String name);

	List<BeerDTO> findAllByBrand(String brand);

	List<BeerDTO> findAllByName(String name);

	void insert(BeerDTO beer);

	int update(BeerDTO beer);

	int delete(int id);
}
