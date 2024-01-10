package ResearchAreaParents;

import DatabaseComm.Dao;
import DatabaseComm.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle the database operations of Research_Area_Parent table
 */
public class ResearchAreaParentsService implements Dao<ResearchAreaParent> {
    DatabaseService databaseService;

    /**
     * Constructor to initialize object
     */
    public ResearchAreaParentsService() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Method to get a research area and parent by its unique name
     *
     * @param id -- String name of the research area
     * @return -- ResearchAreaParent object containing retrieved information
     */
    @Override
    public ResearchAreaParent getById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        ResearchAreaParent researchAreaParent = null;
        try {
            this.databaseService.connect();

            String query = "Select * from research_area_parent where research_area='" + id + "'";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                String researchArea = resultSet.getString("research_area");
                String parentResearchArea = resultSet.getString("parent_research_area");
                researchAreaParent = new ResearchAreaParent(researchArea, parentResearchArea);
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return researchAreaParent;
    }

    /**
     * Method to get all research areas parents from the database
     *
     * @return -- List of ResearchAreaParent retrieved
     */
    @Override
    public List<ResearchAreaParent> getAll() {
        List<ResearchAreaParent> researchAreaParents = new ArrayList<>();
        try {
            this.databaseService.connect();

            String query = "Select * from research_area_parent";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                String researchArea = resultSet.getString("research_area");
                String parentResearchArea = resultSet.getString("parent_research_area");
                ResearchAreaParent researchAreaParent = new ResearchAreaParent(researchArea, parentResearchArea);
                researchAreaParents.add(researchAreaParent);
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return researchAreaParents;
    }

    /**
     * Method to insert one row of data into the research_area_parent table
     *
     * @param researchAreaParent -- ResearchAreaParent to be inserted to the table
     * @return -- true if record inserted
     */
    @Override
    public boolean save(ResearchAreaParent researchAreaParent) {
        if (researchAreaParent == null) {
            throw new IllegalArgumentException("Invalid argument researchAreaParent: null");
        }

        int numberOfRecords = 0;

        String query = "Insert into research_area_parent(research_area,parent_research_area) values(?,?)";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, researchAreaParent.getResearchArea());
            preparedStatement.setString(2, researchAreaParent.getParentResearchArea());

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
     * Method to update one row of data into the research_area_parent table
     *
     * @param id                 -- String name of the research area to be updated
     * @param researchAreaParent -- New value of the record
     * @return -- true if updated successfully
     */
    @Override
    public boolean update(String id, ResearchAreaParent researchAreaParent) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        if (researchAreaParent == null) {
            throw new IllegalArgumentException("Invalid argument researchAreaParent: null");
        }

        int numberOfRecords = 0;

        String query = "Update research_area_parent set research_area=?,parent_research_area=? where research_area=?";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, researchAreaParent.getResearchArea());
            preparedStatement.setString(2, researchAreaParent.getParentResearchArea());
            preparedStatement.setString(3, id);

            numberOfRecords = this.databaseService.upsert(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (numberOfRecords == 1) {
            return true;
        }

        return false;
    }

    /**
     * Method to get all research areas by area and parent area
     *
     * @param researchArea -- String research area
     * @param parentArea   -- String parent area
     * @return -- List of ResearchAreaParent received from query
     */
    public List<ResearchAreaParent> getListById(String researchArea, String parentArea) {
        if (researchArea == null || researchArea.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter researchArea: " + researchArea);
        }

        if (parentArea == null || parentArea.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter parentArea: " + parentArea);
        }

        List<ResearchAreaParent> researchAreaParents = new ArrayList<>();
        try {
            this.databaseService.connect();

            String query = "Select * from research_area_parent where research_area='" + researchArea + "' "
                    + "AND parent_research_area='" + parentArea + "'";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                String researchArea1 = resultSet.getString("research_area");
                String parentResearchArea = resultSet.getString("parent_research_area");
                researchAreaParents.add(new ResearchAreaParent(researchArea1, parentResearchArea));
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return researchAreaParents;
    }
}
