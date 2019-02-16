package admin.vo;

import java.util.List;

public class ErInfoVO {
	
	String img, erNum, erId, name, email, tel, inputDate, subject, coName,
		education, rank, loc, hireType, portfolio, erDesc;
	int sal;
	List<String> listSkill;
	
	public ErInfoVO(String img, String erNum, String erId, String name, String email, String tel, String inputDate,
			String subject, String coName, String education, String rank, String loc, String hireType, String portfolio,
			String erDesc, int sal, List<String> listSkill) {
		this.img = img;
		this.erNum = erNum;
		this.erId = erId;
		this.name = name;
		this.email = email;
		this.tel = tel;
		this.inputDate = inputDate;
		this.subject = subject;
		this.coName = coName;
		this.education = education;
		this.rank = rank;
		this.loc = loc;
		this.hireType = hireType;
		this.portfolio = portfolio;
		this.erDesc = erDesc;
		this.sal = sal;
		this.listSkill = listSkill;
	}

	public String getImg() {
		return img;
	}

	public String getErNum() {
		return erNum;
	}

	public String getErId() {
		return erId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getTel() {
		return tel;
	}

	public String getInputDate() {
		return inputDate;
	}

	public String getSubject() {
		return subject;
	}

	public String getCoName() {
		return coName;
	}

	public String getEducation() {
		return education;
	}

	public String getRank() {
		return rank;
	}

	public String getLoc() {
		return loc;
	}

	public String getHireType() {
		return hireType;
	}

	public String getPortfolio() {
		return portfolio;
	}

	public String getErDesc() {
		return erDesc;
	}

	public int getSal() {
		return sal;
	}

	public List<String> getListSkill() {
		return listSkill;
	}

	@Override
	public String toString() {
		return "ErInfoVO [img=" + img + ", erNum=" + erNum + ", erId=" + erId + ", name=" + name + ", email=" + email
				+ ", tel=" + tel + ", inputDate=" + inputDate + ", subject=" + subject + ", coName=" + coName
				+ ", education=" + education + ", rank=" + rank + ", loc=" + loc + ", hireType=" + hireType
				+ ", portfolio=" + portfolio + ", erDesc=" + erDesc + ", sal=" + sal + ", listSkill=" + listSkill + "]";
	}
}
