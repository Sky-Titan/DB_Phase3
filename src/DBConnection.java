import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	public static final String URL = "jdbc:oracle:thin:@155.230.36.61:1521:orcl";
	public static final String USER_KNU ="s2014105038";
	public static final String USER_PASSWD ="qkrwns2081";
	
	private static Connection conn = null; // Connection object
	private static Statement stmt = null;	// Statement object
	
	public DBConnection()
	{
	
	}
	
	public static String[] selectAccountInfo(String id)
	{
		String[] results = {"","","","","","","","","","","",""};
		int count=0;
		connect();
		//서버에 확인
		try
		{
			String sql = "SELECT * FROM ACCOUNT WHERE ID = '"+id+"'";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				// Fill out your code
				results[0] = rs.getString(1);//아이디
				for(int i=0;i<results.length;i++)
				{	
					results[i] = rs.getString(i+1);
					System.out.println(results[i]);
				}
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
	
	public static boolean deleteAccount(String id)
	{
		connect();
		
		boolean result = false;
		//서버에 확인
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
	
	//관리자 계정인지 확인
	public static boolean isAdmin(String id)
	{
		boolean result=false;
		connect();
		//서버에 확인
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
	public static boolean isMember(String id) //해당 id 존재하는지 확인
	{
		boolean result=false;
		connect();
		//서버에 확인
		try
		{
			String sql = "SELECT ID, PASSWORD FROM ACCOUNT WHERE ID = '"+id+"'";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("wow");
			if(!rs.next())//해당 회원 존재하지 않음
			{
				result = false;//존재안함 (가입가능)
			}
			else {
				result = true;//존재함 (가입불가능)
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
	
	//account 테이블에 tuple 추가(회원가입)
	public static boolean signUpDB(String id, String pw, String fname, String lname, String phone, String kind,
			String bdate, String gender, String job, String country, String city, String detail_address)
	{
		connect();
		
		boolean result = false;
		//서버에 확인
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
	
	public static String[] signInDB(String id)//해당 id 존재 여부 및 비밀번호 가져옴
	{
		String[] results = {"",""};
	
		connect();
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
			stmt = conn.createStatement();
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
