package user.er.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.common.vo.ErMainVO;
import user.er.vo.ErListVO;


@SuppressWarnings("serial")
public class ErAppView extends JDialog {


	private JTable jtEr;
	private DefaultTableModel dtmEr;
	
	public ErAppView(ErMainView rmv, List<ErListVO> rl_vo) {
		super(rmv,"���� ��Ȳ",true);
	
		String[] erInfoColumns= {"��ȣ","����������ȣ","����","����","�ٹ�����","�з�","�������","�����"};
		dtmEr=new DefaultTableModel(erInfoColumns, 40) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtEr=new JTable(dtmEr);
		JScrollPane jspEeInfo=new JScrollPane(jtEr);
		
		JLabel jlEeInfo=new JLabel("���� ����� �������� �� : ");
		
		setLayout(null);
		
		jlEeInfo.setBounds(800, 8, 150, 30);
		jspEeInfo.setBounds(0, 40, 995, 450);
		
		add(jlEeInfo);
		add(jspEeInfo);
		
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	public JTable getJtEeInfo() {
		return jtEr;
	}

	public DefaultTableModel getDtmEeInfo() {
		return dtmEr;
	}
/*	public static void main(String[] args) {
		ErMainVO rm_vo = new ErMainVO("fs", "sd", "as", "sd");
		ErMainView rmv=new ErMainView(rm_vo); 
		List<ErListVO> rl_vo = new ArrayList<ErListVO>();
		new ErAppView(rmv,rl_vo);
		 
	}*/

}
