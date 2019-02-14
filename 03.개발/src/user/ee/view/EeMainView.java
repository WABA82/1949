package user.ee.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import com.sun.corba.se.impl.orbutil.closure.Constant;

import user.common.vo.EeMainVO;
import user.ee.controller.EeMainController;

public class EeMainView extends JFrame {

	/* 임시로 쓰는 데이터입니다. */
	public static final String EE_ID="kun90";
	
	private JButton jbEeInfo, jbErInfo, jbInterestEr, jbApp;
	private JLabel jlUserInfo, jlLogOut, jlActivation;
	 
	public EeMainView(EeMainVO emv) {
//		super("1949 - 일반사용자 ["+emv.getName()+"]");
		
		JLabel jlAct = new JLabel("기본정보 등록여부 : ");
		JLabel jlImg = new JLabel(new ImageIcon("C:/dev/1949/03.개발/가데이터/구직자사진/150x200px/각키.jpg"));
		jlImg.setBorder(new TitledBorder("내 이미지"));
		
		jlActivation = new JLabel("O"/*emv.getActivation()*/);
		jlUserInfo = new JLabel("회원정보관리");
		jlLogOut = new JLabel("로그아웃");
		
		jbEeInfo = new JButton("기본정보 관리");
		jbErInfo = new JButton("구인정보 보기");
		jbInterestEr = new JButton("관심 구인정보");
		jbApp = new JButton("지원현황");
		
		setLayout(null);
		
		jlAct.setBounds(20, 20, 150, 30);
		jlActivation.setBounds(145,20,30,30);
		jlImg.setBounds(15, 70, 200, 260);
		jlUserInfo.setBounds(260, 15, 100, 30); // 회원정보관리(이미지로 수정 예정)
		jlLogOut.setBounds(380, 15, 100, 30); // 로그아웃(이미지로 수정 예정)
		
		jbEeInfo.setBounds(250, 70, 200, 50);
		jbErInfo.setBounds(250, 140, 200, 50);
		jbInterestEr.setBounds(250, 210, 200, 50);
		jbApp.setBounds(250, 280, 200, 50);
		
		add(jlAct);
		add(jlActivation);
		add(jlImg);
		
		add(jlUserInfo);
		add(jlLogOut);
		add(jbEeInfo);
		add(jbErInfo);
		add(jbInterestEr);
		add(jbApp);
		

		EeMainController emc = new EeMainController(this,emv);
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

}


