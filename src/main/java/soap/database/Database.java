//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package soap.database;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import soap.database.entity.ActorEntity;
import soap.database.entity.FilmEntity;
import soap.database.entity.LanguageEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackageClasses = {ActorEntity.class,FilmEntity.class,LanguageEntity.class})
public class Database {
	private static final String dbUrl = "postgres://mnjtlhsosvnbhb:4d6195c6d64ca5e53ad6844e113aef816dc8b8f39125ae02f3e67d178b1545d3@ec2-54-243-137-182.compute-1.amazonaws.com:5432/d34f317612ph8k";
	public Database() {}

	@Bean
	public Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.ProgressDialect");
		return properties;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(dbUrl);
		dataSource.setUsername("sakila");
		dataSource.setPassword("sakila");
		return dataSource;
	}

	@Bean
	public Connection getConnection(){
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			java.sql.Connection connection = DriverManager.getConnection(dbUrl,"sakila","sakila");
			return (Connection) connection;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
