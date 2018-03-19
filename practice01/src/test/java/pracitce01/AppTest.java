package pracitce01;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wp on 2018/3/19.
 */
public class AppTest {
    SessionFactory factory;

    @BeforeSuite
    public void setup() {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
        srBuilder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
        factory = configuration.buildSessionFactory(serviceRegistry);
    }

    @Test
    public void saveStudent() {
        Map student = new HashMap();
        student.put("id", 1);
        student.put("firstName", "f");
        student.put("lastName", "l");
        student.put("grade", 5);

        GoodStudent goodStudent=new GoodStudent();
        goodStudent.setId(1);
        goodStudent.setFirst_name("f");
        goodStudent.setLast_name("l");
        goodStudent.setGrade(1);

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        //session.save("Student", goodStudent); // error
        session.save("Student", student);
        tx.commit();
        session.close();
    }

    @Test
    public void saveComputer() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        for (int i = 1; i < 3; i++) {
            Map computer = new HashMap();
            computer.put("id", i);
            computer.put("os", "mac os");
            computer.put("cpu", "i73770");
            computer.put("monitor", "p2415");
            session.save("Computer", computer);
        }

        tx.commit();
        session.close();
    }


    @Test(dependsOnMethods = "saveStudent")
    public void findStudents() {
        Session session = factory.openSession();
        //@SuppressWarnings("unchecked")
        List<Map<String,Object>> list = (List<Map<String,Object>>) session.createQuery("from Student").list();

        System.out.println(list.size());
        Map s=list.get(0);
        assert Integer.valueOf(s.get("id").toString()) == 1;

        session.close();
    }

    @Test(dependsOnMethods = {"saveStudent","saveComputer"})
    public void findStudentsAndcomuters() {
        Session session = factory.openSession();
        //@SuppressWarnings("unchecked")
        List list = (List) session.createQuery("from Student,Computer").list();

        for(Iterator it=list.iterator();it.hasNext();) {
            Object[] objs = (Object[]) it.next();
            System.out.println(objs[0]);
            System.out.println(objs[1]);
        }

        session.close();
    }
}
