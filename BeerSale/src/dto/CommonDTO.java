package dto;

// what different VO and DTO ? Data transfer object i cant understand like passing parameter
// VO(Value Object) == Entity == Model 
public abstract class CommonDTO {
	// child class want use parent class field
	private int id; 
	private int regId;
	private String regDate;
	private int updtId;
	private String updtDate;

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRegId() {
		return regId;
	}

	public void setRegId(int regId) {
		this.regId = regId;
	}

	public int getUpdtId() {
		return updtId;
	}

	public void setUpdtId(int updtId) {
		this.updtId = updtId;
	}

	public String getUpdtDate() {
		return updtDate;
	}

	public void setUpdtDate(String updtDate) {
		this.updtDate = updtDate;
	}

}
