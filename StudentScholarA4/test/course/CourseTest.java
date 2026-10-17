/*
 * StudentScholar - Assignment 4 (ISYS1118 / ISYS3413 Software Engineering Fundamentals)
 * JUnit 5 tests for Course.createNewAssessment()
 *
 * Contributors (each member wrote the tests in their own section below):
 *   Inderjit Sidhu (s4163527)  - Member 1: Test Cases 1-4, shared constants and helpers
 *   Sadman Al Asad Sazin       - Member 2: Test Cases 5-8
 *   Sumaya Sanjida Sarna       - Member 3: Test Cases 9-12
 *
 * Tests the Course class to check that assessments are handled correctly.
 * The tests cover valid assessment details and cases where the
 * validation conditions should reject an assessment.
 *
 * Which test case checks which condition:
 *   Test Case 1        all details valid (three sets of test data)
 *   Test Cases 2-4     Conditions 1-3  (name and description)
 *   Test Cases 5-8     Conditions 4-7  (points and submission type)
 *   Test Cases 9-12    Conditions 8-11 (grouping and dates)
 *
 * All text inputs are lowercase, as the specification requires.
 */
package course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CourseTest {

    // Valid assessment details used by the tests as a starting point.
    // Individual tests change these values when checking different conditions.
    private static final String VALID_NAME = "assignment one";
    private static final String VALID_DESCRIPTION = "requirements analysis report";  // 3 words
    private static final int VALID_POINTS = 20;
    private static final String VALID_SUBMISSION_TYPE = "pdf";
    private static final boolean VALID_GROUP = false;                // individual
    private static final String VALID_GROUPING_NAME = "";            // none for individual
    private static final String VALID_PUBLISH = "01/11/2026 09:00";
    private static final String VALID_DUE = "15/11/2026 23:59";
    private static final String VALID_HIDE = "22/11/2026 23:59";

    private Course course;

    @BeforeEach
    void setUp() {
        // Start every test with a new, empty course so the tests do not affect each other.
        course = new Course();
    }

    // =========================================================================
    // Member 1 - Inderjit Sidhu: Test Cases 1-4 (all valid, Conditions 1, 2, 3)
    // =========================================================================

    /*
     * Test Case 1 - valid details (test data 1 of 3).
     * Uses the smallest values that are still valid: 1 point, a description
     * of exactly 3 words, and dates only one minute apart.
     * Expected result: true, and the assessment is added to the list.
     */
    @Test
    @DisplayName("Test Case 1 (data 1/3): valid inputs on the lower boundaries")
    void validInputsLowerBoundaries() {
        // Setup
        String name = "weekly quiz";
        String description = "short answer quiz";
        int points = 1;
        String publish = "01/11/2026 09:00";
        String due = "01/11/2026 09:01";
        String hide = "01/11/2026 09:02";

        // Precondition
        assertTrue(course.getAssessments().isEmpty(), "Precondition: course should start empty");
        assertEquals(3, wordCount(description), "Precondition: description should be exactly 3 words");

        // Call
        boolean result = course.createNewAssessment(name, description, points,
                "pdf", false, "", publish, due, hide);

        // Assertion
        assertTrue(result, "Valid input should return true");
        assertEquals(1, course.getAssessments().size(), "Exactly one assessment should be stored");
        assertAttributes(course.getAssessments().get(0),
                name, description, points, "pdf", false, "", publish, due, hide);
    }

    /*
     * Test Case 1 - valid details (test data 2 of 3).
     * A group assessment worth 50 points, the most one assessment can have.
     * It is due on 29/02/2028, which is a real date because 2028 is a leap year.
     * Expected result: true, and the assessment is added to the list.
     */
    @Test
    @DisplayName("Test Case 1 (data 2/3): valid 50-point group assessment due on a leap day")
    void validInputsUpperPointsGroupLeapDay() {
        // Setup
        String name = "team project";
        String description = "group software prototype and report";
        int points = 50;
        String groupingName = "project teams";
        String publish = "01/02/2028 09:00";
        String due = "29/02/2028 23:59";
        String hide = "01/03/2028 00:00";

        // Precondition
        assertTrue(course.getAssessments().isEmpty(), "Precondition: course should start empty");

        // Call
        boolean result = course.createNewAssessment(name, description, points,
                "zip", true, groupingName, publish, due, hide);

        // Assertion
        assertTrue(result, "Valid input should return true");
        assertEquals(1, course.getAssessments().size(), "Exactly one assessment should be stored");
        assertAttributes(course.getAssessments().get(0),
                name, description, points, "zip", true, groupingName, publish, due, hide);
    }

    /*
     * Test Case 1 - valid details (test data 3 of 3).
     * The course already has 50 points, so adding a 50-point assessment
     * brings the total to exactly 100, the highest total allowed.
     * Expected result: true, and the assessment is added to the list.
     */
    @Test
    @DisplayName("Test Case 1 (data 3/3): valid assessment bringing the course total to exactly 100")
    void validInputsCourseTotalExactlyOneHundred() {
        // Setup - add two assessments worth 25 points each
        assertTrue(addValidAssessment("assignment one", 25), "Setup: first assessment should be added");
        assertTrue(addValidAssessment("assignment two", 25), "Setup: second assessment should be added");
        String name = "final exam";
        String description = "closed book written exam";
        int points = 50;
        String publish = "01/12/2026 09:00";
        String due = "10/12/2026 12:00";
        String hide = "20/12/2026 12:00";

        // Precondition
        assertEquals(2, course.getAssessments().size(), "Precondition: course should hold two assessments");
        assertEquals(100, totalPoints() + points, "Precondition: new total should be exactly 100");

        // Call
        boolean result = course.createNewAssessment(name, description, points,
                "doc", false, "", publish, due, hide);

        // Assertion
        assertTrue(result, "Valid input should return true");
        assertEquals(3, course.getAssessments().size(), "Exactly one more assessment should be stored");
        assertEquals(100, totalPoints(), "Course total should now be exactly 100");
        assertAttributes(course.getAssessments().get(2),
                name, description, points, "doc", false, "", publish, due, hide);
    }

    /*
     * Test Case 2 - duplicate name (Condition 1).
     * Test data: "assignment one", the same name as an assessment already
     * in the course.
     * Expected result: false. Nothing is added and the existing assessment
     * is not changed.
     */
    @Test
    @DisplayName("Test Case 2: duplicate assessment name is rejected (Condition 1)")
    void duplicateNameIsRejected() {
        // Setup - add an assessment called "assignment one"
        assertTrue(addValidAssessment("assignment one", 20), "Setup: first assessment should be added");
        Assessment existing = course.getAssessments().get(0);
        // A new copy of the same name, like a user typing it in again.
        // This checks that names are compared by their text using equals().
        String duplicateName = new String("assignment one");

        // Precondition
        assertEquals(1, course.getAssessments().size(), "Precondition: course should hold one assessment");
        assertEquals(duplicateName, existing.getName(), "Precondition: the name should already be taken");
        assertNotSame(existing.getName(), duplicateName, "Precondition: test name should be a separate object");

        // Call - all other details are valid, so only the name can cause a rejection
        boolean result = course.createNewAssessment(duplicateName, "design document draft", 10,
                "zip", VALID_GROUP, VALID_GROUPING_NAME, VALID_PUBLISH, VALID_DUE, VALID_HIDE);

        // Assertion
        assertFalse(result, "A duplicate name should return false");
        assertEquals(1, course.getAssessments().size(), "No new assessment should be stored");
        assertSame(existing, course.getAssessments().get(0), "The existing assessment should still be stored");
        assertAttributes(existing, "assignment one", VALID_DESCRIPTION, 20, VALID_SUBMISSION_TYPE,
                VALID_GROUP, VALID_GROUPING_NAME, VALID_PUBLISH, VALID_DUE, VALID_HIDE);
    }

    /*
     * Test Case 3 - blank name (Condition 2).
     * Test data: " " (a single space). It has one character but no real text,
     * so it should still count as blank.
     * Expected result: false, and nothing is added.
     */
    @Test
    @DisplayName("Test Case 3: blank assessment name is rejected (Condition 2)")
    void blankNameIsRejected() {
        // Setup
        String blankName = " ";

        // Precondition
        assertTrue(course.getAssessments().isEmpty(), "Precondition: course should start empty");
        assertEquals(1, blankName.length(), "Precondition: test name should be one character long");
        assertTrue(blankName.trim().isEmpty(), "Precondition: test name should contain only whitespace");

        // Call
        boolean result = course.createNewAssessment(blankName, VALID_DESCRIPTION, VALID_POINTS,
                VALID_SUBMISSION_TYPE, VALID_GROUP, VALID_GROUPING_NAME, VALID_PUBLISH, VALID_DUE, VALID_HIDE);

        // Assertion
        assertFalse(result, "A blank name should return false");
        assertTrue(course.getAssessments().isEmpty(), "No assessment should be stored");
    }

    /*
     * Test Case 4 - description too short (Condition 3).
     * Test data: "requirements analysis", which has 2 words, one less
     * than the minimum of 3.
     * Expected result: false, and nothing is added.
     */
    @Test
    @DisplayName("Test Case 4: two-word description is rejected (Condition 3)")
    void twoWordDescriptionIsRejected() {
        // Setup
        String shortDescription = "requirements analysis";

        // Precondition
        assertTrue(course.getAssessments().isEmpty(), "Precondition: course should start empty");
        assertEquals(2, wordCount(shortDescription), "Precondition: description should be exactly 2 words");

        // Call
        boolean result = course.createNewAssessment(VALID_NAME, shortDescription, VALID_POINTS,
                VALID_SUBMISSION_TYPE, VALID_GROUP, VALID_GROUPING_NAME, VALID_PUBLISH, VALID_DUE, VALID_HIDE);

        // Assertion
        assertFalse(result, "A 2-word description should return false");
        assertTrue(course.getAssessments().isEmpty(), "No assessment should be stored");
    }

    // =========================================================================
    // Member 2 - Sadman Al Asad Sazin: Test Cases 5-8 (Conditions 4, 5, 6, 7)
    // TODO (Sadman): one @Test per condition, each with one boundary test data,
    // following the Setup / Precondition / Call / Assertion structure above.
    // =========================================================================

    // =========================================================================
    // Member 3 - Sumaya Sanjida Sarna: Test Cases 9-12 (Conditions 8, 9, 10, 11)
    // TODO (Sumaya): one @Test per condition with one boundary test data each,
    // except Test Cases 11 and 12 (Conditions 10 and 11), which need two each.
    // =========================================================================

    // =========================================================================
    // Shared helpers (Inderjit Sidhu) - available to every member's tests
    // =========================================================================

    /** Adds a valid assessment with the given name and points, and returns the result. */
    private boolean addValidAssessment(String name, int points) {
        return course.createNewAssessment(name, VALID_DESCRIPTION, points, VALID_SUBMISSION_TYPE,
                VALID_GROUP, VALID_GROUPING_NAME, VALID_PUBLISH, VALID_DUE, VALID_HIDE);
    }

    /** Checks that every detail of the assessment matches the expected values. */
    private void assertAttributes(Assessment a, String name, String description, int points,
                                  String submissionType, boolean group, String groupingName,
                                  String publish, String due, String hide) {
        assertEquals(name, a.getName(), "name");
        assertEquals(description, a.getDescription(), "description");
        assertEquals(points, a.getPoints(), "points");
        assertEquals(submissionType, a.getSubmissionType(), "submissionType");
        assertEquals(group, a.isGroup(), "group");
        assertEquals(groupingName, a.getGroupingName(), "groupingName");
        assertEquals(publish, a.getPublishDateTime(), "publishDateTime");
        assertEquals(due, a.getDueDateTime(), "dueDateTime");
        assertEquals(hide, a.getHideDateTime(), "hideDateTime");
    }

    /** Adds up the points of all assessments currently in the course. */
    private int totalPoints() {
        int total = 0;
        for (Assessment a : course.getAssessments()) {
            total += a.getPoints();
        }
        return total;
    }

    /** Counts the words in some text. Used to check the test data before a test runs. */
    private static int wordCount(String text) {
        String trimmed = text.trim();
        return trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
    }
}
