package user.ee.vo;

/**
 *	김건하
 *	트랜잭션에 사용하는 activation 변경을 위한 VO
 * @author 82102
 */
public class ActivationVO {

	private String  id;

	public ActivationVO(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ActivationVO [id=" + id + "]";
	}

	
}//class
