## SWEN90006 Assignment 1: Testing the Air Quality Management System

In this assignment, you will test a simplified Air Quality Management System
(AQMS) in which data is collected from a sensor network. The system allows
authenticated users and devices to upload and retrieve data. To enable these
features, the system implements functions for users to:

- register,
- log in, and
- add and read sensor data.

For simplicity, the system implements the database as a Java data structure.
We assume that the implementation has no security vulnerabilities, and all
these functions are intended for use by the administrators.

This assignment focuses on input partitioning, boundary-value analysis, control-flow testing,
and briefly touches on mutation analysis.

You are provided with the specification of the AQMS and its implementation as a Java
program. The program is organized into a folder containing multiple files that adhere to
the specification. Your task is to test the program using various techniques and analyze
the differences in the effectiveness of these methods.

While you will be deriving and comparing test cases, debugging the program is not required.
This assignment has both practical and analytical components. You will need to update the
provided templated JUnit driver program to run your test cases, which may require some
experimentation with the JUnit framework. Additionally, you will apply testing techniques
to derive your test cases and compare the results.

This assignment accounts for 20% of your final mark.

### Project Structure and Build Instructions

The project is organized into key directories and files, each serving a specific
purpose:

- **`AQMSSpecs.txt`**: A text file detailing the specifications of the AQMS. You will
  use these specifications for testing techniques such as input partitioning and
  boundary-value analysis.
  
- **`programs/`**: Contains the Java source files that implement the AQMS as per
  the provided specifications (AQMSSpecs.txt). The `original/` subdirectory includes the necessary
  classes, such as `AQMS.java` and various exceptions. These files are the main
  focus for testing and will be compiled and executed.

- **`tests/`**: Contains the JUnit test scripts you'll work with. The `swen90006/aqms/`
  subdirectory includes test cases like `BoundaryTests.java` and `PartitioningTests.java`.
  Your task is to update and expand these tests to evaluate the AQMS implementation.

- **`build.xml`**: The Ant build file automates compiling the AQMS and running the
  JUnit tests. It includes targets for checking validity, compiling sources, running
  tests, and cleaning up files. Use this file to build and test the program.

- **`id.txt`**: A file for you to **write down your student ID**. Please replace the sample
  ID with your own, ensuring it includes **only your student ID**. The teaching team uses
  this to automatically mark your JUnit tests.

The `lib/` directory contains the necessary libraries (`junit-4.11.jar`,
`hamcrest-core-1.3.jar`, and `commons-lang3-3.15.0.jar`) for running the tests, and
`classes/` stores the compiled Java classes, which are automatically generated during
the build process.

**Prerequisites:** Ensure that the following software is installed on your system:

- **Java Development Kit (JDK):** Java is required to compile and run the project. 
Ensure that the JDK is installed and that the `JAVA_HOME` environment variable 
is set correctly.

