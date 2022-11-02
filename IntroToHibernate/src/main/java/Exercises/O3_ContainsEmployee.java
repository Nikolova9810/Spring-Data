package Exercises;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;
import java.util.stream.Stream;

public class O3_ContainsEmployee {
    private static final String SELECT_QUERY = "SELECT count (e) FROM Employee e WHERE e.firstName= :fn AND e.lastName = :ln";

    private static final String NO_MESSAGE = "No";
    private static final String YES_MESSAGE = "Yes";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        final Scanner sc = new Scanner(System.in);
        System.out.println(Utils.READ_MESSAGE);

        final String[] name = sc.nextLine().split("\\s+");

        final String first_name = name[0];
        final String last_name = name[1];

        if (entityManager.createQuery(SELECT_QUERY, Long.class)
                .setParameter("fn", first_name)
                .setParameter("ln", last_name)
                .getSingleResult() > 0) {
            System.out.println(YES_MESSAGE);
        } else {
            System.out.println(NO_MESSAGE);
        }
        entityManager.close();
    }
}
