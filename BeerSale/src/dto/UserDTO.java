package dto;

public class UserDTO extends CommonDTO {
	private int userAccountId;
	private String name;
	private String email;
	private int phoneNumber;
	
	public UserDTO() {
	}

	public UserDTO(int userAccountId, String name, String email, int phoneNumber, int regId, int updtId) {
		this.userAccountId = userAccountId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.regId = regId;
		this.updtId = updtId;
	}

	public int getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(int userAccountId) {
		this.userAccountId = userAccountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
}
