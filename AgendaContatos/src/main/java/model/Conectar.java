package model;

import java.sql.Connection;
import java.sql.DriverManager;

// TODO: Auto-generated Javadoc
/**
 * The Class Conectar.
 */
public class Conectar {

	/** The Constant URL. */
	private static final String URL = "jdbc:mysql://localhost:3306/dbagenda";

	/** The Constant USER. */
	private static final String USER = "root";

	/** The Constant PASSWORD. */
	private static final String PASSWORD = "12345";

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public static Connection getConnection() {
		try {
			// forço conexao e carregamento de drivers SQL
			Class.forName("com.mysql.cj.jdbc.Driver");

			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
