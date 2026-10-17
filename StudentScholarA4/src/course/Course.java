/*
 * StudentScholar - Assignment 4 (ISYS1118 / ISYS3413 Software Engineering Fundamentals)
 *
 * Contributors (each member owns the validator methods for their own conditions):
 *   Inderjit Sidhu (s4163527)     - Member 1: class structure, createNewAssessment(),
 *                                   all-valid case, Conditions 1, 2, 3
 *   Sadman Al Asad Sazin          - Member 2: Conditions 4, 5, 6, 7
 *   Sumaya Sanjida Sarna          - Member 3: Conditions 8, 9, 10, 11
 *
 * How createNewAssessment() works:
 * The method checks the assessment details against each condition.
 * If any check fails, it returns false and does not add the assessment.
 * If all checks pass, the assessment is created and added to the list.
 *
 * Note from the specification: all string inputs are lowercase, so case
 * variations do not need to be handled.
 */
package course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Course {

    // The list is created straight away so it is ready to use.
    // This also prevents a NullPointerException when adding
    // the first assessment.
    private List<Assessment> assessments = new ArrayList<>();

    /**
     * Checks the assessment details against all 11 conditions.
     * If everything is valid, the assessment is created and added
     * to the list. Otherwise, nothing is added.
     *
     * @return true if the assessment is added successfully,
     *         or false if any condition is not met.
     */
    public boolean createNewAssessment(String name, String description, int points,
                                       String submissionType, boolean group, String groupingName,
                                       String publishDateTime, String dueDateTime, String hideDateTime) {

        // ---- Member 1 (Inderjit): name and description ----
        // Check that the name is not blank before checking
        // whether another assessment already uses it.
        if (!isNameNotBlank(name)) {
            return false;                                          // Condition 2
        }
        if (!isNameUnique(name)) {
            return false;                                          // Condition 1
        }
        if (!hasMinimumDescriptionWords(description)) {
            return false;                                          // Condition 3
        }

        // ---- Member 2 (Sadman): points and submission type ----
        if (!isPointsAboveZero(points)) {
            return false;                                          // Condition 4
        }
        if (!isPointsWithinSingleLimit(points)) {
            return false;                                          // Condition 5
        }
        if (!isTotalPointsWithinCourseLimit(points)) {
            return false;                                          // Condition 6
        }
        if (!isSubmissionTypeAllowed(submissionType)) {
            return false;                                          // Condition 7
        }

        // ---- Member 3 (Sumaya): grouping and dates ----
        if (!isGroupingNameProvidedForGroup(group, groupingName)) {
            return false;                                          // Condition 8
        }
        if (!isGroupingNameUnique(group, groupingName)) {
            return false;                                          // Condition 9
        }
        if (!areDateTimesValid(publishDateTime, dueDateTime, hideDateTime)) {
            return false;                                          // Condition 10
        }
        // Check the date order only after all three dates
        // have passed the validation in Condition 10.
        if (!areDateTimesInOrder(publishDateTime, dueDateTime, hideDateTime)) {
            return false;                                          // Condition 11
        }

        // ---- All conditions met (Member 1: all-valid case) ----
        // All checks have passed, so create the assessment
        // with the supplied details and add it to the list.
        Assessment newAssessment = new Assessment(name, description, points,
                submissionType, group, groupingName,
                publishDateTime, dueDateTime, hideDateTime);
        assessments.add(newAssessment);
        return true;
    }

    /**
     * Returns the assessments currently stored in the course.
     * The returned list cannot be changed, so the original
     * assessments remain protected from outside changes.
     */
    public List<Assessment> getAssessments() {
        return Collections.unmodifiableList(assessments);
    }

    // =====================================================================
    // Member 1 - Inderjit Sidhu: Conditions 1, 2, 3
    // =====================================================================

    /**
     * Checks whether the assessment name is already being used.
     * If another assessment has the same name, it returns false.
     */
    private boolean isNameUnique(String name) {
        for (Assessment existing : assessments) {
            if (existing.getName().equals(name)) {
                return false;   // an assessment with this name already exists
            }
        }
        return true;
    }

    /**
     * Checks that the assessment name has been provided.
     * A null name, an empty string or a name containing only
     * spaces is not accepted.
     */
    private boolean isNameNotBlank(String name) {
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Checks that the description contains at least three words.
     * Extra spaces at the start, end or between words are ignored.
     */
    private boolean hasMinimumDescriptionWords(String description) {
        if (description == null || description.trim().isEmpty()) {
            return false;       // no words at all
        }
        String[] words = description.trim().split("\\s+");
        return words.length >= 3;
    }

    // =====================================================================
    // Member 2 - Sadman Al Asad Sazin: Conditions 4, 5, 6, 7
    // TODO (Sadman): replace each placeholder body with the real check.
    // Each currently returns true so the all-valid tests can run meanwhile.
    // =====================================================================

    /** Condition 4: the assessment points must be greater than 0. */
    private boolean isPointsAboveZero(int points) {
        return true;    // TODO (Sadman): implement Condition 4
    }

    /** Condition 5: no single assessment can exceed 50 points. */
    private boolean isPointsWithinSingleLimit(int points) {
        return true;    // TODO (Sadman): implement Condition 5
    }

    /** Condition 6: the total points across all assessments cannot exceed 100. */
    private boolean isTotalPointsWithinCourseLimit(int points) {
        return true;    // TODO (Sadman): implement Condition 6
    }

    /** Condition 7: submission type must be one of "pdf", "zip", "doc". */
    private boolean isSubmissionTypeAllowed(String submissionType) {
        return true;    // TODO (Sadman): implement Condition 7
    }

    // =====================================================================
    // Member 3 - Sumaya Sanjida Sarna: Conditions 8, 9, 10, 11
    // TODO (Sumaya): replace each placeholder body with the real check.
    // Each currently returns true so the all-valid tests can run meanwhile.
    // =====================================================================

    /**
     * Condition 8: assessments are either individual or group. If it is a group
     * assessment, the grouping name must be provided.
     */
    private boolean isGroupingNameProvidedForGroup(boolean group, String groupingName) {
        return true;    // TODO (Sumaya): implement Condition 8
    }

    /** Condition 9: the grouping name must be unique for each assessment. */
    private boolean isGroupingNameUnique(boolean group, String groupingName) {
        return true;    // TODO (Sumaya): implement Condition 9
    }

    /**
     * Condition 10: date times must be in the format DD/MM/YYYY hh:mm (24-hour
     * clock) and must be strictly validated (e.g. "29/02/2026 20:00" is invalid
     * because 2026 is not a leap year).
     */
    private boolean areDateTimesValid(String publishDateTime, String dueDateTime,
                                      String hideDateTime) {
        return true;    // TODO (Sumaya): implement Condition 10
    }

    /**
     * Condition 11: the hide date time must be after the due date time, which
     * must be after the publish date time.
     */
    private boolean areDateTimesInOrder(String publishDateTime, String dueDateTime,
                                        String hideDateTime) {
        return true;    // TODO (Sumaya): implement Condition 11
    }
}
