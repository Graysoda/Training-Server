//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package training.database;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import training.database.entity.*;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Lazy
@Configuration
@EnableTransactionManagement
@EntityScan(basePackageClasses ={
		ActorEntity.class,
		AddressEntity.class,
		CategoryEntity.class,
		CityEntity.class,
		CustomerEntity.class,
		FilmActorEntity.class,
		FilmCategoryEntity.class,
		FilmEntity.class,
		InventoryEntity.class,
		LanguageEntity.class,
		PaymentEntity.class,
		RentalEntity.class,
		StaffEntity.class,
		StoreEntity.class,
})
public class Database {

	@Lazy
	@Bean
	public DataSource dataSource() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://"+dbUri.getHost()+":"+dbUri.getPort()+dbUri.getPath()+"?ssl=true";

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
	}

	@Lazy
	@Bean
	public Connection getConnection() throws SQLException {
		String dbUrl = System.getenv("JDBC_DATABASE_URL");
		return DriverManager.getConnection(dbUrl);
	}
}
