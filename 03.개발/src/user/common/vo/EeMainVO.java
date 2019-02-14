package user.common.vo;

public class EeMainVO {

	private String name, img, activation;

	public EeMainVO(String name, String img, String activation) {
		this.name = name;
		this.img = img;
		this.activation = activation;
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
		return "EeMainVO [name=" + name + ", img=" + img + ", activation=" + activation + "]";
	}

	
}