package PublicationAuthors;

/**
 * Class to store data for publication author
 */
public class PublicationAuthor {
    private String publicationID;
    private String author;

    /**
     * Constructor to initialize the object
     *
     * @param publicationID -- String id of the publication
     * @param author        -- String name of the author
     */
    public PublicationAuthor(String publicationID, String author) {
        this.publicationID = publicationID;
        this.author = author;
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
     * Getter method for the author
     *
     * @return -- String name of the author
     */
    public String getAuthor() {
        return author;
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
     * Setter method for the author
     *
     * @param author -- String name of the author
     */
    public void setAuthor(String author) {
        this.author = author;
    }
}
