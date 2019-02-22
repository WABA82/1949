package user.ee.vo;

public class EeInsertVO {

	private String eeId, img, rank,  loc, education, portfolio, extResume;

	public EeInsertVO(String eeId, String img, String rank, String loc, String education, String portfolio,
			String extResume) {
		this.eeId = eeId;
		this.img = img;
		this.rank = rank;
		this.loc = loc;
		this.education = education;
		this.portfolio = portfolio;
		this.extResume = extResume;
	}//������

	//getter
	public String getEeId() {
		return eeId;
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
		return "EeInsertVO [eeId=" + eeId + ", img=" + img + ", rank=" + rank + ", loc=" + loc + ", education="
				+ education + ", portfolio=" + portfolio + ", extResume=" + extResume + "]";
	}

	
	
	
}//EeInsertVO
