package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.dao.EeDAO;
import user.ee.view.EeInfoRegView;
import user.ee.view.ModifyExtView;
import user.ee.vo.EeInsertVO;
import user.ee.vo.EeRegVO;

public class EeInfoRegController extends WindowAdapter implements ActionListener {

	/* �ν��Ͻ����� */
	private EeInfoRegView eirv;
	private String uploadImg;
	private EeDAO eedao;
	
	//�׽�Ʈ�� ���̵�
	private String eeid = "";// �� ���̵�.
//	private EeRegVO eevo;

	public EeInfoRegController(EeInfoRegView eirv, String eeid) {
		this.eirv = eirv;
		this.eeid = eeid;
		uploadImg = "";
		eedao = EeDAO.getInstance();
		setInfo("kun90");
	}// ������

	// �̹������۵� ���� ����
	public void register(String eeid) {
		if (uploadImg.isEmpty()) {
			JOptionPane.showMessageDialog(eirv, "�̹����� �ʼ� �Դϴ�.");
			return;
		}
		
		
		JComboBox<String>jcbRank= eirv.getJcbRank();
		
		String tempRank = eirv.getJcbRank().getSelectedItem().toString();
		String rank=tempRank.replace("����", "C").replaceAll("���", "N");
		jcbRank.setSelectedIndex(1);
		
		String loc = eirv.getJcbLoc().getSelectedItem().toString();
		String education = (String)eirv.getJcbEducation().getSelectedItem();
		String tempPortfolio = eirv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio =tempPortfolio.replaceAll("YES", "Y").replaceAll("NO", "N");
		String extResume = eirv.getJtfExtResume().getText();
		File file = new File(uploadImg);

		try {	//��� ���� ������ ���缭 �Է�
		EeInsertVO eivo= new EeInsertVO( eeid, file.getName(), rank, loc, education, portfolio, extResume);
			eedao.insertEeinfo(eivo);
			JOptionPane.showMessageDialog(eirv, "��ϵǾ����ϴ�");
			eirv.dispose();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eirv, "DB�� ������ �߻��Ͽ����ϴ�.");
			e.printStackTrace();
		}//DB�� �߰�
	}// register

	
	public void changeImg() {
		FileDialog fd = new FileDialog(eirv, "�̹��� ����", FileDialog.LOAD);
		fd.setVisible(true);

		String path = fd.getDirectory();
		String name = fd.getFile();

		if (path != null) {
			if (!name.endsWith(".jpg") && !name.endsWith(".jpeg") && !name.endsWith(".png") && !name.endsWith(".bmp") && !name.endsWith(".gif")) {
				JOptionPane.showMessageDialog(eirv, name + "�� ����Ҽ� �����ϴ�.");
			} else {
				uploadImg = path + name;
				eirv.getJlImage().setIcon(new ImageIcon(uploadImg));
			}
		}
	}// changeImg

	/**
	 * �̸�, ����, ����
	 */
	public void setInfo(String eeid) {
		JTextField name = eirv.getJtfName();
		JTextField age = eirv.getJtfAge();
		JTextField gender = eirv.getJtfGender();
		EeRegVO ervo = null;
		try {
			ervo = eedao.selectEeReg(eeid);
			name.setText(ervo.getName());
			age.setText(String.valueOf(ervo.getAge()));
			gender.setText(ervo.getGender());

		} catch (SQLException e) {
			e.printStackTrace();
		}// end catch
	}// setInfo.

	@Override
	public void windowClosing(WindowEvent e) {
		eirv.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == eirv.getJbClose()) {
			eirv.dispose();
		} // end if

		if (ae.getSource() == eirv.getJbRegisterImg()) {
			changeImg();
		} // end if

		if (ae.getSource() == eirv.getJbRegisterExt()) {
			new ModifyExtView(eirv);
		} // end if

		if (ae.getSource() == eirv.getJbRegister()) {
			register(eeid);
		} // end if

	}// actionPerformed

}// class
