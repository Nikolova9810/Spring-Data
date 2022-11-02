package Exercises;

import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class O6_AddingANewAddressAndUpdatingEmployee {
    private static final String SELECT_QUERY = "SELECT e FROM Employee e WHERE e.lastName= :ln";
    private static final String ADDRESS_TEXT = "Vitoshka 15";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.getTransaction().begin();

        Address newAddress = new Address();
        newAddress.setText(ADDRESS_TEXT);
        entityManager.persist(newAddress);

        final Scanner sc = new Scanner(System.in);
        System.out.println(Utils.READ_MESSAGE);
        final String[] name = sc.nextLine().split("\\s+");
        final String last_name = name[1];

        entityManager.createQuery(SELECT_QUERY, Employee.class)
                .setParameter("ln", last_name)
                .getResultList()
                .forEach(employee -> {
                    employee.setAddress(newAddress);
                    entityManager.persist(employee);
                });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
