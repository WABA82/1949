package user.ee.vo;

public class EeInsertVO {

	private String eeNum, img, rank,  loc, education, portfolio, extResume, inputDate, eeId;

	public EeInsertVO() {
		super();
	}

	public EeInsertVO(String eeNum, String img, String rank, String loc, String education, String portfolio,
			String extResume, String inputDate, String eeId) {
		super();
		this.eeNum = eeNum;
		this.img = img;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.portfolio = portfolio;
		this.extResume = extResume;
		this.inputDate = inputDate;
		this.eeId = eeId;
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

	public String getInputDate() {
		return inputDate;
	}

	public String getEeId() {
		return eeId;
	}

	@Override
	public String toString() {
		return "EeInsertVO [eeNum=" + eeNum + ", img=" + img + ", rank=" + rank + ", loc=" + loc + ", education="
				+ education + ", portfolio=" + portfolio + ", extResume=" + extResume + ", inputDate=" + inputDate
				+ ", eeId=" + eeId + "]";
	}
	
	
	
}//EeInsertVO
