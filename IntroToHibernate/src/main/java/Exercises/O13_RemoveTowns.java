package Exercises;

import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class O13_RemoveTowns {
    private static final String SELECT_QUERY_TOWN = "select t from Town t where t.name =:tn ";
    private static final String SELECT_QUERY_ADDRESS = "select a from Address a where a.town =:town ";
    private static final String PRINT_FORMAT = "%d address in %s deleted";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        final Scanner sc = new Scanner(System.in);
        System.out.println(Utils.READ_TOWN);
        final String townName = sc.nextLine();
        try {
            entityManager.getTransaction().begin();

            Town town = entityManager.createQuery(SELECT_QUERY_TOWN, Town.class)
                    .setParameter("tn", townName)
                    .getSingleResult();

            List<Address> addressLists = entityManager.createQuery(SELECT_QUERY_ADDRESS, Address.class)
                    .setParameter("town", town)
                    .getResultList();

            addressLists.forEach(address -> {
                for (Employee e : address.getEmployees()) {
                    e.setAddress(null);
                }
                address.setTown(null);
                entityManager.remove(address);
            });

            entityManager.remove(town);

            entityManager.getTransaction().commit();

            System.out.printf(PRINT_FORMAT, addressLists.size(), townName);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        entityManager.close();
    }
}
