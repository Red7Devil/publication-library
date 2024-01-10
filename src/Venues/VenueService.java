package Venues;

import DatabaseComm.Dao;
import DatabaseComm.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle the database operations of Venue table
 */
public class VenueService implements Dao<Venue> {
    DatabaseService databaseService;

    /**
     * Constructor to initialize object
     */
    public VenueService() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Method to get a venue by their name from the database
     *
     * @param id -- String name of the venue
     * @return -- Venue received from the database
     */
    @Override
    public Venue getById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        Venue venue = null;
        try {
            this.databaseService.connect();

            String query = "Select * from venue where venue_name='" + id + "'";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                venue = this.extractVenueFromResult(resultSet);
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return venue;
    }

    /**
     * Method to get all venues from the database
     *
     * @return -- List of venues retrieved
     */
    @Override
    public List<Venue> getAll() {
        List<Venue> venues = new ArrayList<>();
        try {
            this.databaseService.connect();

            String query = "Select * from venue";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                Venue venue = this.extractVenueFromResult(resultSet);
                venues.add(venue);
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return venues;
    }

    /**
     * Method to insert one row to the venue table
     *
     * @param venue -- Venue to be inserted to the table
     * @return -- true if inserted successfully
     */
    @Override
    public boolean save(Venue venue) {
        if (venue == null) {
            throw new IllegalArgumentException("Invalid argument venue: null");
        }

        int numberOfRecords = 0;

        String query = "Insert into venue(venue_name,publisher,editor,editor_contact,location,conference_year) values(?,?,?,?,?,?)";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            setStatementValues(preparedStatement, venue);

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
     * Method to updated one row of the venue table
     *
     * @param id    -- String name of the venue to be updated
     * @param venue -- Venue to be updated to the table
     * @return -- true if updated successfully
     */
    @Override
    public boolean update(String id, Venue venue) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        if (venue == null) {
            throw new IllegalArgumentException("Invalid argument venue: null");
        }

        int numberOfRecords = 0;

        String query = "Update venue set venue_name=?,publisher=?,editor=?,editor_contact=?,location=?, conference_year=? "
                + "where venue_name=?";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            setStatementValues(preparedStatement, venue);
            preparedStatement.setString(7, id);

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
     * Method to convert values from result set to Venue object
     *
     * @param resultSet -- ResultSet pointing to one row
     * @return -- Venue extracted from the result set
     */
    private Venue extractVenueFromResult(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }

        Venue venue = new Venue();
        try {
            venue.setVenueName(resultSet.getString("venue_name"));
            venue.setPublisher(resultSet.getString("publisher"));
            venue.setEditor(resultSet.getString("editor"));
            venue.setEditorContact(resultSet.getString("editor_contact"));
            venue.setLocation(resultSet.getString("location"));
            venue.setConferenceYear(resultSet.getString("conference_year"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return venue;
    }

    /**
     * Set values from the venue to the prepared statement
     *
     * @param preparedStatement -- PreparedStatement to be updated
     * @param venue             -- Venue to be used for update
     */
    private void setStatementValues(PreparedStatement preparedStatement, Venue venue) {
        if (preparedStatement == null) {
            throw new IllegalArgumentException("Invalid argument preparedStatement: null");
        }

        if (venue == null) {
            throw new IllegalArgumentException("Invalid argument venue: null");
        }

        try {
            preparedStatement.setString(1, venue.getVenueName());
            preparedStatement.setString(2, venue.getPublisher());
            preparedStatement.setString(3, venue.getEditor());
            preparedStatement.setString(4, venue.getEditorContact());
            preparedStatement.setString(5, venue.getLocation());
            preparedStatement.setString(6, venue.getConferenceYear());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
