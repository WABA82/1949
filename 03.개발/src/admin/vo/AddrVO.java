package admin.vo;

public class AddrVO {
	
	String seq, zipcode, sido, gugun, dong, bunji;

	public AddrVO(String seq, String zipcode, String sido, String gugun, String dong, String bunji) {
		this.seq = seq;
		this.zipcode = zipcode;
		this.sido = sido;
		this.gugun = gugun;
		this.dong = dong;
		this.bunji = bunji;
	}

	public String getSeq() {
		return seq;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getSido() {
		return sido;
	}

	public String getGugun() {
		return gugun;
	}

	public String getDong() {
		return dong;
	}

	public String getBunji() {
		return bunji;
	}

	@Override
	public String toString() {
		return "AddrVO [seq=" + seq + ", zipcode=" + zipcode + ", sido=" + sido + ", gugun=" + gugun + ", dong=" + dong
				+ ", bunji=" + bunji + "]";
	}
}
