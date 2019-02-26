package user.er.vo;

/**
 *	회사 정보 가져오는 VO
 *	트랜잭션으로 erId 추가
 *	19.02.17 김건하
 * @author 82102
 */
public class CoInfoVO {

	private String erId, coNum, coName, img1, img2, img3, img4, estDate, coDesc;
	private int memberNum;
	
	public CoInfoVO(String erId, String coNum, String coName, String img1, String img2, String img3, String img4, String estDate,
			String coDesc, int memberNum) {
		this.erId=erId;
		this.coNum = coNum;
		this.coName = coName;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
		this.img4 = img4;
		this.estDate = estDate;
		this.coDesc = coDesc;
		this.memberNum = memberNum;
		
	}
	
	public String getErId() {
		return erId;
	}
	public String getCoNum() {
		return coNum;
	}
	public String getCoName() {
		return coName;
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
		return "CoInfoVO [erId=" + erId + ", coNum=" + coNum + ", coName=" + coName + ", img1=" + img1 + ", img2="
				+ img2 + ", img3=" + img3 + ", img4=" + img4 + ", estDate=" + estDate + ", coDesc=" + coDesc
				+ ", memberNum=" + memberNum + "]";
	}

	
	
	
}//class
