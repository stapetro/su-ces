package bg.sofia.uni.fmi.ces.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import bg.sofia.uni.fmi.ces.model.user.Role;
import bg.sofia.uni.fmi.ces.model.user.User;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String users = getUsers();
    	System.out.println(users);
    }
    
    public static void testAddUserRoles() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("sucesPU");
        EntityManager entityMgr = emFactory.createEntityManager();
        EntityTransaction transaction = entityMgr.getTransaction();
        transaction.begin();
        
        User user = new User("kbailov@uni-sofia.bg", "test");
        User user1 = new User("stanislavp@uni-sofia.bg", "test");
        entityMgr.persist(user);
        entityMgr.persist(user1);
        
        Role adminRole = new Role("admin", "Admin role");
        Role studentRole = new Role("student", "Student role");
        adminRole.addUser(user1);
        studentRole.addUser(user);
        studentRole.addUser(user1);      
        entityMgr.persist(adminRole);
        entityMgr.persist(studentRole);
        
        entityMgr.flush();
                
        transaction.commit();
        entityMgr.close();
        emFactory.close();    	
    }
    
    public static String getUsers() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("sucesPU");
        EntityManager entityMgr = emFactory.createEntityManager();
        List<User> users = (List<User>) entityMgr.createQuery("SELECT u FROM User u").getResultList();
        StringBuilder output = new StringBuilder("------ Suces Users ------<br/>");
        for(User user : users) {
        	String email = user.getEmail();
        	List<Role> roles = user.getRoles();
        	output.append(String.format("{\"user\": \"%s\", \"roles\": \"%s\"}<br/>", 
        			email, Arrays.toString(roles.toArray())));
        }
        entityMgr.close();
        emFactory.close();      	
        output.append("----------------------<br/>");
    	return output.toString();
    }
    
    public static String hello() {
    	return "Hello from model";
    }
}
