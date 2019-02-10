package user.er.view;

import javax.sql.rowset.serial.SerialRef;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import user.common.vo.ErMainVO;
import user.ee.controller.EeMainController;
import user.er.controller.ErMainController;

public class ErMainView extends JFrame {

	private JButton jbCoMgmt, jbEeInfo, jbErMgmt, jbApp, jbInterestEe;
	private JLabel jlUserInfo, jlLogOut, jlActivation;
	//////////수정중
	public ErMainView(ErMainVO emv) {
//		super("1949 - 기업사용자 ["+emv.getName()+"]");
		
		JLabel jlAct = new JLabel("회사정보 등록여부 : ");
		JLabel jlImg = new JLabel(new ImageIcon("C:/Users/owner/youngRepositories/1949/03.개발/src/user/img/eeImg/temp_no_image.png"));
		jlImg.setBorder(new TitledBorder("회사 이미지"));
		
		jlActivation = new JLabel("O"/*emv.getActivation()*/);
		jlUserInfo = new JLabel("회원정보관리");
		jlLogOut = new JLabel("로그아웃");
		
		jbCoMgmt = new JButton("회사정보 관리");
		jbEeInfo = new JButton("구직정보 보기");
		jbErMgmt = new JButton("구인정보 관리");
		jbApp = new JButton("지원현황");
		jbInterestEe = new JButton("관심 구직자 관리");
		
		setLayout(null);
		
		jlAct.setBounds(20, 20, 150, 30);
		jlActivation.setBounds(145,20,30,30);
		jlImg.setBounds(15, 70, 210, 180);
		jlUserInfo.setBounds(260, 15, 100, 30); // 회원정보관리(이미지로 수정 예정)
		jlLogOut.setBounds(380, 15, 100, 30); // 로그아웃(이미지로 수정 예정)
		
		jbCoMgmt.setBounds(250, 70, 200, 50);
		jbEeInfo.setBounds(250, 140, 200, 50);
		jbErMgmt.setBounds(250, 210, 200, 50);
		jbApp.setBounds(250, 280, 200, 50);
		jbInterestEe.setBounds(250, 350, 200, 50);
		
		add(jlAct);
		add(jlActivation);
		add(jlImg);
		
		add(jlUserInfo);
		add(jlLogOut);
		add(jbCoMgmt);
		add(jbEeInfo);
		add(jbErMgmt);
		add(jbApp);
		add(jbInterestEe);
		
		ErMainController emc = new ErMainController(this, emv.getErId());
		jbCoMgmt.addActionListener(emc);
		jbEeInfo.addActionListener(emc);
		jbErMgmt.addActionListener(emc);
		jbApp.addActionListener(emc);
		jbInterestEe.addActionListener(emc);
		jlUserInfo.addMouseListener(emc);
		jlLogOut.addMouseListener(emc);
		
		addWindowListener(emc);

		setBounds(500, 200, 475, 450);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ErMainView(null);
	}
	
	public JButton getJbCoMgmt() {
		return jbCoMgmt;
	}
	public JButton getJbEeInfo() {
		return jbEeInfo;
	}
	public JButton getJbErMgmt() {
		return jbErMgmt;
	}
	public JButton getJbApp() {
		return jbApp;
	}
	public JButton getJbInterestEe() {
		return jbInterestEe;
	}
	public JLabel getJlUserInfo() {
		return jlUserInfo;
	}
	public JLabel getJlLogOut() {
		return jlLogOut;
	}
	public JLabel getJlActivation() {
		return jlActivation;
	}
	
	
}
