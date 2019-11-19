import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

public class DBConnection {
	public static final String URL = "jdbc:oracle:thin:@155.230.36.61:1521:orcl";
	public static final String USER_KNU ="s2014105038";
	public static final String USER_PASSWD ="qkrwns2081";
	
	private static Connection conn = null; // Connection object
	private static Statement stmt = null;	// Statement object
	
	public DBConnection()
	{
	
	}
	
	//�ŷ������ҷ�����
	public static String[][] selectOrderlist(String id, boolean isAdmin)
	{
		String[][] result = null;
		
		connect();
		
		try
		{
			String sql;
			if(isAdmin)//�������� ���
				sql = "SELECT * FROM ORDER_LIST";
			else//���� ���
				sql = "SELECT * FROM ORDER_LIST WHERE BUYERID ='"+id+"'";
			
			ResultSet rs = stmt.executeQuery(sql);
			int i=0;
			ResultSetMetaData rsmd = rs.getMetaData();
			
			rs.last();
			
			result = new String[rs.getRow()][rsmd.getColumnCount()];
			rs.beforeFirst();
			while(rs.next())
			{
				for(int j=0;j<rsmd.getColumnCount();j++)
				{
					if(j==6 || j==1)
					{
						System.out.println(rs.getString(j+1));
						StringTokenizer strtok = new StringTokenizer(rs.getString(j+1)," ");
						result[i][j] = strtok.nextToken();
					}
					else if(j==9)
					{
						System.out.println(rs.getString(j+1));
						if(rs.getString(j+1).equals("1"))
							result[i][j] = "O";
						else
							result[i][j] = "X";
					}
					else
					{
						System.out.println(rs.getString(j+1));
						result[i][j] = rs.getString(j+1);
					}
				}
				i++;
			}
			rs.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		disconnect();
		
		return result;
	}
	
	//�Ź��ҷ���
	public static String[][] selectVehicles()
	{
		String[][] result = null;
		
		connect();
		
		try
		{
			String sql = "SELECT * FROM VEHICLE";
			ResultSet rs = stmt.executeQuery(sql);
			int i=0;
			ResultSetMetaData rsmd = rs.getMetaData();
			
			rs.last();
			
			result = new String[rs.getRow()][rsmd.getColumnCount()];
			rs.beforeFirst();
			while(rs.next())
			{
				for(int j=0;j<rsmd.getColumnCount();j++)
				{
					if(j==5)
					{
						System.out.println(rs.getString(j+1));
						StringTokenizer strtok = new StringTokenizer(rs.getString(j+1)," ");
						result[i][j] = strtok.nextToken();
						
						
					}
					else if(j==9)
					{
						System.out.println(rs.getString(j+1));
						if(rs.getString(j+1).equals("1"))
							result[i][j] = "O";
						else
							result[i][j] = "X";
					}
					else
					{
						System.out.println(rs.getString(j+1));
						result[i][j] = rs.getString(j+1);
					}
				}
				i++;
			}
			rs.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		disconnect();
		
		return result;
	}
	
	//���� ������ admin ���� ����
	public static int countAdminAccount()
	{
		int result=0;
		connect();
		//������ Ȯ��
		try
		{
			String sql = "SELECT COUNT(*) FROM ACCOUNT WHERE KIND = 'Admin'";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				result = rs.getInt(1);
			}
			rs.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		disconnect();
		
		return result;
	}
	
	//ȸ������ �ҷ�����
	public static String[] selectAccountInfo(String id)
	{
		String[] results = {"","","","","","","","","","","",""};
		int count=0;
		connect();
		//������ Ȯ��
		try
		{
			String sql = "SELECT * FROM ACCOUNT WHERE ID = '"+id+"'";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				// Fill out your code
				results[0] = rs.getString(1);//���̵�
				for(int i=0;i<results.length;i++)
				{	
					if(i!=6)//���ϸ� ����
					{
						results[i] = rs.getString(i+1);
						System.out.println(results[i]);
					}
				}
			}
			rs.close();
			
			sql = "SELECT TO_CHAR(BDATE,'YYYY-MM-DD') FROM ACCOUNT WHERE ID = '"+id+"'";
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				// Fill out your code
				results[6] = rs.getString(1);//����
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		disconnect();
		
		return results;
	}
	
	//ȸ��Ż��
	public static boolean deleteAccount(String id)
	{
		connect();
		
		boolean result = false;
		//������ Ȯ��
		try
		{
			
			String sql = "DELETE FROM ACCOUNT WHERE ID = '"+id+"'";
			int res = stmt.executeUpdate(sql);
			
			if(res > 0) 
			{
				result=true;
				System.out.println("Tuple was successfully inserted.");
			}
			else
			{
				result=false;
			}
			conn.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		disconnect();
		return result;
	}
	
	//������ �������� Ȯ��
	public static boolean isAdmin(String id)
	{
		boolean result=false;
		connect();
		//������ Ȯ��
		try
		{
			String sql = "SELECT KIND FROM ACCOUNT WHERE ID = '"+id+"'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				if(rs.getString(1).equals("Admin"))
					result = true;
				else
					result = false;
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		disconnect();
		return result;
	}
	
	public static boolean isMember(String id) //�ش� id �����ϴ��� Ȯ��
	{
		boolean result=false;
		connect();
		//������ Ȯ��
		try
		{
			String sql = "SELECT ID, PASSWORD FROM ACCOUNT WHERE ID = '"+id+"'";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("wow");
			if(!rs.next())//�ش� ȸ�� �������� ����
			{
				result = false;//������� (���԰���)
			}
			else {
				result = true;//������ (���ԺҰ���)
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		disconnect();
		return result;
	}
	
	//ȸ������ ����
	public static boolean updateAccount(String id, String pw, String fname, String lname, String phone, String kind,
				String bdate, String gender, String job, String country, String city, String detail_address)
	{
		connect();
			
		boolean result = false;
		//������ Ȯ��
		try
		{
			
			String sql = "UPDATE ACCOUNT SET PASSWORD = '"+pw+"', FNAME = '"+fname+"', LNAME = '"+lname+"', KIND = '"+kind+"', PHONENUMBER = '"+phone+"', "
					+"BDATE ="+bdate+", GENDER ="+gender+", JOB ="+job+", COUNTRY = "+country+", CITY = "+city+", DETAILADDRESS ="+detail_address+" "
					+ " WHERE ID = '"+id+"'";
			int res = stmt.executeUpdate(sql);
			
			if(res > 0) 
			{
				result=true;
				System.out.println("Tuple was successfully inserted.");
			}
			else
			{
				result=false;
			}
			conn.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		disconnect();
		return result;
			
	}
	
	//account ���̺� tuple �߰�(ȸ������)
	public static boolean signUpDB(String id, String pw, String fname, String lname, String phone, String kind,
			String bdate, String gender, String job, String country, String city, String detail_address)
	{
		connect();
		
		boolean result = false;
		//������ Ȯ��
		try
		{
			
			String sql = "INSERT INTO ACCOUNT VALUES('"+id+"', '"+kind+"', '"+fname+"', '"+lname+"', '"+pw+"', '"+phone+"',"
					+ " "+bdate+", "+gender+", "+job+", "+country+", "+city+", "+detail_address+")";
			int res = stmt.executeUpdate(sql);
			
			if(res > 0) 
			{
				result=true;
				System.out.println("Tuple was successfully inserted.");
			}
			else
			{
				result=false;
			}
			conn.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		disconnect();
		return result;
		
	}
	
	public static String[] signInDB(String id)//�ش� id ���� ���� �� ��й�ȣ ������
	{
		String[] results = {"",""};
	
		connect();
		//������ Ȯ��
		try
		{
			String sql = "SELECT ID, PASSWORD FROM ACCOUNT WHERE ID = '"+id+"'";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("wow");
			if(!rs.next())//�ش� ȸ�� �������� ����
			{
				System.out.println("dkdkdkdk");
				results[0] = null;
				results[1] = null;
			}
			else {
				// Fill out your code
				results[0] = rs.getString(1);//���̵�
				results[1] = rs.getString(2);//��й�ȣ
				System.out.println(results[0]+" "+results[1]);
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		disconnect();
		
		return results;
		
	}
	
	private static void connect()
	{
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
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static void disconnect()
	{
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
	}
	
}
