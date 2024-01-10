package Publications;

/**
 * Class to store details regarding a publication
 */
public class Publication {
    private String publicationID;
    private String title;
    private String pages;
    private String volume;
    private String issue;
    private String month;
    private String year;
    private String location;
    private String venue;

    public Publication() {
        this.publicationID = "";
        this.title = "";
        this.pages = "";
        this.volume = "";
        this.issue = "";
        this.month = "";
        this.year = "";
        this.location = "";
        this.venue = "";
    }

    /**
     * Getter method for the publication ID
     *
     * @return -- String publication ID of this publication
     */
    public String getPublicationID() {
        return publicationID;
    }

    /**
     * Getter method for the title
     *
     * @return -- String title of this publication
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter method for the pages
     *
     * @return -- String pages of this publication
     */
    public String getPages() {
        return pages;
    }

    /**
     * Getter method for the volume
     *
     * @return -- String volume of this publication
     */
    public String getVolume() {
        return volume;
    }

    /**
     * Getter method for the issue
     *
     * @return -- String issue of this publication
     */
    public String getIssue() {
        return issue;
    }

    /**
     * Getter method for the month
     *
     * @return -- String month of this publication
     */
    public String getMonth() {
        return month;
    }

    /**
     * Getter method for the year
     *
     * @return -- String year of this publication
     */
    public String getYear() {
        return year;
    }

    /**
     * Getter method for the location
     *
     * @return -- String location of this publication
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter method for the venue
     *
     * @return -- String venue name of this publication
     */
    public String getVenue() {
        return venue;
    }

    /**
     * Setter method for the publication ID
     *
     * @param publicationID -- String publication ID of this publication
     */
    public void setPublicationID(String publicationID) {
        this.publicationID = publicationID;
    }

    /**
     * Setter method for the title
     *
     * @param title -- String title of this publication
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter method for the pages
     *
     * @param pages -- String pages of this publication
     */
    public void setPages(String pages) {
        this.pages = pages;
    }

    /**
     * Setter method for the volume
     *
     * @param volume -- String volume of this publication
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * Setter method for the issue
     *
     * @param issue -- String issue of this publication
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * Setter method for the month
     *
     * @param month -- String month of this publication
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Setter method for the year
     *
     * @param year -- String year of this publication
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Setter method for the location
     *
     * @param location -- String location of this publication
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Setter method for the venue
     *
     * @param venue -- String pages of this publication
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }
}
