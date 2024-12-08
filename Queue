import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Node {
    String id;
    String name;
    double marks;
    String rank;
    Node next;

    public Node(String id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.rank = calculateRank();
        this.next = null;
    }

    String calculateRank() {
        if (marks < 5.0) return "Fail";
        else if (marks < 6.5) return "Medium";
        else if (marks < 7.5) return "Good";
        else if (marks < 9.0) return "Very Good";
        else return "Excellent";
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Marks: " + marks + ", Rank: " + rank;
    }
}

class CustomQueue {
    private Node front;
    private Node rear;
    private final Set<String> existingIds;  // Set to track existing IDs

    public CustomQueue() {
        this.front = this.rear = null;
        this.existingIds = new HashSet<>();
    }

    private boolean isValidName(String name) {
        // Check if the name only contains alphabets (letters).
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private boolean isValidMarks(double marks) {
        return marks >= 0 && marks <= 10;
    }

    private boolean isValidId(String id) {
        // Check if ID contains both letters and numbers
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }

    private boolean isUniqueId(String id) {
        return !existingIds.contains(id);
    }

    public void enqueue() {
        Scanner scanner = new Scanner(System.in);
        String id = "";
        String name = "";
        double marks = -1;

        while (true) {
            System.out.print("Enter Student ID (alphanumeric, unique): ");
            id = scanner.next();
            if (isValidId(id)) {
                if (isUniqueId(id)) {
                    existingIds.add(id);
                    break;
                } else {
                    System.out.println("ID already exists. Please enter a unique ID.");
                }
            } else {
                System.out.println("Invalid ID. Please enter a valid alphanumeric ID.");
            }
        }

        while (true) {
            System.out.print("Enter Student Name (letters only): ");
            name = scanner.next();
            if (isValidName(name)) {
                break;
            } else {
                System.out.println("Invalid name. Please enter only letters.");
            }
        }

        while (true) {
            System.out.print("Enter Student Marks (0 to 10): ");
            try {
                marks = scanner.nextDouble();
                if (isValidMarks(marks)) {
                    break;
                } else {
                    System.out.println("Invalid marks. Please enter a number between 0 and 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for marks.");
                scanner.nextLine(); // Clear the buffer
            }
        }

        Node newNode = new Node(id, name, marks);
        if (rear == null) {
            front = rear = newNode;
            System.out.println("Added: " + newNode);
            return;
        }
        rear.next = newNode;
        rear = newNode;
        System.out.println("Added: " + newNode);
    }

    public void enqueueMultiple(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println("\nAdding student " + (i + 1) + " of " + count);
            enqueue();
        }
    }

    public void dequeue(String id) {
        try {
            if (front == null) {
                System.out.println("No students to remove.");
                return;
            }
            if (front.id.equals(id)) {
                System.out.println("Removed: " + front);
                front = front.next;
                if (front == null) {
                    rear = null;
                }
                existingIds.remove(id); // Remove ID from the set
                return;
            }
            Node temp = front;
            while (temp.next != null && !temp.next.id.equals(id)) {
                temp = temp.next;
            }
            if (temp.next != null) {
                System.out.println("Removed: " + temp.next);
                existingIds.remove(temp.next.id); // Remove ID from the set
                temp.next = temp.next.next;
                if (temp.next == null) rear = temp;
            } else {
                System.out.println("Student with ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error during removing student: " + e.getMessage());
        }
    }

    public void search(String id) {
        try {
            // Sort the list by ID before performing binary search
            List<Node> nodes = new ArrayList<>();
            Node temp = front;
            while (temp != null) {
                nodes.add(temp);
                temp = temp.next;
            }
            nodes.sort(Comparator.comparing(node -> node.id)); // Sorting by ID

            // Binary Search
            int low = 0, high = nodes.size() - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                Node midNode = nodes.get(mid);
                if (midNode.id.equals(id)) {
                    System.out.println("Found: " + midNode);
                    return;
                } else if (midNode.id.compareTo(id) < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            System.out.println("Student with ID " + id + " not found.");
        } catch (Exception e) {
            System.out.println("Error during search: " + e.getMessage());
        }
    }

    public void edit(String id, String newId, String newName, double newMarks) {
        try {
            if (!isValidName(newName)) {
                System.out.println("Invalid name. Please enter only letters.");
                return;
            }
            if (!isValidMarks(newMarks)) {
                System.out.println("Invalid marks. Please enter a number between 0 and 10.");
                return;
            }

            Node temp = front;
            while (temp != null) {
                if (temp.id.equals(id)) {
                    // Check if new ID is valid and unique
                    if (isValidId(newId) && isUniqueId(newId)) {
                        existingIds.remove(id); // Remove old ID from set
                        existingIds.add(newId); // Add new ID to set
                        temp.id = newId;
                    } else {
                        System.out.println("Invalid or duplicate ID. No changes made.");
                        return;
                    }
                    temp.name = newName;
                    temp.marks = newMarks;
                    temp.rank = temp.calculateRank();
                    System.out.println("Updated: " + temp);
                    return;
                }
                temp = temp.next;
            }
            System.out.println("Student with ID " + id + " not found.");
        } catch (Exception e) {
            System.out.println("Error during editing student: " + e.getMessage());
        }
    }

    public void displayQueue() {
        try {
            if (front == null) {
                System.out.println("Queue is empty. No students to display.");
                return;
            }

            // Bubble sort by ID
            for (Node i = front; i != null; i = i.next) {
                for (Node j = i.next; j != null; j = j.next) {
                    if (i.id.compareTo(j.id) > 0) {
                        String tempId = i.id;
                        String tempName = i.name;
                        double tempMarks = i.marks;

                        i.id = j.id;
                        i.name = j.name;
                        i.marks = j.marks;

                        j.id = tempId;
                        j.name = tempName;
                        j.marks = tempMarks;
                    }
                }
            }

            Node temp = front;
            System.out.println("Students sorted by ID:");
            while (temp != null) {
                System.out.println(temp);
                temp = temp.next;
            }
        } catch (Exception e) {
            System.out.println("Error during displaying students: " + e.getMessage());
        }
    }

    public void sortByMarks() {
        if (front == null || front.next == null) {
            System.out.println("Queue is empty or contains only one student. No need to sort.");
            return;
        }

        List<Node> nodes = new ArrayList<>();
        Node temp = front;
        while (temp != null) {
            nodes.add(temp);
            temp = temp.next;
        }

        nodes.sort(Comparator.comparingDouble(node -> node.marks));

        System.out.println("Students sorted by Marks (ascending):");
        for (Node node : nodes) {
            System.out.println(node);
        }
    }
}

public class MainQueue {
    public static void main(String[] args) {
        CustomQueue queue = new CustomQueue();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n--- Queue-based Student Management System ---");
                System.out.println("1. Add Students");
                System.out.println("2. Remove Student");
                System.out.println("3. Search Student");
                System.out.println("4. Edit Student");
                System.out.println("5. Display All Students");
                System.out.println("6. Sort Students");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter the number of students to add: ");
                        int count = scanner.nextInt();
                        queue.enqueueMultiple(count);
                        break;
                    case 2:
                        System.out.print("Enter Student ID to remove: ");
                        String removeId = scanner.next();
                        queue.dequeue(removeId);
                        break;
                    case 3:
                        System.out.print("Enter Student ID to search: ");
                        String searchId = scanner.next();
                        queue.search(searchId);
                        break;
                    case 4:
                        System.out.print("Enter Student ID to edit: ");
                        String editId = scanner.next();
                        System.out.print("Enter new ID: ");
                        String newId = scanner.next();
                        System.out.print("Enter new Name: ");
                        String newName = scanner.next();
                        System.out.print("Enter new Marks: ");
                        double newMarks = scanner.nextDouble();
                        queue.edit(editId, newId, newName, newMarks);
                        break;
                    case 5:
                        queue.displayQueue();
                        break;
                    case 6:
                        System.out.println("Sort Options:");
                        System.out.println("1. By ID (Ascending)");
                        System.out.println("2. By Marks (Ascending)");
                        System.out.print("Choose sorting option: ");
                        int sortChoice = scanner.nextInt();
                        switch (sortChoice) {
                            case 1:
                                queue.displayQueue();
                                break;
                            case 2:
                                queue.sortByMarks();
                                break;
                            default:
                                System.out.println("Invalid sorting option.");
                                break;
                        }
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter the correct data type.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
