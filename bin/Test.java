

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

/*
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;*/
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


//import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;
public class Test {
	public static String insert_sql_student = "insert into student(registno,name,kdno,kcno,ccno,seat) values(?,?,?,?,?,?)";
	public static String insert_sql_room = "insert into room(kdno,kcno,ccno,kdname,exptime,papername) values(?,?,?,?,?,?)";
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException{

		//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver") ;
		//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

		//		Connection connDbf = null;    
		//		PreparedStatement psDbf = null;    
		//		ResultSet rsDbf = null;    
		//		//一个目录名称，下面存放ＤＢＦ文件    
		//		String filePath = "./data";    
		//		//一个ＤＢＦ文件夹，实际文件名称为tbUser.dbf，这里做为表名不用扩展名就可以    
		//		String fileName = "Room_20040610"; 
		//
		//		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		//		//connDbf = DriverManager.getConnection("jdbc:odbc:DRIVER={Microsoft dBase Driver (*.dbf)};DBQ=" + filePath + ";","","");    
		//		connDbf=DriverManager.getConnection("jdbc:ucanaccess://./data//Student_20040610.dbf");
		//
		//		String sql = "select * from " + fileName;    
		//		psDbf = connDbf.prepareStatement(sql);    
		//		rsDbf = psDbf.executeQuery(); 

		//..
		/*
		//Dealing With the xlsx Files
		FileInputStream file = new FileInputStream(new File("C:\\test.xlsx"));

		//Get the workbook instance for XLS file 
		XSSFWorkbook workbook = new XSSFWorkbook (file);

		//Get first sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);*/

		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/DBTransfer?characterEncoding=utf8";

		// MySQL配置时的用户名
		String user = "root"; 

		// MySQL配置时的密码
		String password = "xkwin1793";
		PreparedStatement ps=null;
		try { 
			// 加载驱动程序
			Class.forName(driver);

			// 连续数据库
			Connection conn = DriverManager.getConnection(url, user, password);

			if(!conn.isClosed()) 
				System.out.println("Succeeded connecting to the Database!");

			// statement用来执行SQL语句
			ps = conn.prepareStatement(insert_sql_student);
			for(Student tmp:readStudent())
			{
				ps.setString(1, tmp.getRegistno());
				ps.setString(2, tmp.getName());
				ps.setString(3, tmp.getKdno());
				ps.setInt(4, tmp.getKcno());
				ps.setInt(5, tmp.getCcno());
				ps.setInt(6, tmp.getSeat());
				ps.execute();
			}
			ps = conn.prepareStatement(insert_sql_room);
			for(Room tmp:readRoom())
			{
				//System.out.println(tmp.getExptime());
				ps.setString(1, tmp.getKdno());
				ps.setInt(2, tmp.getKcno());
				ps.setInt(3, tmp.getCcno());
				ps.setString(4, tmp.getKdname());
				ps.setString(5, tmp.getExptime());
				ps.setString(6, tmp.getPaperName());
				ps.execute();
			}
			//			ps.setInt(1, tmp.getRegistno());
			//			ps.setString(2, tmp.getName());
			//			ps.setInt(3, tmp.getKdno());
			//			ps.setInt(4, tmp.getKcno());
			//			ps.setInt(5, tmp.getCcno());
			//			ps.setInt(6, tmp.getSeat());
			//			ps.execute();
		} catch(ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!"); 
			e.printStackTrace();
		}


	}
	public static List<Student> readStudent() throws IOException{
		List<Student>retStudent = new ArrayList<Student>();
		FileInputStream file = new FileInputStream(new File("./data/student.xls"));

		Workbook workbook = null;

		try{
			//Get the workbook instance for XLS file 
			//HSSFWorkbook
			workbook = new HSSFWorkbook(file);
		}
		catch(Exception e)
		{
			workbook = new XSSFWorkbook (file);
		}
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			// 创建工作表
			Sheet sheet = workbook.getSheetAt(i);

			int rows = sheet.getPhysicalNumberOfRows(); // 获得行数
			if (rows > 0) {
				sheet.getMargin(Sheet.TopMargin);
				for (int r = 1; r < rows; r++) { // 行循环
					Student tmp=new Student();
					Row row = sheet.getRow(r);
					if (row != null) {
						int cells = row.getLastCellNum();// 获得列数
						for (short c = 0; c < cells; c++) { // 列循环
							switch(c){
							case 0:
								tmp.setRegistno(row.getCell(c).toString());
								break;
							case 1:
								tmp.setName(row.getCell(c).toString());
								break;
							case 2:
								tmp.setKdno(row.getCell(c).toString());
								break;
							case 3:
								tmp.setKcno(Integer.parseInt(getValue(row.getCell(c))));
								break;
							case 4:
								tmp.setCcno(Integer.parseInt(getValue(row.getCell(c))));
								break;
							case 5:
								tmp.setSeat(Integer.parseInt(getValue(row.getCell(c))));
								break;
							}

						}
					}
					retStudent.add(tmp);
				}

			}

		}
		return retStudent;
	}
	public static List<Room> readRoom() throws IOException{
		List<Room>retRoom = new ArrayList<Room>();
		FileInputStream file = new FileInputStream(new File("./data/room.xls"));

		Workbook workbook = null;

		try{
			//Get the workbook instance for XLS file 
			//HSSFWorkbook
			workbook = new HSSFWorkbook(file);
		}
		catch(Exception e)
		{
			workbook = new XSSFWorkbook (file);
		}
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			// 创建工作表
			Sheet sheet = workbook.getSheetAt(i);

			int rows = sheet.getPhysicalNumberOfRows(); // 获得行数
			if (rows > 0) {
				sheet.getMargin(Sheet.TopMargin);
				for (int r = 1; r < rows; r++) { // 行循环
					Room tmp=new Room();
					Row row = sheet.getRow(r);
					if (row != null) {
						int cells = row.getLastCellNum();// 获得列数
						for (short c = 0; c < cells; c++) { // 列循环
							switch(c){
							case 0:
								tmp.setKdno(row.getCell(c).toString());
								break;
							case 1:
								tmp.setKcno(Integer.parseInt(getValue(row.getCell(c))));
								
								break;
							case 2:
								tmp.setCcno(Integer.parseInt(getValue(row.getCell(c))));
								break;
							case 3:
								tmp.setKdname(row.getCell(c).toString());
								break;
							case 4:
								tmp.setExptime(row.getCell(c).toString());
								break;
							case 5:
								tmp.setPaperName(row.getCell(c).toString());
								break;
							}

						}
					}
					retRoom.add(tmp);

				}

			}

		}
		return retRoom;
	}
	public static String getValue(Cell cell) {

		String value = "";
		switch (cell.getCellType()) {

		case Cell.CELL_TYPE_NUMERIC: // 数值型
			if (DateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				value = DateUtil.getJavaDate(cell.getNumericCellValue()).toString();
			} else {// 纯数字
				double content = cell.getNumericCellValue();
				if(content-(int)content==0)
				{
					value = String.valueOf((int)content);
				}
				else
				{
					value = String.valueOf(content);
				}
				//System.out.print("Numeric");
			}
			break;
		default:value = cell.toString();
		}
		return value;
	}
}