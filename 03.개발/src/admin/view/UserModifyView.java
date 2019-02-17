package admin.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import admin.controller.AdminMgMtController;
import admin.controller.UserModifyController;
import admin.vo.UserInfoVO;

@SuppressWarnings("serial")
public class UserModifyView extends JDialog {
	private JTextField jtfId, jtfName, jtfSsn1, jtfSsn2, jtfTel, jtfZip, jtfAddr1, jtfAddr2,jtfEmail, jtfAnswer, jtfInputDate;
	private JPasswordField jpfPass;
	private JButton jbModify, jbSearchAddr, jbRemove, jbClose;
	private JComboBox<String> jcbQuestion, jcbUser;
	
	public UserModifyView(AdminMgMtView ammv, UserInfoVO uivo, AdminMgMtController ammc) {
		super(ammv, "회원 상세 정보", true);
		
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
		
		jtfId = new JTextField(uivo.getId());
		jtfId.setEditable(false);
		jpfPass = new JPasswordField(uivo.getPass());
		jtfName = new JTextField(uivo.getName());
		jtfSsn1 = new JTextField(uivo.getSsn().substring(0, 6));
		jtfSsn2 = new JTextField(uivo.getSsn().substring(7, 14));
		jtfTel = new JTextField(uivo.getTel()); 
		jtfZip = new JTextField(uivo.getZipcode());
		jtfZip.setEditable(false);
		jtfAddr1 = new JTextField(uivo.getAddr1());
		jtfAddr1.setEditable(false);
		jtfAddr2 = new JTextField(uivo.getAddr2());
		jtfEmail = new JTextField(uivo.getEmail());
		jtfAnswer = new JTextField(uivo.getAnswer());
		jtfInputDate = new JTextField(uivo.getInputDate());
		jtfInputDate.setEditable(false);
		
		jbModify = new JButton("수정");
		jbSearchAddr = new JButton("주소검색");
		jbRemove = new JButton("삭제");
		jbClose = new JButton("닫기");
		
		String[] qItems = { "내 혈액형은?", "가장 친한 친구는?" };
		jcbQuestion = new JComboBox<String>(qItems);
		jcbQuestion.setSelectedIndex(Integer.parseInt(uivo.getQuestionType()));
		
		String[] uItems = { "일반", "기업" };
		jcbUser = new JComboBox<String>(uItems);
		if(uivo.getUserType().equals("E")) {
			jcbUser.setSelectedIndex(0);
		} else {
			jcbUser.setSelectedIndex(1);
		}
		
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
		add(jcbQuestion);
		
		jcbUser.setBounds(133, 476, 200, 30);
		add(jcbUser);
		
		jbSearchAddr.setBounds(241, 236, 92, 30);
		add(jbSearchAddr);
		
		jbModify.setBounds(75, 576, 70, 30);
		add(jbModify);
		
		jbRemove.setBounds(157, 576, 70, 30);
		add(jbRemove);
		
		jbClose.setBounds(235, 576, 70, 30);
		add(jbClose);
		
		UserModifyController umc = new UserModifyController(this, ammv, uivo.getAddrSeq(), ammc);
		jbModify.addActionListener(umc);
		jbRemove.addActionListener(umc);
		jbSearchAddr.addActionListener(umc);
		jbClose.addActionListener(umc);
		addWindowListener(umc);
		
		setBounds(ammv.getX()+500,ammv.getY()+50,390,680);
		setVisible(true);
		
	}
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
