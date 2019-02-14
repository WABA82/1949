package user.common.vo;

public class EeMainVO {

	private String eeId, name, img, activation;

	public EeMainVO(String eeId,String name, String img, String activation) {
		this.eeId=eeId;
		this.name = name;
		this.img = img;
		this.activation = activation;
	}

	public String getEeId() {
		return name;
	}

	public String getName() {
		return name;
	}

	public String getImg() {
		return img;
	}

	public String getActivation() {
		return activation;
	}

	@Override
	public String toString() {
		return "EeMainVO [eeId=" + eeId + ", name=" + name + ", img=" + img + ", activation=" + activation + "]";
	}


	
}