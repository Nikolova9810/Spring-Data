package Exercises;

import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class O10_IncreaseSalaries {
    private static final String SELECT_QUERY = "select e from Employee e where e.department.name in (:dps)";
    private static final String PRINT_FORMAT = "%s %s ($%.2f)%n";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        final List<String> departmentsList = Arrays.asList("Engineering", "Tool Design", "Marketing", "Information Services");
        entityManager.getTransaction().begin();
        entityManager.createQuery(SELECT_QUERY, Employee.class)
                .setParameter("dps", departmentsList)
                .getResultList()
                .forEach(employee -> {
                    Double increasedSalary = employee.getSalary() * 1.12;
                    employee.setSalary(increasedSalary);
                    entityManager.persist(employee);
                    System.out.printf(PRINT_FORMAT,
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getSalary());
                });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
