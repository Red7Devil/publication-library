package Publishers;

/**
 * Class to store data related to a publisher
 */
public class Publisher {
    private String publisherID;
    private String contactName;
    private String contactEmail;
    private String location;

    /**
     * Constructor to initialize the object with the given values
     *
     * @param publisherID  -- String publisherID of this publisher
     * @param contactName  -- String contact name of this publisher
     * @param contactEmail -- String contact email of this publisher
     * @param location     -- String location of this publisher
     */
    public Publisher(String publisherID, String contactName, String contactEmail, String location) {
        this.publisherID = publisherID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.location = location;
    }

    /**
     * Getter method for publisher ID
     *
     * @return -- String publisher ID of this publisher
     */
    public String getPublisherID() {
        return publisherID;
    }

    /**
     * Getter method for contact name
     *
     * @return -- String contact name of this publisher
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Getter method for contact email
     *
     * @return -- String contact email of this publisher
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Getter method for location
     *
     * @return -- String location of this publisher
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter method for publisher ID
     *
     * @param publisherID -- String publisher ID of this publisher
     */
    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }

    /**
     * Setter method for contact name
     *
     * @param contactName -- String contact name of this publisher
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Setter method for contact email
     *
     * @param contactEmail -- String contact email of this publisher
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Setter method for location
     *
     * @param location -- String location of this publisher
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
