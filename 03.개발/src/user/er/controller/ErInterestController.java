package user.er.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.dao.ErDAO;
import user.er.view.ErDetailEeView;
import user.er.view.ErInterestView;
import user.er.vo.DetailEeInfoVO;
import user.er.vo.ErHiringForInterestVO;
import user.util.UserUtil;

public class ErInterestController extends WindowAdapter implements MouseListener {

	private ErInterestView eriv;
	private String er_id;
	private ErDAO er_dao;
	private final int DBL_CLICK = 2;

	public ErInterestController(ErInterestView eriv, String er_id) {
		this.eriv = eriv;
		this.er_id = er_id;
		er_dao = ErDAO.getInstance();
		setDTM(er_id);
	}// ������

	@Override
	public void windowClosing(WindowEvent e) {
		eriv.dispose(); // ����ó��
	}// windowClosing(WindowEvent e)

	/**
	 * �α��� �Ǿ��ִ� ���������� id�� ����� DB���� ���ɱ����� ����� ������ ȭ�鿡 ������� ����ϴ� ��.
	 * 
	 * @param er_id
	 */
	private void setDTM(String er_id) {

		DefaultTableModel dtm = eriv.getDtmEeInfo();
		dtm.setRowCount(0); // DTM 0���� �ʱ�ȭ.

		try {
			// DB���� ����ȸ�縦 ��ȸ.
			List<ErHiringForInterestVO> list = er_dao.selectInterestEEInfoList(er_id);

			StringBuffer interestCnt = new StringBuffer("�� ���� ������ �� : ");
			interestCnt.append(String.valueOf(list.size())).append(" ��");
			eriv.getJlEeInfo().setText(interestCnt.toString());

			// JTable�� ��ȸ�� ������ ���.
			ErHiringForInterestVO erhForInterest = null;
			String imgPath = "C:/dev/1949/03.����/src/user/img/ee/";

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list�� ����� VO��ü�� EeInterestVO��ü �����ϱ� */
				erhForInterest = list.get(i);

				// DTM�� �����͸� �߰��ϱ� ���� �������迭(Vector)�� �����ϰ� �����͸� �߰�
				rowData = new Object[11];
				rowData[0] = new Integer(i + 1);
				rowData[1] = erhForInterest.getEe_num();

				File imgFile = new File(imgPath + erhForInterest.getImg());
				// user.img.co��Ű���� �̹��� ������ ���ٸ� ����.
				// System.out.println(imgFile.exists());
				if (!imgFile.exists()) {
					Socket client = null; // "211.63.89.144", 7002 : ������ǻ��IP, ���ϼ����� ��Ʈ
					DataInputStream dis = null;
					DataOutputStream dos = null;
					FileOutputStream fos = null;
					UserUtil util = new UserUtil(); // ������ ������ ��ƿ��ü ����.
					try {
						util.reqFile(imgFile.getName(), "ee", client, dos, dis, fos);
					} catch (IOException e) {
						e.printStackTrace();
					} // end try
				} // end if

				rowData[2] = new ImageIcon(imgPath + erhForInterest.getImg());
				rowData[3] = erhForInterest.getName();
				rowData[4] = (erhForInterest.getRank().equals("N") ? "���" : "����");
				rowData[5] = erhForInterest.getLoc();
				rowData[6] = erhForInterest.getEducation();
				rowData[7] = new Integer(erhForInterest.getAge());
				rowData[8] = (erhForInterest.getPortfolio().equals("Y") ? "����" : "����");
				rowData[9] = (erhForInterest.getGender().equals("M") ? "����" : "����");
				rowData[10] = erhForInterest.getInput_date();

				// DTM�� �߰�
				dtm.addRow(rowData);
			} // end for

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eriv, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	/**
	 * ����Ŭ������ �� ����� �޼ҵ�.
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		switch (me.getClickCount()) {
		case DBL_CLICK:
			if (me.getSource() == eriv.getJtEeInfo()) {
				showDetailEeInfo();
			} // end if
		}// end switch
	}// mouseClicked

	private void showDetailEeInfo() {
		JTable jt = eriv.getJtEeInfo();
		String eeNum = String.valueOf(jt.getValueAt(jt.getSelectedRow(), 1));
		DetailEeInfoVO devo = null;
		try {
			devo = er_dao.selectDetailEe(eeNum, er_id);
			String ee_num = ((String) jt.getValueAt(jt.getSelectedRow(), 1));
			ErDetailEeView erdev = new ErDetailEeView(eriv, devo, ee_num, er_id, "1");

			// ErDetailEeView ��ü�� ������ ���߸� true�߻�
			if (erdev.isActive()) {
				setDTM(er_id);
			} // end if

		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
	}// showDetailErInfo()

	////////// �Ⱦ��� �޼ҵ� //////////
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
	////////// �Ⱦ��� �޼ҵ� //////////

}// class
