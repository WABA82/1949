package admin.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import admin.controller.EeModifyController;
import admin.controller.ModifyExtController;

/**
 * 19.02.14 �̷¼����� ��ɱ���
 * @author ����
 */
@SuppressWarnings("serial")
public class ModifyExtView extends JDialog { 

	private JTextField jtfPath;
	private JButton jbChoose, jbChange, jbCancel;

	public ModifyExtView(EeModifyView emv, EeModifyController emc) {
		super(emv, "�ܺ��̷¼� ���", true);

		setLayout(null);

		JLabel jlMsg = new JLabel("�ܺ� �̷¼� ÷��");
		jlMsg.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
		add(jlMsg);
		jlMsg.setBounds(10, 32, 250, 30);

		JLabel jlTemp = new JLabel("�ܺ��̷¼��� doc, pdf�� ÷�ΰ��� �մϴ�.");
		jlTemp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		add(jlTemp);
		jlTemp.setBounds(80, 60, 250, 30);

		jtfPath = new JTextField();
		add(jtfPath);
		jtfPath.setBounds(120, 37, 140, 20);

		jbChoose = new JButton("���ϼ���");
		add(jbChoose);
		jbChoose.setBounds(270, 37, 90, 20);

		jbChange = new JButton("÷���ϱ�");
		add(jbChange);
		jbChange.setBounds(75, 95, 100, 25);

		jbCancel = new JButton("���");
		add(jbCancel);
		jbCancel.setBounds(190, 95, 100, 25);

		// �̺�Ʈ ���
		ModifyExtController mec = new ModifyExtController(this, emv, emc);
		jbCancel.addActionListener(mec);
		jbChange.addActionListener(mec);
		jbChoose.addActionListener(mec);
		jtfPath.addActionListener(mec);
		addWindowListener(mec);

		setResizable(false);
		setBounds(emv.getX() + 50, emv.getY() + 150, 380, 170);
		setVisible(true);
	}// ������

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
}