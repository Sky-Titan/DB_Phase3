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
		super("�α���");
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
	
		signin=new JButton("�α���");
		signin.setBounds(50, 140, 100, 30);
		
		signup=new JButton("ȸ������");
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
			if(event.getSource()==signup)//ȸ������
			{
				
				new SignUp();
				setVisible(false);
			}
			else if(event.getSource()==signin)//�α���
			{
				
				if(!id.getText().equals("") && !password.getText().equals(""))
				{
					//ȸ�� �´��� DB���� Ȯ��
					DBConnection connection = new DBConnection();
					String[] results = connection.signInDB(id.getText());
					if(results[0]== null)//ȸ�� �������� ����
					{
						JOptionPane.showMessageDialog(null, "�ش� ȸ���� �������� �ʽ��ϴ�");
						return ;
					}
					System.out.println(password.getText()+" "+results[1]);
					if(password.getText().equals(results[1]))
					{
						if(connection.isAdmin(id.getText()) == true)//���������� Ȯ��
						{
							//�����ڸ޴� ����
							new AccountMenu(id.getText(), true);
						}
						else
						{
							//�� �޴� ����
							new AccountMenu(id.getText(), false);
						}
						//�޴� â���� �̵�
						setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� ���� �ʽ��ϴ�");
						return ;
					}
						
			
				}
				else
				{
					JOptionPane.showMessageDialog(null, "�� ĭ�� �ֽ��ϴ�");
					return ;
				}
			}
		}
	}

}
