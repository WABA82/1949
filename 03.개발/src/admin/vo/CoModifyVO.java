package admin.vo;

public class CoModifyVO {
	private String coNum, coName, estDate, coDesc, img1, img2, img3, img4;
	private int memberNum;
	
	public CoModifyVO(String coNum, String coName, String estDate, String coDesc, String img1, String img2, String img3,
			String img4, int memberNum) {
		this.coNum = coNum;
		this.coName = coName;
		this.estDate = estDate;
		this.coDesc = coDesc;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
		this.img4 = img4;
		this.memberNum = memberNum;
	}

	public String getCoNum() {
		return coNum;
	}

	public String getCoName() {
		return coName;
	}

	public String getEstDate() {
		return estDate;
	}

	public String getCoDesc() {
		return coDesc;
	}

	public String getImg1() {
		return img1;
	}

	public String getImg2() {
		return img2;
	}

	public String getImg3() {
		return img3;
	}

	public String getImg4() {
		return img4;
	}

	public int getMemberNum() {
		return memberNum;
	}

	@Override
	public String toString() {
		return "CoModifyVO [coNum=" + coNum + ", coName=" + coName + ", estDate=" + estDate + ", coDesc=" + coDesc
				+ ", img1=" + img1 + ", img2=" + img2 + ", img3=" + img3 + ", img4=" + img4 + ", memberNum=" + memberNum
				+ "]";
	}
}
