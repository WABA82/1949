package user.ee.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import user.ee.vo.DetailErInfoVO;

/**
 * 일반사용자 - 구인정보보기 - 상세구인정보 View
 */
@SuppressWarnings("serial")
public class EeDetailErView extends JDialog {

	/* 인스턴스 변수 선언 */
	JLabel jlHeart;
	JButton jbCoInfo, jbApply, jbClose;

	public EeDetailErView(EeHiringView ehv, DetailErInfoVO dei_vo, String s, String st) {
		super(ehv,"상세 구인 정보",true);/* 창의 제목 */

		/* 컴포넌트 생성 */
		// 라벨들
		JLabel jlImage = new JLabel("이미지");
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
		String[] rItem = { "신입", "경력" };
		JComboBox<String> jcbRank = new JComboBox<>(rItem);
		String[] eItem = { "고졸", "초대졸", "석사", "박사" };
		JComboBox<String> jcbEducation = new JComboBox<>(eItem);
		String[] lItem = { "서울", "경기", "인천", "대전", "세종", "충남", "충북", "광주", "전남", "전북", "대구", "경북", "부산", "울산", "경남",
				"강원", "제주", "전국" };
		JComboBox<String> jcbLoc = new JComboBox<>(lItem);
		String[] hItem = { "정규직", "계약직", "프리 " };
		JComboBox<String> jcbHireType = new JComboBox<>(hItem);
		String[] pItem = { "YES", "NO" };
		JComboBox<String> jcbPortfolio = new JComboBox<>(pItem);

		// 상세정보
		JTextArea jtaErDesc = new JTextArea();
		jtaErDesc.setRows(6);
		jtaErDesc.setColumns(25);
		JScrollPane jspErDesc = new JScrollPane(jtaErDesc);

		JLabel jlSkill1 = new JLabel("이미지1");
		jlSkill1.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill2 = new JLabel("이미지2");
		jlSkill2.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill3 = new JLabel("이미지3");
		jlSkill3.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill4 = new JLabel("이미지4");
		jlSkill4.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill5 = new JLabel("이미지5");
		jlSkill5.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill6 = new JLabel("이미지6");
		jlSkill6.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill7 = new JLabel("이미지7");
		jlSkill7.setBorder(new LineBorder(Color.BLACK));
		JLabel jlSkill8 = new JLabel("이미지8");
		jlSkill8.setBorder(new LineBorder(Color.BLACK));

		// 버튼들
		jlHeart = new JLabel("하트");
		jlHeart.setBorder(new LineBorder(Color.BLACK));
		jbCoInfo = new JButton("회사정보");
		jbApply = new JButton("지원하기");
		jbClose = new JButton("닫기");

		/* 컴포넌트 크기 설정 */
		JPanel wrapPanel = new JPanel();
		wrapPanel.setLayout(null);
		wrapPanel.setBorder(new TitledBorder("구인정보"));

		jlSubject.setBounds(15, 30, 60, 20);
		wrapPanel.add(jlSubject);

		JPanel imgPanel = new JPanel();
		imgPanel.setBounds(20, 60, 170, 170);
		imgPanel.setBorder(new TitledBorder("회사로고"));
		imgPanel.add(jlImage);
		wrapPanel.add(imgPanel);

		jlCoName.setBounds(20, 240, 60, 20);
		jlRank.setBounds(20, 270, 60, 20);
		jlSal.setBounds(20, 300, 60, 20);
		jlName.setBounds(205, 100, 60, 20);
		jlTel.setBounds(205, 130, 60, 20);
		jlEmail.setBounds(205, 160, 60, 20);
		jlEducation.setBounds(205, 240, 60, 20);
		jlLoc.setBounds(205, 270, 60, 20);
		jlHireType.setBounds(205, 300, 60, 20);
		jlPortfolio.setBounds(205, 330, 80, 20);
		jtfSubject.setBounds(50, 25, 330, 25);
		jtfCoName.setBounds(70, 240, 120, 25);
		jcbRank.setBounds(70, 270, 120, 25);
		jtfSal.setBounds(70, 300, 120, 25);
		jtfName.setBounds(255, 100, 125, 25);
		jtfTel.setBounds(255, 130, 125, 25);
		jtfEmail.setBounds(255, 160, 125, 25);
		jcbEducation.setBounds(275, 240, 100, 25);
		jcbLoc.setBounds(275, 270, 100, 25);
		jcbHireType.setBounds(275, 300, 100, 25);
		jcbPortfolio.setBounds(275, 330, 100, 25);

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
		wrapPanel.add(jcbRank);
		wrapPanel.add(jtfSal);
		wrapPanel.add(jtfName);
		wrapPanel.add(jtfTel);
		wrapPanel.add(jtfEmail);

		wrapPanel.add(jcbEducation);
		wrapPanel.add(jcbLoc);
		wrapPanel.add(jcbHireType);
		wrapPanel.add(jcbPortfolio);

		// 상세정보 컴포넌트 크기설정
		JPanel erDescPanel = new JPanel();
		erDescPanel.setLayout(new BorderLayout());
		erDescPanel.setBorder(new TitledBorder("상세정보"));
		erDescPanel.setBounds(15, 360, 365, 120);
		erDescPanel.add(jspErDesc);

		wrapPanel.add(erDescPanel);

		// 필요 기술스택
		JPanel skillGridPanel = new JPanel();
		skillGridPanel.setLayout(null);
		skillGridPanel.setBounds(10, 490, 370, 75);
		skillGridPanel.setBorder(new TitledBorder("필요 기술스택"));
		jlSkill1.setBounds(7, 20, 40, 40);
		jlSkill2.setBounds(52, 20, 40, 40);
		jlSkill3.setBounds(97, 20, 40, 40);
		jlSkill4.setBounds(142, 20, 40, 40);
		jlSkill5.setBounds(187, 20, 40, 40);
		jlSkill6.setBounds(232, 20, 40, 40);
		jlSkill7.setBounds(277, 20, 40, 40);
		jlSkill8.setBounds(322, 20, 40, 40);
		skillGridPanel.add(jlSkill1);
		skillGridPanel.add(jlSkill2);
		skillGridPanel.add(jlSkill3);
		skillGridPanel.add(jlSkill4);
		skillGridPanel.add(jlSkill5);
		skillGridPanel.add(jlSkill6);
		skillGridPanel.add(jlSkill7);
		skillGridPanel.add(jlSkill8);

		wrapPanel.add(skillGridPanel);

		jlHeart.setBounds(65, 570, 30, 30);
		jbCoInfo.setBounds(105, 575, 100, 25);
		jbApply.setBounds(210, 575, 100, 25);
		jbClose.setBounds(315, 575, 60, 25);

		wrapPanel.add(jlHeart);
		wrapPanel.add(jbCoInfo);
		wrapPanel.add(jbApply);
		wrapPanel.add(jbClose);
		
		/* 프레임에 배치 */
		add(wrapPanel);

		/* 프레임 크기 설정 및 가시화 */
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 410, 650);
		setVisible(true);
	}// 생성자

/*	public static void main(String[] args) {
		new EeDetailErView();
	}// main
*/
}// class
