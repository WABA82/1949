package user.er.vo;

import java.util.List;

public class ErDetailVO {
	private String erNum, img1, name, tel, email, subject, coName, education, rank
		,loc, hireType, portfolio, erDesc;
	private int sal;
	private List<String> listSkill;
	public ErDetailVO(String erNum, String img1, String name, String tel, String email, String subject, String coName,
			String education, String rank, String loc, String hireType, String portfolio, String erDesc, int sal,
			List<String> listSkill) {
		super();
		this.erNum = erNum;
		this.img1 = img1;
		this.name = name;
		this.tel = tel;
		this.email = email;
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
	public String getErNum() {
		return erNum;
	}
	public String getImg1() {
		return img1;
	}
	public String getName() {
		return name;
	}
	public String getTel() {
		return tel;
	}
	public String getEmail() {
		return email;
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
		return "ErDetailVO [erNum=" + erNum + ", img1=" + img1 + ", name=" + name + ", tel=" + tel + ", email=" + email
				+ ", subject=" + subject + ", coName=" + coName + ", education=" + education + ", rank=" + rank
				+ ", loc=" + loc + ", hireType=" + hireType + ", portfolio=" + portfolio + ", erDesc=" + erDesc
				+ ", sal=" + sal + ", listSkill=" + listSkill + "]";
	}
	
	
	
}
