package user.er.view;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *	김건하 19.02.08
 * @author owner
 */
public class AppDetailView extends JDialog {

	private JButton jbExtRsm, jbAccept, jbRefuse, jbClose;
	
	public AppDetailView() {
//		super(arg0, arg1, arg2)
		
		ImageIcon ii=new ImageIcon();
		JLabel jlImage=new JLabel(ii);
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
		

		//Label
		JLabel jlName=new JLabel("이름");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlTel=new JLabel("연락처");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		JLabel jlEmail=new JLabel("이메일");
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
		
		
		//연락처 이메일,
		//Label
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

		//text
		JTextField jtfName=new JTextField(10);
		add(jtfName);
		jtfName.setBounds(315, 22, 130, 20);
		
		JTextField jtfTel=new JTextField(10);
		add(jtfTel);
		jtfTel.setBounds(315, 62, 130, 20);
		
		JTextField jtfEmail=new JTextField(10);
		add(jtfEmail);
		jtfEmail.setBounds(315, 102, 130, 20);
		
		JTextField jtfRank=new JTextField(10);
		add(jtfRank);
		jtfRank.setBounds(315, 142, 130, 20);
		
		JTextField jtfLoc=new JTextField(10);
		add(jtfLoc);
		jtfLoc.setBounds(315, 182, 130, 20);
		
		JTextField jtfEdu=new JTextField(10);
		add(jtfEdu);
		jtfEdu.setBounds(315, 222, 130, 20);
		
		JTextField jtfAge=new JTextField(10);
		add(jtfAge);
		jtfAge.setBounds(315, 262, 130, 20);
		
		JTextField jtfPort=new JTextField(10);
		add(jtfPort);
		jtfPort.setBounds(315, 302, 130, 20);
		
		JTextField jtfGender=new JTextField(10);
		add(jtfGender);
		jtfGender.setBounds(315, 342, 130, 20);
		
		setLayout(null);
		setBounds(100, 100, 490, 520);

		/* 이벤트 등록 */
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
		setVisible(true);
		setResizable(false);
		
	}//생성자
	
	public static void main(String[] args) {
		new AppDetailView();
	}//main
	
}//class
