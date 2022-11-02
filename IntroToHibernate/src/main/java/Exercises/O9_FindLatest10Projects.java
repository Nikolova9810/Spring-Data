package Exercises;

import entities.Project;

import javax.persistence.EntityManager;
import java.util.Comparator;

public class O9_FindLatest10Projects {
    private static final String SELECT_QUERY = "SELECT p FROM Project p";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.createQuery(SELECT_QUERY, Project.class)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(Project::getStartDate).reversed())
                .limit(10)
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> {
                    System.out.println(
                            "Project name: " + project.getName() + "\n" +
                                    "\t" + "Project Description: " + project.getDescription() + "\n" +
                                    "\t" + "Start Date: " + project.getStartDate() + "\n" +
                                    "\t" + "End Date: " + project.getEndDate());
                });
        entityManager.close();

    }
}
