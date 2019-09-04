import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Engine implements Runnable {

    private final BufferedReader reader;
    private final EntityManager entityManager;

    Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        try {
            p02_RemoveObjects();
//            p03_ContainsEmployee();
//            p04_EmployeesWithSalaryOver50000();
//            p05_EmployeesFromDepartment();
//            p06_AddingNewAddressAndUpdatingEmployee();
//            p07_AddressesWithEmployeeCount();
//            p08_GetEmployeeWithProject();
//            p09_FindLatest10Projects();
//            p10_IncreaseSalaries();
//            p11_RemoveTowns();
//            p12_FindEmployeeByFirstName();
//            p13_EmployeesMaximumSalary();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.entityManager.getTransaction().rollback();
        }
    }

    private void p13_EmployeesMaximumSalary() {
        StringBuilder sb = new StringBuilder();

        this.entityManager
                .createQuery("FROM  Employee AS e WHERE e.salary NOT BETWEEN 30000 AND 70000 GROUP BY e.department ORDER BY e.salary DESC", Employee.class)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(e -> e.getDepartment().getId()))
                .forEach(employee -> sb.append(String.format("%s - %.2f%n",
                        employee.getDepartment().getName(), employee.getSalary())));

        System.out.print(sb);
    }

    private void p12_FindEmployeeByFirstName() throws IOException {
        String pattern = reader.readLine();
        this.entityManager
                .createQuery("FROM Employee WHERE firstName LIKE :pattern", Employee.class)
                .setParameter("pattern", pattern + "%")
                .getResultList()
                .forEach(employee ->
                        System.out.printf("%s %s - %s - ($%.2f)%n",
                                employee.getFirstName(),
                                employee.getLastName(),
                                employee.getJobTitle(),
                                employee.getSalary()
                        ));
    }

    private void p11_RemoveTowns() throws IOException {
        String inputName = reader.readLine();

        List<Address> addresses = this.entityManager.createQuery("FROM Address WHERE town.name = :input", Address.class)
                .setParameter("input", inputName)
                .getResultList();

        Town town = this.entityManager.createQuery("FROM Town where name = :input_town_name", Town.class)
                .setParameter("input_town_name", inputName)
                .getSingleResult();

        String output = String.format("%d address%s in %s deleted\n",
                addresses.size(), (addresses.size() != 1) ? "es" : "", town.getName());

        this.entityManager.getTransaction().begin();
        addresses.forEach(a -> {
            for (Employee employee : a.getEmployees()) {
                employee.setAddress(null);
            }
            a.setTown(null);
            this.entityManager.remove(a);
        });

        this.entityManager.remove(town);
        this.entityManager.getTransaction().commit();

        System.out.print(output);
    }

    private void p10_IncreaseSalaries() {
        this.entityManager.getTransaction().begin();
        List<Employee> resultList = this.entityManager
                .createQuery("FROM Employee WHERE department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class).getResultList();

        StringBuilder sb = new StringBuilder();
        resultList.forEach(e -> {
            e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12)));
            sb.append(String.format("%s %s ($%.2f)\n", e.getFirstName(), e.getLastName(), e.getSalary()));
        });

        this.entityManager.getTransaction().commit();
        System.out.print(sb);
    }

    private void p09_FindLatest10Projects() {
        List<Project> projectList = this.entityManager
                .createQuery("FROM Project ORDER BY startDate DESC , name", Project.class)
                .setMaxResults(10)
                .getResultList();
        projectList.sort(Comparator.comparing(Project::getName));
        StringBuilder sb = new StringBuilder();
        projectList.forEach(p -> {
            sb.append(String.format(
                    "Project name: %s\n" +
                            " \tProject Description: %s\n" +
                            " \tProject Start Date:%s\n" +
                            " \tProject End Date: %s\n", p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate()));
        });
        System.out.print(sb);
    }

    private void p08_GetEmployeeWithProject() throws IOException {
        int inputIdEmployee = Integer.parseInt(this.reader.readLine());
        Employee employee = entityManager
                .createQuery("FROM Employee WHERE id = :id", Employee.class)
                .setParameter("id", inputIdEmployee)
                .getSingleResult();

        StringBuilder sb = new StringBuilder();
        sb.append(
                employee.getFirstName())
                .append(" ")
                .append(employee.getLastName())
                .append(" - ")
                .append(employee.getJobTitle())
                .append(System.lineSeparator());

        employee.getProjects().stream().sorted(Comparator.comparing(Project::getName))
                .forEach(p -> sb.append("\t").append(p.getName()).append(System.lineSeparator()));
        System.out.print(sb);
    }

    private void p07_AddressesWithEmployeeCount() {
        this.entityManager
                .createQuery("FROM Address ORDER BY employees.size DESC, town.id ASC", Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(a ->
                        System.out.printf("%s, %s - %d employees%n", a.getText(), a.getTown().getName(), a.getEmployees().size()));
    }

    private void p06_AddingNewAddressAndUpdatingEmployee() throws IOException {
        String lastNameEmployee = this.reader.readLine();
        Address address = addNewAddress("Sofia", "Vitoshka 15");
        updateEmployeeAddress(lastNameEmployee, address);
    }

    private void updateEmployeeAddress(String lastNameEmployee, Address address) {
        this.entityManager.getTransaction().begin();

        Employee employee = this.entityManager
                .createQuery("FROM Employee WHERE lastName = :last_name", Employee.class)
                .setParameter("last_name", lastNameEmployee)
                .getSingleResult();

        this.entityManager.detach(employee.getAddress());
        employee.setAddress(address);
        this.entityManager.merge(employee.getAddress());

        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    private Address addNewAddress(String townName, String text) {
        this.entityManager.getTransaction().begin();

        Town town = this.entityManager.createQuery("FROM Town WHERE name = :town_name", Town.class)
                .setParameter("town_name", townName)
                .getSingleResult();
        Address address = new Address();
        address.setText(text);
        address.setTown(town);
        this.entityManager.persist(address);

        this.entityManager.getTransaction().commit();
        return address;
    }

    private void p05_EmployeesFromDepartment() {
        final String departmentSearchName = "Research and Development";
        String query = "FROM Employee AS e WHERE e.department.name = :depart_name ORDER BY salary, id";
        this.entityManager.createQuery(query, Employee.class)
                .setParameter("depart_name", departmentSearchName)
                .getResultList()
                .forEach(e -> {
                    System.out.printf("%s %s from %s - $%.2f%n",
                            e.getFirstName(), e.getLastName(), departmentSearchName, e.getSalary());
                });
    }

    private void p04_EmployeesWithSalaryOver50000() {
        this.entityManager.createQuery("FROM Employee WHERE salary > 50000", Employee.class)
                .getResultList()
                .forEach(e -> System.out.println(e.getFirstName()));
    }

    private void p03_ContainsEmployee() {
        try {
            String inputFullName = reader.readLine();
            String query = "FROM Employee WHERE CONCAT(firstName, ' ', lastName) = :full_name";
            this.entityManager
                    .createQuery(query, Employee.class)
                    .setParameter("full_name", inputFullName)
                    .getSingleResult();
            System.out.println("Yes");
        } catch (NoResultException | IOException e) {
            System.out.println("No");
        }
    }

    private void p02_RemoveObjects() {
        this.entityManager.getTransaction().begin();
        List<Town> towns = this.entityManager
                .createQuery("FROM Town", Town.class)
                .getResultList();
        towns.forEach(t -> {
            if (t.getName().length() > 5) {
                this.entityManager.detach(t);
            } else {
                t.setName(t.getName().toLowerCase());
            }
        });

        this.entityManager.getTransaction().commit();
    }
}
