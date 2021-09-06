import CourseEnrolment.Controller;
import CourseEnrolment.Student.Student;
import CourseEnrolment.System.Offering;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;

class Interface {
    public static void main(String[] args){
        PrintStream consoleOut = System.out;
        InputStream consoleIn = System.in;
        start(consoleIn, consoleOut);
    }

    public static void start(InputStream inputStream, PrintStream outputStream) {
        System.setOut(outputStream);
        Controller controller = new Controller();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                try {
                    String[] input_parts = parseInput(line);
                    String command = input_parts[0];
                    String jsonData = "";
                    if (input_parts.length == 2) {
                        jsonData = input_parts[1];
                    }
                    runCommand(command, jsonData, controller);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void runCommand(String command, String jsonData, Controller controller) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Properties properties = new Properties();
            String studentId;
            switch (command) {
                case "addOffering": {
                    System.out.println("Adding offering");
                    Offering offering = gson.fromJson(jsonData, Offering.class);
                    controller.addOffering(offering);
                    offering.print();
                    System.out.println("Added offering");

                    break;
                }
                case "addStudent": {
                    System.out.println("Adding student");
                    Student student = gson.fromJson(jsonData, Student.class);
                    controller.addStudent(student);
                    student.print();
                    System.out.println("Added student");

                    break;
                }
                case "getOfferings":
                    System.out.println("Getting Offerings");
                    properties = gson.fromJson(jsonData, Properties.class);
                    studentId = properties.getProperty("studentId");
                    Student student = controller.getStudent(studentId);
                    controller.printOfferings();

                    break;
                case "getOffering": {
                    System.out.println("Getting Offering");
                    properties = gson.fromJson(jsonData, Properties.class);
                    studentId = properties.getProperty("studentId");
                    String offeringCode = properties.getProperty("code");
                    Student sstudent = controller.getStudent(studentId);
                    Offering offering = controller.getOffering(offeringCode);
                    String offeringDetail = gson.toJson(offering);
                    System.out.println(offeringDetail);

                    break;
                }
                case "addToWeeklySchedule": {
                    System.out.println("Adding to Weekly Schedule");
                    properties = gson.fromJson(jsonData, Properties.class);
                    studentId = properties.getProperty("studentId");
                    String code = properties.getProperty("code");
                    controller.addToWeeklySchedule(studentId, code);
                    System.out.println("Added to Weekly Schedule");

                    break;
                }
                case "removeFromWeeklySchedule": {
                    System.out.println("Removing from Weekly Schedule");
                    properties = gson.fromJson(jsonData, Properties.class);
                    studentId = properties.getProperty("studentId");
                    String code = properties.getProperty("code");
                    controller.removeFromWeeklySchedule(studentId, code);
                    System.out.println("Removed from Weekly Schedule");

                    break;
                }
                case "getWeeklySchedule": {
                    System.out.println("Getting Weekly Schedule");
                    properties = gson.fromJson(jsonData, Properties.class);
                    studentId = properties.getProperty("studentId");
                    controller.getWeeklySchedule(studentId);

                    break;
                }
                case "finalize":
                    System.out.println("Finalizing");
                    properties = gson.fromJson(jsonData, Properties.class);
                    studentId = properties.getProperty("studentId");
                    controller.finalizeWeeklySchedule(studentId);
                    controller.getWeeklySchedule(studentId);
                    System.out.println("Finalized");

                    break;
                default:
                    throw new Exception("Error: Invalid Command");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String[] parseInput(String input) {
        return input.split(" ", 2);
    }
}
