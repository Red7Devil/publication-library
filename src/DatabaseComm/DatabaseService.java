package DatabaseComm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Class to connect and query database
 */
public class DatabaseService {
    private Connection connection = null;

    /**
     * Connect to the give database using the database url, username and password in the prop file
     * Also select schema using the prop file
     *
     * @return -- true if connection is successful
     * @throws RuntimeException
     */
    public boolean connect() throws RuntimeException {
        Statement statement = null;

        try {
            // Retrieve database url, username and password from the prop file
            Properties authDetails = new Properties();
            InputStream stream = new FileInputStream("src/DatabaseComm/db_details.prop");
            authDetails.load(stream);

            String username = authDetails.getProperty("username");
            String password = authDetails.getProperty("password");
            String dbUrl = authDetails.getProperty("databaseUrl");

            Class.forName(  "com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(dbUrl, username, password);

            // Set schema name from the properties file
            String schema = authDetails.getProperty("schema");
            statement = this.connection.createStatement();
            statement.execute("use " + schema + ";");

            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Disconnect from the connected database
     *
     * @return -- true if connection closed else return false
     * @throws RuntimeException
     */
    public boolean disconnect() throws RuntimeException {
        try {
            this.connection.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Query the database using the given query string to get the result set
     *
     * @param query -- String query to use over the connected database
     * @return -- ResultSet returns by the statement execution. Returns null if not successful
     */
    public ResultSet select(String query) {
        ResultSet resultSet = null;
        Statement statement = null;

        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter query: " + query);
        }

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(query);

            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Insert/Update data into the database using the statement provided
     *
     * @param preparedStatement -- PreparedStatement containing the insert query
     * @return -- Number of records inserted/updated
     */
    public int upsert(PreparedStatement preparedStatement) {
        if (preparedStatement == null) {
            throw new IllegalArgumentException("Invalid parameter preparedStatement: null");
        }

        int recordsInserted = 0;

        try {
            recordsInserted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return recordsInserted;
    }

    /**
     * Getter method for connection
     *
     * @return Connection object
     */
    public Connection getConnection() {
        return this.connection;
    }
}
