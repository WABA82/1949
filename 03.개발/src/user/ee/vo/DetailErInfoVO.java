package user.ee.vo;

import java.util.List;

public class DetailErInfoVO {
	private String erNum, subject, name, tel, email, inputDate, img1, coName, edudation, rank, loc, hireType, portfolio,
			erDesc, interest;
	private int sal;
	private List<String> skill;

	public DetailErInfoVO(String erNum, String subject, String name, String tel, String email, String inputDate,
			String img1, String coName, String edudation, String rank, String loc, String hireType, String portfolio,
			String erDesc, String interest, int sal, List<String> skill) {
		super();
		this.erNum = erNum;
		this.subject = subject;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.inputDate = inputDate;
		this.img1 = img1;
		this.coName = coName;
		this.edudation = edudation;
		this.rank = rank;
		this.loc = loc;
		this.hireType = hireType;
		this.portfolio = portfolio;
		this.erDesc = erDesc;
		this.interest = interest;
		this.sal = sal;
		this.skill = skill;
	}

	public String getErNum() {
		return erNum;
	}

	public String getSubject() {
		return subject;
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
	public String getInputDate() {
		return inputDate;
	}
	public String getImg1() {
		return img1;
	}
	public String getCoName() {
		return coName;
	}
	public String getEdudation() {
		return edudation;
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
	public String getInterest() {
		return interest;
	}
	public int getSal() {
		return sal;
	}
	public List<String> getSkill() {
		return skill;
	}
	
	
	
}
