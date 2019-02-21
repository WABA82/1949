package user.er.vo;

import java.util.List;

public class ErAddVO {
	private String erId, subject, education, rank, loc, hireType, portfolio, erDesc;
	private int sal;
	private List<String> listSkill;
	public ErAddVO(String erId, String subject, String education, String rank, String loc, String hireType,
			String portfolio, String erDesc, int sal, List<String> listSkill) {
		super();
		this.erId = erId;
		this.subject = subject;
		this.education = education;
		this.rank = rank;
		this.loc = loc;
		this.hireType = hireType;
		this.portfolio = portfolio;
		this.erDesc = erDesc;
		this.sal = sal;
		this.listSkill = listSkill;
	}
	public String getErId() {
		return erId;
	}
	public String getSubject() {
		return subject;
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

	
	
}
