package user.ee.dto;

public class EeHiringCdtDTO {
	private String sort, cdt, coName;

	public EeHiringCdtDTO(String sort, String cdt, String coName) {
		super();
		this.sort = sort;
		this.cdt = cdt;
		this.coName = coName;
	}

	public String getSort() {
		return sort;
	}

	public String getCdt() {
		return cdt;
	}

	public String getCoName() {
		return coName;
	}
}
