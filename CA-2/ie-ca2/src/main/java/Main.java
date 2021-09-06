import InterfaceServer.InterfaceServer;

public class Main {
    public static void main(String[] args) {
        final String COURSES_URI = "http://138.197.181.131:5000/api/courses";
        final String STUDENTS_URI = "http://138.197.181.131:5000/api/students";
        final String GRADES_URI = "http://138.197.181.131:5000/api/grades";

        final int PORT = 5000;
        InterfaceServer interfaceServer = new InterfaceServer();
        interfaceServer.start(COURSES_URI, STUDENTS_URI, GRADES_URI, PORT);
    }
}
