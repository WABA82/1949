package user.common.vo;

public class ErMainVO {
	
	private String erId, name, img1, activation;

	public ErMainVO(String erId, String name, String img1, String activation) {
		this.erId = erId;
		this.name = name;
		this.img1 = img1;
		this.activation = activation;
	}

	public String getErId() {
		return erId;
	}
	public String getName() {
		return name;
	}
	public String getImg1() {
		return img1;
	}
	public String getActivation() {
		return activation;
	}
	@Override
	public String toString() {
		return "ErMainVO [erId=" + erId + ", name=" + name + ", img1=" + img1 + ", activation=" + activation + "]";
	}
}
