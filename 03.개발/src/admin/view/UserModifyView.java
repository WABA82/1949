package admin.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UserModifyView extends JDialog {
	private JTextField jtfId, jtfName, jtfSsn1, jtfSsn2, jtfTel, jtfZip, jtfAddr1, jtfAddr2,jtfEmail, jtfAnswer, jtfInputDate;
	private JPasswordField jpfPass;
	private JButton jbModify, jbSearchAddr, jbRemove, jbClose;
	private JComboBox<String> jcbQuestion, jcbUser;
	public UserModifyView() {
		
		setTitle("회원 상세 정보"); //다음 작업에 상위 프레임으로 타이틀이랑 모달구현하기(x좌표 y좌표 받아서 화면띄우기)
		JLabel jlId = new JLabel("아이디");
		JLabel jlPw = new JLabel("비밀번호");
		JLabel jlName= new JLabel("이름");
		JLabel jlSsn = new JLabel("주민번호");
		JLabel jlTel = new JLabel("연락처");
		JLabel jlAddr1 = new JLabel("주소");
		JLabel jlAddr2 = new JLabel("상세주소");
		JLabel jlEmail =new JLabel("이메일");
		JLabel jlQuestion = new JLabel("인증질문");
		JLabel jlAnswer = new JLabel("질문답");
		JLabel jlUser = new JLabel("회원타입");
		JLabel jlInputDate = new JLabel("가입일");
		JLabel jlSlash = new JLabel("-");
		
		jtfId = new JTextField();
		jpfPass = new JPasswordField();
		jtfName = new JTextField();
		jtfSsn1 = new JTextField();
		jtfSsn2 = new JTextField();
		jtfTel = new JTextField(); 
		jtfZip = new JTextField();
		jtfAddr1 = new JTextField();
		jtfAddr2 = new JTextField();
		jtfEmail = new JTextField();
		jtfAnswer = new JTextField();
		jtfInputDate = new JTextField();
		
		jbModify = new JButton("수정");
		jbSearchAddr = new JButton("주소검색");
		jbRemove = new JButton("삭제");
		jbClose = new JButton("닫기");
		
		jcbQuestion = new JComboBox<String>();
		jcbUser = new JComboBox<String>();
		
		setLayout(null);
		
		jlId.setBounds(42, 35, 80, 30);
		add(jlId);
		
		jlPw.setBounds(42, 75, 80, 30);
		add(jlPw);
		
		jlName.setBounds(42, 115, 80, 30);
		add(jlName);
		
		jlSsn.setBounds(42, 155, 80, 30);
		add(jlSsn);
		
		jlTel.setBounds(42, 195, 80, 30);
		add(jlTel);
		
		jlAddr1.setBounds(41, 235, 80, 30);
		add(jlAddr1);
		
		jlAddr2.setBounds(40, 315, 80, 30);
		add(jlAddr2);
		
		jlEmail.setBounds(41, 355, 80, 30);
		add(jlEmail);
		
		jlQuestion.setBounds(41, 396, 80, 30);
		add(jlQuestion);
		
		jlAnswer.setBounds(41, 435, 80, 30);
		add(jlAnswer);
		
		jlUser.setBounds(41, 476, 80, 30);
		add(jlUser);
		
		jlInputDate.setBounds(41, 515, 80, 30);
		add(jlInputDate);
		
		jlSlash.setBounds(229, 156, 16, 30);
		add(jlSlash);
		
		jtfId.setBounds(133, 36, 200, 30);
		add(jtfId);
		
		jpfPass.setBounds(133, 76, 200, 30);
		add(jpfPass);
		
		jtfName.setBounds(133, 116, 200, 30);
		add(jtfName);
		
		jtfSsn1.setBounds(133, 156, 92, 30);
		add(jtfSsn1);
		
		jtfSsn2.setBounds(241, 156, 92, 30);
		add(jtfSsn2);
		
		jtfTel.setBounds(133, 196, 200, 30);
		add(jtfTel);
		
		jtfZip.setBounds(133, 236, 102, 30);
		add(jtfZip);
		
		jtfAddr1.setBounds(133, 276, 200, 30);
		add(jtfAddr1);
		
		jtfAddr2.setBounds(133, 316, 200, 30);
		add(jtfAddr2);
		
		jtfEmail.setBounds(133, 356, 200, 30);
		add(jtfEmail);
		
		jtfAnswer.setBounds(133, 436, 200, 30);
		add(jtfAnswer);
		
		jtfInputDate.setBounds(133, 516, 200, 30);
		add(jtfInputDate);
		
		jcbQuestion.setBounds(133, 396, 200, 30);
		jcbQuestion.addItem("내 혈액형은? ");
		jcbQuestion.addItem("초등학교 시절 가장 친했던 친구는? ");
		add(jcbQuestion);
		
		jcbUser.setBounds(133, 476, 200, 30);
		jcbUser.addItem("구직자");
		jcbUser.addItem("구인자");
		add(jcbUser);
		
		jbSearchAddr.setBounds(241, 236, 92, 30);
		add(jbSearchAddr);
		
		jbModify.setBounds(75, 576, 70, 30);
		add(jbModify);
		
		jbRemove.setBounds(157, 576, 70, 30);
		add(jbRemove);
		
		jbClose.setBounds(235, 576, 70, 30);
		add(jbClose);
		
		
		setBounds(0,0,390,680);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}
/*	public static void main(String[] args) {
		new UserModifyView();
	}*/
	public JTextField getJtfId() {
		return jtfId;
	}
	public JTextField getJtfName() {
		return jtfName;
	}
	public JTextField getJtfSsn1() {
		return jtfSsn1;
	}
	public JTextField getJtfSsn2() {
		return jtfSsn2;
	}
	public JTextField getJtfTel() {
		return jtfTel;
	}
	public JTextField getJtfZip() {
		return jtfZip;
	}
	public JTextField getJtfAddr1() {
		return jtfAddr1;
	}
	public JTextField getJtfAddr2() {
		return jtfAddr2;
	}
	public JTextField getJtfEmail() {
		return jtfEmail;
	}
	public JTextField getJtfAnswer() {
		return jtfAnswer;
	}
	public JTextField getJtfInputDate() {
		return jtfInputDate;
	}
	public JPasswordField getJpfPass() {
		return jpfPass;
	}
	public JButton getJbModify() {
		return jbModify;
	}
	public JButton getJbSearchAddr() {
		return jbSearchAddr;
	}
	public JButton getJbRemove() {
		return jbRemove;
	}
	public JButton getJbClose() {
		return jbClose;
	}
	public JComboBox<String> getJcbQuestion() {
		return jcbQuestion;
	}
	public JComboBox<String> getJcbUser() {
		return jcbUser;
	}
	
}
