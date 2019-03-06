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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.dao.ErDAO;
import user.er.view.ErDetailEeView;
import user.er.vo.DetailEeInfoVO;
import user.er.vo.ErInterestVO;
import user.util.UserLog;

public class ErDetailEeController extends WindowAdapter implements ActionListener, MouseListener {
	private ErDetailEeView edev;
	private String eeNum, erId;
	private boolean mouseClickFlag; 
	private ErInterestVO eivo;
	private ErDAO erdao;
	private DetailEeInfoVO devo;
	private UserLog ul;
	
	public ErDetailEeController(ErDetailEeView edev, String eeNum, String erId, boolean flagHeart) {
		this.edev = edev;
		this.eeNum = eeNum;
		this.erId = erId;
		mouseClickFlag=flagHeart;
		erdao= ErDAO.getInstance();
		ul=new UserLog();
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

		edev.getJlHeart().setIcon(new ImageIcon("C:/dev/1949/03.����/src/user/img/r_heart.png"));
		JOptionPane.showMessageDialog(edev, "���� �����ڿ� �߰��Ǿ����ϴ�!");
		ul.sendLog(erId, "["+eeNum+ "]��ȣ ������ ���� �����ڷ� �߰��Ͽ����ϴ�.");
		try {
			devo= erdao.selectDetailEe(eeNum, erId);

		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void removeInterestEe() {
		boolean deleteFlag=false;
		eivo = new ErInterestVO(eeNum, erId);
		
		try {
			deleteFlag = erdao.deleteInterestEe(eivo);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(edev, "����Ʈ������ �����߽��ϴ�.");
			
		}
		if(deleteFlag) {
			JOptionPane.showMessageDialog(edev, "���� �����ڸ� ����߽��ϴ�.");
			ul.sendLog(erId, "["+eeNum+ "]��ȣ ������ ���� �����ڿ��� ����Ͽ����ϴ�.");
			edev.getJlHeart().setIcon(new ImageIcon("C:/dev/1949/03.����/src/user/img/b_heart.png"));
		}else {
			JOptionPane.showMessageDialog(edev, "����Ʈ������ �����߽��ϴ�.");
		}
	}//removeInterestEr
	
	public void extRsmDown() throws UnknownHostException, IOException{
		//ee_info����  erNum���� ��ȸ�ؼ� �ִ��� ������ ��ȸ�ؼ� �̷¼� �̸��ޱ�
		try {
			devo= erdao.selectDetailEe(eeNum, erId);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(edev, "DB����!!");
			e.printStackTrace();
		}
		if (devo.getExtResume() == null) {
			JOptionPane.showMessageDialog(edev, "�� �����ڰ� ����� �ܺ��̷¼��� �����ϴ�.");
			return;
		} // end if

		if (devo.getExtResume() != null) {
			
			// FileDialog�� �̷¼� �������, ���ϸ��� ���ϰ� �� ���,���Ϸ� fos�� write
			FileDialog fdSave = new FileDialog(edev, "���� ��� ����", FileDialog.SAVE);
			fdSave.setVisible(true);
			
			String path = fdSave.getDirectory();
			String name = fdSave.getFile();
			String resumeName=devo.getExtResume();
			String ext ="";
			
			//Ȯ���� �����
			ext = resumeName.substring(resumeName.lastIndexOf("."));
			
			Socket socket = null;
			DataOutputStream dos = null;
			DataInputStream dis = null;
			FileOutputStream fos = null;

			try {
				System.out.println("111");
				socket = new Socket("localhost", 7002);
				// socket = new Socket("211.63.89.144", 7002);
				System.out.println("--"+socket);
				dos = new DataOutputStream(socket.getOutputStream());

				// �������� �̷¼����� ���� ��û ������.
				dos.writeUTF("ee_ext_request");
				dos.flush();
				System.out.println("222");

				// �������� ��û�� ���ϸ� ������.
				dos.writeUTF(devo.getExtResume().trim());
				dos.flush();
				System.out.println("333");

				dis = new DataInputStream(socket.getInputStream());

				int fileCnt = 0; // �������� �������� ���� ������ ����.
				int data = 0; // �������� �������� ������

				// ���޹��� ���� ������ ����
				fileCnt = dis.readInt();
				
				fos = new FileOutputStream(path+name+ext);
				System.out.println("----"+path+name+ext);

				byte[] readData = new byte[512];
				while (fileCnt > 0) {
					data = dis.read(readData); // �������� ������ ���������� �о�鿩
					fos.write(readData, 0, data);// ������ ���Ϸ� ���
					fos.flush();
					fileCnt--;
				} // end while
				
				dos.writeUTF("����Ǿ����ϴ�.");
				dos.flush();
				JOptionPane.showMessageDialog(edev, "���� �ٿ��� �Ϸ�Ǿ����ϴ�!");
				
			} finally {
				if(fos != null) {
					fos.close();
				}// end if
				if (dos != null) {
					dos.close();
				} // end if
				if (socket != null) {
					socket.close();
				} // end if
			} // end finally
		} // end if

	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if(me.getSource()==edev.getJlHeart()) {
			mouseClickFlag = !mouseClickFlag;
		}
		if(mouseClickFlag) {
			addInterestEe();
		}else {
			removeInterestEe();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==edev.getJbRsmDown()) {
			try {
				
				extRsmDown();
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(edev, "DB����!");
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(edev, "DB����!");
				e.printStackTrace();
			}
		}
		if(ae.getSource()==edev.getJbClose()) {
			edev.dispose();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		edev.dispose();
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}