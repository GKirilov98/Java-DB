import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) {
        //TODO: Remove the comment from other classes for current package task
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("first_code");
        EntityManager entityManager = entityManagerFactory
                .createEntityManager();
    }
}
