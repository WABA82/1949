package user.er.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import user.er.controller.AppDetailController;

/**
 * 김건하 19.02.08 : 지원현황 - 상세 지원 현황 - 지원자 상세 정보
 * 
 * @author owner
 */
@SuppressWarnings("serial")
public class AppDetailView extends JDialog {

	/* 인스턴스 변수 */
	private JLabel jlImage;
	private JTextField jtfName, jtfTel, jtfEmail, jtfRank, jtfLoc, jtfEdu, jtfAge, jtfPort, jtfGender;
	private JButton jbExtRsm, jbAccept, jbRefuse, jbClose;

	public AppDetailView(AppListView alv, String app_num) {
		super(alv, "지원자 상세 정보", true);

		jlImage = new JLabel();
		jlImage.setBorder(new TitledBorder("구직자 이미지"));
		jlImage.setBounds(38, 20, 160, 225);
		add(jlImage);

		jbExtRsm = new JButton("클릭하여 다운로드");
		add(jbExtRsm);
		jbExtRsm.setBounds(305, 375, 145, 27);

		jbAccept = new JButton("지원수락");
		add(jbAccept);
		jbAccept.setBounds(150, 430, 90, 27);

		jbRefuse = new JButton("지원거절");
		add(jbRefuse);
		jbRefuse.setBounds(255, 430, 90, 27);
		jbClose = new JButton("닫기");
		add(jbClose);
		jbClose.setBounds(360, 430, 90, 27);

		// Label
		JLabel jlName = new JLabel("이름");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlTel = new JLabel("연락처");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlEmail = new JLabel("이메일");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlRank = new JLabel("직급");
		jlRank.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlLoc = new JLabel("근무지역");
		jlLoc.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlEdu = new JLabel("학력");
		jlEdu.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlAge = new JLabel("나이");
		jlAge.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlPort = new JLabel("포트폴리오 유무");
		jlPort.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlGender = new JLabel("성별");
		jlGender.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlResume = new JLabel("외부이력서");
		jlResume.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		setLayout(null);// 수동배치 설정

		// 연락처 이메일,
		// Label
		add(jlName);
		jlName.setBounds(255, 20, 50, 20);
		add(jlTel);
		jlTel.setBounds(252, 60, 50, 20);
		add(jlEmail);
		jlEmail.setBounds(248, 100, 70, 20);
		add(jlRank);
		jlRank.setBounds(255, 138, 50, 30);
		add(jlLoc);
		jlLoc.setBounds(242, 175, 70, 30);
		add(jlEdu);
		jlEdu.setBounds(255, 215, 100, 30);
		add(jlAge);
		jlAge.setBounds(255, 255, 50, 30);
		add(jlPort);
		jlPort.setBounds(210, 295, 100, 30);
		add(jlGender);
		jlGender.setBounds(255, 335, 100, 30);
		add(jlResume);
		jlResume.setBounds(228, 375, 100, 30);

		// text
		jtfName = new JTextField(10);
		jtfName.setEditable(false);
		add(jtfName);
		jtfName.setBounds(315, 22, 130, 20);

		jtfTel = new JTextField(10);
		jtfTel.setEditable(false);
		add(jtfTel);
		jtfTel.setBounds(315, 62, 130, 20);

		jtfEmail = new JTextField(10);
		jtfEmail.setEditable(false);
		add(jtfEmail);
		jtfEmail.setBounds(315, 102, 130, 20);

		jtfRank = new JTextField(10);
		jtfRank.setEditable(false);
		add(jtfRank);
		jtfRank.setBounds(315, 142, 130, 20);

		jtfLoc = new JTextField(10);
		jtfLoc.setEditable(false);
		add(jtfLoc);
		jtfLoc.setBounds(315, 182, 130, 20);

		jtfEdu = new JTextField(10);
		jtfEdu.setEditable(false);
		add(jtfEdu);
		jtfEdu.setBounds(315, 222, 130, 20);

		jtfAge = new JTextField(10);
		jtfAge.setEditable(false);
		add(jtfAge);
		jtfAge.setBounds(315, 262, 130, 20);

		jtfPort = new JTextField(10);
		jtfPort.setEditable(false);
		add(jtfPort);
		jtfPort.setBounds(315, 302, 130, 20);

		jtfGender = new JTextField(10);
		jtfGender.setEditable(false);
		add(jtfGender);
		jtfGender.setBounds(315, 342, 130, 20);

		/* 이벤트 등록 */
		AppDetailController adc = new AppDetailController(this, app_num);
		addWindowListener(adc);
		jbExtRsm.addActionListener(adc); // 외부이력서 다운 버튼 이벤트 등록
		jbAccept.addActionListener(adc); // 지원 수락 버튼 이벤트 등록
		jbRefuse.addActionListener(adc); // 지원 거절 버튼 이벤트 등록
		jbClose.addActionListener(adc); // 닫기 버튼 이벤트 등록

		/* 프레임 크기 설정 및 가시화 */
		setBounds(/*(alv.getX() + 100)*/ 150, /*(alv.getY() + 100)*/150, 490, 520);
		setResizable(false);
		setVisible(true);

	}// 생성자

	/****** getter 메서드 ******/

	public JLabel getJlImage() {
		return jlImage;
	}

	public JTextField getJtfName() {
		return jtfName;
	}

	public JTextField getJtfTel() {
		return jtfTel;
	}

	public JTextField getJtfEmail() {
		return jtfEmail;
	}

	public JTextField getJtfRank() {
		return jtfRank;
	}

	public JTextField getJtfLoc() {
		return jtfLoc;
	}

	public JTextField getJtfEdu() {
		return jtfEdu;
	}

	public JTextField getJtfAge() {
		return jtfAge;
	}

	public JTextField getJtfPort() {
		return jtfPort;
	}

	public JTextField getJtfGender() {
		return jtfGender;
	}

	public JButton getJbExtRsm() {
		return jbExtRsm;
	}

	public JButton getJbAccept() {
		return jbAccept;
	}

	public JButton getJbRefuse() {
		return jbRefuse;
	}

	public JButton getJbClose() {
		return jbClose;
	}

	/****** getter 메서드 끝 ******/

	public static void main(String[] args) {
		new AppDetailView(null, "app_000062");
	}// main

}// class
