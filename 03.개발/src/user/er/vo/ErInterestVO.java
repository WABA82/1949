package user.er.vo;

/**
 * �������ڿ��� ���ɱ����� �����쿡 ��� ����ִ� 
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
