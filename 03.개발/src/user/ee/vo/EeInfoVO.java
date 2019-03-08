package user.ee.vo;

public class EeInfoVO {

	private String  eeId, eeNum, img, name, rank, loc, education, portfolio, gender, extResume;
	private int age;
	
	public EeInfoVO() {
		super();
	}

	public EeInfoVO(String eeId, String eeNum, String img, String name, String rank, String loc, String education, String portfolio,
			String gender, String extResume, int age) {
		super();
		this.eeId=eeId;
		this.eeNum = eeNum;
		this.img = img;
		this.name = name;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.portfolio = portfolio;
		this.gender = gender;
		this.extResume = extResume;
		this.age = age;
	}

	public String getEeId() {
		return eeId;
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

	public String getExtResume() {
		return extResume;
	}

	public int getAge() {
		return age;
	}
	
}//class
