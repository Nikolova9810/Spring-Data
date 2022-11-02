package Exercises;

import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.Scanner;

public class O11_FindEmployeesByFirstName {
    private static final String SELECT_QUERY = "select e from Employee e WHERE e.firstName like CONCAT(:pa,'%') ";
    private static final String PRINT_FORMAT = "%s %s - %s - ($%.2f)%n";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        final Scanner sc = new Scanner(System.in);
        System.out.println(Utils.READ_PATTERN);
        final String pattern = sc.nextLine();
        entityManager.createQuery(SELECT_QUERY, Employee.class)
                .setParameter("pa",pattern)
                .getResultList()
                .stream().sorted(Comparator.comparing(Employee::getFirstName).reversed())
                .forEach(employee -> {
                    System.out.printf(PRINT_FORMAT,
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getJobTitle(),
                            employee.getSalary());
                });
        entityManager.close();
    }
}
