
public class DBparams {
	
	String username;
	String password;
	String dbms; 
	String server; 
	int port;
    String database;
    String driver;
    
	public DBparams(String username, String password, String dbms,
			String server, int port, String database, String driver) {
		super();
		this.username = username;
		this.password = password;
		this.dbms = dbms;
		this.server = server;
		this.port = port;
		this.database = database;
		this.driver = driver;
	}

}
