import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.mysql.jdbc.PreparedStatement;


public class DBFParser {
	public static String insert_sql = "insert into room(kdno,kcno,ccno,kdname,exptime,papername) values(?,?,?,?,?,?)";
	public static void main(String[]args) throws SQLException{
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/DBTransfer?characterEncoding=utf8";

		// MySQL配置时的用户名
		String user = "root"; 

		// MySQL配置时的密码
		String password = "xkwin1793";
		PreparedStatement ps = null;
		try { 
			// 加载驱动程序
			Class.forName(driver);

			// 连续数据库
			Connection conn = DriverManager.getConnection(url, user, password);

			if(!conn.isClosed()) 
				System.out.println("Succeeded connecting to the Database!");

			// statement用来执行SQL语句
			ps = (PreparedStatement) conn.prepareStatement(insert_sql);
			System.out.println(ps.getBytesRepresentation(0));
			
		} catch(ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!"); 
			e.printStackTrace();
		}

		
		
		
		
		InputStream fis = null;   
		String path="./data/Room_20040610.dbf";
		try    

		{   

			//读取文件的输入流   

			fis  = new FileInputStream(path);   

			//根据输入流初始化一个DBFReader实例，用来读取DBF文件信息   

			DBFReader reader = new DBFReader(fis);    

			//调用DBFReader对实例方法得到path文件中字段的个数   

			int fieldsCount = reader.getFieldCount();   

			//取出字段信息   

			for( int i=0; i<fieldsCount; i++)    

			{   

				DBFField field = reader.getField(i);   

				System.out.println(field.getName());   

			}   

			Object[] rowValues;   

			//一条条取出path文件中记录   
			String sql="insert into room values (";
			rowValues = reader.nextRecord();
			while((rowValues = reader.nextRecord()) != null) 
			{   
				
				Room tmp = new Room();
				//System.out.print(Integer.parseInt((String)(rowValues[0])));
				//Number kdno = (Number) (rowValues[0]);
				tmp.setKdno( Integer.parseInt((String)(rowValues[0])) );
				
				
				Number kcno = (Number)(rowValues[1]);
				tmp.setKcno ( kcno.intValue());
				Number ccno = (Number)(rowValues[2]);
				
				tmp.setCcno( ccno.intValue() );
				tmp.setKdname((String)(rowValues[3]));
				System.out.print((String)(rowValues[3]));
				tmp.setExptime((String)(rowValues[4]));
				tmp.setPaperName((String)(rowValues[5]));
				
				ps.setInt(1,tmp.getKdno());
				ps.setInt(2,tmp.getKcno());
				ps.setInt(3,tmp.getCcno());
				ps.setString(4,tmp.getKdname());
				ps.setString(5, tmp.getExptime());
				ps.setString(6, tmp.getPaperName());
				ps.execute();
			}   

		}   

		catch(Exception e)    

		{   

			e.printStackTrace();   

		}   

		finally  

		{   

			try{   

				fis.close();   

			}catch(Exception e){}   

		}   
	}


}
