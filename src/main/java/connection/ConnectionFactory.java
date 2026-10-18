package connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
		
		private static HikariDataSource dataSource;
		
		static {
			try (InputStream input = ConnectionFactory.class
					.getClassLoader()
					.getResourceAsStream("database.properties")) {
				
				if (input == null) {
					throw new RuntimeException("Arquivo database.properties não encontrado!");
				}
				
				Properties properties = new Properties();
				properties.load(input);
				
				HikariConfig config = new HikariConfig();
				config.setJdbcUrl(properties.getProperty("db.url"));
				config.setUsername(properties.getProperty("db.user"));
				config.setPassword(properties.getProperty("db.password"));
				
				config.setMaximumPoolSize(10);
				config.setMinimumIdle(2);
				config.setConnectionTimeout(30000);
				config.setIdleTimeout(600000);
				
				dataSource = new HikariDataSource(config);
				
			}catch (IOException e) {
				throw new RuntimeException("Erro ao carregar configurações do banco!", e);
			}
		}
		
		public static Connection getConnection() {
			try {
				return dataSource.getConnection();
			} catch (SQLException e) {
				throw new RuntimeException("Erro ao conectar com o banco de dados!", e);
			}
		}
		
		public static void closeConnection(Connection connection) {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException("Erro ao devolver conexão ao pool!", e);
				}
			}
		}
		
		public static void closePool() {
			if (dataSource != null && !dataSource.isClosed()) {
				dataSource.close();
			}
		}
		
	}

