package user.ee.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import user.ee.controller.EeDetailErController;
import user.ee.vo.DetailErInfoVO;

/**
 * 일반사용자 - 구인정보보기 - 상세구인정보 View
 */
@SuppressWarnings("serial")
public class EeDetailErView extends JDialog {

	/* 인스턴스 변수 선언 */
	private JLabel jlHeart;

	private JButton jbCoInfo, jbApply, jbClose; 
	private String erNum,eeId,appStatus,interest;
	private boolean flagHeart;
	
	public EeDetailErView(EeHiringView ehv, DetailErInfoVO deivo, String erNum, String eeId,String appStatus,String interest) {
		super(ehv, "상세구인정보", true);/* 창의 제목 */
		this.erNum = erNum;
		this.eeId = eeId;
		this.appStatus = appStatus;
		this.interest = interest;
		/* 컴포넌트 생성 */
		ImageIcon erLogo = new ImageIcon("C:/dev/1949/03.개발/src/user/img/co/no_co_img1.png");
		// 라벨들
		JLabel jlImage = new JLabel(erLogo);
		JLabel jlSubject = new JLabel("제목");
		JLabel jlCoName = new JLabel("회사명");
		JLabel jlRank = new JLabel("직급");
		JLabel jlSal = new JLabel("급여");
		JLabel jlName = new JLabel("이름");
		JLabel jlTel = new JLabel("연락처");
		JLabel jlEmail = new JLabel("이메일");
		JLabel jlEducation = new JLabel("학력");
		JLabel jlLoc = new JLabel("근무지역");
		JLabel jlHireType = new JLabel("고용형태");
		JLabel jlPortfolio = new JLabel("포트폴리오");

		// 필드
		JTextField jtfSubject = new JTextField();
		JTextField jtfCoName = new JTextField();
		JTextField jtfSal = new JTextField();
		JTextField jtfName = new JTextField();
		JTextField jtfTel = new JTextField();
		JTextField jtfEmail = new JTextField();

		// 콤보박스들
		JTextField jtfRank = new JTextField();
		JTextField jtfEducation = new JTextField();
		JTextField jtfLoc = new JTextField();
		JTextField jtfHireType = new JTextField();
		JTextField jtfPortfolio = new JTextField();

		// 상세정보
		JTextArea jtaErDesc = new JTextArea();
		jtaErDesc.setRows(6);
		jtaErDesc.setColumns(25);
		JScrollPane jspErDesc = new JScrollPane(jtaErDesc);

		ImageIcon imgSkill = new ImageIcon("C:/dev/1949/03.개발/src/admin/img/co/오라클.png");
		JLabel jlSkill1, jlSkill2, jlSkill3, jlSkill4, jlSkill5, jlSkill6, jlSkill7, jlSkill8;
		jlSkill1 = new JLabel(imgSkill);
		jlSkill1.setBorder(new LineBorder(Color.BLACK));
		jlSkill2 = new JLabel("");
		jlSkill2.setBorder(new LineBorder(Color.BLACK));
		jlSkill3 = new JLabel("");
		jlSkill3.setBorder(new LineBorder(Color.BLACK));
		jlSkill4 = new JLabel("");
		jlSkill4.setBorder(new LineBorder(Color.BLACK));
		jlSkill5 = new JLabel("");
		jlSkill5.setBorder(new LineBorder(Color.BLACK));
		jlSkill6 = new JLabel("");
		jlSkill6.setBorder(new LineBorder(Color.BLACK));
		jlSkill7 = new JLabel("");
		jlSkill7.setBorder(new LineBorder(Color.BLACK));
		jlSkill8 = new JLabel("");
		jlSkill8.setBorder(new LineBorder(Color.BLACK));

		// 버튼들
		System.out.println(interest);
		if(interest.equals("0")) {
			ImageIcon heart = new ImageIcon("C:/dev/1949/03.개발/가데이터/하트/b_heart.png");
			jlHeart = new JLabel(heart);
		}else if(interest.equals("1")) {
			ImageIcon heart = new ImageIcon("C:/dev/1949/03.개발/가데이터/하트/r_heart.png");
			jlHeart = new JLabel(heart);
			flagHeart=true;
		}
		jbCoInfo = new JButton("회사정보");
		jbApply = new JButton("지원하기");
		jbClose = new JButton("닫기");

		jtfSubject.setEditable(false);
		jtfName.setEditable(false);
		jtfTel.setEditable(false);
		jtfEmail.setEditable(false);
		jtfCoName.setEditable(false);

		jtfSal.setEditable(false);
		jtfRank.setEditable(false);
		jtfEducation.setEditable(false);
		jtfLoc.setEditable(false);
		jtfHireType.setEditable(false);
		jtfPortfolio.setEditable(false);

		/* 컴포넌트 크기 설정 */
		JPanel wrapPanel = new JPanel();
		wrapPanel.setLayout(null);
		wrapPanel.setBorder(new TitledBorder("구인정보"));

		jlSubject.setBounds(15, 25, 60, 20);
		jtfSubject.setBounds(50, 22, 330, 23);

		JPanel imgPanel = new JPanel();
		imgPanel.setLayout(null);
		imgPanel.setBorder(new TitledBorder("회사로고"));
		imgPanel.setBounds(15, 52, 185, 192);
		jlImage.setBounds(7, 15, 170, 170);
		imgPanel.add(jlImage);

		jlCoName.setBounds(215, 90, 60, 20);
		jlName.setBounds(215, 120, 60, 20);
		jlTel.setBounds(215, 150, 60, 20);
		jlEmail.setBounds(215, 180, 60, 20);
		jtfCoName.setBounds(265, 90, 110, 25);
		jtfName.setBounds(265, 120, 110, 25);
		jtfTel.setBounds(265, 150, 110, 25);
		jtfEmail.setBounds(265, 180, 110, 25);

		jlHireType.setBounds(20, 255, 60, 20);
		jlRank.setBounds(20, 285, 60, 20);
		jlSal.setBounds(20, 315, 60, 20);
		jlEducation.setBounds(215, 255, 60, 20);
		jlLoc.setBounds(215, 285, 60, 20);
		jlPortfolio.setBounds(215, 315, 80, 20);

		jtfHireType.setBounds(77, 255, 120, 25);
		jtfRank.setBounds(77, 285, 120, 25);
		jtfSal.setBounds(77, 315, 120, 25);
		jtfEducation.setBounds(285, 255, 90, 25);
		jtfLoc.setBounds(285, 285, 90, 25);
		jtfPortfolio.setBounds(285, 315, 90, 25);

		wrapPanel.add(jlSubject);
		wrapPanel.add(imgPanel);
		wrapPanel.add(jlCoName);
		wrapPanel.add(jlRank);
		wrapPanel.add(jlSal);
		wrapPanel.add(jlName);
		wrapPanel.add(jlTel);
		wrapPanel.add(jlEmail);
		wrapPanel.add(jlEducation);
		wrapPanel.add(jlLoc);
		wrapPanel.add(jlHireType);
		wrapPanel.add(jlPortfolio);

		wrapPanel.add(jtfSubject);
		wrapPanel.add(jtfCoName);
		wrapPanel.add(jtfName);
		wrapPanel.add(jtfTel);
		wrapPanel.add(jtfEmail);
		wrapPanel.add(jtfHireType);
		wrapPanel.add(jtfRank);
		wrapPanel.add(jtfSal);
		wrapPanel.add(jtfEducation);
		wrapPanel.add(jtfLoc);
		wrapPanel.add(jtfPortfolio);

		// 상세정보 컴포넌트 크기설정
		JPanel erDescPanel = new JPanel();
		erDescPanel.setLayout(new BorderLayout());
		erDescPanel.setBorder(new TitledBorder("상세정보"));
		erDescPanel.setBounds(15, 350, 365, 135);
		erDescPanel.add(jspErDesc);

		wrapPanel.add(erDescPanel);

		// 필요 기술스택
		JPanel skillGridPanel = new JPanel();
		skillGridPanel.setLayout(null);
		skillGridPanel.setBounds(10, 490, 370, 75);
		skillGridPanel.setBorder(new TitledBorder("필요 기술스택"));
		jlSkill1.setBounds(7, 25, 40, 40);
		jlSkill2.setBounds(52, 25, 40, 40);
		jlSkill3.setBounds(97, 25, 40, 40);
		jlSkill4.setBounds(142, 25, 40, 40);
		jlSkill5.setBounds(187, 25, 40, 40);
		jlSkill6.setBounds(232, 25, 40, 40);
		jlSkill7.setBounds(277, 25, 40, 40);
		jlSkill8.setBounds(322, 25, 40, 40);

		JLabel[] arrLbSkill = { jlSkill1, jlSkill2, jlSkill3, jlSkill4, jlSkill5, jlSkill6, jlSkill7, jlSkill8 };
		for (int i = 0; i < deivo.getSkill().size(); i++) {
			if (deivo.getSkill().get(i).equals("Java")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.개발/가데이터/Test_ Skill_Image/Java.png"));
			} else if (deivo.getSkill().get(i).equals("JSP/Servlet")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.개발/가데이터/Test_ Skill_Image/jsp_servelt.png"));

			} else if (deivo.getSkill().get(i).equals("Spring")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.개발/가데이터/Test_ Skill_Image/spring.png"));

			} else if (deivo.getSkill().get(i).equals("Oracle")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.개발/가데이터/Test_ Skill_Image/oracle.png"));

			} else if (deivo.getSkill().get(i).equals("HTML")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.개발/가데이터/Test_ Skill_Image/html.png"));

			} else if (deivo.getSkill().get(i).equals("CSS")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.개발/가데이터/Test_ Skill_Image/css.png"));

			} else if (deivo.getSkill().get(i).equals("Linux")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.개발/가데이터/Test_ Skill_Image/linux.png"));

			} else if (deivo.getSkill().get(i).equals("JS")) {
				arrLbSkill[i].setIcon(new ImageIcon("C:/dev/1949/03.개발/가데이터/Test_ Skill_Image/js.png"));
			}
			skillGridPanel.add(arrLbSkill[i]);
		}// end for 

		wrapPanel.add(skillGridPanel);

		jlHeart.setBounds(65, 572, 32, 32);
		jbCoInfo.setBounds(105, 575, 100, 25);
		jbApply.setBounds(210, 575, 100, 25);
		jbClose.setBounds(315, 575, 60, 25);

		wrapPanel.add(jlHeart);
		wrapPanel.add(jbCoInfo);
		if (appStatus == null) {
			wrapPanel.add(jbApply);
		}
		wrapPanel.add(jbClose);

		/* 프레임에 배치 */
		add(wrapPanel);

		jtfSubject.setText(deivo.getSubject());
		jlImage.setIcon(new ImageIcon("C:/dev/1949/03.개발/src/img/coImg/" + deivo.getImg1()));
		jtfCoName.setText(deivo.getCoName());
		jtfName.setText(deivo.getName());
		jtfTel.setText(deivo.getTel());
		jtfEmail.setText(deivo.getEmail());
		jtfEducation.setText(deivo.getEdudation());
		jtfLoc.setText(deivo.getLoc());
		jtfSal.setText(String.valueOf(deivo.getSal()));
		jtfPortfolio.setText(deivo.getPortfolio());
		jtaErDesc.setText(deivo.getErDesc());
		jtaErDesc.setEditable(false);

		// hiretype 'C' - 정규직'N' - 비정규직'F' - 프리랜서
		if (deivo.getHireType().equals("C")) {
			jtfHireType.setText("정규직");
		} else if (deivo.getHireType().equals("N")) {
			jtfHireType.setText("비정규직");
		} else if (deivo.getHireType().equals("F")) {
			jtfHireType.setText("프리랜서");
		}

		// rank: 'N' - 신입 'C' - 경력
		if (deivo.getRank().equals("C")) {
			jtfRank.setText("경력");
		} else if (deivo.getRank().equals("N")) {
			jtfRank.setText("신입");

		}

		
		
		//이벤트
		EeDetailErController edec = new EeDetailErController(this,erNum,eeId,flagHeart);
		jlHeart.addMouseListener(edec);
		jbCoInfo.addActionListener(edec);
		jbClose.addActionListener(edec);
		jbApply.addActionListener(edec);

		/* 프레임 크기 설정 및 가시화 */
		setBounds(100, 100, 410, 660);
		setVisible(true);

	}// 생성자

	// 단위테스용.
//	public static void main(String[] args) {
//		new EeDetailErView(null, null, null, null, null);
//	}
	
	public JLabel getJlHeart() {
		return jlHeart;
	}

	public JButton getJbCoInfo() {
		return jbCoInfo;
	}

	public JButton getJbApply() {
		return jbApply;
	}

	public JButton getJbClose() {
		return jbClose;
	}

}// class
