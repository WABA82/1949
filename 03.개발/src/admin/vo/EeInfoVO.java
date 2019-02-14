package admin.vo;

public class EeInfoVO {
	String eeNum, img, id, name, rank, loc, education, portfolio, gender, inputDate, extResume;
	int age;
	
	public EeInfoVO(String eeNum, String img, String id, String name, String rank, String loc, String education,
			String portfolio, String gender, String inputDate, String extResume, int age) {
		this.eeNum = eeNum;
		this.img = img;
		this.id = id;
		this.name = name;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.portfolio = portfolio;
		this.gender = gender;
		this.inputDate = inputDate;
		this.extResume = extResume;
		this.age = age;
	}

	public String getEeNum() {
		return eeNum;
	}

	public String getImg() {
		return img;
	}

	public String getId() {
		return id;
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

	public String getExtResume() {
		return extResume;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "EeInfoVO [eeNum=" + eeNum + ", img=" + img + ", id=" + id + ", name=" + name + ", rank=" + rank
				+ ", loc=" + loc + ", education=" + education + ", portfolio=" + portfolio + ", gender=" + gender
				+ ", inputDate=" + inputDate + ", extResume=" + extResume + ", age=" + age + "]";
	}
}
