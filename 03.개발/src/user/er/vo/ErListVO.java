package user.er.vo;

/**
 * 재현 : 기업사용자의 지원형황 창의 목록을 채울 VO
 * @author owner
 *
 */
public class ErListVO {
	private String erNum, subject, rank, loc, education, hireType, inputDate;

	public ErListVO(String erNum, String subject, String rank, String loc, String education, String hireType, String inputDate) {
		this.erNum = erNum;
		this.subject = subject;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.hireType = hireType;
		this.inputDate = inputDate;
	}// 생성자

	public String getErNum() {
		return erNum;
	}

	public String getSubject() {
		return subject;
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

	@Override
	public String toString() {
		return "ErListVO [erNum=" + erNum + ", subject=" + subject + ", rank=" + rank + ", loc=" + loc + ", education="
				+ education + ", hireType=" + hireType + ", inputDate=" + inputDate + "]";
	}// toString

}// class
