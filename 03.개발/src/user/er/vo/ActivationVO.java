package user.er.vo;

/**
 *	트랜잭션을 이용한 activaion 변경 VO
 * @author 82102
 */
public class ActivationVO {

	private String id;

	public ActivationVO(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ActivationVO [id=" + id + "]";
	}
	

}//main
