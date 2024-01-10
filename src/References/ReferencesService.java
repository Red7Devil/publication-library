package References;

import DatabaseComm.Dao;
import DatabaseComm.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle the database operations of references table
 */
public class ReferencesService implements Dao<Reference> {
    DatabaseService databaseService;

    /**
     * Constructor to initialize object
     */
    public ReferencesService() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Method to get a row by the publication ID from the table
     *
     * @param id -- String id of the publication
     * @return -- PublicationAuthor received from the query
     */
    @Override
    public Reference getById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        Reference reference = null;

        try {
            this.databaseService.connect();

            String query = "Select * from references_table where publication_id='" + id + "'";
            ResultSet resultSet = this.databaseService.select(query);
            while (resultSet.next()) {
                String publicationID = resultSet.getString("publication_id");
                String referencePublicationID = resultSet.getString("reference_publication_id");
                reference = new Reference(publicationID, referencePublicationID);
            }

            this.databaseService.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return reference;
    }

    /**
     * Method to get all the rows from the table
     *
     * @return -- List of PublicationAuthor received from the query
     */
    @Override
    public List<Reference> getAll() {
        List<Reference> references = new ArrayList<>();

        try {
            this.databaseService.connect();

            String query = "Select * from references_table";
            ResultSet resultSet = this.databaseService.select(query);
            references = extractReferenceList(resultSet);

            this.databaseService.disconnect();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return references;
    }

    /**
     * Method to insert one row of data to the references table
     *
     * @param reference -- PublicationReference to be inserted
     * @return -- true if inserted successfully
     */
    @Override
    public boolean save(Reference reference) {
        if (reference == null) {
            throw new IllegalArgumentException("Invalid argument reference: null");
        }

        int numberOfRecords = 0;

        String query = "Insert into references_table(publication_id,reference_publication_id) values(?,?)";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, reference.getPublicationID());
            preparedStatement.setString(2, reference.getReferencePublicationID());

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
     * Method to update one row of data to the references table
     *
     * @param id        -- String id of the publication
     * @param reference -- Updated PublicationReference
     * @return -- true if updated successfully
     */
    @Override
    public boolean update(String id, Reference reference) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        }

        if (reference == null) {
            throw new IllegalArgumentException("Invalid argument reference: null");
        }

        int numberOfRecords = 0;

        String query = "Update references_table set publication_id=?,reference_publication_id=? where publication_id=?";
        try {
            this.databaseService.connect();

            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, reference.getPublicationID());
            preparedStatement.setString(2, reference.getReferencePublicationID());
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
     * Method to get all rows by the publication ID from the table
     *
     * @param publicationID -- String publicationID of the publication
     * @return -- List of PublicationAuthor received from the query
     */
    public List<Reference> getListById(String publicationID) {
        if (publicationID == null || publicationID.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter publicationID: " + publicationID);
        }

        List<Reference> references = new ArrayList<>();

        try {
            this.databaseService.connect();

            String query = "Select * from references_table where publication_id='" + publicationID + "'";
            ResultSet resultSet = this.databaseService.select(query);
            references = extractReferenceList(resultSet);

            this.databaseService.disconnect();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return references;
    }

    /**
     * Method to get all rows by the publication ID and reference ID from the table
     *
     * @param publicationId -- String ID of the publication
     * @param referenceId -- String ID of the referenced publication
     * @return -- List of references retrieved
     */
    public List<Reference> getListById(String publicationId, String referenceId) {
        if (publicationId == null || publicationId.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter publicationId: " + publicationId);
        }

        if (referenceId == null || referenceId.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameter referenceId: " + publicationId);
        }

        List<Reference> references = null;

        try {
            this.databaseService.connect();

            String query = "Select * from references_table where publication_id='" + publicationId + "' "
                    + "AND reference_publication_id='" + referenceId + "'";

            ResultSet resultSet = this.databaseService.select(query);
            references = extractReferenceList(resultSet);

            this.databaseService.disconnect();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return references;
    }

    /**
     * Method to extract the list of references from the result set
     * @param resultSet -- ResultSet containing result of query
     * @return -- List of references extracted
     */
    private List<Reference> extractReferenceList(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }

        List<Reference> references = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String publicationID = resultSet.getString("publication_id");
                String referencePublicationID = resultSet.getString("reference_publication_id");
                references.add(new Reference(publicationID, referencePublicationID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return references;
    }
}
