package user.ee.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import user.ee.controller.EeInfoModifyController;
import user.ee.controller.EeInfoRegController;
import user.ee.controller.ModifyExtController;

@SuppressWarnings("serial")
public class ModifyExtView extends JDialog {

	private JTextField jtfPath;
	private JButton jbChoose, jbChange, jbCancel;
	
	private EeInfoRegView eirv;
	private EeInfoRegController eirc;
	private EeInfoModifyView eimv;
	private EeInfoModifyController eimc;
	
	public ModifyExtView(JDialog jd, EeInfoRegView eirv, 
			EeInfoRegController eirc, EeInfoModifyView eimv, 
			EeInfoModifyController eimc, String flag) {
		super(jd,"�ܺ��̷¼� ���",true);
		
		if (flag.equals("modi")) {
			this.eimv = eimv;
			this.eimc = eimc;
		} else {
			this.eirv = eirv;
			this.eirc = eirc;
		}
		
		JLabel jlMsg=new JLabel("�ܺ� �̷¼� ÷��");
		jlMsg.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
		add(jlMsg);
		jlMsg.setBounds(10, 32, 250, 30);
		
		JLabel jlTemp=new JLabel("�ܺ��̷¼��� doc, pdf�� ÷�ΰ��� �մϴ�.");
		jlTemp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		add(jlTemp);
		jlTemp.setBounds(80, 60, 250, 30);
		
		jtfPath=new JTextField("");
		jtfPath.setEditable(false);
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
		
		//�̺�Ʈ ���
		ModifyExtController mec=new ModifyExtController(this, eirv, 
			eirc, eimv, eimc, flag);
		jbCancel.addActionListener(mec);
		jbChange.addActionListener(mec);
		jbChoose.addActionListener(mec);
		addWindowListener(mec);
		
		setLayout(null);
		setResizable(false);
		setBounds(jd.getX()+50, jd.getY()+150, 380, 170);
		setVisible(true);
	}//������

	public JTextField getJtfPath() {
		return jtfPath;
	}

	public JButton getJbChoose() {
		return jbChoose;
	}

	public JButton getJbChange() {
		return jbChange;
	}

	public JButton getJbCancel() {
		return jbCancel;
	}
}//class
