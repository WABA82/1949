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
 * 회사 상세 정보의 컨트롤러.
 * 
 * @author 재현
 *
 */
public class EeDetailCoController extends WindowAdapter implements ActionListener {

	private EeDetailCoView edcv;
	private CoDetailVO cdvo;

	public EeDetailCoController(EeDetailCoView edcv, CoDetailVO cdvo) {
		this.edcv = edcv;
		this.cdvo = cdvo;
		setInfo(cdvo);
	}// 생성자

	/**
	 * 회사 상세 정보 창의 정보를 담는 메서드. - reqCoImg();메서드 포함.
	 * 
	 * @param cdvo
	 */
	private void setInfo(CoDetailVO cdvo) {

		try { // 이미지 받아오는 메서드.
			reqCoImg();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(edcv, "서버의 IP를 찾을 수 없습니다.");
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(edcv, "파일을 내보내는 도중 문제가 발생했습니다.");
			e.printStackTrace();
		} // end catch

		String imgPath = "C:/dev/1949/03.개발/src/user/img/co/";

		edcv.getImg1().setIcon(new ImageIcon(imgPath + cdvo.getImg1()));
		edcv.getImg2().setIcon(new ImageIcon(imgPath + cdvo.getImg2()));
		;
		edcv.getImg3().setIcon(new ImageIcon(imgPath + cdvo.getImg3()));
		;
		edcv.getImg4().setIcon(new ImageIcon(imgPath + cdvo.getImg4()));
		;

		edcv.getJtfCoName().setText(cdvo.getCo_name()); // 회사명.
		edcv.getJtfEstDate().setText(cdvo.getEst_date());// 설립일.
		edcv.getMemberNum().setText(String.valueOf(cdvo.getMember_num())); // 사원수
		edcv.getJtaCoDesc().setText(cdvo.getCo_desc()); // 상세내용.

	}// setInfo()

	/**
	 * user.img.co패키지에 CoDetailVO가 가지고 있는 이미지가 없다면 서버로 기업의 이미지 파일을 요청해서 받아온다.
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void reqCoImg() throws UnknownHostException, IOException {

		String coImgFilePath = "C:/dev/1949/03.개발/src/user/img/co/";
		File imgFile1 = new File(coImgFilePath + cdvo.getImg1());
		File imgFile2 = new File(coImgFilePath + cdvo.getImg2());
		File imgFile3 = new File(coImgFilePath + cdvo.getImg3());
		File imgFile4 = new File(coImgFilePath + cdvo.getImg4());

		File[] fileArr = { imgFile1, imgFile2, imgFile3, imgFile4 };
		// 단위 테스트 : 이미지파일 배열에 들어있는 파일들의 이름 찍어보기.
//		for (int i = 0; i < fileArr.length; i++) {
//			System.out.println(fileArr[i].getName());
//		} // end for
		for (int i = 0; i < fileArr.length; i++) {

			// user.img.co패키지에 이미지 파일이 없다면 실행.
			if (!fileArr[i].exists()) {
				Socket client = null; // "211.63.89.144", 7002 : 영근컴퓨터IP, 파일서버의 포트
				DataInputStream dis = null;
				DataOutputStream dos = null;
				FileOutputStream fos = null;

				UserUtil util = new UserUtil(); // 서버와 소통할 유틸객체 생성.
				util.reqFile(fileArr[i].getName(), "co", client, dos, dis, fos);
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
	}// 버튼 작업

}// class