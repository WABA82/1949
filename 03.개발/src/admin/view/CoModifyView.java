package admin.view;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import admin.controller.CoModifyController;
import admin.vo.CoInfoVO;

@SuppressWarnings("serial")
public class CoModifyView extends JDialog {
	
private JTextField jtfCoName, jtfEstDate, memberNum;
private JTextArea jtaCoDesc;
private JButton jbModify, jbRemove, jbClose;
private JLabel jlImg1, jlImg2, jlImg3, jlImg4;

//public CoModifyView(AdminMgMtView ammv, CoInfoVO cvo) {
//	super(ammv, "ȸ�������", true); //��� true
public CoModifyView() {

	JTextField jtfCoId = new JTextField();
	jtfCoName = new JTextField();
	jtfEstDate = new JTextField();
	memberNum = new JTextField();
	
	JLabel jlCoId = new JLabel(" ��� ID");
	JLabel jlCoName = new JLabel("ȸ�� �̸�");
	JLabel jlEstDate = new JLabel("�����⵵");
	JLabel jlmemberNum = new JLabel(" �����");
	
	jtaCoDesc = new JTextArea();
	JScrollPane jspTaDesc = new JScrollPane(jtaCoDesc);
	
	jbModify = new JButton("����");
	jbRemove = new JButton("����");
	jbClose = new JButton("�ݱ�");
	
	jlImg1 = new JLabel("j");
	jlImg2 = new JLabel("j");
	jlImg3 = new JLabel("j");
	jlImg4 = new JLabel("j");
	
	//��ġ
	setLayout(null);
	
	jlCoId.setBounds(240, 50, 57, 26);
	jlCoName.setBounds(240, 96, 57, 26);
	jlEstDate.setBounds(240, 138, 57, 26);
	jlmemberNum.setBounds(240, 180, 57, 26);
	
	
	jtfCoId.setBounds(300, 50, 133, 29);
	jtfCoName.setBounds(300, 96, 133, 29);
	jtfEstDate.setBounds(300, 138, 133, 29);
	memberNum.setBounds(300, 180, 133, 29);
	
	jlImg1.setBounds(28, 45, 190, 120);
	jlImg1.setBorder(new TitledBorder("ȸ�� �̹���"));
	
	jlImg2.setBounds(33, 178, 50, 44);
	jlImg3.setBounds(93, 178, 50, 44);
	jlImg4.setBounds(158, 178, 50, 44);
	
	jspTaDesc.setBounds(32, 274, 405, 133);
	jspTaDesc.setBorder(new TitledBorder("��� ����"));
	
	jbModify.setBounds(140, 426, 92, 24);
	jbRemove.setBounds(243, 426, 92, 24);
	jbClose.setBounds(342, 426, 92, 24);
	
	CoModifyController cmc = new CoModifyController(this);
	addWindowListener(cmc);
	
	jbModify.addActionListener(cmc);
	jbRemove.addActionListener(cmc);
	jbClose.addActionListener(cmc);
	add(jlCoId);
	add(jlCoName);
	add(jlEstDate);
	add(jlmemberNum);
	
	add(jtfCoId);
	add(jtfCoName);
	add(jtfEstDate);
	add(memberNum);
	
	add(jlImg1);
	add(jlImg2);
	add(jlImg3);
	add(jlImg4);
	
	add(jspTaDesc);
	
	add(jbModify);
	add(jbRemove);
	add(jbClose);
	
	setBounds(100, 100, 480, 520);
	setVisible(true);
	
}//CoModifyView ������



	
public JTextField getJtfCoName() {
	return jtfCoName;
}




public JTextField getJtfEstDate() {
	return jtfEstDate;
}




public JTextField getMemberNum() {
	return memberNum;
}




public JTextArea getJtaCoDesc() {
	return jtaCoDesc;
}




public JButton getJbModify() {
	return jbModify;
}




public JButton getJbRemove() {
	return jbRemove;
}




public JButton getJbClose() {
	return jbClose;
}




public JLabel getJlImg1() {
	return jlImg1;
}




public JLabel getJlImg2() {
	return jlImg2;
}




public JLabel getJlImg3() {
	return jlImg3;
}




public JLabel getJlImg4() {
	return jlImg4;
}




//public static void main(String[] args) {
//	new CoModifyView();
//}

}//class
