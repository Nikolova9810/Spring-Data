package Exercises;

import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class O2_ChangeCasing {

    private static final String SELECT_QUERY = "select t from Town t";


    public static void main(String[] args) {
     final    EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        List<Town> townsResultList = entityManager.createQuery(SELECT_QUERY, Town.class).getResultList();
        for (Town t : townsResultList) {
            final String townName = t.getName();
            if (townName.length() < 5) {
                t.setName(townName.toUpperCase());
                entityManager.persist(t);
            }
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
