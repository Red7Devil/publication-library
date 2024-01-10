package Publications;

import DatabaseComm.Dao;
import DatabaseComm.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to handle the database operations of Authors table
 */
public class PublicationsService implements Dao<Publication> {
    DatabaseService databaseService;

    /**
     * Constructor to initialize object
     */
    public PublicationsService() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Method to get a publication by their id from the database
     *
     * @param id -- String id of the publication
     * @return -- Publication retrieved from the query
     */
    @Override
    public Publication getById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        Publication publication = null;
        try {
            this.databaseService.connect();

            String query = "Select * from publications where publication_id='" + id + "'";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                publication = this.extractPublicationFromResult(resultSet);
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return publication;
    }

    /**
     * Method to get all publications from the database
     *
     * @return -- List of publications received from the query
     */
    @Override
    public List<Publication> getAll() {
        List<Publication> publications = new ArrayList<>();

        try {
            this.databaseService.connect();

            String query = "Select * from publications";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                publications.add(this.extractPublicationFromResult(resultSet));
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return publications;
    }

    /**
     * Method to insert one row to the publications table
     *
     * @param publication -- Publication to be inserted
     * @return -- true if inserted successfully
     */
    @Override
    public boolean save(Publication publication) {
        if (publication == null) {
            throw new IllegalArgumentException("Invalid argument publication: null");
        }

        int numberOfRecords = 0;

        String query = "Insert into publications(publication_id,title,pages,volume,issue,month,year,location,venue) "
                + "values(?,?,?,?,?,?,?,?,?)";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            this.setStatementValues(preparedStatement, publication);

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
     * Method to update a publication in the publications table
     *
     * @param id          -- String id of the publication to be updated
     * @param publication -- Updated Publication
     * @return -- true if updated successfully
     */
    @Override
    public boolean update(String id, Publication publication) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        if (publication == null) {
            throw new IllegalArgumentException("Invalid argument publication: null");
        }

        int numberOfRecords = 0;

        String query = "Update publications set publication_id=?, title=?, pages=?, volume=?, issue=?, month=?, year=?, location=?, venue=? "
                + "where publication_id=?";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            this.setStatementValues(preparedStatement, publication);
            preparedStatement.setString(10, id);

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
     * Method to convert values from result set to Publication object
     *
     * @param resultSet -- ResultSet pointing to one row
     * @return -- Publication extracted from the result set
     */
    private Publication extractPublicationFromResult(ResultSet resultSet) {
        if (resultSet == null) {
            throw new IllegalArgumentException("Invalid arguement resultSet: null");
        }

        Publication publication = new Publication();
        try {
            publication.setPublicationID(resultSet.getString("publication_id"));
            publication.setTitle(resultSet.getString("title"));
            publication.setPages(resultSet.getString("pages"));
            publication.setVolume(resultSet.getString("volume"));
            publication.setIssue(resultSet.getString("issue"));
            publication.setMonth(resultSet.getString("month"));
            publication.setYear(resultSet.getString("year"));
            publication.setLocation(resultSet.getString("location"));
            publication.setVenue(resultSet.getString("venue"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return publication;
    }

    /**
     * Set values from the publication to the prepared statement
     *
     * @param preparedStatement -- PreparedStatement to be updated
     * @param publication       -- Publication to be used for update
     */
    private void setStatementValues(PreparedStatement preparedStatement, Publication publication) {
        if (preparedStatement == null) {
            throw new IllegalArgumentException("Invalid argument preparedStatement: null");
        }

        if (publication == null) {
            throw new IllegalArgumentException("Invalid argument publication: null");
        }

        try {
            preparedStatement.setString(1, publication.getPublicationID());
            preparedStatement.setString(2, publication.getTitle());
            preparedStatement.setString(3, publication.getPages());
            preparedStatement.setString(4, publication.getVolume());
            preparedStatement.setString(5, publication.getIssue());
            preparedStatement.setString(6, publication.getMonth());
            preparedStatement.setString(7, publication.getYear());
            preparedStatement.setString(8, publication.getLocation());
            preparedStatement.setString(9, publication.getVenue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to get the set of seminal papers from the database
     *
     * @param researchArea   -- String research area
     * @param paperCitation  -- Integer count of paper citations
     * @param otherCitations -- Integer count of other citations
     * @return
     */
    public Set<String> getSeminalPapers(String researchArea, int paperCitation, int otherCitations) {
        Set<String> seminalPapers = new HashSet<>();

        if (researchArea == null || researchArea.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument researchArea: " + researchArea);
        }

        try {
            this.databaseService.connect();

            String query = "SELECT p.publication_id, COUNT(rt.reference_publication_id) as cited_count " +
                    "FROM publications p " +
                    "JOIN references_table rt ON p.publication_id = rt.publication_id " +
                    "JOIN publication_authors pa ON p.publication_id = pa.publication_id " +
                    "JOIN authors a ON pa.author = a.name " +
                    "JOIN venue_research_area vra ON p.venue = vra.venue_name " +
                    "JOIN research_areas ra ON vra.research_area = ra.name " +
                    "WHERE ra.name = '" + researchArea + "' " +
                    "GROUP BY p.publication_id " +
                    "HAVING COUNT(rt.reference_publication_id) <= " + paperCitation + " " +
                    "AND cited_count >= " + otherCitations +
                    " ORDER BY cited_count DESC;";

            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                seminalPapers.add(resultSet.getString("publication_id"));
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return seminalPapers;
    }
}
