package user.er.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.dao.ErDAO;
import user.er.view.AppDetailView;
import user.er.vo.DetailAppEeVO;
import user.er.vo.ErAppStatusVO;

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
	private DetailAppEeVO daevo = null;

	// appStatusFlag�� �̹� ������ �ߴ��� ���ߴ����� �Ǵ��ϴ� flag�Դϴ�.
	// false - ���� �������� �ʾ��� ���
	// true - �̹� ������ �� ���
	private boolean appStatusFlag = false;

	public AppDetailController(AppDetailView adv, String app_num) {
		this.adv = adv;
		this.app_num = app_num;
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
				String imgPath = "C:/dev/1949/03.����/��������/�����ڻ���/150x200px/";
				adv.getJlImage().setIcon(new ImageIcon(imgPath + daevo.getImg()));
				adv.getJtfName().setText(daevo.getName());
				adv.getJtfTel().setText(daevo.getTel());
				adv.getJtfEmail().setText(daevo.getEmail());
				adv.getJtfRank().setText(daevo.getRank());
				adv.getJtfLoc().setText(daevo.getLoc());
				adv.getJtfEdu().setText(daevo.getEducation());
				adv.getJtfAge().setText(String.valueOf(daevo.getAge()));
				adv.getJtfPort().setText(daevo.getPortfolio());
				adv.getJtfGender().setText(daevo.getGender());

				switch (daevo.getApp_status()) {
				case "U": // �������¸� R�� �ٲ��ش�.
					er_dao.updateAppSatus(new ErAppStatusVO(app_num, "R"));
					break;
				case "A": // ������ �������¸� ������ ���.
					appStatusFlag = true;
					break;
				case "D": // ������ �������¸� ������ ���.
					appStatusFlag = true;
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
			if (appStatusFlag) { // �̹� ������ ���� ���.
				JOptionPane.showMessageDialog(adv, "�̹� �����ڿ��� ������ �߽��ϴ�.");
				return;
			} // end if

			if (!appStatusFlag) {
				switch (JOptionPane.showConfirmDialog(adv, "�� �������� ������ ���� �Ͻðڽ��ϱ�?\n �ѹ� ���� �ϸ� �ǵ��� �� �����ϴ�.")) {
				case JOptionPane.OK_OPTION:
					changeStatusAccept();
				}// end switch
			} // end if
		} // end if

		if (e.getSource() == adv.getJbRefuse()) {// �������� ��ư �̺�Ʈ ó��
			if (appStatusFlag) { // �̹� ������ ���� ���.
				JOptionPane.showMessageDialog(adv, "�̹� �����ڿ��� ������ �߽��ϴ�.");
				return;
			} // end if

			switch (JOptionPane.showConfirmDialog(adv, "�� �������� ������ ���� �Ͻðڽ��ϱ�?\n �ѹ� ���� �ϸ� �ǵ��� �� �����ϴ�.")) {
			case JOptionPane.OK_OPTION:
				changeStatusRefuse();
			}// end switch
		} // end if

		if (e.getSource() == adv.getJbExtRsm()) { // �ܺ��̷¼� ��ư �̺�Ʈ ó��

			try {
				extResumeDown();
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
				appStatusFlag = true;
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
				appStatusFlag = true;
			} // end if
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(adv, "DB���� ������ �߻��߽��ϴ�. ��� �� �ٽ� �̿��� �ּ���.");
			e.printStackTrace();
		} // end catch
	}// changeStatusAccept()

	/**
	 * ������ ���� �����ڰ� ����� �ܺ��̷¼��� �ٿ�޴� �޼���.
	 * 
	 * @throws IOException
	 */
	public void extResumeDown() throws UnknownHostException, IOException {
		if (daevo.getExt_resume() == null) {
			JOptionPane.showMessageDialog(adv, "�� �����ڰ� ����� �ܺ��̷¼��� �����ϴ�.");
			return;
		} // end if

		if (daevo.getExt_resume() != null) {
			String fileName = daevo.getExt_resume();
			Socket client = null;
			DataOutputStream dos = null;
			DataInputStream dis = null;
			try {

				client = new Socket("211.63.89.144", 7002);

				dis = new DataInputStream(client.getInputStream()); // ������ ���� ��û�ޱ�.
				dos = new DataOutputStream(client.getOutputStream()); // ������ ��û ������.
				
				// ������ ���� ���� ��û.
				dos.writeUTF("ee_ext_request"); 
				dos.flush();

				// ������ ���ϸ� ����.
				dos.writeUTF(fileName);
				dos.flush();
				
				
				
			} finally {
				if(client != null) {
					client.close();
				}// end if
			} // end finally

		} // end if

		///////////////////// ���� ���� ���̾� �α� ����. /////////////////////

//		FileDialog fd = new FileDialog(adv, "�̷¼� ����", FileDialog.SAVE);
//		fd.setVisible(true);
//
//		if (fd.getFile() != null && fd.getDirectory() != null) {
//			String ext_resumePath = fd.getDirectory() + fd.getFile() + ".doc";
//
//			// ������ ���� ���� �о������.
////			DefaultListModel<String> dlm = amv.getDlmLog();
////			StringBuilder logData = new StringBuilder();
////			for (int i = 0; i < dlm.size(); i++) {
////				logData.append(dlm.get(i)).append("\n");
////			}
//
//			FileOutputStream fos = null;
//			OutputStreamWriter osw = null;
//			BufferedWriter bw = null;
//			try {
//				fos = new FileOutputStream(ext_resumePath);
//				osw = new OutputStreamWriter(fos);
//				bw = new BufferedWriter(osw);
//
//				// �̷¼� ���� ��Ʈ���� ����.
//				bw.write("");
//				// �̷¼� ���� ��������.
//				bw.flush();
//
//				JOptionPane.showMessageDialog(adv, "�̷¼� ������ ����Ǿ����ϴ�.");
//			} finally { // ���� ����.
//				if (bw != null) {
//					bw.close();
//				} // end if
//				if (osw != null) {
//					osw.close();
//				} // end if
//				if (fos != null) {
//					fos.close();
//				} // end if
//			} // end finally
//
//		} // end if

	}// changeStatusAccept()

}// class
