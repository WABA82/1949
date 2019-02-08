package user.ee.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.common.vo.EeMainVO;
import user.ee.vo.EeHiringVO;

@SuppressWarnings("serial")
public class EeInterestView extends JDialog {

	private JTable jtErInfo;
	private DefaultTableModel dtmErInfo;
	
	public EeInterestView(EeMainView emv, List<EeHiringVO> ehvo) {
		super(emv,"���� ���� ����",true);
	
		String[] erInfoColumns= {"��ȣ","����������ȣ","����","ȸ���","����","�ٹ�����","�з�","�������","�޿�","�����"};
		dtmErInfo=new DefaultTableModel(erInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtErInfo=new JTable(dtmErInfo);
		JScrollPane jspEeInfo=new JScrollPane(jtErInfo);
		
		JLabel jlEeInfo=new JLabel("�� ���� �������� �� : ");
		
		setLayout(null);
		
		jlEeInfo.setBounds(830, 8, 130, 30);
		jspEeInfo.setBounds(0, 40, 1000, 450);
		
		add(jlEeInfo);
		add(jspEeInfo);
		
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	public JTable getjtErInfo() {
		return jtErInfo;
	}

	public DefaultTableModel getdtmErInfo() {
		return dtmErInfo;
	}
/*	public static void main(String[] args) {
		EeMainVO em_vo= new EeMainVO("fsd", "fdsf", "sdf", "dsfs");
		EeMainView emv=new EeMainView(em_vo);
		List<EeHiringVO> ehvo=new ArrayList<EeHiringVO>();
		new EeInterestView(emv,ehvo);
	}*/

}
