import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JButton back;

	private JButton sales_btn;

	
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
		setSize(1200, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		back = new JButton("�ڷ� ����");
		back.setBounds(950, 30, 150, 30);
		add(back);
		
		DBConnection connection = new DBConnection();
		Handler h = new Handler();
		
		if(isAdmin)
		{
			sales_btn = new JButton("����� ����");
			sales_btn.setBounds(1100-310, 30, 150, 30);
			add(sales_btn);
			sales_btn.addActionListener(h);
		}
		
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
		
		
		back.addActionListener(h);
		
		setVisible(true);
	}

	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==back)//�ڷΰ���
			{
				new AccountMenu(id, isAdmin);
				dispose();
			}
			else if(event.getSource()==sales_btn)
			{
				new ShowSales(id, isAdmin);
				//dispose();
			}
		}
	}
}
