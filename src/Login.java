import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import javafx.scene.control.PasswordField;


public class Login extends JFrame{
	
	public static final String URL = "jdbc:oracle:thin:@155.230.36.61:1521:orcl";
	public static final String USER_KNU ="s2014105038";
	public static final String USER_PASSWD ="qkrwns2081";
	
	private JPanel loginPan,pan;
	private JButton signin, signup;
	private JTextField id;
	private JPasswordField password;

	
	public Login()
	{
		super("로그인");
		try
		 {
			   UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		 }
		 catch(Exception e)
		 {
		 }
		setLocation(800, 230);
		setSize(310, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		
		id=new JTextField(15);
		id.setBounds(50, 50, 210, 30);

		password=new JPasswordField(15);
		password.setBounds(50, 90, 210, 30);
	
		signin=new JButton("로그인");
		signin.setBounds(50, 140, 100, 30);
		
		signup=new JButton("회원가입");
		signup.setBounds(160, 140, 100, 30);
		
		
		Handler h=new Handler();
		signin.addActionListener(h);
		signup.addActionListener(h);
		
		add(id);
		add(password);
		add(signin);
		add(signup);
		
		setVisible(true);
	}
	static String[] signInDB(String id)
	{
		String[] results = {"",""};
		Connection conn = null; // Connection object
		Statement stmt = null;	// Statement object
		
		try {
			// Load a JDBC driver for Oracle DBMS
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Get a Connection object 
			System.out.println("Success!");
		}catch(ClassNotFoundException e) {
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		}

		// Make a connection
		try{
			conn = DriverManager.getConnection(URL, USER_KNU, USER_PASSWD); 
		}catch(SQLException ex) {
			System.err.println("Cannot get a connection: " + ex.getMessage());
			System.exit(1);
		}
		
		try {
			conn.setAutoCommit(false); // auto-commit disabled  
			// Create a statement object
			stmt = conn.createStatement();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	
		//서버에 확인
		try
		{
			String sql = "SELECT ID, PASSWORD FROM ACCOUNT WHERE ID = '"+id+"'";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("wow");
			if(!rs.next())//해당 회원 존재하지 않음
			{
				System.out.println("dkdkdkdk");
				results[0] = null;
				results[1] = null;
			}
			else {
				// Fill out your code
				results[0] = rs.getString(1);//아이디
				results[1] = rs.getString(2);//비밀번호
				System.out.println(results[0]+" "+results[1]);
			}
			rs.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Release database resources.
		try {
			// Close the Statement object.
			stmt.close(); 
			// Close the Connection object.
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
		
	}
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==signup)//회원가입
			{
				
				new SignUp();
				setVisible(false);
			}
			else if(event.getSource()==signin)//로그인
			{
				
				if(!id.getText().equals("") && !password.getText().equals(""))
				{
					//회원 맞는지 DB에서 확인
					String[] results = signInDB(id.getText());
					if(results[0]== null)//회원 존재하지 않음
					{
						JOptionPane.showMessageDialog(null, "해당 회원은 존재하지 않습니다");
						return ;
					}
					System.out.println(password.getText()+" "+results[1]);
					if(password.getText().equals(results[1]))
					{
						//TODO : 회원 관리 창으로 이동
						setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "아이디와 비밀번호가 맞지 않습니다");
						return ;
					}
						
			
				}
				else
				{
					JOptionPane.showMessageDialog(null, "빈 칸이 있습니다");
					return ;
				}
			}
		}
	}

}
