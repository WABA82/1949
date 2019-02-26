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
import user.er.view.ErDetailEeView;
import user.er.view.ErInterestView;
import user.er.vo.DetailEeInfoVO;
import user.er.vo.ErHiringForInterestVO;
import user.util.UserUtil;

public class ErInterestController extends WindowAdapter implements MouseListener {

	private ErInterestView eriv;
	private String er_id;
	private ErDAO er_dao;
	private final int DBL_CLICK = 2;

	public ErInterestController(ErInterestView eriv, String er_id) {
		this.eriv = eriv;
		this.er_id = er_id;
		er_dao = ErDAO.getInstance();
		setDTM(er_id);
	}// 생성자

	@Override
	public void windowClosing(WindowEvent e) {
		eriv.dispose(); // 종료처리
	}// windowClosing(WindowEvent e)

	/**
	 * 로그인 되어있는 기업사용자의 id를 사용해 DB에서 관심구직자 목록을 가져와 화면에 목록으로 출력하는 일.
	 * 
	 * @param er_id
	 */
	private void setDTM(String er_id) {

//		try {
//			reqEeImg();
//		} catch (UnknownHostException e1) {
//			JOptionPane.showMessageDialog(eriv, "서버의 IP를 찾을수 없습니다.");
//			e1.printStackTrace();
//		} catch (ClassNotFoundException e1) {
//			JOptionPane.showMessageDialog(eriv, "객체를 찾을 수 없습니다.");
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			JOptionPane.showMessageDialog(eriv, "파일을 내보내는 도중 문제가 발생했습니다.");
//			e1.printStackTrace();
//		} // end catch

		DefaultTableModel dtm = eriv.getDtmEeInfo();
		dtm.setRowCount(0); // DTM 0으로 초기화.

		try {
			// DB에서 관심회사를 조회.
			List<ErHiringForInterestVO> list = er_dao.selectInterestEEInfoList(er_id);

			StringBuffer interestCnt = new StringBuffer("내 관심 구직자 수 : ");
			interestCnt.append(String.valueOf(list.size())).append(" 개");
			eriv.getJlEeInfo().setText(interestCnt.toString());

			// JTable에 조회한 정보를 출력.
			ErHiringForInterestVO erhForInterest = null;
			String imgPath = "C:/dev/1949/03.개발/src/user/img/ee/";

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list에 담겨진 VO객체로 EeInterestVO객체 생성하기 */
				erhForInterest = list.get(i);

				// DTM에 데이터를 추가하기 위한 일차원배열(Vector)을 생성하고 데이터를 추가
				rowData = new Object[11];
				rowData[0] = new Integer(i + 1);
				rowData[1] = erhForInterest.getEe_num();

				File imgFile = new File(imgPath + erhForInterest.getImg());
				// user.img.co패키지에 이미지 파일이 없다면 실행.
				System.out.println(imgFile.exists());
				if (!imgFile.exists()) {
					Socket client = null; // "211.63.89.144", 7002 : 영근컴퓨터IP, 파일서버의 포트
					DataInputStream dis = null;
					DataOutputStream dos = null;
					FileOutputStream fos = null;
					UserUtil util = new UserUtil(); // 서버와 소통할 유틸객체 생성.
					try {
						util.reqFile(imgFile.getName(), "ee", client, dos, dis, fos);
					} catch (IOException e) {
						e.printStackTrace();
					} // end try
				} // end if

				rowData[2] = new ImageIcon(imgPath + erhForInterest.getImg());
				rowData[3] = erhForInterest.getName();
				rowData[4] = (erhForInterest.getRank().equals("N") ? "경력" : "신입");
				rowData[5] = erhForInterest.getLoc();
				rowData[6] = erhForInterest.getEducation();
				rowData[7] = new Integer(erhForInterest.getAge());
				rowData[8] = (erhForInterest.getPortfolio().equals("Y") ? "있음" : "없음");
				rowData[9] = (erhForInterest.getGender().equals("M") ? "남자" : "여자");
				rowData[10] = erhForInterest.getInput_date();

				// DTM에 추가
				dtm.addRow(rowData);
			} // end for

			if (list.isEmpty()) {
				JOptionPane.showMessageDialog(eriv, "관심구인정보가 없습니다. 먼저 구인정보에서 하트를 눌러주세요.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eriv, "DB에서 데이터를 받아오는 중 문제 발생...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	/**
	 * 더블클릭했을 때 실행될 메소드.
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		switch (me.getClickCount()) {
		case DBL_CLICK:
			if (me.getSource() == eriv.getJtEeInfo()) {
				showDetailErInfo();
			} // end if
		}// end switch
	}// mouseClicked

	private void showDetailErInfo() {
		JTable jt = eriv.getJtEeInfo();
		String eeNum = String.valueOf(jt.getValueAt(jt.getSelectedRow(), 1));
		DetailEeInfoVO devo = null;
		try {
			devo = er_dao.selectDetailEe(eeNum, er_id);
			String ee_num = ((String) jt.getValueAt(jt.getSelectedRow(), 1));
			ErDetailEeView erdev = new ErDetailEeView(eriv, devo, ee_num, er_id, "1");

			// ErDetailEeView 객체가 동작을 멈추면 true발생
			if (erdev.isActive()) {
				setDTM(er_id);
			} // end if

		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
	}// showDetailErInfo()

//	private void reqEeImg() throws UnknownHostException, IOException, ClassNotFoundException {
//		// 파일서버에 접속해서 없는 co이미지를 내려받는 메소드
//		Socket client = null;
//		DataOutputStream dos = null;
//		DataInputStream dis = null;
//		ObjectOutputStream oos = null;
//		ObjectInputStream ois = null;
//		FileOutputStream fos = null;
//
//		try {
//			client = new Socket("211.63.89.144", 7002);
//
//			dos = new DataOutputStream(client.getOutputStream());
//			dis = new DataInputStream(client.getInputStream());
//
//			dos.writeUTF("eeImgs_list_req"); // flag - ee전체 파일목록 요청
//			dos.flush();
//
//			ois = new ObjectInputStream(client.getInputStream());
//
//			// 파일서버로부터 파일명리스트를 전달받음
//			List<String> listImg = (List<String>) ois.readObject();
//
//			File dir = new File("C:/dev/1949/03.개발/src/user/img/ee");
//			for (File f : dir.listFiles()) {
//				listImg.remove(f.getName()); // 존재하는 파일은 제외
//			}
//
//			oos = new ObjectOutputStream(client.getOutputStream());
//
//			// user에 없는 파일들, 파일서버에 전송
//			oos.writeObject(listImg);
//			oos.flush();
//
//			String fileName = "";
//			byte[] readData = new byte[512];
//			int arrCnt = 0;
//			int len = 0;
//
//			for (int i = 0; i < listImg.size(); i++) {
//				fileName = dis.readUTF(); // 파일명 받기
//
//				arrCnt = dis.readInt(); // 파일 크기 받기
//
//				fos = new FileOutputStream(dir.getAbsolutePath() + "/" + fileName);
//
//				for (int j = 0; j < arrCnt; j++) {
//					len = dis.read(readData);
//					fos.write(readData, 0, len);
//					fos.flush();
//				}
//				fos.close();
//				dos.writeUTF("downDone");
//				dos.flush();
//			}
//		} finally {
//			if (client != null) {
//				client.close();
//			}
//			if (dos != null) {
//				dos.close();
//			}
//			if (dis != null) {
//				dis.close();
//			}
//			if (oos != null) {
//				oos.close();
//			}
//			if (ois != null) {
//				ois.close();
//			}
//			if (fos != null) {
//				fos.close();
//			}
//		} // end finally
//	}// reqEeImg()

	////////// 안쓰는 메소드 //////////
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
	////////// 안쓰는 메소드 //////////

}// class
