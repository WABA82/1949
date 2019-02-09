package user.ee.vo;

/**
 *	19.02.09 ±Ë∞««œ
 *	VO ¿€º∫
 * @author 82102
 */
public class EeMainVO {

	private String	eeId, name, img, activation;

	public EeMainVO() {
		super();
	}

	public EeMainVO(String eeId, String name, String img, String activation) {
		super();
		this.eeId = eeId;
		this.name = name;
		this.img = img;
		this.activation = activation;
	}

	public String getEeId() {
		return eeId;
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
	
	
	
}//class
