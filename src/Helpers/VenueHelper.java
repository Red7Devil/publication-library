package Helpers;

import Publishers.Publisher;
import Publishers.PublishersService;
import ResearchAreaParents.ResearchAreaParent;
import ResearchAreaParents.ResearchAreaParentsService;
import ResearchAreas.ResearchArea;
import ResearchAreas.ResearchAreasService;
import VenueResearchAreas.VenueResearchArea;
import VenueResearchAreas.VenueResearchAreaService;
import Venues.Venue;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class VenueHelper {
    /**
     * Method to add the publisher to the database if ID doesn't already exist
     *
     * @param identifier           -- String ID of the publisher
     * @param publisherInformation -- Map of publisher information
     * @return -- true if the publisher was added, else return false
     */
    public static boolean addPublisher(String identifier, Map<String, String> publisherInformation) {
        PublishersService publishersService = new PublishersService();
        boolean publisherIDExists = publishersService.getById(identifier) != null;
        if (publisherIDExists) {
            return false;
        } else {
            String contactName = publisherInformation.get("contact_name");
            String contactEmail = publisherInformation.get("contact_email");
            String location = publisherInformation.get("location");

            if (contactName == null) {
                contactName = "";
            }

            if (contactEmail == null) {
                contactEmail = "";
            }

            if (location == null) {
                location = "";
            }


            Publisher publisher = new Publisher(identifier, contactName, contactEmail, location);
            try {
                publishersService.save(publisher);
            } catch (RuntimeException e) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method to add research area to the library if it does not already exist
     *
     * @param researchArea -- String name of research area
     * @return -- true if added, else return false;
     */
    public static boolean addResearchArea(String researchArea) {
        if (researchArea == null || researchArea.isEmpty()) {
            return false;
        }

        ResearchAreasService researchAreasService = new ResearchAreasService();
        boolean researchAreaExists = researchAreaExists(researchArea);

        if (!researchAreaExists) {
            try {
                researchAreasService.save(new ResearchArea(researchArea));
            } catch (RuntimeException e) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method to link the given parent areas to the given research area
     *
     * @param researchArea -- String name of the research area
     * @param parentArea   -- Set of Strings of parent research areas
     * @return -- true if all parent areas were linked
     */
    public static boolean linkResearchAreaToParents(String researchArea, Set<String> parentArea) {
        ResearchAreasService researchAreasService = new ResearchAreasService();
        ResearchAreaParentsService researchAreaParentsService = new ResearchAreaParentsService();
        for (String parent : parentArea) {
            boolean parentAreaExists = researchAreaExists(parent);
            if (!parentAreaExists) {
                try {
                    researchAreasService.save(new ResearchArea(parent));
                } catch (RuntimeException e) {
                    return false;
                }
            }

            List<ResearchAreaParent> researchAreaParent = researchAreaParentsService.getListById(researchArea, parent);
            if (researchAreaParent == null || researchAreaParent.size() == 0) {
                try {
                    researchAreaParentsService.save(new ResearchAreaParent(researchArea, parent));
                } catch (RuntimeException e) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Method to check if given research area exits in the database
     *
     * @param researchArea -- String name of research area
     * @return -- true if exists else false
     */
    public static boolean researchAreaExists(String researchArea) {
        ResearchAreasService researchAreasService = new ResearchAreasService();
        try {
            ResearchArea rA = researchAreasService.getById(researchArea);
            if (rA == null) {
                return false;
            }
        } catch (RuntimeException e) {
            return false;
        }

        return true;
    }

    /**
     * Method to extract venue from the given map
     *
     * @param venueInformation -- Map of venue information
     * @param venue            -- Venue object to be updated
     */
    public static void extractVenueInfo(Map<String, String> venueInformation, Venue venue) {
        for (String key : venueInformation.keySet()) {
            String value = venueInformation.get(key);
            if (value == null) {
                value = "";
            }

            switch (key) {
                case "publisher":
                    venue.setPublisher(value);
                    break;
                case "editor":
                    venue.setEditor(value);
                    break;
                case "editor_contact":
                    venue.setEditorContact(value);
                    break;
                case "location":
                    venue.setLocation(value);
                    break;
                case "conference_year":
                    venue.setConferenceYear(value);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Method to check if the publisher of given venue is valid
     *
     * @param venue -- Venue for which publisher to be checked
     * @return -- true if publisher is valid, else return false
     */
    public static boolean isVenuePublisherValid(Venue venue) {
        if (venue.getPublisher() == null || venue.getPublisher().isEmpty()) {
            return false;
        }

        PublishersService publishersService = new PublishersService();
        boolean publisherExists = true;
        try {
            Publisher publisher = publishersService.getById(venue.getPublisher());
            if (publisher == null) {
                publisherExists = false;
            }
        } catch (RuntimeException e) {
            publisherExists = true;
        }

        if (!publisherExists) {
            return false;
        }

        return true;
    }

    /**
     * Method to link given research areas to the given venue
     *
     * @param venueName     -- String name of the venue
     * @param researchAreas -- Set of strings of research areas
     * @return -- true if all areas are linked to the venue.
     */
    public static boolean linkResearchAreaToVenue(String venueName, Set<String> researchAreas) {
        for (String researchArea : researchAreas) {
            if (addResearchArea(researchArea)) {
                VenueResearchAreaService venueResearchAreaService = new VenueResearchAreaService();
                try {
                    venueResearchAreaService.save(new VenueResearchArea(venueName, researchArea));
                } catch (RuntimeException e) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
