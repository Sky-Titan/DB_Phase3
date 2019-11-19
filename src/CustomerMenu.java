import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class CustomerMenu extends JFrame{

	private JButton modifyInfo, deleteAccount, vehicles, orderlist;
	private String id;
	
	
	public CustomerMenu(String id) {
		super("�� �޴� "+id);
		
		this.id = id;
		
		try
		 {
			   UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		 }
		 catch(Exception e)
		 {
		 }
		setLocation(800, 200);
		//getContentPane().setBackground(Color.white);
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		modifyInfo = new JButton("ȸ�� ���� ����");
		modifyInfo.setBounds(75, 50, 150, 30);
		add(modifyInfo);
		
		deleteAccount = new JButton("ȸ�� Ż��");
		deleteAccount.setBounds(75, 100, 150, 30);
		add(deleteAccount);
		
		vehicles = new JButton("�Ź� ����");
		vehicles.setBounds(75, 150, 150, 30);
		add(vehicles);
		
		orderlist = new JButton("�ŷ� ���� ����");
		orderlist.setBounds(75, 200, 150, 30);
		add(orderlist);
		
		Handler h=new Handler();
		modifyInfo.addActionListener(h);
		deleteAccount.addActionListener(h);
		vehicles.addActionListener(h);
		orderlist.addActionListener(h);
		
		setVisible(true);
	}
	
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==modifyInfo)//ȸ����������
			{
				
				//TODO : ȸ����������â
				
			}
			else if(event.getSource()==deleteAccount)//ȸ�� Ż��
			{
				//ȸ��Ż�� ��Ű��
				DBConnection connection = new DBConnection();
				if(connection.deleteAccount(id))
				{
					setVisible(false);
					new Login();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "��������");
					return ;
				}
			}
			else if(event.getSource() == vehicles)//�Ź�����
			{
				//TODO : �Ź�â
			}
			else if(event.getSource() == orderlist)//�ŷ���������
			{
				//TODO : �ŷ����� â
			}
		}
	}
}
