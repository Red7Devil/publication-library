package Authors;

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
public class AuthorsService implements Dao<Author> {
    DatabaseService databaseService;

    /**
     * Constructor to initialize object
     */
    public AuthorsService() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Method to get an author by their name from the database
     *
     * @param id -- String name of the author
     * @return -- Author received from the query
     */
    @Override
    public Author getById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        Author author = null;
        try {
            this.databaseService.connect();

            String query = "Select * from authors where name='" + id + "'";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                author = new Author(resultSet.getString("name"));
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return author;
    }

    /**
     * Method to get all authors from the table
     *
     * @return -- List of authors received from the query
     */
    @Override
    public List<Author> getAll() {
        List<Author> authors = new ArrayList<>();

        try {
            this.databaseService.connect();

            String query = "Select * from authors";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                authors.add(new Author(resultSet.getString("name")));
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return authors;
    }

    /**
     * Method to insert one row to the authors table
     *
     * @param author -- Author to be inserted to the table
     * @return -- true if inserted successfully
     */
    @Override
    public boolean save(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Invalid argument author: null");
        }

        int numberOfRecords = 0;

        String query = "Insert into authors(name) values(?)";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, author.getName());

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
     * Method to update one row of the authors table
     *
     * @param author -- Author to be updated to the table
     * @return -- true if updated successfully
     */
    @Override
    public boolean update(String id, Author author) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        if (author == null) {
            throw new IllegalArgumentException("Invalid argument author: null");
        }

        int numberOfRecords = 0;

        String query = "Update authors set name=? where name=?";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, id);

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
     * Method to get the author citation count
     *
     * @param author -- String name of the author
     * @return -- Integer count of author citations
     */
    public int getAuthorCitationCount(String author) {
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument author: " + author);
        }

        int authorCitations = 0;

        try {
            this.databaseService.connect();

            // The authors table is joined with the publication_authors table on the author's name to get the publication information for each author.
            // The result is then joined with the publications table on the publication ID to get the publication details.
            // The result is further joined with the references_table on the publication ID to get the citations for each publication.
            // The WHERE clause is used to filter the results based on the given author's name.
            String query = "SELECT a.name, COUNT(*) as citation_count " +
                    "FROM authors a " +
                    "JOIN publication_authors pa ON a.name = pa.author " +
                    "JOIN publications p ON pa.publication_id = p.publication_id " +
                    "JOIN references_table rt ON p.publication_id = rt.reference_publication_id " +
                    "WHERE a.name = '" + author + "' " +
                    "GROUP BY a.name";

            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                authorCitations = resultSet.getInt("citation_count");
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return authorCitations;
    }

    public Set<String> getAuthorResearchAreas(String author, int threshold) {
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument author: " + author);
        }


        Set<String> researchAreas = new HashSet<>();
        try {
            this.databaseService.connect();
            String query = "SELECT DISTINCT rah.parent_research_area AS research_area " +
                    "FROM ( " +
                    "  SELECT ra.name AS research_area, COUNT(*) AS paper_count " +
                    "  FROM authors a " +
                    "  JOIN publication_authors pa ON a.name = pa.author " +
                    "  JOIN publications p ON pa.publication_id = p.publication_id " +
                    "  JOIN venue_research_area vra ON p.venue = vra.venue_name " +
                    "  JOIN research_areas ra ON vra.research_area = ra.name " +
                    "  WHERE a.name = '" + author + "' " +
                    "  GROUP BY ra.name " +
                    ") AS rac " +
                    "JOIN ( " +
                    "  SELECT ra.name AS research_area, ra.name AS parent_research_area " +
                    "  FROM research_areas ra " +
                    "  UNION ALL " +
                    "  SELECT rap.research_area, rap.parent_research_area " +
                    "  FROM research_area_parent rap " +
                    ") AS rah ON rac.research_area = rah.research_area " +
                    "WHERE rac.paper_count >= " + threshold + " " +
                    "ORDER BY research_area;";

            ResultSet resultSet = databaseService.select(query);
            while (resultSet.next()) {
                researchAreas.add(resultSet.getString("research_area"));
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return researchAreas;
    }
}
