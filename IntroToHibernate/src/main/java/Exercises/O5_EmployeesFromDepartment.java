package Exercises;

import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class O5_EmployeesFromDepartment {
    private static final String SELECT_QUERY = "SELECT e from Employee as e WHERE e.department.name = :dep ORDER BY e.salary,e.id ";

    private static final String DEPARTMENT = "Research and Development";
    private static final String PRINT_FORMAT = "%s %s from %s - $%.2f%n";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();

        entityManager.createQuery(SELECT_QUERY, Employee.class)
                .setParameter("dep", DEPARTMENT)
                .getResultList()
                .forEach(employee -> System.out.printf(PRINT_FORMAT,
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getDepartment().getName(),
                        employee.getSalary()));

        entityManager.close();
    }
}

