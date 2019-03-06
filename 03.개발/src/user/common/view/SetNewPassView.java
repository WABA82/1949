package user.common.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import user.common.controller.SetNewPassController;

@SuppressWarnings("serial")
public class SetNewPassView extends JDialog {
	
	private JPasswordField jpfPass1, jpfPass2;
	private JButton jbChange, jbClose;

	public SetNewPassView(LoginView lv, String id) {
		super(lv, "1949 - �� ��й�ȣ ����", true);
		
		jpfPass1 = new JPasswordField();
		jpfPass2 = new JPasswordField();
		jbChange = new JButton("��й�ȣ ����");
		jbClose = new JButton("�ݱ�");
		
		JLabel jlTitle = new JLabel("�� ��й�ȣ ����");
		jlTitle.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		JLabel jlPass1 = new JLabel("�� ��й�ȣ");
		JLabel jlPass2 = new JLabel("�� ��й�ȣ");
		JLabel jlPass3 = new JLabel("Ȯ��");
		
		setLayout(null);
		
		jlTitle.setBounds(130, 30, 180, 30);
		jlPass1.setBounds(50, 90, 80, 30);
		jlPass2.setBounds(50, 125, 80, 30);
		jlPass3.setBounds(70, 140, 80, 30);
		jpfPass1.setBounds(130, 90, 210, 30);
		jpfPass2.setBounds(130, 130, 210, 30);
		jbChange.setBounds(80, 185, 120, 30);
		jbClose.setBounds(220, 185, 90, 30);
		
		add(jlTitle);
		add(jlPass1);
		add(jlPass2);
		add(jlPass3);
		add(jpfPass1);
		add(jpfPass2);
		add(jbChange);
		add(jbClose);
		
		SetNewPassController snpc = new SetNewPassController(this, id);
		jbChange.addActionListener(snpc);
		jbClose.addActionListener(snpc);
		jpfPass1.addKeyListener(snpc);
		jpfPass2.addKeyListener(snpc);
		
		addWindowListener(snpc);
		
		setBounds(500, 200, 400, 280);
		setResizable(false);
		setVisible(true);
	}
	
	/*public static void main(String[] args) {
		new SetNewPassView(null, "");
	}*/
	public JPasswordField getJpfPass1() {
		return jpfPass1;
	}
	public JPasswordField getJpfPass2() {
		return jpfPass2;
	}
	public JButton getJbChange() {
		return jbChange;
	}
	public JButton getJbClose() {
		return jbClose;
	}
}
