import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class AccountMenu extends JFrame{

	private JButton modifyInfo, deleteAccount, vehicles, orderlist, logout;
	private String id;
	private boolean isAdmin;
	
	public AccountMenu(String id,boolean isAdmin) {
		super("회원 메뉴 "+id);
		
		this.id = id;
		this.isAdmin = isAdmin;
		try
		 {
			   UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		 }
		 catch(Exception e)
		 {
		 }
		setLocation(800, 200);
		//getContentPane().setBackground(Color.white);
		setSize(300, 350);
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
		
		logout = new JButton("로그아웃");
		logout.setBounds(75, 250, 150, 30);
		add(logout);
		
		Handler h=new Handler();
		modifyInfo.addActionListener(h);
		deleteAccount.addActionListener(h);
		vehicles.addActionListener(h);
		orderlist.addActionListener(h);
		logout.addActionListener(h);
		setVisible(true);
	}
	
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==modifyInfo)//회원정보수정
			{
				
				//회원정보수정창
				new AccountModification(id);
				
			}
			else if(event.getSource()==deleteAccount)//회원 탈퇴
			{
				//회원탈퇴 시키기
				DBConnection connection = new DBConnection();
				if(isAdmin)//관리자 계정은 관리자 계정 최소 1개이상 존재하는지 체크
				{
					if(connection.countAdminAccount() <= 1)//현재 서버에 관리자계정 1개 이하인 경우 탈퇴 불가
					{
						JOptionPane.showMessageDialog(null, "관리자 계정은 최소 1개이상이여야합니다");
						return ;
					}
					
				}
				
				//탈퇴
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
				//매물창
				new VehicleTable(id, isAdmin);
			}
			else if(event.getSource() == orderlist)//거래내역보기
			{
				//TODO : 거래내역 창
			}
			else if(event.getSource() == logout)//로그아웃
			{
				setVisible(false);
				new Login();
			}
		}
	}
}
