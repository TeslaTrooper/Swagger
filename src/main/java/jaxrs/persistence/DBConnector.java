package jaxrs.persistence;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jaxrs.model.beans.Contact;
import jaxrs.model.beans.ContactBean;

import com.google.common.base.Throwables;

public class DBConnector {

	private final Connection connection;

	public DBConnector() {
		try {
			Class.forName("org.h2.Driver");

			connection = DriverManager.getConnection("jdbc:h2:~/test", "jaxrs", "swagger");
		} catch (final Exception e) {
			throw Throwables.propagate(e);
		}
	}

	public void createTable() {
		try (final Statement stmt = connection.createStatement()) {
			stmt.executeUpdate(SQLStatements.CREATE_TABLE);
		} catch (final SQLException e) {
			throw Throwables.propagate(e);
		}
	}

	public void insert(final Contact contact) {
		try (final PreparedStatement stmt = connection
				.prepareStatement(SQLStatements.INSERT_CONTACT)) {
			stmt.setString(1, contact.getId());
			stmt.setString(2, contact.getName());
			stmt.setString(3, contact.getLastName());
			stmt.setString(4, contact.getPhoneNumber());
			stmt.setString(5, contact.getEmail());

			stmt.executeUpdate();
		} catch (final SQLException e) {
			throw Throwables.propagate(e);
		}
	}

	public Contact selectContact(final String uuid) {
		try (final PreparedStatement stmt = connection
				.prepareStatement(SQLStatements.SELECT_CONTACT)) {
			stmt.setString(1, uuid);

			final ResultSet result = stmt.executeQuery();

			if (!result.first()) {
				return null;
			}

			final Contact c = new Contact();
			c.setId(result.getString(1));
			c.setName(result.getString(2));
			c.setLastName(result.getString(3));
			c.setPhoneNumber(result.getString(4));
			c.setEmail(result.getString(5));

			return c;
		} catch (final Exception e) {
			throw Throwables.propagate(e);
		}
	}

	public int deleteContact(final String uuid) {
		try (PreparedStatement stmt = connection.prepareStatement(SQLStatements.DELETE_CONTACT)) {
			stmt.setString(1, uuid);

			return stmt.executeUpdate();
		} catch (final SQLException e) {
			throw Throwables.propagate(e);
		}
	}

	public int alterContact(final String contactId, final Field attrib, final String value) {
		final Contact c = selectContact(contactId);

		try (final PreparedStatement stmt = connection
				.prepareStatement(SQLStatements.UPDATE_CONTACT)) {
			final char a[] = attrib.getName().toCharArray();
			a[0] = Character.toUpperCase(a[0]);
			final String attribTmp = new String(a);

			c.getClass().getMethod("set" + attribTmp, String.class).invoke(c, value);

			stmt.setString(1, contactId);
			stmt.setString(2, c.getName());
			stmt.setString(3, c.getLastName());
			stmt.setString(4, c.getPhoneNumber());
			stmt.setString(5, c.getEmail());
			stmt.setString(6, contactId);

			return stmt.executeUpdate();
		} catch (final Exception e) {
			throw Throwables.propagate(e);
		}
	}

	public void close() {
		try {
			connection.close();
		} catch (final SQLException e) {
			throw Throwables.propagate(e);
		}
	}

	public static void main(final String[] args) throws NoSuchFieldException, SecurityException {
		final DBConnector c = new DBConnector();

		final Field fname = ContactBean.class.getDeclaredField("name");
		final Field flastName = ContactBean.class.getDeclaredField("lastName");
		final Field fnumber = ContactBean.class.getDeclaredField("phoneNumber");
		final Field femail = ContactBean.class.getDeclaredField("email");
		c.alterContact("61b5a5b2-9397-4848-a2a5-e6bee084e4c6", fname, "harald");
		c.alterContact("61b5a5b2-9397-4848-a2a5-e6bee084e4c6", flastName, "harald");
		c.alterContact("61b5a5b2-9397-4848-a2a5-e6bee084e4c6", fnumber, "harald");
		c.alterContact("61b5a5b2-9397-4848-a2a5-e6bee084e4c6", femail, "harald");
	}

}
