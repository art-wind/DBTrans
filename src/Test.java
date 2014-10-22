

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

	public static void main(String[] args) throws IOException, SQLException{

		//Deal with xls Files

		//..
		FileInputStream file = new FileInputStream(new File("./data/room.xls"));

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
				for (int r = 0; r < rows; r++) { // 行循环

					System.out.println();
					Row row = sheet.getRow(r);
					if (row != null) {

						int cells = row.getLastCellNum();// 获得列数
						for (short c = 0; c < cells; c++) { // 列循环
							Cell cell = row.getCell(c);

							if (cell != null) {
								String value = getValue(cell);
								System.out.print("第" + r + "行 " + "第" + c
										+ "列：" + value+"\t");
							}
						}
					}
				}
			}
		}
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
			String url = "jdbc:mysql://127.0.0.1:3306/test";

			// MySQL配置时的用户名
			String user = "root"; 

			// MySQL配置时的密码
			String password = "xkwin1793";

			try { 
				// 加载驱动程序
				Class.forName(driver);

				// 连续数据库
				Connection conn = DriverManager.getConnection(url, user, password);

				if(!conn.isClosed()) 
					System.out.println("Succeeded connecting to the Database!");

				// statement用来执行SQL语句
				Statement statement = conn.createStatement();

				// 要执行的SQL语句
				String sql = "select * from account";

				// 结果集
				ResultSet rs = statement.executeQuery(sql);

				System.out.println("-----------------");
				System.out.println("执行结果如下所示:");
				System.out.println("-----------------");
				System.out.println(" 学号" + "\t" + " 姓名");
				System.out.println("-----------------");

				String name = null;

				while(rs.next()) {

					// 选择sname这列数据
					name = rs.getString("name");

					// 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
					// 然后使用GB2312字符集解码指定的字节数组
					name = new String(name.getBytes("ISO-8859-1"),"GB2312");

					// 输出结果
					System.out.println(rs.getString("address") + "\t" + name);
				}

				rs.close();
				conn.close();

			} catch(ClassNotFoundException e) {

				System.out.println("Sorry,can`t find the Driver!"); 
				e.printStackTrace();
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