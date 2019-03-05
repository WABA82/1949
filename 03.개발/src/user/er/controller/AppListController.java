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
import user.er.view.AppDetailView;
import user.er.view.AppListView;
import user.er.vo.DetailAppListVO;
import user.util.UserUtil;

public class AppListController extends WindowAdapter implements MouseListener {

	private AppListView alv;
	private String er_num;
	private ErDAO er_dao;

	private final int DBL_CLICK = 2; // ���� Ŭ�� ���

	public AppListController(AppListView alv, String er_num) {
		this.alv = alv;
		this.er_num = er_num;
		er_dao = ErDAO.getInstance();
		setDTM(er_num);
	}// ������

	/**
	 * ���� : ������Ȳ â - �� ���� ��Ȳ â�� ���� �� ����� ä��� �޼ҵ�.
	 * 
	 * @param er_num
	 */
	private void setDTM(String er_num) {

		DefaultTableModel dtm = alv.getDtmEeInfo();
		dtm.setRowCount(0); // DTM 0���� �ʱ�ȭ.

		try {
			// DB���� ����ȸ�縦 ��ȸ.
			List<DetailAppListVO> list = er_dao.selectDetailApplist(er_num);

			StringBuilder appCnt = new StringBuilder("�� ������ �� : ");
			appCnt.append(String.valueOf(list.size())).append(" ��");
			alv.getJlEeInfo().setText(appCnt.toString());

			// JTable�� ��ȸ�� ������ ���.
			DetailAppListVO dalvo = null;
			String imgPath = "C:/dev/1949/03.����/src/user/img/ee/";

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list�� ����� VO��ü�� EeInterestVO��ü �����ϱ� */
				dalvo = list.get(i);

				// DTM�� �����͸� �߰��ϱ� ���� �������迭(Vector)�� �����ϰ� �����͸� �߰�
				rowData = new Object[12];
				rowData[0] = new Integer(i + 1);
				rowData[1] = dalvo.getApp_num();

				File imgFile = new File(imgPath + dalvo.getImg());
				// user.img.co��Ű���� �̹��� ������ ���ٸ� ����.
				System.out.println(imgFile.exists());
				if (!imgFile.exists()) {
					try {
						Socket client = null; // "211.63.89.144", 7002 : ������ǻ��IP, ���ϼ����� ��Ʈ
						DataInputStream dis = null;
						DataOutputStream dos = null;
						FileOutputStream fos = null;
						UserUtil util = new UserUtil(); // ������ ������ ��ƿ��ü ����.

						util.reqFile(imgFile.getName(), "ee", client, dos, dis, fos);
					} catch (IOException e) {
						e.printStackTrace();
					} // end try
				} // end if

				rowData[2] = new ImageIcon(imgPath + dalvo.getImg());
				rowData[3] = dalvo.getName();
				rowData[4] = (dalvo.getRank().equals("N") ? "����" : "���");
				rowData[5] = dalvo.getLoc();
				rowData[6] = dalvo.getEducation();
				rowData[7] = dalvo.getAge();
				rowData[8] = (dalvo.getPortfolio().equals("Y") ? "����" : "����");
				rowData[9] = (dalvo.getGender().equals("M") ? "����" : "����");
				rowData[10] = dalvo.getApp_date();
				switch (dalvo.getApp_status()) {
				case "U":
					rowData[11] = "������";
					break;
				case "R":
					rowData[11] = "����";
					break;
				case "A":
					rowData[11] = "��������";
					break;
				case "D":
					rowData[11] = "��������";
				}// end switch

				// DTM�� �߰�
				dtm.addRow(rowData);
			} // end for

			if (list.isEmpty()) {// ����� �޴��� ���� �� : ���ö� �߰� ��ư�� ���� �޴��� �߰� �� �� �ִ�.
				JOptionPane.showMessageDialog(alv, "����� ���ΰ��� ���� ������ �����ڰ� �����ϴ�.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(alv, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	@Override
	public void windowClosing(WindowEvent e) {
		alv.dispose();
	}// windowClosing

	@Override
	public void mouseClicked(MouseEvent me) {
		switch (me.getClickCount()) {
		case DBL_CLICK:
			if (me.getSource() == alv.getJtEeInfo()) {
				showAppDetailInfo();
			} // end if
		}// end switch
	}// mouseClicked

	private void showAppDetailInfo() {
		JTable jt = alv.getJtEeInfo();
		String app_num = (String) jt.getValueAt(jt.getSelectedRow(), 1);
		AppDetailView adv = new AppDetailView(alv, app_num);

		// AppDetailView��ü�� ������ ���߸� true��ȯ
		if (adv.isActive()) {
			setDTM(er_num);
		} // end if
	}// showAppDetailInfo

	///////////////// �Ⱦ��� �޼���/////////////////
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
	///////////////// �Ⱦ��� �޼���/////////////////

}// class
