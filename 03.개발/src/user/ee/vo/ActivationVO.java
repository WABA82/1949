package user.ee.vo;

/**
 *	�����
 *	Ʈ����ǿ� ����ϴ� activation ������ ���� VO
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
