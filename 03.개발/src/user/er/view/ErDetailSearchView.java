package user.er.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import user.ee.controller.EeDetailSearchController;
import user.ee.controller.EeHiringController;
import user.ee.view.EeHiringView;
import user.er.controller.ErDetailSearchController;
import user.er.controller.ErHiringController;

public class ErDetailSearchView extends JDialog {
	
	private JButton jbSearch, jbCancel;
	private JComboBox<String> jcbRank, jcbEducation, jcbLoc, jcbHireType, jcbAge;
	
	public ErDetailSearchView(ErHiringView ehv, ErHiringController ehc) {
		super(ehv, "���ǰ˻�", true);
//	public ErDetailSearchView() {
		
		JLabel jlTitle = new JLabel("�˻��ɼ�");
		jlTitle.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		
		jbSearch = new JButton("���ǰ˻�");
		jbCancel = new JButton("���");

		String[] rankItems = { "===������ �������ּ���===","����", "���" };
		jcbRank = new JComboBox<String>(rankItems);
		
		String[] eduItems = { "===�з��� �������ּ���===","����","�ʴ���","����","����","�ڻ�" };
		jcbEducation = new JComboBox<String>(eduItems);
		
		String[] locItems = { "==�ٹ������� �������ּ���==","����", "���", "��õ", "����", "����", "�泲", "���",
				"����","����","����","�뱸","���","�λ�","���","�泲","����","����","����" };
		jcbLoc = new JComboBox<String>(locItems);
		
		String[] htItems = { "==������¸� �������ּ���==", "������","�����","����" };
		jcbHireType = new JComboBox<String>(htItems);
		
		String[] ageItems = { "===���̸� �������ּ���===" ,"10��", "20��", "30��", "40��", "50��", "60��" };
		jcbAge = new JComboBox<>(ageItems);
		
		setLayout(null);
		
		jlTitle.setBounds(105, 20, 100, 30);
		jcbRank.setBounds(50, 70, 200, 30);
		jcbEducation.setBounds(50, 120, 200, 30);
		jcbLoc.setBounds(50, 170, 200, 30);
		jcbHireType.setBounds(50,220,200,30);
		jcbAge.setBounds(50, 270, 200, 30);
		jbSearch.setBounds(55, 320, 90, 30);
		jbCancel.setBounds(155, 320, 90, 30);
		
		add(jlTitle);
		add(jcbRank);
		add(jcbEducation);
		add(jcbLoc);
		add(jcbHireType);
		add(jcbAge);
		add(jbSearch);
		add(jbCancel);
		
		ErDetailSearchController edsc = new ErDetailSearchController(this, null/*ehc*/);
		jbSearch.addActionListener(edsc);
		jbCancel.addActionListener(edsc);
		
		addWindowListener(edsc);
		
		setBounds(500, 100, 300, 410);
		setResizable(false);
		setVisible(true);
	}
	
	/*public static void main(String[] args) {
		new ErDetailSearchView();
	}*/
	public JButton getJbSearch() {
		return jbSearch;
	}
	public JButton getJbCancel() {
		return jbCancel;
	}
	public JComboBox<String> getJcbRank() {
		return jcbRank;
	}
	public JComboBox<String> getJcbEducation() {
		return jcbEducation;
	}
	public JComboBox<String> getJcbLoc() {
		return jcbLoc;
	}
	public JComboBox<String> getJcbHireType() {
		return jcbHireType;
	}
	public JComboBox<String> getJcbAge() {
		return jcbAge;
	}
}
