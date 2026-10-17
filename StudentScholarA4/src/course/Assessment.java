/*
 * StudentScholar - Assignment 4 (ISYS1118 / ISYS3413 Software Engineering Fundamentals)
 *
 * Contributors:
 *   Inderjit Sidhu (s4163527) - constructor and getters (Member 1: all-valid case)
 *
 * Represents an assessment in a course and stores its details.
 * These details include the name, description, points, submission
 * type, grouping information and relevant dates.
 */
package course;

public class Assessment {
    private String name;
    private String description;
    private int points;
    private String submissionType;
    private boolean group;
    private String groupingName;
    private String publishDateTime;
    private String dueDateTime;
    private String hideDateTime;

    // Sets the assessment details when a new Assessment object is created.
    public Assessment(String name, String description, int points,
                      String submissionType, boolean group, String groupingName,
                      String publishDateTime, String dueDateTime, String hideDateTime) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.submissionType = submissionType;
        this.group = group;
        this.groupingName = groupingName;
        this.publishDateTime = publishDateTime;
        this.dueDateTime = dueDateTime;
        this.hideDateTime = hideDateTime;
    }

    // These methods return the assessment details when they are needed.

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }

    public String getSubmissionType() {
        return submissionType;
    }

    public boolean isGroup() {
        return group;
    }

    public String getGroupingName() {
        return groupingName;
    }

    public String getPublishDateTime() {
        return publishDateTime;
    }

    public String getDueDateTime() {
        return dueDateTime;
    }

    public String getHideDateTime() {
        return hideDateTime;
    }
}
