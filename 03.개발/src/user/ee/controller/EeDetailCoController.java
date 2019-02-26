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
import user.util.UserUtil;

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

		for (int i = 0; i < fileArr.length; i++) {
			// user.img.co��Ű���� �̹��� ������ ���ٸ� ����.
			if (!fileArr[i].exists()) {
				Socket client = null; // "211.63.89.144", 7002 : ������ǻ��IP, ���ϼ����� ��Ʈ
				DataInputStream dis = null;
				DataOutputStream dos = null;
				FileOutputStream fos = null;

				UserUtil util = new UserUtil(); // ������ ������ ��ƿ��ü ����.
				util.reqFile(fileArr[i].getName(), "co", client, dos, dis, fos);
			} // end if
		}// end for
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