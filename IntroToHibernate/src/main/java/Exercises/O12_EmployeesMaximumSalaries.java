package Exercises;

import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;

public class O12_EmployeesMaximumSalaries {
    private static final String SELECT_QUERY = "select d from Department d ";
    private static final String PRINT_FORMAT = "%s %.2f%n";
    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.createQuery(SELECT_QUERY, Department.class)
                .getResultList()
                .forEach(department -> {
                    Double maxSalary =department.getEmployees().stream().mapToDouble(Employee::getSalary).max().getAsDouble();
                    if(maxSalary<30000 || maxSalary>70000) {
                        System.out.printf(PRINT_FORMAT,
                                department.getName(),
                                maxSalary);
                    }
                });
    }
}
