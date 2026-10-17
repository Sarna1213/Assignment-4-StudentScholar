# StudentScholar - Assignment 4

ISYS1118 Software Engineering Fundamentals, group G030 (Friday 8:30).

## How to run
1. In Eclipse: File > Import > Existing Projects into Workspace, then select this folder.
2. If JUnit is missing: right-click the project > Build Path > Add Libraries > JUnit 5.
3. Run `CourseTest.java` as a JUnit Test.

## Files
- `src/course/Assessment.java` - the Assessment class
- `src/course/Course.java` - `createNewAssessment()`, with one check method per condition
- `test/course/CourseTest.java` - all the JUnit tests, one section per member

## Who does what
| Member | Conditions | Test cases |
|---|---|---|
| Inderjit | All valid, 1-3 | 1-4 (done) |
| Sadman | 4-7 | 5-8 |
| Sumaya | 8-11 | 9-12 |

Only edit your own `TODO` methods in `Course.java` and your own section in `CourseTest.java`.

## Writing your tests
- One test per condition, using one boundary value (Conditions 10 and 11 need two each).
- Start from the `VALID_...` values and only change the one you are testing.
- Check the starting state first, then the return value, then the list.
- Helpers you can use: `addValidAssessment()`, `assertAttributes()`, `totalPoints()`, `wordCount()`.

## Things to keep in mind
- Individual assessments use `""` as the grouping name, and the grouping name can also be `null`. Condition 9 should only compare group assessments.
- For dates, use `dd/MM/uuuu HH:mm` with `ResolverStyle.STRICT`. `yyyy` does not work in strict mode.
- Condition 6 adds the new points to the points already in the course.
