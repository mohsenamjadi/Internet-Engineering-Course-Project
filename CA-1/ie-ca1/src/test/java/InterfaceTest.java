import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.*;

public class InterfaceTest {
    @Before
    public void beforeInterfaceTest() {
//        System.out.println("before interface test");
    }

    @Test
    public void CapacityError() {
        final String FILE_IN = "src/test/tests/CapacityError/ProgramInput";
        final String FILE_OUT = "src/test/tests/CapacityError/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/CapacityError/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("CapacityErrorTestNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void MinimumUnitError() {
        final String FILE_IN = "src/test/tests/MinimumUnitsError/ProgramInput";
        final String FILE_OUT = "src/test/tests/MinimumUnitsError/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/MinimumUnitsError/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("MinimumUnitErrorNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void MaximumUnitsError() {
        final String FILE_IN = "src/test/tests/MaximumUnitsError/ProgramInput";
        final String FILE_OUT = "src/test/tests/MaximumUnitsError/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/MaximumUnitsError/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("MaximumUnitsErrorNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void ClassTimeCollisionError() {
        final String FILE_IN = "src/test/tests/ClassTimeCollisionError/ProgramInput";
        final String FILE_OUT = "src/test/tests/ClassTimeCollisionError/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/ClassTimeCollisionError/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("ClassTimeCollisionErrorNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void ExamTimeCollisionError() {
        final String FILE_IN = "src/test/tests/ExamTimeCollisionError/ProgramInput";
        final String FILE_OUT = "src/test/tests/ExamTimeCollisionError/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/ExamTimeCollisionError/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("ExamTimeCollisionErrorNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void OfferingNotFound() {
        final String FILE_IN = "src/test/tests/OfferingNotFound/ProgramInput";
        final String FILE_OUT = "src/test/tests/OfferingNotFound/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/OfferingNotFound/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("OfferingNotFoundNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void StudentNotFound() {
        final String FILE_IN = "src/test/tests/StudentNotFound/ProgramInput";
        final String FILE_OUT = "src/test/tests/StudentNotFound/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/StudentNotFound/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("StudentNotFoundNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void DuplicateOffering() {
        final String FILE_IN = "src/test/tests/DuplicateOffering/ProgramInput";
        final String FILE_OUT = "src/test/tests/DuplicateOffering/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/DuplicateOffering/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("DuplicateOfferingNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void DuplicateStudent() {
        final String FILE_IN = "src/test/tests/DuplicateStudent/ProgramInput";
        final String FILE_OUT = "src/test/tests/DuplicateStudent/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/DuplicateStudent/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("DuplicateStudentNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void DuplicateWeeklyScheduleItem() {
        final String FILE_IN = "src/test/tests/DuplicateWeeklyScheduleItem/ProgramInput";
        final String FILE_OUT = "src/test/tests/DuplicateWeeklyScheduleItem/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/DuplicateWeeklyScheduleItem/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("DuplicateWeeklyScheduleItemNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void EmptyOfferings() {
        final String FILE_IN = "src/test/tests/EmptyOfferings/ProgramInput";
        final String FILE_OUT = "src/test/tests/EmptyOfferings/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/EmptyOfferings/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("EmptyOfferingsNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void EmptyWeeklySchedule() {
        final String FILE_IN = "src/test/tests/EmptyWeeklySchedule/ProgramInput";
        final String FILE_OUT = "src/test/tests/EmptyWeeklySchedule/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/EmptyWeeklySchedule/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("EmptyWeeklyScheduleNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void WeeklyScheduleItemNotFound() {
        final String FILE_IN = "src/test/tests/WeeklyScheduleItemNotFound/ProgramInput";
        final String FILE_OUT = "src/test/tests/WeeklyScheduleItemNotFound/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/WeeklyScheduleItemNotFound/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("WeeklyScheduleItemNotFoundNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void finalizedTest() {
        final String FILE_IN = "src/test/tests/finalized/ProgramInput";
        final String FILE_OUT = "src/test/tests/finalized/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/finalized/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("finalizedNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void SuccessfulScenario() {
        final String FILE_IN = "src/test/tests/SuccessfulScenario/ProgramInput";
        final String FILE_OUT = "src/test/tests/SuccessfulScenario/ProgramOutput";
        final String FILE_EXPECTED_OUT = "src/test/tests/SuccessfulScenario/ExpectedOutput";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(FILE_OUT));
            InputStream in = new FileInputStream(FILE_IN);
            Interface.start(in, out);
            out.close();
            File file1 = new File(FILE_OUT);
            File file2 = new File(FILE_EXPECTED_OUT);
            Assert.assertEquals("SuccessfulScenarioNotPassed", FileUtils.readLines(file1), FileUtils.readLines(file2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @After
    public void afterInterfaceTest() {
//        System.out.println("after interface test");
    }
}
