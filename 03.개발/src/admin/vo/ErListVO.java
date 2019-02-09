package admin.vo;

public class ErListVO {
	String erNum, subject, coName, erId, name, tel, rank, loc, education, hireType, inputDate;
	int sal;
	
	public ErListVO(String erNum, String subject, String coName, String erId, String name, String tel, String rank,
			String loc, String education, String hireType, String inputDate, int sal) {
		this.erNum = erNum;
		this.subject = subject;
		this.coName = coName;
		this.erId = erId;
		this.name = name;
		this.tel = tel;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.hireType = hireType;
		this.inputDate = inputDate;
		this.sal = sal;
	}

	public String getErNum() {
		return erNum;
	}

	public String getSubject() {
		return subject;
	}

	public String getCoName() {
		return coName;
	}

	public String getErId() {
		return erId;
	}

	public String getName() {
		return name;
	}

	public String getTel() {
		return tel;
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
		return "ErListVO [erNum=" + erNum + ", subject=" + subject + ", coName=" + coName + ", erId=" + erId + ", name="
				+ name + ", tel=" + tel + ", rank=" + rank + ", loc=" + loc + ", education=" + education + ", hireType="
				+ hireType + ", inputDate=" + inputDate + ", sal=" + sal + "]";
	}
}
