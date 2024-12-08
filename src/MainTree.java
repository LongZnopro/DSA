import java.util.InputMismatchException;
import java.util.Scanner;

final class TreeNode {
    String id;
    String name;
    double marks;
    String rank;
    TreeNode left, right;

    public TreeNode(String id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.rank = calculateRank();
        this.left = this.right = null;
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

class BinarySearchTree {
    private TreeNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(String id, String name, double marks) {
        root = insertRec(root, id, name, marks);
    }

    private TreeNode insertRec(TreeNode root, String id, String name, double marks) {
        if (root == null) {
            root = new TreeNode(id, name, marks);
            System.out.println("Inserted: " + root);
            return root;
        }
        if (id.compareTo(root.id) < 0) {
            root.left = insertRec(root.left, id, name, marks);
        } else if (id.compareTo(root.id) > 0) {
            root.right = insertRec(root.right, id, name, marks);
        }
        return root;
    }

    public void delete(String id) {
        root = deleteRec(root, id);
    }

    private TreeNode deleteRec(TreeNode root, String id) {
        if (root == null) {
            System.out.println("Student with ID " + id + " not found.");
            return root;
        }
        
        if (id.compareTo(root.id) < 0) {
            root.left = deleteRec(root.left, id);
        } else if (id.compareTo(root.id) > 0) {
            root.right = deleteRec(root.right, id);
        } else {
            if (root.left == null) {
                System.out.println("Deleted: " + root);
                return root.right;
            } else if (root.right == null) {
                System.out.println("Deleted: " + root);
                return root.left;
            }

            TreeNode minNode = minValueNode(root.right);
            root.id = minNode.id;
            root.name = minNode.name;
            root.marks = minNode.marks;
            root.rank = minNode.rank;

            root.right = deleteRec(root.right, minNode.id);
        }
        return root;
    }

    private TreeNode minValueNode(TreeNode node) {
        TreeNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void edit(String id, String newId, String newName, double newMarks) {
        TreeNode node = searchRec(root, id);
        if (node != null) {
            node.id = newId;
            node.name = newName;
            node.marks = newMarks;
            node.rank = node.calculateRank();
            System.out.println("Updated: " + node);
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(TreeNode root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.println(root);
            inOrderRec(root.right);
        }
    }

    public void search(String id) {
        TreeNode result = searchRec(root, id);
        if (result != null) {
            System.out.println("Found: " + result);
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    private TreeNode searchRec(TreeNode root, String id) {
        if (root == null || root.id.equals(id)) {
            return root;
        }
        if (id.compareTo(root.id) < 0) {
            return searchRec(root.left, id);
        }
        return searchRec(root.right, id);
    }

    // Check valid data
    public boolean isValidName(String name) {
        return name.matches("[a-zA-Z ]+");  // Name contains only letters and spaces
    }

    public boolean isValidMarks(String marks) {
        try {
            Double.valueOf(marks);  // Check if marks is a valid number
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

public class MainTree {
    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n--- Tree-based Student Management System ---");
                System.out.println("1. Add Students");
                System.out.println("2. Search Student");
                System.out.println("3. Edit Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Display All Students (In-order Traversal)");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter the number of students to add: ");
                        int numStudents = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character after nextInt()
                        for (int i = 0; i < numStudents; i++) {
                            System.out.print("Enter Student ID: ");
                            String id = scanner.next();
                            scanner.nextLine();  // Consume newline character after next()

                            // Check student name
                            String name;
                            while (true) {
                                System.out.print("Enter Student Name: ");
                                name = scanner.nextLine();
                                if (tree.isValidName(name)) {
                                    break;
                                } else {
                                    System.out.println("Invalid name. Please enter only alphabetic characters.");
                                }
                            }

                            // Check marks
                            double marks = 0;
                            while (true) {
                                System.out.print("Enter Student Marks: ");
                                String marksInput = scanner.next();
                                if (tree.isValidMarks(marksInput)) {
                                    marks = Double.parseDouble(marksInput);
                                    break;
                                } else {
                                    System.out.println("Invalid marks. Please enter a valid number.");
                                }
                            }
                            tree.insert(id, name, marks);
                        }
                        break;
                    case 2:
                        System.out.print("Enter Student ID to search: ");
                        String searchId = scanner.next();
                        tree.search(searchId);
                        break;
                    case 3:
                        System.out.print("Enter Student ID to edit: ");
                        String editId = scanner.next();
                        System.out.print("Enter new ID: ");
                        String newId = scanner.next();
                        
                        // Check new name
                        String newName;
                        scanner.nextLine();  // Consume newline character after next()
                        while (true) {
                            System.out.print("Enter new Name: ");
                            newName = scanner.nextLine();
                            if (tree.isValidName(newName)) {
                                break;
                            } else {
                                System.out.println("Invalid name. Please enter only alphabetic characters.");
                            }
                        }
                        
                        // Check new marks
                        double newMarks = 0;
                        while (true) {
                            System.out.print("Enter new Marks: ");
                            String marksInput = scanner.next();
                            if (tree.isValidMarks(marksInput)) {
                                newMarks = Double.parseDouble(marksInput);
                                break;
                            } else {
                                System.out.println("Invalid marks. Please enter a valid number.");
                            }
                        }
                        
                        tree.edit(editId, newId, newName, newMarks);
                        break;
                    case 4:
                        System.out.print("Enter Student ID to delete: ");
                        String deleteId = scanner.next();
                        tree.delete(deleteId);
                        break;
                    case 5:
                        System.out.println("In-order traversal of students:");
                        tree.inOrder();
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
                scanner.nextLine();  // Clear the invalid input
            } catch (NumberFormatException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
