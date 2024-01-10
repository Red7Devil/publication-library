package PublicationAuthors;

import DatabaseComm.Dao;
import DatabaseComm.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle the database operations of publication_authors table
 */
public class PublicationAuthorService implements Dao<PublicationAuthor> {
    DatabaseService databaseService;

    /**
     * Constructor to initialize object
     */
    public PublicationAuthorService() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Method to get a row by the publication ID from the table
     *
     * @param id -- String id of the publication
     * @return -- PublicationAuthor received from the query
     */
    @Override
    public PublicationAuthor getById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        PublicationAuthor publicationAuthor = null;

        try {
            this.databaseService.connect();

            String query = "Select * from publication_authors where publication_id='" + id + "'";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                String publicationID = resultSet.getString("publication_id");
                String author = resultSet.getString("author");
                publicationAuthor = new PublicationAuthor(publicationID, author);
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return publicationAuthor;
    }

    /**
     * Method to get all the rows from the table
     *
     * @return -- List of PublicationAuthors received from the query
     */
    @Override
    public List<PublicationAuthor> getAll() {
        List<PublicationAuthor> publicationAuthors = new ArrayList<>();

        try {
            this.databaseService.connect();

            String query = "Select * from publication_authors";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                String publicationID = resultSet.getString("publication_id");
                String author = resultSet.getString("author");
                publicationAuthors.add(new PublicationAuthor(publicationID, author));
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return publicationAuthors;
    }

    /**
     * Method to insert one row to the publication_authors table
     *
     * @param publicationAuthor -- PublicationAuthor to be inserted to the table
     * @return -- true if inserted successfully
     */
    @Override
    public boolean save(PublicationAuthor publicationAuthor) {
        if (publicationAuthor == null) {
            throw new IllegalArgumentException("Invalid argument publicationAuthor: null");
        }

        int numberOfRecords = 0;

        String query = "Insert into publication_authors(publication_id,author) values(?,?)";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, publicationAuthor.getPublicationID());
            preparedStatement.setString(2, publicationAuthor.getAuthor());

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
     * Method to update a row from the publication_authors table
     *
     * @param id                -- String id of the publication
     * @param publicationAuthor -- PublicationAuthor to be updated to the table
     * @return -- true if updated successfully
     */
    @Override
    public boolean update(String id, PublicationAuthor publicationAuthor) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        if (publicationAuthor == null) {
            throw new IllegalArgumentException("Invalid argument publicationAuthor: null");
        }

        int numberOfRecords = 0;

        String query = "Update publication_authors set publication_id=?,author=? where publication_id=?";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, publicationAuthor.getPublicationID());
            preparedStatement.setString(2, publicationAuthor.getAuthor());
            preparedStatement.setString(3, id);

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
     * Method to get a list of rows from table based on the given publication id
     *
     * @param id -- String id of the publication
     * @return -- List of PublicationAuthor received from the query
     */
    public List<PublicationAuthor> getListById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        List<PublicationAuthor> publicationAuthors = new ArrayList<>();

        try {
            this.databaseService.connect();

            String query = "Select * from publication_authors where publication_id='" + id + "'";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                String publicationID = resultSet.getString("publication_id");
                String author = resultSet.getString("author");
                publicationAuthors.add(new PublicationAuthor(publicationID, author));
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return publicationAuthors;
    }
}
