package user.er.vo;

/**
 *	회사 정보 입력하는 VO
 *	19.02.17 김건하
 * @author 82102
 */
public class CoInsertVO {

	private String erId, img1, img2, img3, img4, coName, estDate, coDesc;
	private int memberNum;
	
	public CoInsertVO( String erId, String img1, String img2, String img3, String img4, String coName, String estDate,
			String coDesc, int memberNum) {
		this.erId = erId;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
		this.img4 = img4;
		this.coName = coName;
		this.estDate = estDate;
		this.coDesc = coDesc;
		this.memberNum = memberNum;
	}//생성자
	
	
	public String getErId() {
		return erId;
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

	public String getCoName() {
		return coName;
	}

	public String getEstDate() {
		return estDate;
	}

	public String getCoDesc() {
		return coDesc;
	}

	public int getMemberNum() {
		return memberNum;
	}

	@Override
	public String toString() {
		return "CoInsertVO [erId=" + erId + ", img1=" + img1 + ", img2=" + img2 + ", img3=" + img3 + ", img4=" + img4
				+ ", coName=" + coName + ", estDate=" + estDate + ", coDesc=" + coDesc + ", memberNum=" + memberNum
				+ "]";
	}
	
	
}//class
