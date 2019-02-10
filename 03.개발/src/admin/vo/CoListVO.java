package admin.vo;

public class CoListVO {
	
	private String coNum, img1, coName, erId, estDate, inputDate;
	private int memberNum;
	
	public CoListVO(String coNum, String img1, String coName, String erId, String estDate, String inputDate,
			int memberNum) {
		this.coNum = coNum;
		this.img1 = img1;
		this.coName = coName;
		this.erId = erId;
		this.estDate = estDate;
		this.inputDate = inputDate;
		this.memberNum = memberNum;
	}

	public String getCoNum() {
		return coNum;
	}

	public String getImg1() {
		return img1;
	}

	public String getCoName() {
		return coName;
	}

	public String getErId() {
		return erId;
	}

	public String getEstDate() {
		return estDate;
	}

	public String getInputDate() {
		return inputDate;
	}

	public int getMemberNum() {
		return memberNum;
	}

	@Override
	public String toString() {
		return "CoListVO [coNum=" + coNum + ", img1=" + img1 + ", coName=" + coName + ", erId=" + erId + ", estDate="
				+ estDate + ", inputDate=" + inputDate + ", memberNum=" + memberNum + "]";
	}
}
