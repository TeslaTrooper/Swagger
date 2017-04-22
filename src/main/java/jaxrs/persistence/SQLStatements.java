package jaxrs.persistence;

public class SQLStatements {

	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS contacts "
			+ "(uuid VARCHAR(256) PRIMARY KEY, name VARCHAR(32), lastName VARCHAR(32), "
			+ "phoneNumber VARCHAR(32), email VARCHAR(32))";

	public static final String INSERT_CONTACT = "INSERT INTO contacts VALUES (?, ?, ?, ?, ?)";

	public static final String SELECT_CONTACT = "SELECT * FROM contacts WHERE ? = uuid";

	public static final String DELETE_CONTACT = "DELETE FROM contacts WHERE uuid = ?";

	public static final String UPDATE_CONTACT = "UPDATE contacts SET (name = ?, lastName = ?, phoneNumber = ?, email = ?";

}
