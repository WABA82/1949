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

			// 이미지 파일이 없다면 실행.
			if (!fileArr[i].exists()) {

				Socket client = new Socket("localhost", 7002); // "211.63.89.144", 7002 : 영근컴퓨터IP, 파일서버의 포트

				DataInputStream dis = null;
				DataOutputStream dos = null;
				FileOutputStream fos = null;

				try {
					dis = new DataInputStream(client.getInputStream()); // 서버에게 받을 데이터input스트림
					dos = new DataOutputStream(client.getOutputStream()); // 서버에게 보낼 데이터output스트림
					fos = new FileOutputStream(fileArr[i]); // 서버에게 받은 파일 로컬로 내보내기.

					// 서버에서 보내오는 데이터를 담을 byte배열.
					byte[] data = new byte[512];
					// 한줄씩 읽은 배열의 길이 저장.
					int dataArrLen = 0;
					// 총 받아오는 배열의 갯수.
					int totalDtArrCnt = 0;

					// 파일 요청 메시지 보내기 : "기업이미지 요청"
					dos.writeUTF("coImg_request");
					dos.flush();

					// 파일 이름 메시지 보내기
					dos.writeUTF(fileArr[i].getName());
					dos.flush();

					// 1. 서버에서 보내오는 배열의 갯수 받기.
					totalDtArrCnt = dis.readInt();

					while (totalDtArrCnt > 0) {
						dataArrLen = dis.read(data); // .read(byte 배열) : byte 배열을 받아서 배열의 길이를 반환. byte 배열이 존재 하지 않으면 -1
														// 반환.
						fos.write(data, 0, dataArrLen); // .write() : byte 배열을 byte 배열의 0번째 부터 dataArrLen까지 내보냅니다.
						fos.flush();
						totalDtArrCnt--; // byte 배열의 갯수를 한개씩 줄여줍니다.
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
	}// 버튼 작업

}// class