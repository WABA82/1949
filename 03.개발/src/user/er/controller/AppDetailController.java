package user.er.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.dao.ErDAO;
import user.er.view.AppDetailView;
import user.er.vo.DetailAppEeVO;
import user.er.vo.ErAppStatusVO;
import user.util.UserUtil;

/**
 * �� ������ ���� â�� ��Ʈ�ѷ�.
 * 
 * @author ����
 *
 */
public class AppDetailController extends WindowAdapter implements ActionListener {

	// DEFAULT 'U'
	// 'U' - unread ������
	// 'R' - read ����
	// 'A' - approved ��������
	// 'D' - denied ��������

	/* �ν��Ͻ� ���� */
	private AppDetailView adv;
	private ErDAO er_dao;
	private String app_num;
	private String er_num;
	private AppListController ac;
	private DetailAppEeVO daevo = null;

	public AppDetailController(AppDetailView adv, String app_num, AppListController ac, String er_num) {
		this.adv = adv;
		this.app_num = app_num;
		this.er_num = er_num;
		this.ac = ac;
		er_dao = ErDAO.getInstance();
		setInfo(app_num);
	}// ������

	/**
	 * �� ������ ����â�� �⺻ �������� �����ϴ� �޼���.
	 * 
	 * @param app_num
	 */
	public void setInfo(String app_num) {
		try {
			daevo = er_dao.selectDetailAppEe(app_num);

			if (daevo != null) {
				String imgPath = "C:/dev/1949/03.����/src/user/img/ee/";
				File imgFile = new File(imgPath + daevo.getImg());
				// user.img.co��Ű���� �̹��� ������ ���ٸ� ����.
				// System.out.println(imgFile.exists());
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
				adv.getJlImage().setIcon(new ImageIcon(imgPath + daevo.getImg()));
				adv.getJtfName().setText(daevo.getName());
				adv.getJtfTel().setText(daevo.getTel());
				adv.getJtfEmail().setText(daevo.getEmail());
				adv.getJtfRank().setText((daevo.getRank().equals("N") ? "����" : "���"));
				adv.getJtfLoc().setText(daevo.getLoc());
				adv.getJtfEdu().setText(daevo.getEducation());
				adv.getJtfAge().setText(String.valueOf(daevo.getAge()));
				adv.getJtfPort().setText((daevo.getPortfolio().equals("Y") ? "����" : "����"));
				adv.getJtfGender().setText((daevo.getGender().equals("M") ? "����" : "����"));

				// �޾ƿ� ������ �� �������� App_status�� �ٲ��ش�.
				switch (daevo.getApp_status()) {
				case "U": // �������¸� R�� �ٲ��ش�.
					er_dao.updateAppSatus(new ErAppStatusVO(app_num, "R"));
					break;
				case "A": // ������ �������¸� ������ ���.
					adv.getJbAccept().setVisible(false);
					adv.getJbRefuse().setVisible(false);
					break;
				case "D": // ������ �������¸� ������ ���.
					adv.getJbAccept().setVisible(false);
					adv.getJbRefuse().setVisible(false);
					break;
				}// end if

			} else {
				JOptionPane.showMessageDialog(adv, "����� ���ΰ��� ���� ������ �����ڰ� �����ϴ�.");
			} // end else
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(adv, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} // end catch
	}// setInfo()

	/**
	 * ����ó��
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		adv.dispose();
	}// windowClosing(WindowEvent e)

	/**
	 * ��ư�鿡�� �̺�Ʈ �߻��� ó���� �޼���.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == adv.getJbAccept()) { // �������� ��ư �̺�Ʈ ó��
			switch (JOptionPane.showConfirmDialog(adv, "�� �������� ������ ���� �Ͻðڽ��ϱ�?\n �ѹ� ���� �ϸ� �ǵ��� �� �����ϴ�.")) {
			case JOptionPane.OK_OPTION:
				changeStatusAccept();
				setInfo(app_num);
			}// end switch
		} // end if

		if (e.getSource() == adv.getJbRefuse()) {// �������� ��ư �̺�Ʈ ó��
			switch (JOptionPane.showConfirmDialog(adv, "�� �������� ������ ���� �Ͻðڽ��ϱ�?\n �ѹ� ���� �ϸ� �ǵ��� �� �����ϴ�.")) {
			case JOptionPane.OK_OPTION:
				changeStatusRefuse();
				setInfo(app_num);
			}// end switch
		} // end if

		if (e.getSource() == adv.getJbExtRsm()) { // �ܺ��̷¼� ��ư �̺�Ʈ ó��
			try {
				switch (JOptionPane.showConfirmDialog(adv, "�������� �̷¼��� �ٿ�ε� �Ͻðڽ��ϱ�?")) {
				case JOptionPane.OK_OPTION:
					extResumeDown();
					break;
				}// end switch
			} catch (UnknownHostException e1) {
				JOptionPane.showMessageDialog(adv, "������ �� �� �����ϴ�.");
				e1.printStackTrace();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(adv, "���� ����");
				e1.printStackTrace();
			} // end catch
		} // end if

		if (e.getSource() == adv.getJbClose()) { // �ݱ� ��ư �̺�Ʈ ó��
			adv.dispose();
		} // end if
	}// actionPerformed

	/**
	 * DB application �������¸� �������� �����ϴ� �޼���.
	 */
	public void changeStatusAccept() {
		try {
			if (!er_dao.updateAppSatus(new ErAppStatusVO(app_num, "A"))) { // �����۵� ���� ���.
				JOptionPane.showMessageDialog(adv, "�� �������� ������ ���� �Ͽ����ϴ�.");
				ac.setDTM(er_num);
			} // end if
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(adv, "DB���� ������ �߻��߽��ϴ�. ��� �� �ٽ� �̿��� �ּ���.");
			e.printStackTrace();
		} // end catch
	}// changeStatusAccept()

	/**
	 * DB application �������¸� �������� �����ϴ� �޼���.
	 */
	public void changeStatusRefuse() {
		try {
			if (!er_dao.updateAppSatus(new ErAppStatusVO(app_num, "D"))) {// �����۵� ���� ���.
				JOptionPane.showMessageDialog(adv, "�� �������� ������ ���� �Ͽ����ϴ�.");
				ac.setDTM(er_num);
			} // end if
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(adv, "DB���� ������ �߻��߽��ϴ�. ��� �� �ٽ� �̿��� �ּ���.");
			e.printStackTrace();
		} // end catch
	}// changeStatusAccept()

	/**
	 * ������ ���� �����ڰ� ����� �ܺ��̷¼��� �ٿ�޴� �޼���.
	 * 
	 * @throws UnknownHostException
	 * 
	 * @throws IOException
	 */
	public void extResumeDown() throws UnknownHostException, IOException {
		if (daevo.getExt_resume() == null) {
			JOptionPane.showMessageDialog(adv, "�� �����ڰ� ����� �ܺ��̷¼��� �����ϴ�.");
			return;
		} // end if

		if (daevo.getExt_resume() != null) {
			// FileDialog�� �̷¼� �������, ���ϸ��� ���ϰ� �� ���,���Ϸ� fos�� write
			FileDialog fdSave = new FileDialog(adv, "���� ��� ����", FileDialog.SAVE);
			fdSave.setVisible(true);

			String path = fdSave.getDirectory();
			String name = fdSave.getFile();
			String resumeName = daevo.getExt_resume();
			String ext = "";

			// Ȯ���� �����
			ext = resumeName.substring(resumeName.lastIndexOf(".") + 1);

			Socket socket = null;
			DataOutputStream dos = null;
			DataInputStream dis = null;
			FileOutputStream fos = null;

			try {
				socket = new Socket("211.63.89.144", 7002);
				dos = new DataOutputStream(socket.getOutputStream());

				// �������� �̷¼����� ���� ��û ������.
				dos.writeUTF("ee_ext_request");
				dos.flush();

				// �������� ��û�� ���ϸ� ������.
				dos.writeUTF(daevo.getExt_resume().trim());
				dos.flush();

				dis = new DataInputStream(socket.getInputStream());

				byte[] readData = new byte[512]; // �����Ͱ� ��� �迭.

				int dataLen = 0; // readData�迭 �� ���� ����.
				int dataArrCnt = 0; // readData�迭�� �� ����.

				dataArrCnt = dis.readInt(); // �������� �������� ������ readData�迭�� �� ���� �ޱ�.

				fos = new FileOutputStream(path + "/" + name + "." + ext);

				// �迭�� ������ 0�� �ƴ� ���� ������ ��������
				while (dataArrCnt > 0) {
					dataLen = dis.read(readData);
					fos.write(readData, 0, dataLen);
					fos.flush();
					dataArrCnt--;
				} // end while

				dos.writeUTF("����Ǿ����ϴ�.");
				dos.flush();
				JOptionPane.showMessageDialog(adv, "���� �ٿ��� �Ϸ�Ǿ����ϴ�!");

			} finally {
				if (fos != null) {
					fos.close();
				} // end if
				if (dos != null) {
					dos.close();
				} // end if
				if (socket != null) {
					socket.close();
				} // end if
			} // end finally
		} // end if

	}// changeStatusAccept()

}// class
