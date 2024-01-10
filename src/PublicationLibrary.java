import Authors.Author;
import Authors.AuthorsService;
import Helpers.PublicationsHelper;
import Helpers.VenueHelper;
import PublicationAuthors.PublicationAuthor;
import PublicationAuthors.PublicationAuthorService;
import Publications.Publication;
import Publications.PublicationsService;
import Publishers.Publisher;
import Publishers.PublishersService;
import References.Reference;
import References.ReferencesService;
import ResearchAreaParents.ResearchAreaParent;
import ResearchAreaParents.ResearchAreaParentsService;
import ResearchAreas.ResearchArea;
import ResearchAreas.ResearchAreasService;
import Venues.Venue;
import Venues.VenueService;

import java.util.*;

public class PublicationLibrary {
    /**
     * Method to add a publication to the library
     *
     * @param identifier             -- String ID of the publication
     * @param publicationInformation -- Map of publication information
     * @return -- true if publication was added, else returns false
     */
    public boolean addPublication(String identifier, Map<String, String> publicationInformation) {
        if (identifier == null || identifier.isEmpty()) {
            return false;
        }

        if (publicationInformation == null || publicationInformation.keySet().size() == 0) {
            return false;
        }

        // Extract information from map into POJO class objects
        Publication publication = new Publication();
        publication.setPublicationID(identifier);
        Set<Author> authors = new HashSet<>();
        PublicationsHelper.extractPublicationInfo(publicationInformation, publication, authors);

        boolean publicationAdded = PublicationsHelper.addPublication(publication);
        if (!publicationAdded) {
            return false;
        }

        boolean authorsAdded = PublicationsHelper.addPublicationAuthors(identifier, authors);
        if (!authorsAdded) {
            return false;
        }

        return true;
    }

    /**
     * Method to add references to an existing publication
     *
     * @param identifier -- String ID of the publication
     * @param references -- Set of Strings of referenced publication IDs
     * @return -- true if references were added else return false
     */
    public boolean addReferences(String identifier, Set<String> references) {
        boolean validIdentifier = PublicationsHelper.isPublicationIdValid(identifier);
        if (!validIdentifier) {
            return false;
        }

        if (references == null || references.size() == 0) {
            return false;
        }

        ReferencesService referencesService = new ReferencesService();
        for (String reference : references) {
            boolean validReference = PublicationsHelper.isPublicationIdValid(reference);
            if (!validReference) {
                return false;
            }

            List<Reference> existingReferences = referencesService.getListById(identifier, reference);
            boolean referenceExists = existingReferences != null || existingReferences.size() > 0;

            if (!referenceExists) {
                referencesService.save(new Reference(identifier, reference));
            }
        }
        return true;
    }

    /**
     * Method to add a venue to the library
     *
     * @param venueName        -- String name of the venue
     * @param venueInformation -- Map of venue information
     * @param researchAreas    -- Set of strings of research areas linked to the venue
     * @return -- true if venue was added, else return false
     */
    public boolean addVenue(String venueName, Map<String, String> venueInformation, Set<String> researchAreas) {
        if (venueName == null || venueName.isEmpty()) {
            return false;
        }

        if (venueInformation == null || venueInformation.keySet().size() == 0) {
            return false;
        }

        Venue venue = new Venue();
        venue.setVenueName(venueName);

        VenueHelper.extractVenueInfo(venueInformation, venue);

        //Check if publisher is valid
        if (!VenueHelper.isVenuePublisherValid(venue)) {
            return false;
        }

        VenueService venueService = new VenueService();
        try {
            venueService.save(venue);
        } catch (RuntimeException e) {
            return false;
        }

        boolean linkedResearchAreas = VenueHelper.linkResearchAreaToVenue(venueName, researchAreas);
        if (!linkedResearchAreas) {
            return false;
        }

        return true;
    }

    /**
     * Method to add a publisher to the library
     *
     * @param identifier           -- String ID of the publisher
     * @param publisherInformation -- Map of publisher information
     * @return -- true if publisher was added, else return false
     */
    public boolean addPublisher(String identifier, Map<String, String> publisherInformation) {
        if (identifier == null || identifier.isEmpty()) {
            return false;
        }

        if (publisherInformation == null || publisherInformation.keySet().size() == 0) {
            return false;
        }

        boolean publisherAdded = VenueHelper.addPublisher(identifier, publisherInformation);

        return publisherAdded;
    }

