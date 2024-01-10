-- -----------------------------------------------------
-- Table `authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `authors` (
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`name`));
  
-- -----------------------------------------------------
-- Table `publisher`
-- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS `publisher` (
  `publisher_id` VARCHAR(255) NOT NULL,
  `contact_name` VARCHAR(100) NULL DEFAULT NULL,
  `contact_email` VARCHAR(100) NULL DEFAULT NULL,
  `location` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`publisher_id`));
  
-- -----------------------------------------------------
-- Table `venue`
-- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS `venue` (
  `venue_name` VARCHAR(255) NOT NULL,
  `publisher` VARCHAR(255) NULL DEFAULT NULL,
  `editor` VARCHAR(100) NULL DEFAULT NULL,
  `editor_contact` VARCHAR(100) NULL DEFAULT NULL,
  `location` VARCHAR(100) NULL DEFAULT NULL,
  `conference_year` VARCHAR(10) NULL DEFAULT NULL,
  `type` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`venue_name`),
  CONSTRAINT `fk_publisher_id`
    FOREIGN KEY (`publisher`)
    REFERENCES `publisher` (`publisher_id`));
    
-- -----------------------------------------------------
-- Table `publications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `publications` (
  `publication_id` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `pages` VARCHAR(100) NULL DEFAULT NULL,
  `volume` VARCHAR(100) NULL DEFAULT NULL,
  `issue` VARCHAR(100) NULL DEFAULT NULL,
  `month` VARCHAR(15) NULL DEFAULT NULL,
  `year` INT NULL DEFAULT NULL,
  `location` VARCHAR(100) NULL DEFAULT NULL,
  `venue` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`publication_id`),
  CONSTRAINT `fk_publication_venue`
    FOREIGN KEY (`venue`)
    REFERENCES `venue` (`venue_name`));
    
-- -----------------------------------------------------
-- Table `publication_authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `publication_authors` (
  `publication_id` VARCHAR(255) NOT NULL,
  `author` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`publication_id`, `author`),
  CONSTRAINT `fk_publication_author`
    FOREIGN KEY (`author`)
    REFERENCES `authors` (`name`),
  CONSTRAINT `fk_publication_id`
    FOREIGN KEY (`publication_id`)
    REFERENCES `publications` (`publication_id`));

-- -----------------------------------------------------
-- Table `references_table`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `references_table` (
  `publication_id` VARCHAR(255) NOT NULL,
  `reference_publication_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`publication_id`, `reference_publication_id`),
  CONSTRAINT `fk_r_publication_id`
    FOREIGN KEY (`publication_id`)
    REFERENCES `publications` (`publication_id`),
  CONSTRAINT `fk_r_reference_publication_id`
    FOREIGN KEY (`reference_publication_id`)
    REFERENCES `publications` (`publication_id`));
    
-- -----------------------------------------------------
-- Table `research_areas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `research_areas` (
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`name`));

-- -----------------------------------------------------
-- Table `research_area_parent`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `research_area_parent` (
  `research_area` VARCHAR(255) NOT NULL,
  `parent_research_area` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`research_area`, `parent_research_area`),
  CONSTRAINT `fk_parent_research_area`
    FOREIGN KEY (`parent_research_area`)
    REFERENCES `research_areas` (`name`),
  CONSTRAINT `fk_research_area`
    FOREIGN KEY (`research_area`)
    REFERENCES `research_areas` (`name`));
    
-- -----------------------------------------------------
-- Table `venue_research_area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `venue_research_area` (
  `venue_name` VARCHAR(255) NOT NULL,
  `research_area` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`venue_name`, `research_area`),
  CONSTRAINT `fk_venue_name`
    FOREIGN KEY (`venue_name`)
    REFERENCES `venue` (`venue_name`),
  CONSTRAINT `fk_venue_research_area`
    FOREIGN KEY (`research_area`)
    REFERENCES `research_areas` (`name`));