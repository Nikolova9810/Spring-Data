package Exercises;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class O4_EmployeeWithSalaryOver50000 {
    private static final String SELECT_QUERY = "SELECT e.firstName from Employee as e WHERE e.salary > 50000";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.createQuery(SELECT_QUERY, String.class)
                .getResultList()
                .forEach(System.out::println);
        entityManager.close();

    }
}
