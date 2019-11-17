import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import javafx.scene.control.PasswordField;


public class Login extends JFrame{
	
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
				
				//TODO : 회원 맞는지 DB에서 확인
				if(id.getText().equals("")&password.getText().equals(""))
				{
					
					setVisible(false);
			
				}
				//TODO : 회원 아니면 경고 메시지
				/*else
				{
					JOptionPane.showMessageDialog(null, "아이디와 비번이 맞지않습니다");
					return ;
				}*/
			}
		}
	}

}
