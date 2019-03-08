package admin.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.sun.glass.events.KeyEvent;

import admin.controller.LoginController;

@SuppressWarnings("serial")
public class LoginView extends JFrame {
	private JTextField jtfId;
	private JPasswordField jpfPass;
	private JButton jbLogin;

	public LoginView() {
		super("1949 - 관리자 로그인");
		
		ImageIcon logoImg = new ImageIcon("C:/dev/1949/03.개발/src/admin/img/admin_logo.png");
		JLabel jlLogo = new JLabel(logoImg);
		JLabel jlId= new JLabel("아이디");
		JLabel jlPass= new JLabel("비밀번호");
		jtfId= new JTextField();
		jpfPass = new JPasswordField();
		jbLogin = new JButton("로그인");
		
		setLayout(null);
		
		jlLogo.setBounds(23, 15, 350, 180);
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
		
		LoginController lc = new LoginController(this);
		jtfId.addActionListener(lc);// 눌리면 로그인 눌려지게
		jpfPass.addActionListener(lc);// 예외처리 해야함
		jbLogin.addActionListener(lc);
		jtfId.addKeyListener(lc);
		addWindowListener(lc);
		
		jtfId.setFocusTraversalKeysEnabled(false);
		setBounds(200,200,400,330);
		setResizable(false);
		setVisible(true);
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
}
