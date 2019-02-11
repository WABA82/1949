package user.ee.vo;


/**
 * 
 * @author ����
 * �Ϲݻ������ ���ɱ��θ���� ��� VO.
 */
public class EeInterestVO {
	private String erNum, subject, coName, rank, loc, education, hireType, inputDate;
	private int sal;
	
	public EeInterestVO(String erNum, String subject, String coName, String rank, String loc, String education, String hireType, String inputDate, int sal) {
		this.erNum = erNum;
		this.subject = subject;
		this.coName = coName;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.hireType = hireType;
		this.inputDate = inputDate;
		this.sal = sal;
	}// ������

	public String getErNum() {
		return erNum;
	}

	public String getSubject() {
		return subject;
	}

	public String getCoName() {
		return coName;
	}

	public String getRank() {
		return rank;
	}

	public String getLoc() {
		return loc;
	}

	public String getEducation() {
		return education;
	}

	public String getHireType() {
		return hireType;
	}

	public String getInputDate() {
		return inputDate;
	}

	public int getSal() {
		return sal;
	}

	@Override
	public String toString() {
		return "EeInterestVO [erNum=" + erNum + ", subject=" + subject + ", coName=" + coName + ", rank=" + rank
				+ ", loc=" + loc + ", education=" + education + ", hireType=" + hireType + ", inputDate=" + inputDate
				+ ", sal=" + sal + "]";
	}// toString
	
	
}// class