- **Apache Ant:** Ant is the tool used to automate the build process. 
You can install Ant using Homebrew `brew install ant` (for macOS users) or by downloading it 
from the official [Apache Ant website](https://ant.apache.org/).

- **Integrated Development Environment (IDE):** While any Java IDE works, 
**IntelliJ IDEA** is recommended for its robust support,
including Ant integration, code completion, and debugging tools.

**Build Instructions:** The `build.xml` file contains **targets** for tasks like compiling code (converting
Java source files into bytecode), running tests (verifying functionality), and
specifying configurations such as program version (`original` or `mutant`) and
test suite (`BoundaryTests` or `PartitioningTests`).

**Available Build Targets and their Usage:**

| Target Name    | Description                                                                               | Terminal Command                                                                           |
|----------------|-------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
| `compile_orig` | Compiles the original program located in the `src/original/` directory.                   | `ant compile_orig`                                                                         |
| `compile_test` | Compiles the test cases located in the `src/tests/` directory. The test should match one of {PartitioningTests, BoundaryTests} (The same list applies to all `Dtest` options below)                           | `ant compile_test -Dtest='PartitioningTests'` OR <br>`ant compile_test -Dtest='BoundaryTests'` |
| `check_prog`   | Validates that a program is specified and checks if it matches one of {original,mutant-1,mutant-2,mutant-3,mutant-4,mutant-5} (The same list applies to all `Dprogram` options below). | `ant check_prog -Dprogram={your program}`                                                                           |
| `check_test`   | Validates that a test is specified and checks if it matches one of the allowed values.    | `ant check_test -Dtest={your test}`                                                                           |
| `compile_prog` | Compiles the specified program, whether it's the original or a mutant version.            | `ant compile_prog -Dprogram={your program}`                                                                         |
| `test`         | Runs the specified test cases against the compiled program. (**The most complete command for you to use**). Results for the tests can be found in the `results/` folder.| `ant test -Dprogram={your program} -Dtest={your test}`                                                    |
| `default`      | Default compile, compiles the original program and `boundaryTests`   | `ant`                                                                                      |
| `clean`        | Cleans up the compiled files and any other generated artifacts.                           | `ant clean`                                                                                |

The command below is an example command to run in a terminal from the root directory of this assignment. It will run the Partition tests you implemented against the original AQMS source code would be:

```shell
ant test -Dprogram="original" -Dtest="PartitioningTests"
```
After you run this command, you could see information in the terminal about whether the build of the program or test is successful or not, and how many tests are passing/failing. 


### Your Tasks:

#### Task 1 -- Equivalence Partitioning

Using the specifications provided in **AQMSSpecs.txt**, apply equivalence partitioning to derive equivalence classes for the 
following methods in the API: `register`, `login`, `assign_role`, and `get_data`.

**Important:** For this task, you should **only** refer to the specifications in **AQMSSpecs.txt**. 
While the `AQMS.java` file includes Javadoc comments, these are for overall software quality purposes. 
Since we are conducting black-box testing in this task, you must derive your test cases 
exclusively from **AQMSSpecs.txt**.

Document your equivalence partitioning process for each method using test template trees, 
listing any assumptions you make. You should create four trees, one for each method. 
You will be marked *only* on your test template trees (plus any assumptions listed), 
so ensure they are clear and concise. Note that as part of your input domain, you must 
consider instance variables if any. These are not parameters to any methods but are still *inputs*.

You may omit some nodes to improve readability, provided it is clear what you intend. 
For example, if testing a book store and you want to test all seven Harry Potter books, 
you could create nodes for books 1 and 7, and use `\ldots` to represent the other five books.

Finally, ensure your set of equivalence classes covers the entire input space. Justify your claim.

#### Task 2 -- JUnit Test Driver for Equivalence Partitioning

Select test cases associated with your equivalence classes, and implement them in the JUnit test driver 
located at `tests/swen90006/aqms/PartitioningTests.java`. Use *one* JUnit test method for each equivalence class. 
For each test, clearly identify which equivalence class it has been selected from. 
Once completed, push this script to your Git repository.

Include this as Appendix A in your submission.

**Note:** When implementing tests for a specific method, you may use other methods to verify 
that the first method has worked as expected. Additionally, you might need to execute other methods 
in the class to bring the instance into a testable state (see the example in `PartitioningTests.java`).

#### Task 3 -- Boundary-Value Analysis

Conduct a boundary-value analysis for your equivalence classes. Document your process and reasoning. 
Select test cases associated with the identified boundary values.

#### Task 4 -- JUnit Test Driver for Boundary-Value Analysis

Implement your boundary-value tests in the JUnit test driver located at `test/swen90006/aqms/BoundaryTests.java`. 
As before, use *one* JUnit test method for each test input. Once completed, push this script to your Git repository.

Include this as Appendix B in your submission.

**Note:** The `BoundaryTests` JUnit script inherits from `PartitioningTests`, which means all tests 
from `PartitioningTests` are included in `BoundaryTests`. A JUnit test is simply a standard public Java class! 
You may choose to remove this inheritance, but you can also use it to your advantage to make the `BoundaryTests` 
script easier to create. Overriding an existing test will replace it in the `BoundaryTests` script.

#### Task 5 -- Coverage-based Testing

Measure the effectiveness of the two test suites (equivalence partitioning and boundary-value analysis). For this task, you should perform these three sub-tasks:

1. Draw a control flow graph of the `register` method. 
2. Calculate the coverage score of your two test suites using *condition coverage* for the `isValidUsername` method. Note that you can access the source code in Coverage-based Testing part, and the `isValidUsername` method is called by the `register` method.  
3. Calculate the converage score of your two test suites using *multi-condition coverage* for the `register` method. 

**Note:** For the entire Coverage-based Testing part, you do not need to consider any inter-procedural analysis, that means, you only need to use information available for that function. For example, to investigate the `login` method, you do not need to draw control flow graph or measure coverage referencing inside the methods called by the `login` method. 

Show your working for the coverage calculation in a table that lists each test objective (that is, each combination for multiple-condition coverage or each condition for condition coverage) and one test that achieves this, if any. 

You will reveive marks for each of the sub-tasks. For drawing the correct control flow graph, deriving correct coverage scores and showing how you come to this score. No marks are allocated for having a higher coverage score. 


#### Task 6 -- Mutation selection

Derive five *non-equivalent* mutants for the AQMS class using only the nine Java mutation operators in the subject notes. These mutants should be difficult to find using testing. Insert each of these mutants into the files `programs/mutant-1/swen90006/aqms/AQMS.java`, `programs/mutant-2/swen90006/aqms/AQMS.java`, etc.

All five mutants must be non-equivalent AND each mutant must be killed by at least one test in your JUnit BoundaryTest script to demonstrate that they are non-equivalent. They must be in code that is executed when calling one of the four methods tested in Task 1, this includes code in the functions that one of the four methods calls, but excludes functions throwing execptions or functions not implemented by the teaching team (e.g., Java built-in functions). 

Importantly, do not change anything else about the mutant files except for inserting the mutant.

Each mutant must change exactly one line of `AQMS.java` for each version `mutant-1`, `mutant-2`, etc.

#### Task 7 -- Comparison
Compare the two set of test cases (equivalence partitioning and boundary-value analysis) and their results. Which method did you find was more effective and why? You should consider the coverage of the valid input/output domain, the coverage achieved, and the mutants it kills. Limit your comparison to half a page. If your comparision is over half a page, you will be marked only on the first half page. 

## Marking criteria

| Criterion  | Description  | Marks  |
|---|---|---|
| Equivalence partitioning  | Clear evidence that partitioning the input space to find equivalence classes has been done systematically and correctly. Resulting equivalence classes are disjoint and cover the appropriate input space | 6 |
| Boundary-value analysis | Clear evidence that boundary-value analysis has been applied systematically and correctly, and all boundaries, including on/off points, have been identified | 3 |
| Control-flow analysis    | Clear evidence that the control flow graph is derived systematically and correctly. Resulting control flow graph reflects the branches and loops of the specified function  |  2    |
|                          | Clear evidence that measurement of the control-flow criterion has been done systematically and correctly	 |  2   |
|                          | Clear and succinct justification/documentation of which test covers each objective	 |  2   |
|  Original tests           | No build failure, no failing tests | 1   |
|Mutant score| All your mutants are killed, no equivalent mutants | 1 |
| Staff mutant score                | Your tests kill all mutants that the teaching team creates| 2 |
| Discussion               | Clear demonstration of understanding of the topics used in the assignment, presented in a logical manner |   1   |
| **Total** |   | 20 |


For the Original tests, we award 1 mark if your JUnit test does not have build failure, and your test suite **does not fail** against the original code base. If you do not receive the mark for the original tests, you will also receive no mark for the Mutant score and staff mutant score part. 

**Important**: We determine that a mutant is killed when JUnit contains a failed test. Because of this, if a test case fails when applied to the original source code, it will fail on most of your mutants, and the staff mutants. With that being said, if your find a test that supposed to pass according to the spec, but fails in the original source code, pleast let us know via a private thread showing your test cases on the discussion board. 

For Mutant score, we award 1 mark if your JUnit test kills all your own mutant, that means some test in your JUnit tests fail when applied to your mutants. 

If not all mutant killed, we give mark using the following formula:

Mutant_score = mutants_killed / 5 * 1 - penalty_for_equivalent_mutant

For Staff mutant score, the teaching team will create five mutants following the same instruction in Task 6. we award 2 marks if all staff mutants are killed by some of your tests. This part ensures that your mutant and your test cases aiming to kill the mutant is not deliberatly crafted.

If not all mutant killed, we give mark using the following formula:

Staff_mutant_score = staff_mutants_killed / 5 * 2

## Submission instructions

### JUnit script submission 
For the JUnit test scripts, we will clone everyone's repository at the due time. We will mark the latest version on the main branch of the repository. To have any late submissions marked, please email Hira ([`hira.syeda@unimelb.edu.au`](mailto:hira.syeda@unimelb.edu.au)) to let her know to pull changes from your repository.

Some important instructions:

1. Do NOT change the package names in any of the files.
2. Do NOT change the directory structure.
3. Do NOT add any new files: you should be able to complete the assignment without adding any new source files.

JUnit scripts will be batch run automatically, so any script that does not follow the instructions will not run and will not be awarded any marks.

### Report submission
For the remainder of the assignment (test template tree, boundary-value analysis working, coverage, and discussion) submit a PDF file using the links on the subject Canvas site. Go to the SWEN90006 Canvas site, select *Assignments* from the subject menu, and submit in *Assignment 1 report*.

## Tips

Some tips to managing the assignment, in particular, the equivalence partitioning:

1. Ensure that you understand the notes *before* diving into the assignment. Trying to learn equivalence partitioning or boundary-value analysis on a project this size is difficult. If you do not understand the simple examples in the notes, the understanding will not come from applying to a more complex example.

2. Keep it simple: don't focus on what you think we want to see --- focus on looking for good tests and then documenting them in a systematic way.  That IS what we want to see.
	
3. Focus on the requirements: as with any testing effort, focus your testing on the requirements, NOT on demonstrating the theory from the notes. Simply look at each requirement and see which guidelines should apply.

4. If you cannot figure out how to start your test template tree, just start listing tests that you think are important. Once you have a list, think about putting them into a tree.

### Late submission policy

If you require an extension, please contact Hira ([`hira.syeda@unimelb.edu.au`](mailto:hira.syeda@unimelb.edu.au)) to discuss. Having assessments due for other subjects is not a valid reason for an extension.

By default, everyone is implicitly granted an extension of up to 7 days, but with a penalty of 10% (2 marks) per day that the assignment is submitted late. So, if you are falling behind, you may want to consider submitted 1-2 days late if you feel you can do enough to make up the 2-4 marks. 

If you submit late, email Hira to let her know so she can pull the changes from the repository.

### Academic Misconduct

The University academic integrity policy (see [https://academicintegrity.unimelb.edu.au/](https://academicintegrity.unimelb.edu.au/) applies. Students are encouraged to discuss the assignment topic, but all submitted work must represent the individual's understanding of the topic. 

The subject staff take academic misconduct very seriously. In this subject in the past, we have successfully prosecuted several students that have breached the university policy. Often this results in receiving 0 marks for the assessment, and in some cases, has resulted in failure of the subject. 

### Originality Multiplier

For work that we find is similar to another submission or information found online, an originality multiplier will be applied to the work.  For example, if 20% of the assessment is deemed to have been taken from another source, the final mark will be multiplied by 0.8.
