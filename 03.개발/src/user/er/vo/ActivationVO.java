package user.er.vo;

/**
 *	Ʈ������� �̿��� activaion ���� VO
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
