package user.common.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import user.common.controller.FindIdController;

public class FindIdView extends JDialog {
	
	private JTextField jtfName, jtfTel;
	private JButton jbValidate, jbClose;
	
	public FindIdView(LoginView lv) {
		super(lv, "1949 - ���̵� ã�� ����");
//	public FindIdView() {
		
		jtfName = new JTextField();
		jtfTel = new JTextField();
		jbValidate = new JButton("���� ����");
		jbClose = new JButton("�ݱ�");
		
		JLabel jlTitle = new JLabel("���̵� ã��");
		jlTitle.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		JLabel jlName = new JLabel("�̸�");
		JLabel jlTel = new JLabel("����ó");
		
		setLayout(null);
		
		jlTitle.setBounds(140, 30, 150, 30);
		jlName.setBounds(50, 90, 80, 30);
		jlTel.setBounds(50, 130, 80, 30);
		jtfName.setBounds(120, 90, 210, 30);
		jtfTel.setBounds(120, 130, 210, 30);
		jbValidate.setBounds(90, 185, 100, 30);
		jbClose.setBounds(210, 185, 90, 30);
		
		add(jlTitle);
		add(jlName);
		add(jlTel);
		add(jtfName);
		add(jtfTel);
		add(jbValidate);
		add(jbClose);
		
		FindIdController fic = new FindIdController(this);
		jbValidate.addActionListener(fic);
		jbClose.addActionListener(fic);
		
		addWindowListener(fic);
		
		setBounds(500, 200, 390, 280);
		setResizable(false);
		setVisible(true);
	}
	
	/*public static void main(String[] args) {
		new FindIdView();
	}*/
	public JTextField getJtfName() {
		return jtfName;
	}
	public JTextField getJtfTel() {
		return jtfTel;
	}
	public JButton getJbValidate() {
		return jbValidate;
	}
	public JButton getJbClose() {
		return jbClose;
	}
}