    /**
     * Method to add a research area to the library
     *
     * @param researchArea -- String name of the research area
     * @param parentArea   -- Set of Strings of parent research areas
     * @return -- true if research area was added and parent areas were linked to it
     */
    public boolean addArea(String researchArea, Set<String> parentArea) {
        boolean researchAreaAdded = VenueHelper.addResearchArea(researchArea);
        if (!researchAreaAdded) {
            return false;
        }

        boolean parentAreaLinked = VenueHelper.linkResearchAreaToParents(researchArea, parentArea);
        if (!parentAreaLinked) {
            return false;
        }

        return true;
    }

    /**
     * Method to get the publication with the given key
     *
     * @param key -- String key of the publication
     * @return -- Map of publication information
     */
    Map<String, String> getPublications(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }

        Map<String, String> publicationInfo = new HashMap<>();
        PublicationsService publicationsService = new PublicationsService();
        ReferencesService referencesService = new ReferencesService();
        PublicationAuthorService publicationAuthorService = new PublicationAuthorService();

        try {
            Publication publication = publicationsService.getById(key);
            if (publication == null) {
                return null;
            } else {
                publicationInfo.put("publication_id", key);
                publicationInfo.put("title", publication.getTitle());
                publicationInfo.put("pages", publication.getPages());
                if (publication.getVolume() != null && !publication.getVolume().isEmpty()) {
                    publicationInfo.put("volume", publication.getVolume());
                }
                if (publication.getIssue() != null && !publication.getIssue().isEmpty()) {
                    publicationInfo.put("issue", publication.getIssue());
                }
                if (publication.getMonth() != null && !publication.getMonth().isEmpty()) {
                    publicationInfo.put("month", publication.getMonth());
                }
                publicationInfo.put("year", publication.getYear());
                if (publication.getLocation() != null && !publication.getLocation().isEmpty()) {
                    publicationInfo.put("location", publication.getLocation());
                }
                publicationInfo.put("venue", publication.getVenue());
            }

            List<Reference> references = referencesService.getListById(key);
            String referenceString = "";
            for (int i = 0; i < references.size(); i++) {
                referenceString += references.get(i).getReferencePublicationID();
                if (i != references.size() - 1) {
                    referenceString += ",";
                }
            }
            publicationInfo.put("references", referenceString);

            String authorsString = "";
            List<PublicationAuthor> authors = publicationAuthorService.getListById(key);
            for (int i = 0; i < authors.size(); i++) {
                authorsString += authors.get(i).getAuthor();
                if (i != authors.size() - 1) {
                    authorsString += ",";
                }
            }
            publicationInfo.put("authors", authorsString);

        } catch (RuntimeException e) {
            return null;
        }

        return publicationInfo;
    }

    /**
     * Method to get the count of publications directly cited the given author in their publications
     *
     * @param author -- String name of the author
     * @return -- Integer count of citations
     */
    public int authorCitations(String author) {
        if (author == null || author.isEmpty()) {
            return 0;
        }

        AuthorsService authorsService = new AuthorsService();
        try {
            int citationCount = authorsService.getAuthorCitationCount(author);
            return citationCount;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Method to get the list of seminal papers from the library
     *
     * @param area           -- String name of research area
     * @param paperCitation  -- Integer count of paper citations
     * @param otherCitations -- Integer count of other citations
     * @return -- Set of strings of publication IDs of seminal papers
     */
    public Set<String> seminalPapers(String area, int paperCitation, int otherCitations) {
        if (area == null || area.isEmpty()) {
            return null;
        }

        PublicationsService publicationsService = new PublicationsService();
        Set<String> seminalPaperList = new HashSet<>();
        try {
            seminalPaperList = publicationsService.getSeminalPapers(area, paperCitation, otherCitations);
        } catch (Exception e) {
            return null;
        }

        return seminalPaperList;
    }

    /**
     * Method to get the list of author research areas
     *
     * @param author -- String name of the author
     * @param threshold -- Integer threshold
     * @return -- Set of author research areas
     */
    public Set<String> authorResearchAreas(String author, int threshold) {
        if (author == null || author.isEmpty()) {
            return null;
        }

        Set<String> authorResearchAreas = new HashSet<>();
        try {
            AuthorsService authorsService = new AuthorsService();
            authorResearchAreas = authorsService.getAuthorResearchAreas(author, threshold);

        } catch (RuntimeException e) {
            return null;
        }
        return authorResearchAreas;
    }

}
