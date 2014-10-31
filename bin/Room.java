public class Room {
	private String kdno;
	private int kcno;
	private int ccno;
	private String kdname;
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
	Room(String kdno,int kcno,int ccno,String kname,String exptime){
		this.kdno=kdno;
		this.kcno = kcno;
		this.ccno = ccno;
		this.kdname = kname;
		this.exptime = exptime;
	}
	public String getKdno() {
		return kdno;
	}
	public void setKdno(String kdno) {
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
	public String getKdname() {
		return kdname;
	}
	public void setKdname(String kname) {
		this.kdname = kname;
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
