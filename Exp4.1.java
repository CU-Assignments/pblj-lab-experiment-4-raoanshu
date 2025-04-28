import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

// Class to represent an Employee
class Employee {
    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }

    // Override equals and hashCode methods for proper comparison and usage in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

// Class to manage Employee records
public class EmployeeManagement {
    private List<Employee> employees;
    private Scanner scanner;

    public EmployeeManagement() {
        this.employees = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    // Method to add a new employee
    public void addEmployee() {
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Check if employee ID already exists
        if (findEmployee(id) != null) {
            System.out.println("Error: Employee with ID " + id + " already exists.");
            return;
        }

        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter employee salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        employees.add(new Employee(id, name, salary));
        System.out.println("Employee added successfully.");
    }

    // Method to update an existing employee's details
    public void updateEmployee() {
        System.out.print("Enter ID of employee to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Employee employee = findEmployee(id);
        if (employee == null) {
            System.out.println("Error: Employee with ID " + id + " not found.");
            return;
        }

        System.out.print("Enter new name (current: " + employee.getName() + "): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            employee.setName(newName);
        }

        System.out.print("Enter new salary (current: " + employee.getSalary() + "): ");
        String salaryInput = scanner.nextLine();
        if (!salaryInput.isEmpty()) {
            try {
                double newSalary = Double.parseDouble(salaryInput);
                employee.setSalary(newSalary);
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary format.  Keeping current salary.");
            }
        }

        System.out.println("Employee updated successfully.");
    }

    // Method to remove an employee
    public void removeEmployee() {
        System.out.print("Enter ID of employee to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Employee employee = findEmployee(id);
        if (employee == null) {
            System.out.println("Error: Employee with ID " + id + " not found.");
            return;
        }

        employees.remove(employee); // Use the overridden equals method
        System.out.println("Employee removed successfully.");
    }

    // Method to search for an employee by ID
    public void searchEmployee() {
        System.out.print("Enter ID of employee to search: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Employee employee = findEmployee(id);
        if (employee == null) {
            System.out.println("Employee with ID " + id + " not found.");
        } else {
            System.out.println("Employee found: " + employee);
        }
    }

    // Helper method to find an employee by ID
    private Employee findEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    // Method to display all employees
    public void displayAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
            return;
        }
        System.out.println("All employees:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public static void main(String[] args) {
        EmployeeManagement management = new EmployeeManagement();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nEmployee Management System Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Remove Employee");
            System.out.println("4. Search Employee");
            System.out.println("5. Display All Employees");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    management.addEmployee();
                    break;
                case 2:
                    management.updateEmployee();
                    break;
                case 3:
                    management.removeEmployee();
                    break;
                case 4:
                    management.searchEmployee();
                    break;
                case 5:
                    management.displayAllEmployees();
                    break;
                case 0:
                    System.out.println("Exiting system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}

