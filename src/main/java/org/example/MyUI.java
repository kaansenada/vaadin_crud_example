package org.example;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import org.example.dao.StudentDao;
import org.example.entities.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("org.example.MyAppWidgetset")
public class MyUI extends UI{
    //field components
    private final TextField fName=new TextField("First Name:");
    private final TextField lName=new TextField("Last Name:");
    private final TextField bookName=new TextField("Favourite Book Name:");

    //create button components
    private Button btnSave=new Button("Save");
    private Button btnClr=new Button("Clear");

    //Create table components
    private Table tableStudent=new Table();

    private Notification notification;

    private IndexedContainer indexedContainer;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
//        layout.setMargin(true);
        layout.setSpacing(true);

        layout.addComponent(createPageLayout());//function to initialize fields
        setContent(layout);
    }

    private Component createPageLayout() {
        VerticalLayout layout =new VerticalLayout();
        layout.setImmediate(true);
        layout.setSizeFull();

        //split panel bölen çizgiyi dinamik yapar hareket ettirilebilir.
        HorizontalSplitPanel panel= new HorizontalSplitPanel();
        panel.setImmediate(true);
        panel.setSizeFull();
        panel.setSplitPosition(30, UNITS_PERCENTAGE);
        panel.setLocked(true);
        panel.setFirstComponent(createFormFields());
        panel.setSecondComponent(createTableLayout());
        layout.addComponent(panel);
        return layout;


    }

    private Component createTableLayout() {
        tableStudent.setImmediate(true);
        tableStudent.setSizeFull();
        tableStudent.setSelectable(true);
        tableStudent.setColumnCollapsingAllowed(true);

        tableStudent.addContainerProperty("ID", Long.class, null);
        tableStudent.addContainerProperty("First Name", String.class, null);
        tableStudent.addContainerProperty("Last Name", String.class, null);
        tableStudent.addContainerProperty("Book Name", String.class, null);
//        fillTable();

        buildTableIndexedContainer();



        return tableStudent;
    }

    private void buildTableIndexedContainer(){
        indexedContainer= new IndexedContainer();
        indexedContainer.addContainerProperty("id",Long.class,null);
        indexedContainer.addContainerProperty("First Name",String.class,null);
        indexedContainer.addContainerProperty("Last Name",String.class,null);
        indexedContainer.addContainerProperty("Book Name",String.class,null);
        StudentDao studentDao=new StudentDao();
        List<Student>students=studentDao.getStudents();
        for (Student student: students){
            tableStudent.addItem(new Object[]{student.getId(),student.getfName(),student.getlName(),student.getBookName()},student.getId());
        }
    }

    private void fillTable(){
        indexedContainer.removeAllItems();
        StudentDao studentDao=new StudentDao();
        List<Student>students=studentDao.getStudents();
        for (Student student: students){
            tableStudent.addItem(new Object[]{student.getId(),student.getfName(),student.getlName(),student.getBookName()},student.getId());
        }
    }

    private Component createFormFields(){
        VerticalLayout layout=new VerticalLayout();
        layout.setImmediate(true);
        layout.setMargin(true);
        layout.setSizeFull();

        fName.setRequired(true);
        lName.setRequired(true);
        bookName.setRequired(true);

        btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
//        btnSave.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

        btnClr.addStyleName(ValoTheme.BUTTON_DANGER);
        btnClr.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//        btnSave.addClickListener((Button.ClickListener) this);
//        btnClr.addClickListener((Button.ClickListener) this);


        btnSave.addClickListener(clickEvent -> {
            Student temp=new Student();
            temp.setfName(fName.getValue());
            temp.setlName(lName.getValue());
            temp.setBookName(bookName.getValue());
            StudentDao dao=new StudentDao();
            dao.saveStudent(temp);
            System.out.println(temp.getfName());
            Notification.show("Saved!");
            fillTable();
        });




        //create layout that hold button components
        HorizontalLayout group =new HorizontalLayout();
        group.setImmediate(true);
        group.setSpacing(true);
        group.addComponents(btnSave, btnClr);

        FormLayout formLayout=new FormLayout();//layout to hold fields component
        formLayout.setImmediate(true);
        formLayout.setSizeFull();
        formLayout.addComponent(fName);
        formLayout.addComponent(lName);
        formLayout.addComponent(bookName);
        formLayout.addComponent(group);
        layout.addComponent(formLayout);

        return layout;
    }



    //Executes if click event is triggered
//    @Override
//    public void buttonClick(ClickEvent clickEvent) {
//        final Button source= clickEvent.getButton();
//        if (source==btnSave){
//
//            getNotification().show("Saved!");
//        } else if (source==btnClr) {
//            fName.setValue("");
//            lName.setValue("");
//            bookName.setValue("");
//
//            fName.focus();
//            getNotification().show("Cleared!");
//
//        }
//
//    }
//    private void refreshTable() {
//
//        tableStudent.removeAllItems();
//        tableStudent.addContainerProperty("First Name", String.class,null);
//        tableStudent.addContainerProperty("Last Name", String.class,null);
//        tableStudent.addContainerProperty("Book Name", String.class,null);
//        tableStudent.addContainerProperty("Date Created", String.class,null);
//        tableStudent.addContainerProperty("Date Last Updated", String.class,null);
//        StudentDao studentDao =new StudentDao();
//        tableStudent.set
//        setLstStudent(studentDao.getStudents());
//
//    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
