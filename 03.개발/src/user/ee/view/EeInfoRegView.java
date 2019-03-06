package user.ee.view;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import user.ee.controller.EeInfoRegController;
import user.ee.vo.EeRegVO;
import user.util.JTextFieldLimit;
/**
 *	 기본 정보 관리 -김건하-
 *	19.02.07
 * @author owner
 */
@SuppressWarnings("serial")
public class EeInfoRegView extends JDialog {

	private JButton jbRegister, jbRegisterExt, jbRegisterImg, jbClose;
	private JComboBox<String>jcbRank, jcbLoc, jcbEducation, jcbPortfolio;
	private JTextField jtfExtResume;
	private JLabel jlImage;
	private JTextField jtfName, jtfAge, jtfGender, jtfId;
//	private  EeRegVO ervo;
	
	
	public EeInfoRegView(EeMainView emv, EeRegVO ervo) {
		super(emv, "기본 정보 관리",true);
//		this.ervo=ervo;
		
		//image
		ImageIcon ii=new ImageIcon("C:/dev/1949/03.개발/src/user/img/ee/no_ee_img.png");
		jlImage=new JLabel(ii);
		jlImage.setBorder(new TitledBorder("구직자 이미지"));
		jlImage.setBounds(38, 20, 160, 225);
		add(jlImage);
		
		jbRegisterImg=new JButton("이미지 변경");
		add(jbRegisterImg);
		jbRegisterImg.setBounds(42, 260, 150, 30);
		
		jbRegisterExt = new JButton("외부이력서 등록");
		add(jbRegisterExt);
		jbRegisterExt.setBounds(50, 360, 150, 30);
		
		jbRegister = new JButton("등록");
		add(jbRegister);
		jbRegister.setBounds(240, 360, 100, 30);
		
		jbClose=new JButton("닫기");
		add(jbClose);
		jbClose.setBounds(355, 360, 100, 30);
		
		
		//Label
		JLabel jlName=new JLabel("이름");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlRank=new JLabel("직급");
		jlRank.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlLoc=new JLabel("근무지역");
		jlLoc.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlEdu=new JLabel("학력");
		jlEdu.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlAge=new JLabel("나이");
		jlAge.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlPort=new JLabel("포트폴리오 유무");
		jlPort.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlGender=new JLabel("성별");
		jlGender.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlResume=new JLabel("외부이력서");
		jlResume.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		
		//Combobox  jcbRank, jcbLoc, jcbEducation, jcbPortfolio;
		String[] rank= {"신입", "경력"};
		jcbRank =new JComboBox<>(rank);
		String[] loc= {"서울", "경기", "인천", "대전", "세종", "충남", "충북", "광주", "전남", "전북", "대구", "경북", "부산", "울산", "경남",
				"강원", "제주", "전국"};
		jcbLoc=new JComboBox<>(loc);
		String[] edu= {"고졸", "초대졸", "대졸", "석사", "박사"};
		jcbEducation=new JComboBox<>(edu);
		String[] port= {"YES", "NO"};
		jcbPortfolio=new JComboBox<>(port);
		
		//Label
		add(jlName);
		jlName.setBounds(265, 20, 50, 20);
		add(jlRank);
		jlRank.setBounds(265, 60, 50, 20);
		add(jlLoc);
		jlLoc.setBounds(255, 100, 70, 20);
		add(jlEdu);
		jlEdu.setBounds(265, 138, 50, 30);
		add(jlAge);
		jlAge.setBounds(265, 175, 50, 30);
		add(jlPort);
		jlPort.setBounds(215, 215, 100, 30);
		add(jlGender);
		jlGender.setBounds(265, 255, 50, 30);
		add(jlResume);
		jlResume.setBounds(235, 295, 100, 30);
		
		//JText
		jtfName=new JTextField();
		jtfName.setDocument((new JTextFieldLimit(10)));
		jtfName.setText(ervo.getName());
		
		add(jtfName);
		jtfName.setBounds(325, 22, 130, 20);
		jtfName.setEditable(false);
		
		jtfAge=new JTextField(String.valueOf(ervo.getAge()));
		add(jtfAge);
		jtfAge.setBounds(325, 182, 130, 20);
		jtfAge.setEditable(false);
		
		jtfGender=new JTextField(ervo.getGender());
		add(jtfGender);
		jtfGender.setBounds(325, 262, 130, 20);
		jtfGender.setEditable(false);
		
		jtfId=new JTextField();
		add(jtfId);
		
		
		jtfExtResume=new JTextField(10);
		add(jtfExtResume);
		jtfExtResume.setBounds(325, 302, 130, 20);
		jtfExtResume.setEditable(false);
		
		//Combobox
		add(jcbRank);
		jcbRank.setBounds(325,62,130,20);
		add(jcbLoc);
		jcbLoc.setBounds(325,102,130,20);
		add(jcbEducation);
		jcbEducation.setBounds(325,142,130,20);
		add(jcbPortfolio);
		jcbPortfolio.setBounds(325,222,130,20);
		
		//이벤트 등록
		EeInfoRegController eirc=new EeInfoRegController(this, ervo.getEeId(), emv);
		addWindowListener(eirc);
		jbRegisterExt.addActionListener(eirc);
		jbClose.addActionListener(eirc);
		jbRegisterImg.addActionListener(eirc);
		jbRegister.addActionListener(eirc);
//		
		setLayout(null);
		setBounds( emv.getX()+250, emv.getY()+100, 490, 465);
		setResizable(false);
		setVisible(true);
	}//EeInfoRegView

	
	public JButton getJbRegister() {
		return jbRegister;
	}

	public JButton getJbRegisterExt() {
		return jbRegisterExt;
	}

	public JButton getJbRegisterImg() {
		return jbRegisterImg;
	}

	public JButton getJbClose() {
		return jbClose;
	}

	public JComboBox<String> getJcbRank() {
		return jcbRank;
	}

	public JComboBox<String> getJcbLoc() {
		return jcbLoc;
	}

	public JComboBox<String> getJcbEducation() {
		return jcbEducation;
	}

	public JComboBox<String> getJcbPortfolio() {
		return jcbPortfolio;
	}

	public JTextField getJtfExtResume() {
		return jtfExtResume;
	}

	public JLabel getJlImage() {
		return jlImage;
	}

	

	public JTextField getJtfName() {
		return jtfName;
	}


	public JTextField getJtfAge() {
		return jtfAge;
	}


	public JTextField getJtfGender() {
		return jtfGender;
	}
	
}//class
