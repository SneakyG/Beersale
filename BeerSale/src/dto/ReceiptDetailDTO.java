package dto;

public class ReceiptDetailDTO extends CommonDTO {
	private int userId;
	private int beerId;
	private int receiptId;
	private int count;
	private int cost;
	
	public ReceiptDetailDTO() {
	}

	public ReceiptDetailDTO(int userId, int beerId, int receiptId, int count, int cost) {
		this.userId = userId;
		this.beerId = beerId;
		this.receiptId = receiptId;
		this.count = count;
		this.cost = cost;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBeerId() {
		return beerId;
	}
	public void setBeerId(int beerId) {
		this.beerId = beerId;
	}
	public int getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
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
	
	@Override
	public String toString() {
		String s = userId + "--" + beerId + "--" + receiptId + "--" + count + "--" + cost + "--" + regDate + "--" + updtId + "--" + updtDate;
		return s;
	}
}
