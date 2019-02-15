package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.dao.ErDAO;
import user.ee.view.EeDetailCoView;
import user.ee.vo.CoDetailVO;
import user.ee.vo.DetailErInfoVO;
import user.ee.vo.EeInterestAndAppVO;
import user.er.view.ErDetailEeView;
import user.er.vo.DetailEeInfoVO;
import user.er.vo.ErInterestVO;

import user.dao.ErDAO;
import user.er.vo.DetailEeInfoVO;

/**
 * 
 * @author owner
 *
 */
public class ErDetailEeController extends WindowAdapter implements ActionListener, MouseListener {
	private ErDetailEeView edev;
	private String eeNum, erId;
	private boolean mouseClickFlag; 
	private ErInterestVO eivo;
	private ErDAO erdao;
	public ErDetailEeController(ErDetailEeView edev, String eeNum, String erId,boolean flagHeart) {
		this.edev = edev;
		this.eeNum = eeNum;
		this.erId = erId;
		System.out.println("controller: "+flagHeart);
		mouseClickFlag=flagHeart;
		erdao= ErDAO.getInstance();
	}
	public void addInterestEe() {
		eivo = new ErInterestVO(eeNum,erId);
		
		try {
			erdao.insertInterestEe(eivo);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(edev, "�߰��� �����߽��ϴ�.");
			return;
		}
		edev.getJlHeart().setIcon(new ImageIcon("C:/dev/1949/03.����/��������/��Ʈ/r_heart.png"));
		JOptionPane.showMessageDialog(edev, "���� ���αۿ� �߰��Ǿ����ϴ�!");
		try {
			DetailEeInfoVO devo= erdao.selectDeatilEe(eeNum, erId);
		}
	}

	private ErDAO er_dao;

	public ErDetailEeController(String er_id, String ee_num) {
		er_dao = ErDAO.getInstance();
		setInfo(er_id, ee_num);
	}// ������

	////////////////// ���� 0213 ������ //////////////////

	private void setInfo(String er_id, String ee_num) {
		DetailEeInfoVO deivo = er_dao.selectDetailEEInfo(er_id, ee_num);
	}// setInfo

	@Override
	public void mouseClicked(MouseEvent e) {
	}// mouseClicked

	@Override
	public void actionPerformed(ActionEvent e) {

	}// actionPerformed

	//////////////// �Ⱦ��� �޼���////////////////
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	//////////////// �Ⱦ��� �޼���////////////////
}// class
