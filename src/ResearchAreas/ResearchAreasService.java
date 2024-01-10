package ResearchAreas;

import DatabaseComm.Dao;
import DatabaseComm.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle the database operations of Research areas table
 */
public class ResearchAreasService implements Dao<ResearchArea> {
    DatabaseService databaseService;

    /**
     * Constructor to initialize object
     */
    public ResearchAreasService() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Method to get a research area by its unique name
     *
     * @param id -- String name of the research area
     * @return -- Research area object containing retrieved information
     */
    @Override
    public ResearchArea getById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        ResearchArea researchArea = null;
        try {
            this.databaseService.connect();

            String query = "Select * from research_areas where name='" + id + "'";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                researchArea = new ResearchArea(resultSet.getString("name"));
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return researchArea;
    }

    /**
     * Method to get all research areas from the database
     *
     * @return -- List of ResearchAreas retrieved
     */
    @Override
    public List<ResearchArea> getAll() {
        List<ResearchArea> researchAreas = new ArrayList<>();
        try {
            this.databaseService.connect();

            String query = "Select * from research_areas";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                researchAreas.add(new ResearchArea(resultSet.getString("name")));
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return researchAreas;
    }

    /**
     * Method to insert one row of data into the research_areas table
     *
     * @param researchArea -- ResearchArea to be inserted to the table
     * @return -- true if record inserted
     */
    @Override
    public boolean save(ResearchArea researchArea) {
        if (researchArea == null) {
            throw new IllegalArgumentException("Invalid argument researchArea: null");
        }

        int numberOfRecords = 0;

        String query = "Insert into research_areas(name) values(?)";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, researchArea.getName());

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
     * Method to update a record in the research_areas table
     *
     * @param id           -- String name of the research area to be updated
     * @param researchArea -- New value of the record
     * @return -- true if record updated
     */
    @Override
    public boolean update(String id, ResearchArea researchArea) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        if (researchArea == null) {
            throw new IllegalArgumentException("Invalid argument researchArea: null");
        }

        int numberOfRecords = 0;

        String query = "Update research_areas set name=? where name=?";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, researchArea.getName());
            preparedStatement.setString(2, id);

            numberOfRecords = this.databaseService.upsert(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (numberOfRecords == 1) {
            return true;
        }

        return false;
    }
}
