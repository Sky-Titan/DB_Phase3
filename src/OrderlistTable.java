import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class OrderlistTable extends JFrame{
	
	private String id;
	private boolean isAdmin;
	private String[] header = {"�ŷ� ��ȣ","�ŷ� ��¥","������ID","��","���θ�","����(��)","����","����","����","���̺긮��"};
	private String[][] data;
	private JTable table;
	private JScrollPane scrollPane;
	
	public OrderlistTable(String id,boolean isAdmin)
	{
		super("�ŷ� ����");
		this.id = id;
		this.isAdmin = isAdmin;
		
		try
		 {
			   UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		 }
		 catch(Exception e)
		 {
		 }
		setLocation(500, 200);
		//getContentPane().setBackground(Color.white);
		setSize(1200, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		DBConnection connection = new DBConnection();
		
		data = connection.selectOrderlist(id, isAdmin);
		
		DefaultTableModel model = new DefaultTableModel(data, header){ public boolean isCellEditable(int i, int c){ return false; } };//�����Ұ�
		
		table = new JTable(model);
		table.setRowHeight(40);
		table.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
	    table.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//���� ���ø��
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(100, 100, 1000, 400);
		add(scrollPane);
		
		setVisible(true);
	}

}
