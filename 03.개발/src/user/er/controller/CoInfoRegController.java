package user.er.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import user.common.vo.ErMainVO;
import user.dao.CommonDAO;
import user.dao.ErDAO;
import user.er.view.CoInfoRegView;
import user.er.view.ErMainView;
import user.er.vo.CoInsertVO;
import user.util.UserLog;
import user.util.UserUtil;

public class CoInfoRegController extends WindowAdapter implements MouseListener, ActionListener {

	private CoInfoRegView cirv;
	private String erId;

	private File uploadImg1, uploadImg2, uploadImg3, uploadImg4;
	private boolean imgFlag1, imgFlag2, imgFlag3, imgFlag4;
	private UserUtil uu;
	private UserLog ul;
	private String path, name;
	private ErDAO erdao;
	private ErMainView emv;

	public CoInfoRegController(CoInfoRegView cirv, String erId, ErMainView emv) {
		this.cirv = cirv;
		this.erId = erId;
		this.emv = emv;
		erdao = ErDAO.getInstance();

		uu = new UserUtil();
		ul = new UserLog();
	}// ������

	@Override
	public void windowClosing(WindowEvent e) {
		cirv.dispose();
	}// ������ �ݱ�

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == cirv.getJbReg()) {
			try {
				register();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(cirv, "DB����");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == cirv.getJbClose()) {
			cirv.dispose();
		}
	}// ��ư ����

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == cirv.getJlImg1()) {
			chgImg(cirv.getJlImg1(), 1);
		} // end if

		if (me.getSource() == cirv.getJlImg2()) {
			chgImg(cirv.getJlImg2(), 2);
		} // end if

		if (me.getSource() == cirv.getJlImg3()) {
			chgImg(cirv.getJlImg3(), 3);
		} // end if

		if (me.getSource() == cirv.getJlImg4()) {
			chgImg(cirv.getJlImg4(), 4);
		} // end if

	}// mouseClicked

	public void register() throws SQLException, IOException {
//		boolean insertFlag = false;
		if (uploadImg1 == null) {
			JOptionPane.showMessageDialog(cirv, "ȸ��ΰ�� �ʼ� �����Դϴ�.");
			return;
		} // end if

		String coName = cirv.getJtfCoName().getText().trim();
		String estDate = cirv.getJtfEstDate().getText().trim();
		String coDesc = cirv.getJtaCoDesc().getText().trim();

		if (coName.equals("")) {
			JOptionPane.showMessageDialog(cirv, "ȸ����� �Է����ּ���.");
			return;
		} // end if

		if (estDate.isEmpty()) {
			JOptionPane.showMessageDialog(cirv, "�������� �Է����ּ���.");
			return;
		} // end if

		if (estDate.length() < 7) {
			JOptionPane.showMessageDialog(cirv, "�����⵵�� �Է������� �Ʒ��� ���� �������� ���ּ���\nex)19901217\nex)1990-12-17");
			return;
		} // end if

		int memberNum = 0;
		try {
			memberNum = Integer.parseInt(cirv.getMemberNum().getText().trim());

			if (memberNum == 0) {
				JOptionPane.showMessageDialog(cirv, "������� �Է����ּ���.");
				return;
			} // end if

		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(cirv, " ������� ���ڸ� �Է� �����մϴ�.");
			return;
		} // end catch

		if (coDesc.equals("")) {
			JOptionPane.showMessageDialog(cirv, "��� ������ �Է����ּ���!");
			return;
		}

		String imgName1 = "";
		String imgName2 = "";
		String imgName3 = "";
		String imgName4 = "";
		
		// ȸ��ΰ�� �ʼ��׸��̹Ƿ� null�ϸ��� ����(������ ����ó��)
		imgName1 = System.currentTimeMillis()+uploadImg1.getName();

		if (uploadImg2 == null) {
			uploadImg2 = new File("C:/dev/1949/03.����/src/user/img/co/no_co_img2.png");
			imgName2 = "no_co_img2.png";
		} else {
			imgName2 = System.currentTimeMillis()+uploadImg2.getName();
		}
		
		if (uploadImg3 == null) {
			uploadImg3 = new File("C:/dev/1949/03.����/src/user/img/co/no_co_img3.png");
			imgName3 = "no_co_img3.png";
		} else {
			imgName3 = System.currentTimeMillis()+uploadImg3.getName();
		}
		
		if (uploadImg4 == null) {
			uploadImg4 = new File("C:/dev/1949/03.����/src/user/img/co/no_co_img4.png");
			imgName4 = "no_co_img4.png";
		} else {
			imgName4 = System.currentTimeMillis()+uploadImg4.getName();
		}
		
		CoInsertVO civo = null;
		civo = new CoInsertVO(erId, imgName1, imgName2, imgName3,
				imgName4, coName, estDate, coDesc, memberNum);
		
//		ActivationVO avo = new ActivationVO(erId);
		
		
		if (erdao.updateErInfo(civo)) {
			JOptionPane.showMessageDialog(cirv, "ȸ�簡 ��ϵǾ����ϴ�.");
		
			Socket client = null;
			DataOutputStream dos = null;
			DataInputStream dis = null;
			FileInputStream fis = null;
			FileOutputStream fos = null;
			
			/////////// �̹����� ����ƴٸ� FS�� ����۾� ���� // ����0302
			if (imgFlag1) {
				uu.addNewFile(imgName1, uploadImg1, "co", client, dos, dis, fis);
				uu.reqFile(imgName1, "co", client, dos, dis, fos);
			}
			
			if (imgFlag2) {
				uu.addNewFile(imgName2, uploadImg2, "co", client, dos, dis, fis);
				uu.reqFile(imgName2, "co", client, dos, dis, fos);
			}
			
			if (imgFlag3) {
				uu.addNewFile(imgName3, uploadImg3, "co", client, dos, dis, fis);
				uu.reqFile(imgName3, "co", client, dos, dis, fos);
			}
			
			if (imgFlag4) {
				uu.addNewFile(imgName3, uploadImg3, "co", client, dos, dis, fis);
				uu.reqFile(imgName3, "co", client, dos, dis, fos);
			}
			
			JOptionPane.showMessageDialog(cirv, "ȸ�� ������ ��ϵǾ����ϴ�\n�������� ���� ������ ��ȸ�����մϴ�.");
			ErMainVO updateEmvo = CommonDAO.getInstance().selectErMain(erId, "Y");
			String coNum=erdao.selectCoNum(erId);
			ul.sendLog(erId, "["+coNum+"] ���");
			new ErMainView(updateEmvo);
			emv.dispose();
			cirv.dispose();
		} else {
			JOptionPane.showMessageDialog(cirv, "ȸ�� ��� ����(DB ����");
		}
	}// register

	public void chgImg(JLabel jl, int imgNumber) {
		boolean flag = false;
		FileDialog fd = new FileDialog(cirv, "�̹��� ����", FileDialog.LOAD);
		fd.setVisible(true);

		path = fd.getDirectory();
		name = fd.getFile();
		
		if (path == null || name == null) {
			return;
		}
		
		String[] extFlag = { "jpg", "jpeg", "png", "bmp", "gif" };
		for (String ext : extFlag) {
			if (name.toLowerCase().endsWith(ext)) {
				flag = true;
			} // end if
		} // end for

		if (flag) {
			if (imgNumber == 1) {
				uploadImg1 = new File(path + name);
				imgFlag1 = true;
				cirv.getJlImg1().setIcon(new ImageIcon(uploadImg1.getAbsolutePath()));
			} else if (imgNumber == 2) {
				uploadImg2 = new File(path + name);
				imgFlag2 = true;
				cirv.getJlImg2().setIcon(new ImageIcon(uploadImg2.getAbsolutePath()));
			} else if (imgNumber == 3) {
				uploadImg3 = new File(path + name);
				imgFlag3 = true;
				cirv.getJlImg3().setIcon(new ImageIcon(uploadImg3.getAbsolutePath()));
			} else if (imgNumber == 4) {
				uploadImg4 = new File(path + name);
				imgFlag4 = true;
				cirv.getJlImg4().setIcon(new ImageIcon(uploadImg4.getAbsolutePath()));
			} // end else if
		} else {
			JOptionPane.showMessageDialog(cirv, name + "������ �̹��������� �ƴմϴ�.\n(jpg, jpeg, png, bmp, gif���� ��� ����)");
		} // end else
	}// chgImg

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}