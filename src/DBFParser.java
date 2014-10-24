import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;


public class DBFParser {
	public static void main(String[]args){
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
				tmp.setKname((String)(rowValues[3]));
				
				String expTime =(String)(rowValues[4]);
				System.out.println(expTime.trim()+":00");
				Timestamp ts=null;
		        try {  
		            ts = Timestamp.valueOf(expTime.trim()+":00");
		              
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        }
				
				
				tmp.setExptime(ts);
				for( int i=0; i<rowValues.length; i++)    

				{   

					//System.out.println(rowValues[i]);   

				}   

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
