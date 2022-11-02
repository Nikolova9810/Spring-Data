package Exercises;

import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class O8_GetEmployeeWithProject {
    private static final String SELECT_QUERY = "SELECT e FROM Employee e WHERE e.id = :id ";
    private static final String PRINT_FORMAT = "%s %s - %s%n%s";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        final Scanner sc = new Scanner(System.in);
        System.out.println(Utils.READ_ID_MESSAGE);
        final int id = Integer.parseInt(sc.nextLine());

        entityManager.createQuery(SELECT_QUERY, Employee.class)
                .setParameter("id", id)
                .getResultList()
                        .forEach(employee -> {
                            System.out.printf(PRINT_FORMAT,
                                    employee.getFirstName(),
                                    employee.getLastName(),
                                    employee.getJobTitle(),
                                    employee.getProjects().stream()
                                            .sorted(Comparator.comparing(Project::getName))
                                            .map(project -> project.getName())
                                            .collect(Collectors.joining(System.lineSeparator())));
                        });

        entityManager.close();
    }
}
