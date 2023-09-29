import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students;

    public StudentManagementSystem() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
    }

    public Student findStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void saveDataToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Student student : students) {
                writer.write(student.getName() + "," + student.getRollNumber() + "," + student.getGrade() + "\n");
            }
        }
    }

    public void loadDataFromFile(String filename) throws IOException {
        students.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int rollNumber = Integer.parseInt(parts[1]);
                    String grade = parts[2];
                    students.add(new Student(name, rollNumber, grade));
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);

        try {
            system.loadDataFromFile("students.txt");
            System.out.println("Data loaded from file.");
        } catch (IOException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }

        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search for Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Save Data to File");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Roll Number: ");
                    int rollNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Grade: ");
                    String grade = scanner.nextLine();

                    if (!name.isEmpty() && !grade.isEmpty()) {
                        system.addStudent(new Student(name, rollNumber, grade));
                        System.out.println("Student added successfully.");
                    } else {
                        System.out.println("Name and Grade fields cannot be empty.");
                    }
                    break;
                case 2:
                    System.out.print("Enter Roll Number to Remove: ");
                    int removeRollNumber = scanner.nextInt();
                    system.removeStudent(removeRollNumber);
                    System.out.println("Student removed successfully.");
                    break;
                case 3:
                    System.out.print("Enter Roll Number to Search: ");
                    int searchRollNumber = scanner.nextInt();
                    Student foundStudent = system.findStudent(searchRollNumber);
                    if (foundStudent != null) {
                        System.out.println("Student found:\n" + foundStudent);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:
                    List<Student> allStudents = system.getAllStudents();
                    if (allStudents.isEmpty()) {
                        System.out.println("No students in the system.");
                    } else {
                        System.out.println("All Students:");
                        for (Student student : allStudents) {
                            System.out.println(student);
                        }
                    }
                    break;
                case 5:
                    try {
                        system.saveDataToFile("students.txt");
                        System.out.println("Data saved to file.");
                    } catch (IOException e) {
                        System.err.println("Error saving data to file: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
