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
	
	public static String[] selectMakes()
	{
		String[] result=null;
		
		connect();
		
		try
		{
			String sql;
			
			sql = "SELECT MAKENAME FROM MAKE";
			
			
			ResultSet rs = stmt.executeQuery(sql);
			int i=0;
			ResultSetMetaData rsmd = rs.getMetaData();
			
			rs.last();
			
			result = new String[rs.getRow()+1];
			result[i++] = "전체";
			rs.beforeFirst();
			while(rs.next())
			{
				result[i] = rs.getString(1);
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
	
	//차량 정보수정
	public static boolean updateVehicles(String serialnumber, String mileage, String model, String detailed_model, String price, String model_year, String fuel, String color, String capacity
			,String ishybrid, String isopen)
	{
		connect();
		
		boolean result = false;
		//서버에 확인
		try
		{
			
			String sql = "UPDATE VEHICLE SET MILEAGE = "+mileage+", MODELNAME='"+model+"', DETAILEDMODELNAME='"+detailed_model+"', PRICE="+price+", MODEL_YEAR="+model_year+", FUELNAME='"+fuel+"', COLORNAME='"+color+"', CAPACITY="+capacity+", ISHYBRID='"+ishybrid+"', ISOPEN='"+isopen+"'"
					+ " WHERE SERIALNUMBER = '"+serialnumber+"'";
			int res = stmt.executeUpdate(sql);
			
			if(res > 0) 
			{
				result=true;
				System.out.println("Tuple was successfully updated.");
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
	
	//차량 매물등록
	public static boolean insertVehicles(String serialnumber, String mileage, String model, String detailed_model, String price, String model_year, String fuel, String color, String capacity
			,String ishybrid, String isopen)
	{
		connect();
		
		boolean result = false;
		//서버에 확인
		try
		{
			
			String sql = "INSERT INTO VEHICLE VALUES('"+serialnumber+"', "+mileage+", '"+model+"', '"+detailed_model+"', "+price+", "+model_year+", '"+fuel+"', '"+color+"', "+capacity+", '"+ishybrid+"', '"+isopen+"')";
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
	
	//배기량 전체리스트불러오기
	public static String[] selectCapacity()
	{
		String[] result=null;
		connect();
		
		try
		{
			String sql;
			
			sql = "SELECT CAPACITY FROM ENGINE_DISPLACEMENT";
			
			
			ResultSet rs = stmt.executeQuery(sql);
			int i=0;
			ResultSetMetaData rsmd = rs.getMetaData();
			
			rs.last();
			
			result = new String[rs.getRow()];
			rs.beforeFirst();
			while(rs.next())
			{
				result[i] = rs.getString(1);
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
	//색상 전체리스트불러오기
	public static String[] selectColor()
	{
		String[] result=null;
		connect();
		
		try
		{
			String sql;
			
			sql = "SELECT COLORNAME FROM COLOR";
			
			
			ResultSet rs = stmt.executeQuery(sql);
			int i=0;
			ResultSetMetaData rsmd = rs.getMetaData();
			
			rs.last();
			
			result = new String[rs.getRow()];
			rs.beforeFirst();
			while(rs.next())
			{
				result[i] = rs.getString(1);
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
	//연료 전체리스트불러오기
	public static String[] selectFuel()
	{
		String[] result=null;
		connect();
		
		try
		{
			String sql;
			
			sql = "SELECT FUELNAME FROM FUEL";
			
			
			ResultSet rs = stmt.executeQuery(sql);
			int i=0;
			ResultSetMetaData rsmd = rs.getMetaData();
			
			rs.last();
			
			result = new String[rs.getRow()];
			rs.beforeFirst();
			while(rs.next())
			{
				result[i] = rs.getString(1);
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
	
	//세부모델 전체리스트불러오기
	public static String[] selectDetailedModel(String modelname)
	{
		String[] result=null;
		connect();
		
		try
		{
			String sql;
			
			sql = "SELECT DETAILEDMODELNAME FROM DETAILED_MODEL WHERE MODELNAME = '"+modelname+"'";
			
			
			ResultSet rs = stmt.executeQuery(sql);
			int i=0;
			ResultSetMetaData rsmd = rs.getMetaData();
			
			rs.last();
			
			result = new String[rs.getRow()];
			rs.beforeFirst();
			while(rs.next())
			{
				result[i] = rs.getString(1);
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
	
	//모델 전체리스트불러오기
	public static String[] selectModel()
	{
		String[] result=null;
		connect();
		
		try
		{
			String sql;
			
			sql = "SELECT MODELNAME FROM MODEL";
			
			
			ResultSet rs = stmt.executeQuery(sql);
			int i=0;
			ResultSetMetaData rsmd = rs.getMetaData();
			
			rs.last();
			
			result = new String[rs.getRow()];
			rs.beforeFirst();
			while(rs.next())
			{
				result[i] = rs.getString(1);
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
	
	//거래내역불러오기
	public static String[][] selectOrderlist(String id, boolean isAdmin)
	{
		String[][] result = null;
		
		connect();
		
		try
		{
			String sql;
			if(isAdmin)//관리자인 경우
				sql = "SELECT * FROM ORDER_LIST";
			else//고객인 경우
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
						//System.out.println(rs.getString(j+1));
						StringTokenizer strtok = new StringTokenizer(rs.getString(j+1)," ");
						result[i][j] = strtok.nextToken();
					}
					else if(j==9)
					{
						//System.out.println(rs.getString(j+1));
						if(rs.getString(j+1).equals("1"))
							result[i][j] = "O";
						else
							result[i][j] = "X";
					}
					else
					{
						//System.out.println(rs.getString(j+1));
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
	
	//매물불러옴
	public static String[][] selectVehicles(boolean isAdmin)
	{
		String[][] result = null;
		
		connect();
		
		try
		{
			String sql;
			if(!isAdmin)//고객일때
				sql = "SELECT v.serialnumber, v.mileage, v.modelname, v.detailedmodelname, v.price, v.model_year, v.fuelname, v.colorname, v.capacity, v.ishybrid, v.isopen, m.makename, dm.categoryname, dm.fuelefficiency, dm.transmissionname FROM VEHICLE V, DETAILED_MODEL DM, MODEL M WHERE V.MODELNAME = M.MODELNAME AND V.DETAILEDMODELNAME = DM.DETAILEDMODELNAME AND V.MODELNAME = DM.MODELNAME AND V.ISOPEN ='1' ORDER BY v.serialnumber ASC";//공개처리된애들만 불러옴
			else//관리자일땐 전체 다불러옴
				sql = "SELECT v.serialnumber, v.mileage, v.modelname, v.detailedmodelname, v.price, v.model_year, v.fuelname, v.colorname, v.capacity, v.ishybrid, v.isopen, m.makename, dm.categoryname, dm.fuelefficiency, dm.transmissionname FROM VEHICLE V, DETAILED_MODEL DM, MODEL M WHERE V.MODELNAME = M.MODELNAME AND V.DETAILEDMODELNAME = DM.DETAILEDMODELNAME AND V.MODELNAME = DM.MODELNAME ORDER BY v.serialnumber ASC";
			ResultSet rs = stmt.executeQuery(sql);
			int i=0;
			ResultSetMetaData rsmd = rs.getMetaData();
			
			rs.last();
			int column = rsmd.getColumnCount();
			
			if(!isAdmin)//고객모드때
				column-=1;
			
			result = new String[rs.getRow()][column];
			rs.beforeFirst();
			while(rs.next())
			{
			
				for(int j=0;j<column;j++)
				{
					System.out.println(rs.getString(j+1));
					if(j==5)
					{
						StringTokenizer strtok = new StringTokenizer(rs.getString(j+1)," ");
						result[i][j] = strtok.nextToken();
					}
					else if(j==9)
					{
						if(rs.getString(j+1).equals("1"))
							result[i][j] = "O";
						else
							result[i][j] = "X";
					}	
					else
					{
						result[i][j] = rs.getString(j+1);
					}
					
					if(j==10 && isAdmin==true)//공개여부는 관리자일때만
					{
						if(rs.getString(j+1).equals("1"))
							result[i][j] = "O";
						else
							result[i][j] = "X";
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
	
	//특정 차량 정보만 불러옴
	public static String[] selectVehicles(boolean isAdmin, String serialnumber)
	{
		String[] result = null;
		
		connect();
		
		try
		{
			String sql = "SELECT * FROM VEHICLE WHERE SERIALNUMBER = '"+serialnumber+"'";
			ResultSet rs = stmt.executeQuery(sql);
		
			ResultSetMetaData rsmd = rs.getMetaData();
			
			rs.last();
			int column = rsmd.getColumnCount();
			
			if(!isAdmin)//고객모드때
				column-=1;
				
			result = new String[column];
			rs.beforeFirst();
			while(rs.next())
			{
					
				for(int j=0;j<column;j++)
				{
					//System.out.println(rs.getString(j+1));
					
					if(j==5)
					{
						StringTokenizer strtok = new StringTokenizer(rs.getString(j+1)," ");
						result[j] = strtok.nextToken();
					}
					else if(j==9)
					{
						if(rs.getString(j+1).equals("1"))
							result[j] = "O";
						else
							result[j] = "X";
					}
					else
					{
						result[j] = rs.getString(j+1);
					}
					
					if(j==10 && isAdmin==true)//공개여부는 관리자일때만
					{
						if(rs.getString(j+1).equals("1"))
							result[j] = "O";
						else
							result[j] = "X";
					}
					
					
					
				}
				
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
	
	//현재 서버의 admin 계정 개수
	public static int countAdminAccount()
	{
		int result=0;
		connect();
		//서버에 확인
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
	
	//회원정보 불러오기
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
					if(i!=6)//생일만 제외
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
				results[6] = rs.getString(1);//생일
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
	
	//회원탈퇴
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
	
	//회원정보 수정
	public static boolean updateAccount(String id, String pw, String fname, String lname, String phone, String kind,
				String bdate, String gender, String job, String country, String city, String detail_address)
	{
		connect();
			
		boolean result = false;
		//서버에 확인
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
