package References;

/**
 * Class to store publication reference
 */
public class Reference {
    private String publicationID;
    private String referencePublicationID;

    /**
     * Constructor to initialize the object
     *
     * @param publicationID          -- String id of the publication
     * @param referencePublicationID -- String id of the referenced publication
     */
    public Reference(String publicationID, String referencePublicationID) {
        this.publicationID = publicationID;
        this.referencePublicationID = referencePublicationID;
    }

    /**
     * Getter method for the publication id
     *
     * @return -- String id of the publication
     */
    public String getPublicationID() {
        return publicationID;
    }

    /**
     * Getter method for the reference publication id
     *
     * @return -- String id of the referenced publication
     */
    public String getReferencePublicationID() {
        return referencePublicationID;
    }

    /**
     * Setter method for the publication id
     *
     * @param publicationID -- String id of the publication
     */
    public void setPublicationID(String publicationID) {
        this.publicationID = publicationID;
    }

    /**
     * Setter method for the reference publication id
     *
     * @param referencePublicationID -- String id of the referenced publication
     */
    public void setReferencePublicationID(String referencePublicationID) {
        this.referencePublicationID = referencePublicationID;
    }
}
