

import java.io.File;
import java.io.FileInputStream;
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
public class Test {
	public static String insert_sql = "insert into student(registno,name,kdno,kcno,ccno,seat) values(?,?,?,?,?,?)";
	public static void main(String[] args) throws IOException, SQLException{


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
			ps = conn.prepareStatement(insert_sql);

			// 要执行的SQL语句
			//			String sql = "select * from account";
			//
			//			// 结果集
			//			ResultSet rs = ps.executeQuery(sql);
			//
			//			System.out.println("-----------------");
			//			System.out.println("执行结果如下所示:");
			//			System.out.println("-----------------");
			//			System.out.println(" 学号" + "\t" + " 姓名");
			//			System.out.println("-----------------");
			//
			//			String name = null;

			//			while(rs.next()) {
			//
			//
			//			}
			//
			//			rs.close();
			//			conn.close();

		} catch(ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!"); 
			e.printStackTrace();
		}
		//Deal with xls Files

		//..
		FileInputStream file = new FileInputStream(new File("./data/student.xls"));

		Workbook workbook = null;
		//XSSFWorkbook workbook = new XSSFWorkbook (file);


		try{
			//Get the workbook instance for XLS file 
			//HSSFWorkbook
			workbook = new HSSFWorkbook(file);
		}
		catch(Exception e)
		{
			workbook = new XSSFWorkbook (file);
		}


		//HSSFSheet sheet = workbook.getSheetAt(0);

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			// 创建工作表
			Sheet sheet = workbook.getSheetAt(i);
			
			int rows = sheet.getPhysicalNumberOfRows(); // 获得行数
			if (rows > 0) {
				sheet.getMargin(Sheet.TopMargin);
				for (int r = 1; r < rows; r++) { // 行循环
					Student tmp=new Student();
					System.out.println();
					Row row = sheet.getRow(r);
					if (row != null) {
						int cells = row.getLastCellNum();// 获得列数
						for (short c = 0; c < cells; c++) { // 列循环
							switch(c){
							case 0:
								tmp.setRegistno(Integer.parseInt(getValue(row.getCell(c))));
								break;
							case 1:
								tmp.setName(row.getCell(c).toString());
								break;
							case 2:
								tmp.setKdno(Integer.parseInt(getValue(row.getCell(c))));
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
					
					ps.setInt(1, tmp.getRegistno());
					ps.setString(2, tmp.getName());
					ps.setInt(3, tmp.getKdno());
					ps.setInt(4, tmp.getKcno());
					ps.setInt(5, tmp.getCcno());
					ps.setInt(6, tmp.getSeat());
					ps.execute();
				}
				
			}
		}
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