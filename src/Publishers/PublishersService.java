package Publishers;

import DatabaseComm.Dao;
import DatabaseComm.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle the database operations of Publishers table
 */
public class PublishersService implements Dao<Publisher> {
    DatabaseService databaseService;

    /**
     * Constructor to initialize object
     */
    public PublishersService() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Method to get a publisher by their id from the database
     *
     * @param id -- String publisher ID of publisher
     * @return -- Publisher retrieved from the database
     */
    @Override
    public Publisher getById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        Publisher publisher = null;
        try {
            this.databaseService.connect();

            String query = "Select * from publisher where publisher_id='" + id + "'";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                String publisherID = resultSet.getString("publisher_id");
                String contactName = resultSet.getString("contact_name");
                String contactEmail = resultSet.getString("contact_email");
                String location = resultSet.getString("location");
                publisher = new Publisher(publisherID, contactName, contactEmail, location);
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return publisher;
    }

    /**
     * Method to get all publishers from the database
     *
     * @return -- List of existing publishers
     */
    @Override
    public List<Publisher> getAll() {
        List<Publisher> publishers = new ArrayList<>();

        try {
            this.databaseService.connect();

            String query = "Select * from publisher";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                String publisherID = resultSet.getString("publisher_id");
                String contactName = resultSet.getString("contact_name");
                String contactEmail = resultSet.getString("contact_email");
                String location = resultSet.getString("location");
                publishers.add(new Publisher(publisherID, contactName, contactEmail, location));
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return publishers;
    }

    /**
     * Method to insert one row of data into the publisher table
     *
     * @param publisher -- Publisher to be inserted
     * @return -- true if inserted successfully
     */
    @Override
    public boolean save(Publisher publisher) {
        if (publisher == null) {
            throw new IllegalArgumentException("Invalid argument publisher: null");
        }

        int numberOfRecords = 0;

        String query = "Insert into publisher(publisher_id,contact_name,contact_email,location) values(?,?,?,?)";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            this.setStatementValues(preparedStatement, publisher);

            numberOfRecords = this.databaseService.upsert(preparedStatement);

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (numberOfRecords == 1) {
            return true;
        }

        return false;
    }

    /**
     * Method to update one row of data into the publisher table
     *
     * @param id        -- String publisher ID of the publisher to be updated
     * @param publisher -- New value of the publisher
     * @return -- true if updated successfully
     */
    @Override
    public boolean update(String id, Publisher publisher) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        if (publisher == null) {
            throw new IllegalArgumentException("Invalid argument publisher: null");
        }

        int numberOfRecords = 0;

        String query = "Update publisher set publisher_id=?,contact_name=?,contact_email=?,location=? where publisher_id=?";

        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            this.setStatementValues(preparedStatement, publisher);
            preparedStatement.setString(5, id);

            numberOfRecords = this.databaseService.upsert(preparedStatement);

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (numberOfRecords == 1) {
            return true;
        }

        return false;
    }

    private void setStatementValues(PreparedStatement preparedStatement, Publisher publisher) {
        if (preparedStatement == null) {
            throw new IllegalArgumentException("Invalid argument preparedStatement: null");
        }

        if (publisher == null) {
            throw new IllegalArgumentException("Invalid argument publisher: null");
        }

        try {
            preparedStatement.setString(1, publisher.getPublisherID());
            preparedStatement.setString(2, publisher.getContactName());
            preparedStatement.setString(3, publisher.getContactEmail());
            preparedStatement.setString(4, publisher.getLocation());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
