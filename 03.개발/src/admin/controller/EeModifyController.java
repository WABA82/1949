package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import admin.dao.AdminDAO;
import admin.view.AdminMgMtView;
import admin.view.EeModifyView;
import admin.view.ModifyExtView;
import admin.vo.EeInfoVO;

public class EeModifyController extends WindowAdapter implements ActionListener {

	private EeModifyView emv;
	private AdminMgMtView ammv;
	private AdminMgMtController ammc;
	private EeInfoVO eivo;
	
	public EeModifyController(EeModifyView emv, AdminMgMtView ammv, AdminMgMtController ammc, EeInfoVO eivo) {
		this.emv = emv;
		this.ammv = ammv;
		this.ammc = ammc;
		this.eivo = eivo;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == emv.getJbCancel()) {
			emv.dispose();
			ammc.setEe();
		}
		
		if (e.getSource() == emv.getJbModify()) {
			modifyEe();
		}
		
		if (e.getSource() == emv.getJbRemove()) {
			switch(JOptionPane.showConfirmDialog(emv, "�⺻������ ���� �����Ͻðڽ��ϱ�?")) {
			case JOptionPane.OK_OPTION:
				removeEe();
				break;
			}
			
		}
		
		if (e.getSource() == emv.getJbChangeExt()) {
			changeExt();
		}
		
		if (e.getSource() == emv.getJbChangeImg()) {
			changeImg();
		}
 	}
	
	public void modifyEe() {
		// �̹���, �̷¼����� ����Ǿ����� Ȯ��
		// �̹����� ����Ǿ��ٸ� img��Ű���� ���� ����(���ϼ��� �ϼ� �� ���濹��)
		// �̷¼��� ����Ǿ��ٸ� ���� �̷¼����� ���� �� ���ο� �̷¼����� ����
		// �� ���� ���� ������ DB���� update�޼ҵ�ó��
		
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(emv, msg);
	}
	
	public void removeEe() {
		
////////////���ϼ��� �ϼ� �� ���� ������ûó���ؾ� �� //////////////////////////////////////////////////
		
		if(AdminDAO.getInstance().deleteEe(eivo)) {
			msgCenter("�⺻������ �����Ǿ����ϴ�.");
			emv.dispose();
			ammc.setEe();
		} else {
			msgCenter("�⺻���� ������ �����߽��ϴ�.");
		}
	}
	
	public void changeImg() {
		
	}
	
	public void changeExt() {
		new ModifyExtView(emv);
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		emv.dispose();
	}
	
}
