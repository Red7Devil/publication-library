package VenueResearchAreas;

import DatabaseComm.Dao;
import DatabaseComm.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle the database operations of venue_research_area table
 */
public class VenueResearchAreaService implements Dao<VenueResearchArea> {
    DatabaseService databaseService;

    /**
     * Constructor to initialize object
     */
    public VenueResearchAreaService() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Method to get a row by the venue name from the database
     *
     * @param id -- String name of the venue
     * @return -- VenueResearchArea for the given venue name
     */
    @Override
    public VenueResearchArea getById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        VenueResearchArea venueResearchArea = null;
        try {
            this.databaseService.connect();

            String query = "Select * from venue_research_area where venue_name='" + id + "'";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                String venueName = resultSet.getString("venue_name");
                String researchArea = resultSet.getString("research_area");
                venueResearchArea = new VenueResearchArea(venueName, researchArea);
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return venueResearchArea;
    }

    /**
     * Method to get all rows  from the venue_research_area table
     *
     * @return -- All VenueResearchAreas in the database
     */
    @Override
    public List<VenueResearchArea> getAll() {
        List<VenueResearchArea> venueResearchAreas = new ArrayList<>();

        try {
            this.databaseService.connect();

            String query = "Select * from venue_research_area";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                String venueName = resultSet.getString("venue_name");
                String researchArea = resultSet.getString("research_area");
                venueResearchAreas.add(new VenueResearchArea(venueName, researchArea));
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return venueResearchAreas;
    }

    /**
     * Method to insert one row to the venue_research_area table
     *
     * @param venueResearchArea -- VenueResearchArea to be inserted
     * @return -- true if inserted successfully
     */
    @Override
    public boolean save(VenueResearchArea venueResearchArea) {
        if (venueResearchArea == null) {
            throw new IllegalArgumentException("Invalid argument venueResearchArea: null");
        }

        int numberOfRecords = 0;

        String query = "Insert into venue_research_area(venue_name,research_area) values(?,?)";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, venueResearchArea.getVenueName());
            preparedStatement.setString(2, venueResearchArea.getResearchArea());

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
     * Method to insert one row to the venue_research_area table
     *
     * @param id                -- String name of the venue
     * @param venueResearchArea -- Update VenueResearchArea object
     * @return -- true if updated successfully
     */
    @Override
    public boolean update(String id, VenueResearchArea venueResearchArea) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        if (venueResearchArea == null) {
            throw new IllegalArgumentException("Invalid argument venueResearchArea: null");
        }

        int numberOfRecords = 0;

        String query = "Update venue_research_area set venue_name=?, research_area=? where venue_name=?";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, venueResearchArea.getVenueName());
            preparedStatement.setString(2, venueResearchArea.getResearchArea());
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
}
