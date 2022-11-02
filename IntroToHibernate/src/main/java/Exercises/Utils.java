package Exercises;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

enum Utils {
    ;
    public static final String DATABASE_NAME = "soft_uni";
    public static final String READ_MESSAGE = "Enter employee name: ";
    public static final String READ_ID_MESSAGE = "Enter id: ";
    public static final String READ_PATTERN = "Enter pattern: ";
    public static final String READ_TOWN = "Enter town name: ";

   public static EntityManager createEntityManager(){
        return Persistence.createEntityManagerFactory(Utils.DATABASE_NAME)
                .createEntityManager();
    }


}
