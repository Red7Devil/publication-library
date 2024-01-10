package VenueResearchAreas;

/**
 * Class to store the data related to a venue and related research area
 */
public class VenueResearchArea {
    private String venueName;
    private String researchArea;

    /**
     * Constructor to initialize this venue research area
     *
     * @param venueName    -- String name of the venue
     * @param researchArea -- String research area of the venue
     */
    public VenueResearchArea(String venueName, String researchArea) {
        this.venueName = venueName;
        this.researchArea = researchArea;
    }

    /**
     * Getter method of venue name
     *
     * @return -- String name of the venue
     */
    public String getVenueName() {
        return venueName;
    }

    /**
     * Getter method of research area
     *
     * @return -- String name of the research area
     */
    public String getResearchArea() {
        return researchArea;
    }

    /**
     * Setter method for the venue
     *
     * @param venueName -- String name of the venue
     */
    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    /**
     * Setter method for the research area
     *
     * @param researchArea -- String name of the research area
     */
    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }
}
