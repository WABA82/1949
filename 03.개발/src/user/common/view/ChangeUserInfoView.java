package user.common.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import user.common.controller.ChangeUserInfoController;
import user.common.vo.UserInfoVO;
import user.ee.view.EeMainView;
import user.er.view.ErMainView;

@SuppressWarnings("serial")
public class ChangeUserInfoView extends JDialog {
	private JTextField jtfId, jtfName, jtfTel, jtfZipcode, jtfAddr1, jtfAddr2, jtfEmail;
	private JPasswordField jpfOriginalPass, jpfNewPass1, jpfNewPass2;
	private JButton jbModify, jbDelete, jbClose, jbAddr;
	
	public ChangeUserInfoView( JFrame jf, UserInfoVO uivo) {
		
		setTitle("ȸ�� ���� ����");
		JLabel jlId = new JLabel("���̵�");
		JLabel jlPw1 = new JLabel("��й�ȣ");
		JLabel jlPw2 = new JLabel("�� ��й�ȣ");
		JLabel jlPw3 = new JLabel("�� ��й�ȣ Ȯ��");
		JLabel jlName = new JLabel("�̸�");
		JLabel jlTel = new JLabel("����ó");
		JLabel jlAddr1 = new JLabel("�ּ�");
		JLabel jlAddr2 = new JLabel("���ּ�");
		JLabel jlEmail = new JLabel("�̸���");

		jtfId = new JTextField(uivo.getId());
		jtfId.setEditable(false);//id ���� ����
		jtfId.setBackground(Color.WHITE);
		jpfOriginalPass = new JPasswordField();
		jpfNewPass1 = new JPasswordField();
		jpfNewPass2 = new JPasswordField();
		jtfName = new JTextField(uivo.getName());
		jtfTel = new JTextField(uivo.getTel());
		jtfZipcode = new JTextField(uivo.getZipcode());
		jtfZipcode.setEditable(false);//�����ȣ ���� ����
		jtfZipcode.setBackground(Color.WHITE);
		jtfAddr1 = new JTextField(uivo.getAddr1());
		jtfAddr1.setEditable(false);//�ּ� ���� ����
		jtfAddr1.setBackground(Color.WHITE);
		jtfAddr2 = new JTextField(uivo.getAddr2());
		jtfEmail = new JTextField(uivo.getEmail());

		jbModify = new JButton("����");
		jbDelete = new JButton("Ż��");
		jbClose = new JButton("�ݱ�");
		jbAddr = new JButton("�ּҰ˻�");
		
		setLayout(null);
		
		jlId.setBounds(32, 35, 80, 30);
		add(jlId);
		
		jlPw1.setBounds(32, 75, 80, 30);
		add(jlPw1);
		
		jlPw2.setBounds(32, 116, 100, 30);
		add(jlPw2);
		
		jlPw3.setBounds(22, 156, 100, 30);
		add(jlPw3);
		
		jlName.setBounds(32, 196, 80, 30);
		add(jlName);
		
		jlTel.setBounds(32, 236, 80, 30);
		add(jlTel);
		
		jlEmail.setBounds(32, 276, 80, 30);
		add(jlEmail);
		
		jlAddr1.setBounds(31, 318, 80, 30);
		add(jlAddr1);
		
		jlAddr2.setBounds(30, 398, 80, 30);
		add(jlAddr2);
		
		jtfId.setBounds(123, 36, 200, 30);
		add(jtfId);
		
		jpfOriginalPass.setBounds(123, 76, 200, 30);
		add(jpfOriginalPass);
		
		jpfNewPass1.setBounds(123, 117, 200, 30);
		add(jpfNewPass1);
		
		jpfNewPass2.setBounds(123, 157, 200, 30);
		add(jpfNewPass2);
		
		jtfName.setBounds(123, 197, 200, 30);
		add(jtfName);
		
		jtfTel.setBounds(123, 237, 200, 30);
		add(jtfTel);
		
		jtfEmail.setBounds(123, 278, 200, 30);
		add(jtfEmail);
		
		jtfZipcode.setBounds(123, 319, 102, 30);
		add(jtfZipcode);
		
		jtfAddr1.setBounds(123, 359, 200, 30);
		add(jtfAddr1);
		
		jtfAddr2.setBounds(123, 399, 200, 30);
		add(jtfAddr2);
		
		jbAddr.setBounds(231, 319, 92, 30);
		add(jbAddr);
		
		jbModify.setBounds(33, 455, 92, 30);
		add(jbModify);
		
		jbDelete.setBounds(133, 455, 92, 30);
		add(jbDelete);
		
		jbClose.setBounds(233, 455, 92, 30);
		add(jbClose);
		
		ChangeUserInfoController cuic=new ChangeUserInfoController(jf, this, uivo);
		addWindowListener(cuic);
		jbModify.addActionListener(cuic);
		jbDelete.addActionListener(cuic);
		jbClose.addActionListener(cuic);
		jbAddr.addActionListener(cuic);
		
		jpfOriginalPass.addKeyListener(cuic);
		jpfNewPass1.addKeyListener(cuic);
		jpfNewPass2.addKeyListener(cuic);
		jtfName.addKeyListener(cuic);
		jtfTel.addKeyListener(cuic);
		jtfAddr2.addKeyListener(cuic);
		jtfEmail.addKeyListener(cuic);
	
		
		setBounds(jf.getX()+50,jf.getY()+50,390,580);
		setVisible(true);
	
	}
	
	/*public static void main(String[] args) {
		UserInfoVO uivo=new UserInfoVO(id, name, tel, seq, zipcode, addr1, addr2, email);
		new ChangeUserInfoView(null, uivo);
	}*/

	

	public JTextField getJtfId() {
		return jtfId;
	}

	public JTextField getJtfName() {
		return jtfName;
	}

	public JTextField getJtfTel() {
		return jtfTel;
	}

	public JTextField getJtfZipcode() {
		return jtfZipcode;
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

	public JPasswordField getJpfOriginalPass() {
		return jpfOriginalPass;
	}

	public JPasswordField getJpfNewPass1() {
		return jpfNewPass1;
	}

	public JPasswordField getJpfNewPass2() {
		return jpfNewPass2;
	}

	public JButton getJbModify() {
		return jbModify;
	}

	public JButton getJbDelete() {
		return jbDelete;
	}

	public JButton getJbClose() {
		return jbClose;
	}

	public JButton getJbAddr() {
		return jbAddr;
	}
	

}
