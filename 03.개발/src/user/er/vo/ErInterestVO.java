package user.er.vo;

/**
 * 기업사용자에서 관심구직자 윈도우에 목록 띄워주는 
 * @author owner
 *
 */
public class ErInterestVO {
	private String eeNum, erId;

	public ErInterestVO(String eeNum, String erId) {
		super();
		this.eeNum = eeNum;
		this.erId = erId;
	}

	public String getEeNum() {
		return eeNum;
	}

	public String getErId() {
		return erId;
	}

	@Override
	public String toString() {
		return "ErInterestVO [eeNum=" + eeNum + ", erId=" + erId + "]";
	}
	
	
}
