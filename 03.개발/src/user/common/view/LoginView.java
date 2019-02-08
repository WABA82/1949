package user.common.view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import user.common.controller.LoginController;

@SuppressWarnings("serial")
public class LoginView extends JFrame {
	private JTextField jtfId;
	private JPasswordField jpfPass;
	private JButton jbLogin;
	private JLabel jlSignUp, jlFindID,jlFindPass;

	public LoginView() {
		ImageIcon logoImg = new ImageIcon("C:/Users/owner/Desktop/1949logo2.png");
		JLabel jlLogo = new JLabel(logoImg);
		JLabel jlId= new JLabel("���̵�");
		JLabel jlPass= new JLabel("��й�ȣ");
		JLabel jlSlash = new JLabel("/");
		jtfId= new JTextField();
		jpfPass = new JPasswordField();
		jbLogin = new JButton("�α���");
		jlSignUp = new JLabel("ȸ������");
		jlFindID = new JLabel("���̵� ã��");
		jlFindPass = new JLabel("��й�ȣ ã��");
		
		setLayout(null);
		
		jlLogo.setBounds(16, 10, 350, 180);
		add(jlLogo);
		
		jlId.setBounds(12,209,40,30);
		add(jlId);
		
		jlPass.setBounds(12,250,70,30);
		add(jlPass);
		
		jtfId.setBounds(77, 210, 207, 30);
		add(jtfId);
		
		jpfPass.setBounds(77, 251, 207, 30);
		add(jpfPass);
		
		jbLogin.setBounds(295, 209, 80, 71);
		add(jbLogin);
		
		jlSignUp.setBounds(160, 301, 80, 15);
		jlSignUp.setForeground(Color.BLUE);
		add(jlSignUp);
		
		jlFindID.setForeground(Color.BLUE);
		jlFindID.setBounds(112, 317, 69, 15);
		add(jlFindID);
		
		jlSlash.setBounds(181, 317, 11, 15);
		add(jlSlash);
		
		jlFindPass.setForeground(Color.BLUE);
		jlFindPass.setBounds(190, 317, 100, 15);
		add(jlFindPass);
		
		LoginController lc = new LoginController(this);
		jtfId.addActionListener(lc);
		jpfPass.addActionListener(lc);
		jbLogin.addActionListener(lc);
		jlSignUp.addMouseListener(lc);
		jlFindID.addMouseListener(lc);
		jlFindPass.addMouseListener(lc);
		addWindowListener(lc);
		
		setBounds(0,0,400,400);
		setVisible(true);
		setResizable(true);
		
	}
	public static void main(String[] args) {
		new LoginView();
	}

	public JTextField getJtfId() {
		return jtfId;
	}

	public JPasswordField getJpfPass() {
		return jpfPass;
	}

	public JButton getJbLogin() {
		return jbLogin;
	}

	public JLabel getJlSignUp() {
		return jlSignUp;
	}

	public JLabel getJlFindPass() {
		return jlFindPass;
	}
	public JLabel getJlFindID() {
		return jlFindID;
	}
	
}
