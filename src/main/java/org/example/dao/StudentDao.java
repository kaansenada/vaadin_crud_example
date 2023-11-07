package org.example.dao;

import org.example.entities.Student;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.util.List;

public class StudentDao {
    //save
    //getall
    //getbyid
    //update
    //delete
    @Transactional
    public void saveStudent(Student student){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(student);
        transaction.commit();
        session.close();
    }
    public void updateStudent(Student student){
        Transaction transaction=null;
        try(Session session= HibernateUtil.getSessionFactory().openSession()){
            //starting transaction
            transaction=session.beginTransaction();
            session.saveOrUpdate(student);
            //commit transaction
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    public Student getStudentById(Long id){
        Transaction transaction=null;
        Student student=null;
        try(Session session= HibernateUtil.getSessionFactory().openSession()){
            //starting transaction
            transaction=session.beginTransaction();
            student=session.get(Student.class,id);

            //commit transaction
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return student;
    }
    public List<Student> getStudents(){
        Transaction transaction=null;
        List<Student> students=null;
        try(Session session= HibernateUtil.getSessionFactory().openSession()){
            //starting transaction
            transaction=session.beginTransaction();

            students=session.createQuery("from Student").list();

            //commit transaction
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return students;
    }
    public Student deleteStudent(Long id){
        Transaction transaction=null;
        Student student=null;
        try(Session session= HibernateUtil.getSessionFactory().openSession()){
            //starting transaction
            transaction=session.beginTransaction();
            student=session.get(Student.class,id);
            session.delete(student);

            //commit transaction
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return student;
    }
}
