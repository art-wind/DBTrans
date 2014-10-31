package transmit;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;


public class DBFParser {
	public static String CREATE_SCHEMA="CREATE SCHEMA `DBTransfer` DEFAULT CHARACTER SET utf8 ;";
	public static String CREATE_TABLE_ROOM="CREATE TABLE `DBTransfer`.`room` ("
  +"`kdno` VARCHAR(45) NOT NULL,`kcno` INT NOT NULL,`ccno` INT NOT NULL,`kdname` VARCHAR(45) NULL,"
  +"`exptime` VARCHAR(45) NULL,`papername` VARCHAR(45) NULL);";
	public static String CREATE_TABLE_STUDENT="CREATE TABLE `DBTransfer`.`student` (`registno` VARCHAR(45) NOT NULL,`name` VARCHAR(45) NULL,"
			+ "`kdno` VARCHAR(45) NULL,`kcno` INT NULL,`ccno` INT NULL,`seat` INT NULL);";
	public static String INSERT_SQL_STUDENT = "insert into student(registno,name,kdno,kcno,ccno,seat) values(?,?,?,?,?,?)";
	public static String INSERT_SQL_ROOM = "insert into room(kdno,kcno,ccno,kdname,exptime,papername) values(?,?,?,?,?,?)";
	public static String DROP_SCHEMA="DROP DATABASE `DBTransfer`;";
	public static String DROP_STUDENT = "DROP TABLE `DBTransfer`.`student`;";
	public static String DROP_ROOM = "DROP TABLE `DBTransfer`.`room`";
	public static void main(String[]args) throws SQLException{
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/DBTransfer?characterEncoding=utf8";

		// MySQL配置时的用户名
		String user = "root"; 

		// MySQL配置时的密码
		String password = "xkwin1793";
		PreparedStatement ps = null;
		Connection conn = null;
		try { 
			// 加载驱动程序
			Class.forName(driver);

			// 连续数据库
			 conn = DriverManager.getConnection(url, user, password);

			if(!conn.isClosed()) 
				System.out.println("Succeeded connecting to the Database!");

			// statement用来执行SQL语句
			
			
		} catch(ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!"); 
			e.printStackTrace();
		}
		ps = conn.prepareStatement(DROP_STUDENT);
		ps.execute();
		ps = conn.prepareStatement(DROP_ROOM);
		ps.execute();
		//
		ps = conn.prepareStatement(CREATE_TABLE_ROOM);
		ps.execute();
		ps = conn.prepareStatement(CREATE_TABLE_STUDENT);
		ps.execute();
		
		
		
		ps = (PreparedStatement) conn.prepareStatement(INSERT_SQL_STUDENT);
		for(Student tmp:readStudent())
		{
			ps.setString(1, tmp.getRegistno());
			ps.setString(2, tmp.getName());
			ps.setString(3, tmp.getKdno());
			ps.setInt(4, tmp.getKcno());
			ps.setInt(5, tmp.getCcno());
			
			ps.setInt(6, tmp.getSeat());
			ps.addBatch();
		}
		ps.executeBatch();
		System.out.println("Student Table's insertion is complete");
		
		ps = (PreparedStatement) conn.prepareStatement(INSERT_SQL_ROOM);
		for(Room tmp:readRoom())
		{
			ps.setString(1, tmp.getKdno());
			ps.setInt(2, tmp.getKcno());
			ps.setInt(3, tmp.getCcno());
			ps.setString(4, tmp.getKdname());
			ps.setString(5, tmp.getExptime());
			ps.setString(6, tmp.getPaperName());
			ps.addBatch();
		}
		System.out.println("Room Table's insertion is complete");
		
		
		ps.executeBatch();
		ps.close();

	}
	public static List<Room> readRoom()
	{
		List<Room>retRoom = new ArrayList<Room>();

		InputStream fileInput = null;   
		String path="./data/Room_20040610.dbf";
		try 
		{      
			//File Stream for a file
			fileInput  = new FileInputStream(path);   

			//Initialize an instance of a DBFREADER
			DBFReader reader = new DBFReader(fileInput);    

//			//调用DBFReader对实例方法得到path文件中字段的个数   
//			int fieldsCount = reader.getFieldCount();   
//			//取出字段信息   
//			for( int i=0; i<fieldsCount; i++)    
//			{   
//				DBFField field = reader.getField(i);   			
//				System.out.println(field.getDataType());   
//			}  
			Object[] rowValues;   

			//Fetch the values in the rows   
			rowValues = reader.nextRecord();
			while((rowValues = reader.nextRecord()) != null) 
			{   

				Room room = new Room();
				
				//设置考点号
				room.setKdno( (String)(rowValues[0]) );
				//将Object对象数字化并设为
				Number kcno = (Number)(rowValues[1]);
				room.setKcno ( kcno.intValue());
				
				//以相同的方法处理测试号
				Number ccno = (Number)(rowValues[2]);
				room.setCcno( ccno.intValue() );
				//考点名、过期时间、卷面名
				room.setKdname((String)(rowValues[3]));
				room.setExptime((String)(rowValues[4]));
				room.setPaperName((String)(rowValues[5]));
				//将room添加到返回的List内
				retRoom.add(room);
			}
		}
		catch(Exception e) 
		{   

			e.printStackTrace();   

		}  
		finally
		{   

			try{   

				fileInput.close();   

			}catch(Exception e){}   

		} 
		return retRoom;
	}
	public static List<Student> readStudent()
	{
		List<Student>retStudent = new ArrayList<Student>();

		InputStream fileInput = null;   
		String path="./data/Student_20040610.dbf";
		try 
		{   
			//File Stream for a file
			fileInput  = new FileInputStream(path);   

			//Initialize an instance of a DBFREADER
			DBFReader reader = new DBFReader(fileInput);    
   
			Object[] rowValues;   
			while((rowValues = reader.nextRecord()) != null) 
			{   

				Student student = new Student();
				student.setRegistno( (String)(rowValues[0]) );
				student.setName((String)(rowValues[1]));
				//设置考点号
				student.setKdno((String)(rowValues[2]) );

				Number kcno = (Number)(rowValues[3]);
				student.setKcno ( (int)kcno.intValue());
				
				//Handle the ccno as the same
				Number ccno = (Number)(rowValues[4]);
				student.setCcno(  (int)ccno.intValue() );
				//考点名、过期时间、卷面名
				
				Number seat = (Number)(rowValues[5]);
				//To avoid Null Pointer
				if(seat==null)
				{
					student.setSeat(0);
				}
				else{
					student.setSeat( (int)seat.intValue());
				}
				//将room添加到返回的List内
				retStudent.add(student);
			}
		}
		catch(Exception e) 
		{   

			e.printStackTrace();   

		}  
		finally
		{   

			try{   

				fileInput.close();   

			}catch(Exception e){}   

		} 
		return retStudent;
	}

}
