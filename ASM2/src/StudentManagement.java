import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class StudentManagement {
    private StudentStack students;

    public StudentManagement() {
        this.students = new StudentStack();
    }

    public void addStudent(Student student) {
        students.push(student);
    }

    public void updateStudent(int id, String newName, String newRank){
        StudentStack tempStack = new StudentStack();
        boolean found = false;

        while (!students.isEmpty()) {
            Student student = students.pop();
            if (student.getId() == id) {
                tempStack.push(new Student(id, newName, newRank));
                found = true;
            } else {
                tempStack.push(student);
            }
        }

        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }

        if (!found) {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    public void deleteStudent(int id) {
        StudentStack tempStack = new StudentStack();

        while (!students.isEmpty()) {
            Student student = students.pop();
            if (student.getId() != id) {
                tempStack.push(student);
            }
        }

        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }
    }
    public void searchStudent(int id) {
        StudentStack tempStack = new StudentStack();
        boolean found = false;

        while (!students.isEmpty()) { // Use the 'students' instance of StudentStack
            Student student = students.pop(); // Pop from 'students'
            if (student.getId() == id) { // Compare the ID
                System.out.println("Student found: " + student);
                found = true;
            }
            tempStack.push(student); // Push to tempStack
        }

        // Restore the original stack
        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop()); // Push back to 'students'
        }
        if (!found) {
            System.out.println("Student not found.");
        }
    }

    public void displayStudents() {
        StudentStack tempStack = new StudentStack();
        while (!students.isEmpty()) {
            Student student = students.pop();
            System.out.println(student);
            tempStack.push(student);
        }
        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }
    }

    // Convert stack to array
    private Student[] stackToArray() {
        StudentStack tempStack = new StudentStack();
        int size = 0;

        while (!students.isEmpty()) {
            tempStack.push(students.pop());
            size++;
        }

        Student[] array = new Student[size];
        for (int i = size - 1; i >= 0; i--) {
            array[i] = tempStack.pop();
            students.push(array[i]);
        }
        return array;
    }

    // Convert array back to stack
    private void arrayToStack(Student[] array) {
        for (Student student : array) {
            students.push(student);
        }
    }

    // Bubble Sort
    public void bubbleSort() {
        // Convert the stack to an array
        Student[] array = stackToArray();
        int n = array.length;

        // Bubble Sort algorithm
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Compare two adjacent students by their ID
                if (array[j].getId() > array[j + 1].getId()) {
                    // Swap if they are in the wrong order
                    Student temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        // After sorting, convert the array back to the stack
        arrayToStack(array);
        System.out.println("Students sorted by Bubble Sort.");
    }

    // Quick Sort Helper
    private void quickSortHelper(Student[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSortHelper(array, low, pivotIndex - 1);  // Recursively sort left part
            quickSortHelper(array, pivotIndex + 1, high); // Recursively sort right part
        }
    }

    // Partition function for Quick Sort
    private int partition(Student[] array, int low, int high) {
        int pivot = array[high].getId();  // Pivot element (last element)
        int i = low - 1;  // Index of smaller element

        for (int j = low; j < high; j++) {
            if (array[j].getId() <= pivot) {
                i++;
                // Swap array[i] and array[j]
                Student temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        // Swap array[i + 1] and array[high] (Pivot element)
        Student temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;  // Return the partition index
    }

    // Quick Sort
    public void quickSort() {
        Student[] array = stackToArray(); // Convert the stack to an array
        quickSortHelper(array, 0, array.length - 1);  // Call the quickSortHelper method
        arrayToStack(array);  // Convert the sorted array back to the stack
        System.out.println("Students sorted by Quick Sort.");
    }

    public void generateStudents(int count) {
        Random random = new Random();
        for (int i = 1; i <= count; i++) {
            int id = random.nextInt(1000) + 1; // Random ID between 1 and 1000
            String name = "Student" + id; // Generate name based on ID
            String rank = "09" + (random.nextInt(9) + 1); // Random rank
            this.addStudent(new Student(id, name, rank));
        }
        System.out.println(count + " students generated successfully.");
    }

    public void generateRandomStudents() {
        Random random = new Random();
        for (int i = 1; i <= 1000; i++) {
            int id = random.nextInt(10000) + 1; // Random ID between 1 and 10,000
            String name = "Student" + id; // Generate name based on ID
            String contact = "09" + (random.nextInt(9) + 1);
            this.addStudent(new Student(id, name, contact));
        }
        System.out.println("1000 random students generated successfully.");
    }

    // Main Method to Run the Application
    public static void main(String[] args) {
        StudentManagement management = new StudentManagement();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Search Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Sort Student (Bubble)");
            System.out.println("7. Sort Student (Quick)");
            System.out.println("8. Generate Student");
            System.out.println("9. 1000 Random Students");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Rank: ");
                    String rank = scanner.nextLine();
                    management.addStudent(new Student(id, name, rank));
                    System.out.println("Student added successfully.");
                    break;

                case 2:
                    System.out.print("Enter Student ID to Update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New rank: ");
                    String newRank = scanner.nextLine();
                    management.updateStudent(updateId, newName, newRank);
                    break;

                case 3:
                    System.out.print("Enter Student ID to Delete: ");
                    int deleteId = scanner.nextInt();
                    management.deleteStudent(deleteId);
                    System.out.println("Student deleted successfully.");
                    break;

                case 4:
                    try {
                        System.out.print("Enter ID to search: ");
                        int searchId = Integer.parseInt(scanner.nextLine()); // Parse input as an integer
                        management.searchStudent(searchId); // Use the correct instance 'management'
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format. Please enter a valid integer.");
                    }
                    break;


                case 5:
                    System.out.println("\n--- All Students ---");
                    management.displayStudents();
                    break;

                case 6:
                    management.bubbleSort();
                    break;

                case 7:
                    management.quickSort();
                    break;

                case 8:
                    System.out.print("Enter number of students to generate: ");
                    int count;
                    try {
                        count = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number. Please enter a valid integer.");
                        break;
                    }
                    management.generateStudents(count);
                    break;

                case 9:
                    management.generateRandomStudents();
                    break;

                case 10:
                    running = false;
                    System.out.println("Exiting Student Management System.");
                    break;

                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
        scanner.close();
    }
}
Error: Attempted to add a null student.
        Details: NullPointerException
Timestamp: <Current Date and Time>














