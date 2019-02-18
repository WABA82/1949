package user.ee.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import user.common.vo.EeMainVO;
import user.ee.controller.EeMainController;

public class EeMainView extends JFrame {

	/* 임시로 쓰는 데이터입니다. */
//	public static final String EE_ID = "";
	private EeMainVO emvo;
	private JButton jbEeInfo, jbErInfo, jbInterestEr, jbApp;
	private JLabel jlUserInfo, jlLogOut, jlActivation, jlImg;
//	private JTextField jtfName;
	//선의비밀번호
	//3nGGeCFFfCaP

	public EeMainView(EeMainVO emvo) {
		//로그인컨트롤에서 매개변수로 emvo받아서 이름과, 이미지, activation를 표시
		super("1949 - 일반사용자 ["+emvo.getName()+"]");
		this.emvo=emvo;
		JLabel jlAct = new JLabel("기본정보 등록여부 : ");
		jlImg = new JLabel(new ImageIcon("C:/dev/1949/03.개발/src/file/eeImg/"+emvo.getImg()));
		
		jlImg.setBorder(new TitledBorder("내 이미지"));
		jlActivation = new JLabel( emvo.getActivation() );
			
		jlUserInfo = new JLabel(new ImageIcon("C:/dev/1949/03.개발/src/file/eeimg/회원정보관리아이콘.png") );
		jlLogOut = new JLabel(new ImageIcon("C:/dev/1949/03.개발/src/file/eeimg/로그아웃이미지.png"));

		jbEeInfo = new JButton("기본정보 관리");
		jbErInfo = new JButton("구인정보 보기");
		jbInterestEr = new JButton("관심 구인정보");
		jbApp = new JButton("지원현황");

		setLayout(null);
		
		JLabel jlUserInfoMsg=new JLabel("회원정보 관리");
		JLabel jlLogoutMsg=new JLabel("로그아웃");

		jlAct.setBounds(20, 20, 150, 30);
		jlActivation.setBounds(145, 20, 30, 30);
		jlImg.setBounds(15, 70, 200, 260);
		jlUserInfo.setBounds(260, 10, 100, 30); // 회원정보관리(이미지로 수정 예정)
		jlUserInfoMsg.setBounds(267,35,100,30);
		jlLogOut.setBounds(340, 10, 100, 30); // 로그아웃(이미지로 수정 예정)
		jlLogoutMsg.setBounds(363,35,100,30);

		jbEeInfo.setBounds(250, 70, 200, 50);
		jbErInfo.setBounds(250, 140, 200, 50);
		jbInterestEr.setBounds(250, 210, 200, 50);
		jbApp.setBounds(250, 280, 200, 50);

		add(jlAct);
		add(jlActivation);
		add(jlImg);
		add(jlUserInfoMsg);
		add(jlLogoutMsg);

		add(jlUserInfo);
		add(jlLogOut);
		add(jbEeInfo);
		add(jbErInfo);
		add(jbInterestEr);
		add(jbApp);

		EeMainController emc = new EeMainController(this, emvo);
		jbEeInfo.addActionListener(emc);
		jbErInfo.addActionListener(emc);
		jbInterestEr.addActionListener(emc);
		jbApp.addActionListener(emc);
		jlUserInfo.addMouseListener(emc);
		jlLogOut.addMouseListener(emc);

		addWindowListener(emc);

		setBounds(500, 200, 475, 385);
		setResizable(false);
		setVisible(true);
	}

	public JLabel getJlActivation() {
		return jlActivation;
	}

	public JButton getJbEeInfo() {
		return jbEeInfo;
	}

	public JButton getJbErInfo() {
		return jbErInfo;
	}

	public JButton getJbInterestEr() {
		return jbInterestEr;
	}

	public JButton getJbApp() {
		return jbApp;
	}

	public JLabel getJlUserInfo() {
		return jlUserInfo;
	}

	public JLabel getJlLogOut() {
		return jlLogOut;
	}

	public JLabel getJlImg() {
		return jlImg;
	}
	
	//단위 테스트 나중에 지우기.
	public static void main(String[] args) {
		EeMainVO emvo  = new EeMainVO("gong1", "공의선", "ee5.jpg", "N");
		new EeMainView(emvo);
	}// main
	
}// class
