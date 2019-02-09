package admin.vo;

public class EeListVO {
	
	private String eeNum, img, eeId, name, rank, loc, 
		education, portfolio, gender, extRsm, inputDate;
	private int age;
	
	public EeListVO(String eeNum, String img, String eeId, String name, String rank, String loc, String education,
			String portfolio, String gender, String extRsm, String inputDate, int age) {
		this.eeNum = eeNum;
		this.img = img;
		this.eeId = eeId;
		this.name = name;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.portfolio = portfolio;
		this.gender = gender;
		this.extRsm = extRsm;
		this.inputDate = inputDate;
		this.age = age;
	}

	public String getEeNum() {
		return eeNum;
	}

	public String getImg() {
		return img;
	}

	public String getEeId() {
		return eeId;
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

	public String getExtRsm() {
		return extRsm;
	}

	public String getInputDate() {
		return inputDate;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "EeListVO [eeNum=" + eeNum + ", img=" + img + ", eeId=" + eeId + ", name=" + name + ", rank=" + rank
				+ ", loc=" + loc + ", education=" + education + ", portfolio=" + portfolio + ", gender=" + gender
				+ ", extRsm=" + extRsm + ", inputDate=" + inputDate + ", age=" + age + "]";
	}
	
}
