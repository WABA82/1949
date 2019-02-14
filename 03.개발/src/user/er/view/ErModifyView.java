package user.er.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import user.er.controller.ErModifyController;
import user.er.vo.ErDetailVO;

@SuppressWarnings("serial")
public class ErModifyView extends JDialog {

	/* 인스턴스 변수 */
	JTextField jtfSubject, jtfSal;
	JTextArea jtaErDesc;
	JCheckBox jchJava, jchJspServlet, jchSpring, jchOracle, jchHTML, jchCSS, jchLinux, jchJS;
	JComboBox<String> jcbRank, jcbEducation, jcbLoc, jcbHireType, jcbPortfolio;
	JButton jbReg /* 수정버튼 */, jbDelete, jbCancel;

	public ErModifyView(ErMgMtView emmv, ErDetailVO edvo, String erNum, String erId) {
		super(emmv, "구인 정보 수정", true);/* 창의 제목 */
		
		/* 컴포넌트 생성하기 */
		// 이미지아이콘 : 회사로고
		// 라벨들
		//C:\dev\1949\03.개발\src\img\coImg\sssdi_logo.png
		JLabel jlImage = new JLabel(new ImageIcon("C:/dev/1949/03.개발/src/file/coImg/"+edvo.getImg1()));
		JLabel jlName = new JLabel("이름");
		JLabel jlTel = new JLabel("연락처");
		JLabel jlEmail = new JLabel("이메일");
		JLabel jlSubject = new JLabel("제목");
		JLabel jlCoName = new JLabel("회사명");
		JLabel jlRank = new JLabel("직급");
		JLabel jlSal = new JLabel("급여");
		JLabel jlEducation = new JLabel("학력");
		JLabel jlLoc = new JLabel("근무지역");
		JLabel jlHireType = new JLabel("고용형태");
		JLabel jlPortfolio = new JLabel("포트폴리오");

		// 텍스트 필드 - 수정불가
		JTextField jtfName = new JTextField(edvo.getName());
		JTextField jtfTel = new JTextField(edvo.getTel());
		JTextField jtfEmail = new JTextField(edvo.getEmail());
		JTextField jtfCoName = new JTextField(edvo.getCoName());
		jtfName.setEditable(false);
		jtfTel.setEditable(false);
		jtfEmail.setEditable(false);
		jtfCoName.setEditable(false);

		// 텍스트 필드
		jtfSubject = new JTextField(edvo.getSubject());
		jtfSal = new JTextField(String.valueOf(edvo.getSal()));

		// 상세정보
		jtaErDesc = new JTextArea(edvo.getErDesc());
		jtaErDesc.setRows(5);
		jtaErDesc.setColumns(25);
		JScrollPane jspErDesc = new JScrollPane(jtaErDesc);

		// 필요기술스택
		jchJava = new JCheckBox("Java");
		jchJspServlet = new JCheckBox("JspServlet");
		jchSpring = new JCheckBox("Spring");
		jchOracle = new JCheckBox("Oracle");
		jchHTML = new JCheckBox("HTML");
		jchCSS = new JCheckBox("HTML");
		jchLinux = new JCheckBox("Linux");
		jchJS = new JCheckBox("JavaScript");

		// 구인정보 콤보박스
		String[] rItem = { "신입", "경력" };
		jcbRank = new JComboBox<>(rItem);
		// rank: 'N' - 신입 'C' - 경력
		if (edvo.getRank().equals("C")) {
			jcbRank.setSelectedIndex(1);
		} else if (edvo.getRank().equals("N")) {
			jcbRank.setSelectedIndex(0);
		}	
			
		String[] eItem = { "고졸", "초대졸", "석사", "박사" };
		jcbEducation = new JComboBox<>(eItem);
		for(int i=0; i<eItem.length;i++) {
			if(edvo.getLoc().equals(eItem[i])) {
				jcbEducation.setSelectedIndex(i);
			}
		}
		
		String[] lItem = { "서울", "경기", "인천", "대전", "세종", "충남", "충북", "광주", "전남", "전북", "대구", "경북", "부산", "울산", "경남",
				"강원", "제주", "전국" };
		jcbLoc = new JComboBox<>(lItem);
		for(int i=0; i<lItem.length;i++) {
			if(edvo.getLoc().equals(lItem[i])) {
				jcbLoc.setSelectedIndex(i);
			}
		}
		
		String[] hItem = { "정규직", "계약직", "프리 " };
		jcbHireType = new JComboBox<>(hItem);
		// hiretype 'C' - 정규직'N' - 비정규직'F' - 프리랜서
		for(int i=0; i<hItem.length;i++) {
			if(edvo.getLoc().equals(hItem[i])) {
				jcbHireType.setSelectedIndex(i);
			}
		}
		
		String[] pItem = { "YES", "NO" };
		jcbPortfolio = new JComboBox<>(pItem);
		if(edvo.getPortfolio().equals("Y")) {
			jcbPortfolio.setSelectedIndex(0);
		}else if(edvo.getPortfolio().equals("N")) {
			jcbPortfolio.setSelectedIndex(1);
		}

		// 버튼들
		jbReg = new JButton("수정");
		jbDelete = new JButton("삭제");
		jbCancel = new JButton("닫기");

		/* 컴포넌트 크기 설정 */
		setLayout(null); // 수동배치 설정

		// 상단
		JPanel imgPanel = new JPanel();
		imgPanel.setLayout(null);
		imgPanel.setBorder(new TitledBorder("회사로고"));
		imgPanel.setBounds(15, 8, 185, 192);
		jlImage.setBounds(7, 15, 170, 170);
		imgPanel.add(jlImage);

		jlName.setBounds(210, 40, 60, 20);
		jlTel.setBounds(210, 80, 60, 20);
		jlEmail.setBounds(210, 120, 60, 20);
		jtfName.setBounds(255, 37, 115, 23);
		jtfTel.setBounds(255, 77, 115, 23);
		jtfEmail.setBounds(255, 117, 115, 23);

		// 구인정보
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBounds(15, 205, 365, 180);
		infoPanel.setBorder(new TitledBorder("구인정보"));
		jlSubject.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlCoName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
		jlRank.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlSal.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlEducation.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlLoc.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlHireType.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlPortfolio.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));

		jlSubject.setBounds(10, 25, 60, 20);
		jlCoName.setBounds(10, 55, 60, 20);
		jlRank.setBounds(10, 85, 60, 20);
		jlSal.setBounds(10, 115, 60, 20);

		jlEducation.setBounds(180, 55, 60, 20);
		jlLoc.setBounds(180, 85, 60, 20);
		jlHireType.setBounds(180, 115, 60, 20);
		jlPortfolio.setBounds(180, 145, 80, 20);

		jtfSubject.setBounds(50, 25, 300, 20);
		jtfCoName.setBounds(50, 55, 100, 20);
		jcbRank.setBounds(50, 85, 100, 20);
		jtfSal.setBounds(50, 115, 100, 20);

		jcbEducation.setBounds(250, 55, 100, 25);
		jcbLoc.setBounds(250, 85, 100, 25);
		jcbHireType.setBounds(250, 115, 100, 25);
		jcbPortfolio.setBounds(250, 145, 100, 25);

		infoPanel.add(jlSubject);
		infoPanel.add(jlCoName);
		infoPanel.add(jlRank);
		infoPanel.add(jlSal);
		infoPanel.add(jlEducation);
		infoPanel.add(jlLoc);
		infoPanel.add(jlHireType);
		infoPanel.add(jlPortfolio);

		infoPanel.add(jtfSubject);
		infoPanel.add(jtfCoName);
		infoPanel.add(jcbRank);
		infoPanel.add(jtfSal);
		infoPanel.add(jcbEducation);
		infoPanel.add(jcbLoc);
		infoPanel.add(jcbHireType);
		infoPanel.add(jcbPortfolio);

		// 상세정보
		JPanel erDescPanel = new JPanel();
		erDescPanel.setLayout(new BorderLayout());
		erDescPanel.setBorder(new TitledBorder("상세정보"));
		erDescPanel.setBounds(15, 455, 365, 110);
		erDescPanel.add(jspErDesc);

		// 필요 기술스택
		JPanel skillGridPanel = new JPanel();
		skillGridPanel.setLayout(new GridLayout(2, 4, 0, 0));
		skillGridPanel.setBounds(15, 390, 365, 60);
		skillGridPanel.setBorder(new TitledBorder("필요 기술스택"));
		skillGridPanel.add(jchJava);
		skillGridPanel.add(jchJspServlet);
		skillGridPanel.add(jchSpring);
		skillGridPanel.add(jchOracle);
		skillGridPanel.add(jchHTML);
		skillGridPanel.add(jchCSS);
		skillGridPanel.add(jchLinux);
		skillGridPanel.add(jchJS);
		
		if(edvo.getListSkill().contains("Java")) {
			jchJava.setSelected(true);
		}else if(edvo.getListSkill().contains("JspServlet")){
			jchJspServlet.setSelected(true);
		}else if(edvo.getListSkill().contains("Spring")){
			jchSpring.setSelected(true);
		}else if(edvo.getListSkill().contains("Oracle")){
			jchOracle.setSelected(true);
		}else if(edvo.getListSkill().contains("HTML")){
			jchHTML.setSelected(true);
		}else if(edvo.getListSkill().contains("CSS")){
			jchCSS.setSelected(true);
		}else if(edvo.getListSkill().contains("Linux")){
			jchLinux.setSelected(true);
		}else if(edvo.getListSkill().contains("JS")){
			jchJS.setSelected(true);
		}
		

		// 최하단 버튼
		jbReg.setBounds(100, 575, 60, 25);
		jbDelete.setBounds(170, 575, 60, 25);
		jbCancel.setBounds(240, 575, 60, 25);

		/* 프레임에 배치 */
		add(imgPanel);
		add(jlName);
		add(jlTel);
		add(jlEmail);
		add(jtfName);
		add(jtfTel);
		add(jtfEmail);
		add(infoPanel);
		add(erDescPanel);
		add(skillGridPanel);
		add(jbReg);
		add(jbDelete);
		add(jbCancel);

		/* 이벤트등록 */
		ErModifyController emc = new ErModifyController(this,erNum,erId);
		jbCancel.addActionListener(emc);
		jbReg.addActionListener(emc);
		jbDelete.addActionListener(emc);
		addWindowListener(emc);
		
		/* 프레임 크기 설정 및 가시화 */
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 410, 660);
		setVisible(true);
	}// 생성자

	/******** getter ********/

	public JTextField getJtfSubject() {
		return jtfSubject;
	}

	public JTextField getJtfSal() {
		return jtfSal;
	}

	public JTextArea getJtaErDesc() {
		return jtaErDesc;
	}

	public JCheckBox getJchJava() {
		return jchJava;
	}

	public JCheckBox getJchJspServlet() {
		return jchJspServlet;
	}

	public JCheckBox getJchSpring() {
		return jchSpring;
	}

	public JCheckBox getJchOracle() {
		return jchOracle;
	}

	public JCheckBox getJchHTML() {
		return jchHTML;
	}

	public JCheckBox getJchCSS() {
		return jchCSS;
	}

	public JCheckBox getJchLinux() {
		return jchLinux;
	}

	public JCheckBox getJchJS() {
		return jchJS;
	}

	public JComboBox<String> getJcbRank() {
		return jcbRank;
	}

	public JComboBox<String> getJcbEducation() {
		return jcbEducation;
	}

	public JComboBox<String> getJcbLoc() {
		return jcbLoc;
	}

	public JComboBox<String> getJcbHireType() {
		return jcbHireType;
	}

	public JComboBox<String> getJcbPortfolio() {
		return jcbPortfolio;
	}

	public JButton getJbReg() {
		return jbReg;
	}

	public JButton getJbDelete() {
		return jbDelete;
	}

	public JButton getJbCancel() {
		return jbCancel;
	}

	/******** getter ********/

/*	public static void main(String[] args) {
		new ErModifyView(null, null, null);
	}// main
*/
}// class
