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
		super("고객 메뉴 "+id);
		
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
		
		modifyInfo = new JButton("회원 정보 수정");
		modifyInfo.setBounds(75, 50, 150, 30);
		add(modifyInfo);
		
		deleteAccount = new JButton("회원 탈퇴");
		deleteAccount.setBounds(75, 100, 150, 30);
		add(deleteAccount);
		
		vehicles = new JButton("매물 보기");
		vehicles.setBounds(75, 150, 150, 30);
		add(vehicles);
		
		orderlist = new JButton("거래 내역 보기");
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
			if(event.getSource()==modifyInfo)//회원정보수정
			{
				
				//TODO : 회원정보수정창
				
			}
			else if(event.getSource()==deleteAccount)//회원 탈퇴
			{
				//회원탈퇴 시키기
				DBConnection connection = new DBConnection();
				if(connection.deleteAccount(id))
				{
					setVisible(false);
					new Login();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "서버에러");
					return ;
				}
			}
			else if(event.getSource() == vehicles)//매물보기
			{
				//TODO : 매물창
			}
			else if(event.getSource() == orderlist)//거래내역보기
			{
				//TODO : 거래내역 창
			}
		}
	}
}
