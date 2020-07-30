package dto;

public class BeerDTO extends CommonDTO {
	private String name;
	private String brand;
	private String originBrand;
	private String origin;
	private String ingredient;
	private String capacity;
	private int count;
	private int cost;
	private int delId;
	private String delDate;

	public BeerDTO() {
	}
	
	public BeerDTO(int id, String name, String brand, String originBrand, String origin, String ingredient, String capacity, int count, int cost) {
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.originBrand = originBrand;
		this.origin = origin;
		this.ingredient = ingredient;
		this.capacity = capacity;
		this.count = count;
		this.cost = cost;
	}
	public BeerDTO(String name, String brand, String originBrand, String origin, String ingredient, String capacity, int count, int cost) {
		this.name = name;
		this.brand = brand;
		this.originBrand = originBrand;
		this.origin = origin;
		this.ingredient = ingredient;
		this.capacity = capacity;
		this.count = count;
		this.cost = cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getOriginBrand() {
		return originBrand;
	}

	public void setOriginBrand(String originBrand) {
		this.originBrand = originBrand;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public int getDelId() {
		return delId;
	}

	public void setDelId(int delId) {
		this.delId = delId;
	}

	public String getDelDate() {
		return delDate;
	}

	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}
