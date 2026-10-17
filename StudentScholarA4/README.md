# StudentScholar – Assignment 4 (createNewAssessment)

ISYS1118 / ISYS3413 Software Engineering Fundamentals · Group ISYS3413_Fri_08:30_G030

## Opening in Eclipse
1. File → Import → General → **Existing Projects into Workspace** → select this folder.
2. If JUnit shows as missing: right-click the project → Build Path → Add Libraries → **JUnit** → **JUnit 5**.
3. Run the tests: right-click `CourseTest.java` → Run As → **JUnit Test**.

## Layout
```
src/course/Assessment.java     constructor (initialises every attribute) + getters
src/course/Course.java         createNewAssessment() + one validator method per condition
test/course/CourseTest.java    all JUnit 5 tests, one section per member
```
The brief (Note 4) asks for Assessment, Course and *the* test class, so everyone adds
their tests to `CourseTest.java`, inside their own section.

## Who owns what
| Member | Test cases | Conditions | Code in `Course.java` | Status |
|---|---|---|---|---|
| 1 – Inderjit | 1, 2, 3, 4 | all valid, 1, 2, 3 | structure, constructor, C1–C3 | done |
| 2 – Sadman | 5, 6, 7, 8 | 4, 5, 6, 7 | the four `TODO (Sadman)` methods | to do |
| 3 – Sumaya | 9, 10, 11, 12 | 8, 9, 10, 11 | the four `TODO (Sumaya)` methods | to do |

Test Case 1 is the all-valid case (as in the course's Week 9 and Week 10 examples);
Test Case *n* + 1 tests Condition *n*. The placeholder validators return `true`, so the
existing tests already pass. Only edit your own methods and your own test section –
that keeps Git merges clean.

## How each test is written
- **One boundary test data value** per test case – the value just past the limit
  (e.g. 0 points, 51 points). Test Cases 11 and 12 (Conditions 10 and 11) need **two** each.
- Start from the `VALID_...` constants and change **only** the input your condition is about.
- Four parts, labelled in comments: **Setup**, **Precondition**, **Call**, **Assertion**.
  - Precondition: check the course's starting state *and* that your test data really sits
    on the boundary (e.g. total points before the call).
  - Assertion: check the return value, then that the list is unchanged (invalid input) or
    that the new object has every attribute set (valid input – use `assertAttributes`).
- Helpers you can use: `addValidAssessment(name, points)`, `assertAttributes(...)`,
  `totalPoints()`, `wordCount(text)`.
- Put your name in the file's contributor header and above your section.

## Agreed rules between members (please confirm in the group chat)
- **Individual assessments pass `""` as the grouping name.** So Condition 9 (unique
  grouping name) must only compare against other **group** assessments. If it compared
  every assessment, a course could never hold two individual assessments – Test Case 1
  (data 3) checks for exactly that mistake.
- **Strict dates (Condition 10):** use
  `DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT)`.
  It must be `uuuu`, not `yyyy` – under STRICT, `yyyy` rejects every date, valid ones included.
- **The grouping name may be `null`** (Halil, Queries board, 12 Oct) – the other inputs never are.
  Conditions 8 and 9 must handle `null` without throwing.
- **Condition 11** runs only after Condition 10 has passed, so it can parse the dates safely.
- **Condition 6** adds the new assessment's points to the points already in the course.
