package user.ee.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ModifyExtView extends JDialog {

	private JTextField jtfPath;
	private JButton jbChoose, jbChange, jbCancel;
	
	public ModifyExtView( EeInfoRegView eirv) {
		super(eirv,"�ܺ��̷¼� ���",true);
		
		JLabel jlMsg=new JLabel("�ܺ� �̷¼� ÷��");
		jlMsg.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
		add(jlMsg);
		jlMsg.setBounds(10, 32, 250, 30);
		
		JLabel jlTemp=new JLabel("�ܺ��̷¼��� doc, pdf�� ÷�ΰ��� �մϴ�.");
		jlTemp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		add(jlTemp);
		jlTemp.setBounds(80, 60, 250, 30);
		
		jtfPath=new JTextField();
		add(jtfPath);
		jtfPath.setBounds(120, 37, 140, 20);
		
		jbChoose=new JButton("���ϼ���");
		add(jbChoose);
		jbChoose.setBounds(270,37,90,20);
		
		jbChange=new JButton("÷���ϱ�");
		add(jbChange);
		jbChange.setBounds(75,95,100,25);
		
		jbCancel=new JButton("���");
		add(jbCancel);
		jbCancel.setBounds(190,95,100,25);
		
		
		setLayout(null);
		setResizable(false);
		setBounds(100, 100, 380, 170);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}//������
	
//	public static void main(String[] args) {
//		new ModifyExtView(null);
//	}
	
}//class
