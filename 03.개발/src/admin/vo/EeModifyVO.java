package admin.vo;

public class EeModifyVO {
	String eeNum, img, rank, loc, education, portfolio, extResume;

	public EeModifyVO(String eeNum, String img, String rank, String loc, String education, String portfolio,
			String extResume) {
		this.eeNum = eeNum;
		this.img = img;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.portfolio = portfolio;
		this.extResume = extResume;
	}

	public String getEeNum() {
		return eeNum;
	}

	public String getImg() {
		return img;
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

	public String getExtResume() {
		return extResume;
	}

	@Override
	public String toString() {
		return "EeModifyVO [eeNum=" + eeNum + ", img=" + img + ", rank=" + rank + ", loc=" + loc + ", education="
				+ education + ", portfolio=" + portfolio + ", extResume=" + extResume + "]";
	}
	
}
