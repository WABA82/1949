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
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import user.common.vo.ErMainVO;
import user.dao.CommonDAO;
import user.dao.ErDAO;
import user.er.view.CoInfoModifyView;
import user.er.view.ErMainView;
import user.er.vo.CoInfoVO;
import user.util.UserLog;
import user.util.UserUtil;

public class CoInfoModifyController extends WindowAdapter implements ActionListener, MouseListener {

	private CoInfoModifyView cimv;
	
	private File uploadImg1, uploadImg2, uploadImg3, uploadImg4;
	private boolean imgFlag1, imgFlag2, imgFlag3, imgFlag4;
	
	private String path, name;
	private ErDAO erdao;
	private CoInfoVO cvo;
	private ErMainView emv;
	
	private UserUtil uu;
	private UserLog ul;

	public CoInfoModifyController(CoInfoModifyView cimv,  CoInfoVO cvo, ErMainView emv) {
		this.cimv = cimv;
		this.cvo=cvo;
		this.emv=emv;
		erdao = ErDAO.getInstance();
		
		uu = new UserUtil();
		ul = new UserLog();
	}// ������

	@Override
	public void windowClosing(WindowEvent e) {
		cimv.dispose();
	}// ������ �ݱ�

	@Override
	public void actionPerformed(ActionEvent ae) {
		/* ���� ��ư ������ �� */
		if (ae.getSource() == cimv.getJbModify()) {
			try {
				modify();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == cimv.getJbClose()) {
			cimv.dispose();
		} // else if

	}// actionPerformed

	public void modify() throws UnknownHostException, IOException {
		String erId=cvo.getErId();
		String coNum=cvo.getCoNum();
		String coName = cimv.getJtfCoName().getText().trim();
		String estDate = cimv.getJtfEstDate().getText().trim();
		int memberNum = 0;
		
		
		if (coName.equals("")) {
			JOptionPane.showMessageDialog(cimv, "ȸ����� �Է����ּ���.");
			return;
		} // end if

		if (estDate.isEmpty()) {
			JOptionPane.showMessageDialog(cimv, "�������� �Է����ּ���.");
			return;
		} // end if
		
		if(estDate.length()  < 7 ) { //////////////////////// ����ó�� ���ּ���..
        	 JOptionPane.showMessageDialog(cimv, "�����⵵�� �Է������� �Ʒ��� ���� �������� ���ּ���\nex)19901217\nex)1990-12-17");
        	 return;
         }//end if

		try {
			memberNum = Integer.parseInt(cimv.getMemberNum().getText().trim()); 
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(cimv, "������� ���ڸ� �Է°����մϴ�.");
			return;
		} // end catch
		
		if (memberNum == 0) {
			JOptionPane.showMessageDialog(cimv, "������� �Է����ּ���.");
			return;
		} // end if
		
		

		String imgName1 = "";
		String imgName2 = "";
		String imgName3 = "";
		String imgName4 = "";
		
		
		
		
		if (!imgFlag1) { // ������� �ʾ����� ���� �̹��� �״��
			imgName1 = cvo.getImg1();
		} else {
			imgName1 = System.currentTimeMillis()+uploadImg1.getName();
		}
		if (!imgFlag2) { // ������� �ʾ����� ���� �̹��� �״��
			imgName2 = cvo.getImg2();
		} else {
			imgName2 = System.currentTimeMillis()+uploadImg2.getName();
		}
		if (!imgFlag3) { // ������� �ʾ����� ���� �̹��� �״��
			imgName3 = cvo.getImg3();
		} else {
			imgName3 = System.currentTimeMillis()+uploadImg3.getName();
		}
		if (!imgFlag4) { // ������� �ʾ����� ���� �̹��� �״��
			imgName4 = cvo.getImg4();
		} else {
			imgName4 = System.currentTimeMillis()+uploadImg4.getName();
		}
		
		String coDesc = cimv.getJtaCoDesc().getText().trim();

		StringBuilder updateMsg = new StringBuilder();
		updateMsg
		.append("ȸ�� �������� \n").append("ȸ�� �� : ").append(cimv.getJtfCoName().getText()).append("\n")
		.append("���� �⵵ : ").append(cimv.getJtfEstDate().getText()).append("\n").append("��� ��  : ")
		.append(cimv.getMemberNum().getText()).append("\n").append("��� ����  : ")
		.append(cimv.getJtaCoDesc().getText()).append("\n").append("���� ������ ȸ���� ������ ����˴ϴ�. �����Ͻðڽ��ϱ�?");

		switch (JOptionPane.showConfirmDialog(cimv, updateMsg.toString())) {
			case JOptionPane.OK_OPTION:
				CoInfoVO newCvo = new CoInfoVO(erId, coNum, coName, imgName1, imgName2,
						imgName3, imgName4, estDate, coDesc, memberNum);
				try {
					if (erdao.updateCoInfo(newCvo)) {
						
						Socket client = null;
						DataOutputStream dos = null;
						DataInputStream dis = null;
						FileInputStream fis = null;
						FileOutputStream fos = null;
						
						// ����� �̹��� ���� ������ ����(����, ���ϼ���), ���ϼ����� �߰�, ��û 
						File originFile = null;
						if (imgFlag1) {
							System.out.println("==== �������� : "+cvo.getImg1());
							originFile = new File("C:/dev/1949/03.����/src/user/img/co/"+cvo.getImg1());
							originFile.delete();
							
							uu.deleteFile(cvo.getImg1(), "co", client, dos, dis);
							uu.addNewFile(imgName1, uploadImg1, "co", client, dos, dis, fis);
							uu.reqFile(imgName1, "co", client, dos, dis, fos);
							System.out.println("�̹���1 ����");
						}
						if (imgFlag2) {
							originFile = new File("C:/dev/1949/03.����/src/user/img/co/"+cvo.getImg2());
							originFile.delete();
							
							uu.deleteFile(cvo.getImg2(), "co", client, dos, dis);
							uu.addNewFile(imgName2, uploadImg2, "co", client, dos, dis, fis);
							uu.reqFile(imgName2, "co", client, dos, dis, fos);
							System.out.println("�̹���2 ����");
						}
						if (imgFlag3) {
							originFile = new File("C:/dev/1949/03.����/src/user/img/co/"+cvo.getImg3());
							originFile.delete();
							
							uu.deleteFile(cvo.getImg3(), "co", client, dos, dis);
							uu.addNewFile(imgName3, uploadImg3, "co", client, dos, dis, fis);
							uu.reqFile(imgName3, "co", client, dos, dis, fos);
							System.out.println("�̹���3 ����");
						}
						if (imgFlag4) {
							originFile = new File("C:/dev/1949/03.����/src/user/img/co/"+cvo.getImg4());
							originFile.delete();
							
							uu.deleteFile(cvo.getImg4(), "co", client, dos, dis);
							uu.addNewFile(imgName4, uploadImg4, "co", client, dos, dis, fis);
							uu.reqFile(imgName4, "co", client, dos, dis, fos);
							System.out.println("�̹���4 ����");
						}
						
						JOptionPane.showMessageDialog(cimv, "ȸ�� ������ ���� �Ǿ����ϴ�");
						ErMainVO updateEmvo=CommonDAO.getInstance().selectErMain(erId, "Y");
						ul.sendLog(erId, "������� ����");
						new ErMainView(updateEmvo);
						cimv.dispose();
						emv.dispose();
					} else {
						JOptionPane.showMessageDialog(cimv, "ȸ������ ���� ����(DB����)");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}// end switch
	}// modify

	public void changeImg(JLabel jl, int imgNumber) {
		boolean flag = false;
		FileDialog fd = new FileDialog(cimv, "�̹��� ����", FileDialog.LOAD);
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
				cimv.getJlImg1().setIcon(new ImageIcon(uploadImg1.getAbsolutePath()));
			} else if (imgNumber == 2) {
				uploadImg2 = new File(path + name);
				imgFlag2 = true;
				cimv.getJlImg2().setIcon(new ImageIcon(uploadImg2.getAbsolutePath()));
			} else if (imgNumber == 3) {
				uploadImg3 = new File(path + name);
				imgFlag3 = true;
				cimv.getJlImg3().setIcon(new ImageIcon(uploadImg3.getAbsolutePath()));
			} else if (imgNumber == 4) {
				uploadImg4 = new File(path + name);
				imgFlag4 = true;
				cimv.getJlImg4().setIcon(new ImageIcon(uploadImg4.getAbsolutePath()));
			} // end else if
		} else {
			JOptionPane.showMessageDialog(cimv, name + "������ �̹��������� �ƴմϴ�.\n(jpg, jpeg, png, bmp, gif���� ��� ����)");
		} // end else
	}// chgImg

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == cimv.getJlImg1()) {
			changeImg(cimv.getJlImg1(), 1);
		} // end if

		if (me.getSource() == cimv.getJlImg2()) {
			changeImg(cimv.getJlImg2(), 2);
		} // end if

		if (me.getSource() == cimv.getJlImg3()) {
			changeImg(cimv.getJlImg3(), 3);
		} // end if

		if (me.getSource() == cimv.getJlImg4()) {
			changeImg(cimv.getJlImg4(), 4);
		} // end if

	}// mouseClicked

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
}// class
