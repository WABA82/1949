package user.ee.controller;

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

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.ee.view.EeDetailCoView;
import user.ee.vo.CoDetailVO;

/**
 * ȸ�� �� ������ ��Ʈ�ѷ�.
 * 
 * @author ����
 *
 */
public class EeDetailCoController extends WindowAdapter implements ActionListener {

	private EeDetailCoView edcv;
	private CoDetailVO cdvo;

	public EeDetailCoController(EeDetailCoView edcv, CoDetailVO cdvo) {
		this.edcv = edcv;
		this.cdvo = cdvo;
		setInfo(cdvo);
	}// ������

	/**
	 * ȸ�� �� ���� â�� ������ ��� �޼���. - reqCoImg();�޼��� ����.
	 * 
	 * @param cdvo
	 */
	private void setInfo(CoDetailVO cdvo) {

		try { // �̹��� �޾ƿ��� �޼���.
			reqCoImg();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(edcv, "������ IP�� ã�� �� �����ϴ�.");
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(edcv, "������ �������� ���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		} // end catch

		String imgPath = "C:/dev/1949/03.����/src/user/img/co/";

		edcv.getImg1().setIcon(new ImageIcon(imgPath + cdvo.getImg1()));
		edcv.getImg2().setIcon(new ImageIcon(imgPath + cdvo.getImg2()));
		;
		edcv.getImg3().setIcon(new ImageIcon(imgPath + cdvo.getImg3()));
		;
		edcv.getImg4().setIcon(new ImageIcon(imgPath + cdvo.getImg4()));
		;

		edcv.getJtfCoName().setText(cdvo.getCo_name()); // ȸ���.
		edcv.getJtfEstDate().setText(cdvo.getEst_date());// ������.
		edcv.getMemberNum().setText(String.valueOf(cdvo.getMember_num())); // �����
		edcv.getJtaCoDesc().setText(cdvo.getCo_desc()); // �󼼳���.

	}// setInfo()

	/**
	 * user.img.co��Ű���� CoDetailVO�� ������ �ִ� �̹����� ���ٸ� ������ ����� �̹��� ������ ��û�ؼ� �޾ƿ´�.
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void reqCoImg() throws UnknownHostException, IOException {

		String coImgFilePath = "C:/dev/1949/03.����/src/user/img/co/";
		File imgFile1 = new File(coImgFilePath + cdvo.getImg1());
		File imgFile2 = new File(coImgFilePath + cdvo.getImg2());
		File imgFile3 = new File(coImgFilePath + cdvo.getImg3());
		File imgFile4 = new File(coImgFilePath + cdvo.getImg4());

		File[] fileArr = { imgFile1, imgFile2, imgFile3, imgFile4 };
		// ���� �׽�Ʈ : �̹������� �迭�� ����ִ� ���ϵ��� �̸� ����.
//		for (int i = 0; i < fileArr.length; i++) {
//			System.out.println(fileArr[i].getName());
//		} // end for

		for (int i = 0; i < fileArr.length; i++) {

			// �̹��� ������ ���ٸ� ����.
			if (!fileArr[i].exists()) {

				Socket client = new Socket("localhost", 7002); // "211.63.89.144", 7002 : ������ǻ��IP, ���ϼ����� ��Ʈ

				DataInputStream dis = null;
				DataOutputStream dos = null;
				FileOutputStream fos = null;

				try {
					dis = new DataInputStream(client.getInputStream()); // �������� ���� ������input��Ʈ��
					dos = new DataOutputStream(client.getOutputStream()); // �������� ���� ������output��Ʈ��
					fos = new FileOutputStream(fileArr[i]); // �������� ���� ���� ���÷� ��������.

					// �������� �������� �����͸� ���� byte�迭.
					byte[] data = new byte[512];
					// ���پ� ���� �迭�� ���� ����.
					int dataArrLen = 0;
					// �� �޾ƿ��� �迭�� ����.
					int totalDtArrCnt = 0;

					// ���� ��û �޽��� ������ : "����̹��� ��û"
					dos.writeUTF("coImg_request");
					dos.flush();

					// ���� �̸� �޽��� ������
					dos.writeUTF(fileArr[i].getName());
					dos.flush();

					// 1. �������� �������� �迭�� ���� �ޱ�.
					totalDtArrCnt = dis.readInt();

					while (totalDtArrCnt > 0) {
						dataArrLen = dis.read(data); // .read(byte �迭) : byte �迭�� �޾Ƽ� �迭�� ���̸� ��ȯ. byte �迭�� ���� ���� ������ -1
														// ��ȯ.
						fos.write(data, 0, dataArrLen); // .write() : byte �迭�� byte �迭�� 0��° ���� dataArrLen���� �������ϴ�.
						fos.flush();
						totalDtArrCnt--; // byte �迭�� ������ �Ѱ��� �ٿ��ݴϴ�.
					} // end while

				} finally {
					if (fos != null) {
						fos.close();
					} // end if
					if (dos != null) {
						dos.close();
					} // end if
					if (dis != null) {
						dis.close();
					} // end if
					if (client != null) {
						client.close();
					} // end if
				} // end finally
			} // end if
		} // end for
	}// reqCoImg()

	@Override
	public void windowClosing(WindowEvent e) {
		edcv.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == edcv.getJbClose()) {
			edcv.dispose();
		} // end if
	}// ��ư �۾�

}// class