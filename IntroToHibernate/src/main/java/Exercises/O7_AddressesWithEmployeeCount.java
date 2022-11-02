package Exercises;

import entities.Address;

import javax.persistence.EntityManager;

public class O7_AddressesWithEmployeeCount {
    private static final String SELECT_QUERY = "SELECT a FROM Address a ORDER BY a.employees.size desc ";
    private static final String PRINT_FORMAT = "%s, %s - %d employees%n";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();

        entityManager.createQuery(SELECT_QUERY, Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(address -> {
                    System.out.printf(PRINT_FORMAT,
                            address.getText(),
                            address.getTown().getName(),
                            address.getEmployees().size());
                });
        entityManager.close();

    }
}
