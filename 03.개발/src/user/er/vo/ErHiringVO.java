package user.er.vo;

public class ErHiringVO {
	private String eeNum,img, name, rank, loc, education, portfolio, gender,inputDate;
	private int age;
	public ErHiringVO(String eeNum, String img, String name, String rank, String loc, String education, String portfolio, String gender, String inputDate, int age) {
		super();
		this.eeNum = eeNum;
		this.img = img;
		this.name = name;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.portfolio = portfolio;
		this.gender = gender;
		this.inputDate = inputDate;
		this.age = age;
	}
	public String getEeNum() {
		return eeNum;
	}
	public String getImg() {
		return img;
	}
	public String getName() {
		return name;
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
	public String getPortfolio() {
		return portfolio;
	}
	public String getGender() {
		return gender;
	}
	public String getInputDate() {
		return inputDate;
	}
	public int getAge() {
		return age;
	}
	
	
}
