import java.sql.Timestamp;


public class Room {
	private int kdno;
	private int kcno;
	private int ccno;
	private String kname;
	private String exptime;
	private String papername;
	public String getPaperName() {
		return papername;
	}
	public void setPaperName(String paperName) {
		this.papername = paperName;
	}
	Room(){
		
	}
	Room(int kdno,int kcno,int ccno,String kname,String exptime){
		this.kdno=kdno;
		this.kcno = kcno;
		this.ccno = ccno;
		this.kname = kname;
		this.exptime = exptime;
	}
	public int getKdno() {
		return kdno;
	}
	public void setKdno(int kdno) {
		this.kdno = kdno;
	}
	public int getKcno() {
		return kcno;
	}
	public void setKcno(int kcno) {
		this.kcno = kcno;
	}
	public int getCcno() {
		return ccno;
	}
	public void setCcno(int ccno) {
		this.ccno = ccno;
	}
	public String getKname() {
		return kname;
	}
	public void setKname(String kname) {
		this.kname = kname;
	}
	public String getExptime() {
		return exptime;
	}
	public void setExptime(String exptime) {
		this.exptime = exptime;
	}
	public String generateSQL(String tableName)
	{
		String sql = "insert into room(kdno,kcno,ccno,kname,exptie,papername) values(?,?,?,?,?,?)";
		//sql.
		return sql;
	}
	
	
}
