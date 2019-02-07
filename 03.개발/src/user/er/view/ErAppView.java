package user.er.view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ErAppView extends JDialog {
	private JTable jtEr;
	private DefaultTableModel dtmEr;
	
	public ErAppView(ErMainView emv) {
		super(emv, "지원 현황",true);
		
		String[] eeColumns= {"번호","지원번호","제목","회사명","직급","근무지역","학력","고용형태", "급여","지원일","지원상태"};
		dtmEr=new DefaultTableModel(eeColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEr=new JTable(dtmEr);
		JScrollPane jspEr=new JScrollPane(jtEr);
		
		
		add("Center",jspEr);
		
		setBounds(100, 100, 800, 600);
		setVisible(true);
	}
	public static void main(String[] args) {
		ErMainView emv=new ErMainView();
		new ErAppView(emv);
	}
}
