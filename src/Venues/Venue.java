package Venues;

/**
 * Class to store details regarding a venue
 */
public class Venue {
    private String venueName;
    private String publisher;
    private String editor;
    private String editorContact;
    private String location;
    private String conferenceYear;

    /**
     * Constructor to initialize all values to empty string
     */
    public Venue() {
        this.venueName = "";
        this.publisher = "";
        this.editor = "";
        this.editorContact = "";
        this.location = "";
        this.conferenceYear = "";
    }

    /**
     * Getter method for venue name
     *
     * @return -- String name of this venue
     */
    public String getVenueName() {
        return venueName;
    }

    /**
     * Getter method for publisher
     *
     * @return -- String publisher of this venue
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Getter method for editor
     *
     * @return -- String editor of this venue
     */
    public String getEditor() {
        return editor;
    }

    /**
     * Getter method for editor contact
     *
     * @return -- String editor contact of this venue
     */
    public String getEditorContact() {
        return editorContact;
    }

    /**
     * Getter method for conference year
     *
     * @return -- String conference year of this venue
     */
    public String getConferenceYear() {
        return conferenceYear;
    }

    /**
     * Getter method for location
     *
     * @return -- String location of this venue
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter method for venue name
     *
     * @param venueName -- String name of this venue
     */
    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    /**
     * Setter method for publisher
     *
     * @param publisher -- String publisher of this venue
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Setter method for editor
     *
     * @param editor -- String editor of this venue
     */
    public void setEditor(String editor) {
        this.editor = editor;
    }

    /**
     * Setter method for editor contact
     *
     * @param editorContact -- String editor contact of this venue
     */
    public void setEditorContact(String editorContact) {
        this.editorContact = editorContact;
    }

    /**
     * Setter method for location
     *
     * @param location -- String location of this venue
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Setter method for conference year
     *
     * @param conferenceYear -- String conference year of this venue
     */
    public void setConferenceYear(String conferenceYear) {
        this.conferenceYear = conferenceYear;
    }
}
