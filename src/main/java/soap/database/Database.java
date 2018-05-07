//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package soap.database;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "soap.database.entity")
public class Database {
	@Autowired
	protected Connection connection;

	@Bean
	public BasicDataSource dataSource() throws URISyntaxException, SQLException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://"+dbUri.getHost()+":"+dbUri.getPort()+dbUri.getPath()+"?ssl=true";

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);

		return basicDataSource;
	}

	@Bean
	public Connection getConnection() throws SQLException {
		String dbUrl = System.getenv("JDBC_DATABASE_URL");
		return DriverManager.getConnection(dbUrl);
	}
}
