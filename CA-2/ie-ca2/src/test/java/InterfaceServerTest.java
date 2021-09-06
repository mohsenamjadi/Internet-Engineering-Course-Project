import CourseEnrolment.CourseEnrolment;
import InterfaceServer.InterfaceServer;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Assert;
import org.junit.Test;


public class InterfaceServerTest {
    private static InterfaceServer interfaceServer;
    private static CourseEnrolment courseEnrolment;

    public static void successfulAddToWeeklySchedule(String id) {
        try {
            courseEnrolment.addToWeeklySchedule(id, "8101001", "01");
            courseEnrolment.addToWeeklySchedule(id, "8101006", "01");
            courseEnrolment.addToWeeklySchedule(id, "8101011", "01");
            courseEnrolment.addToWeeklySchedule(id, "8101012", "01");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void minimumUnitsAddToWeeklySchedule(String id) {
        try {
            courseEnrolment.addToWeeklySchedule(id, "8101001", "01");
            courseEnrolment.addToWeeklySchedule(id, "8101006", "01");
            courseEnrolment.addToWeeklySchedule(id, "8101011", "01");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void maximumUnitsAddToWeeklySchedule(String id) {
        try {
            courseEnrolment.addToWeeklySchedule(id, "8101001", "01");

            courseEnrolment.addToWeeklySchedule(id, "8101003", "01");

            courseEnrolment.addToWeeklySchedule(id, "8101006", "01");

            courseEnrolment.addToWeeklySchedule(id, "8101030", "01");

            courseEnrolment.addToWeeklySchedule(id, "8101011", "01");

            courseEnrolment.addToWeeklySchedule(id, "8101012", "01");

            courseEnrolment.addToWeeklySchedule(id, "8101021", "01");

            courseEnrolment.addToWeeklySchedule(id, "8101022", "01");


        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @BeforeClass
    public static void beforeTests() {
        final String COURSES_URI = "http://138.197.181.131:5000/api/courses";
        final String STUDENTS_URI = "http://138.197.181.131:5000/api/students";
        final String GRADES_URI = "http://138.197.181.131:5000/api/grades";
        interfaceServer = new InterfaceServer();
        courseEnrolment = interfaceServer.getEnrollmentSystem();
        try {
            interfaceServer.start(COURSES_URI, STUDENTS_URI, GRADES_URI, 5000);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void minimumUnitsGetSubmit() {
        String id = "810196129";
        try {
            minimumUnitsAddToWeeklySchedule(id);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        HttpResponse<String> response = Unirest.post("http://localhost:5000/submit/"+id).asString();
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void maximumUnitsGetSubmit() {
        String id = "810196812";
        try {
            maximumUnitsAddToWeeklySchedule(id);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        HttpResponse<String> response = Unirest.post("http://localhost:5000/submit/"+id).asString();
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void successfulGetSubmit() {
        String id = "810197220";
        try {
            successfulAddToWeeklySchedule(id);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        HttpResponse<String> response = Unirest.post("http://localhost:5000/submit/"+id).asString();
        Assert.assertEquals(200, response.getStatus());
    }

    @AfterClass
    public static void afterTests() {
        interfaceServer.stop();
    }
}
