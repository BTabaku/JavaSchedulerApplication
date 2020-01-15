/*
 ** Author: Baftjar Tabaku
 ** created on 11/6/2019
 **
 */

package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import model.Schedule;
import model.Task;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.PublicKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

public class MainPanel extends Application {

    /*Creating a counter thread for each schedule, so no matter whenever it
     * will happen the counter will run meanwhile the application is open*/

    public void start_new_Thread_time_schedule(String task_name,String schedule_name ,String end_Time, ListView listView_completed_schedule){

        Thread timerThread = new Thread(() -> {
            String sname = schedule_name;
            String tname = task_name;
            String etime = end_Time;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            while (true) {
                try {
                    Thread.sleep(1000); //1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String time = simpleDateFormat.format(new Date());
                if (time.equals(etime)) {
                    /*Action to happen , add a new label, task or depend*/
                    Platform.runLater(() -> {
//                        lb_to_beEdited.setText(tname+" completed on "+time);
                    });
                    System.out.println("Schedule "+ sname+" of task "+tname+" completed on "+time);
                    listView_completed_schedule.getItems().add("Schedule " + sname + " of task " + tname + " completed on " + time);
                }
            }
        });
//        System.out.println("Thread "+schedule_name +" Started");
        timerThread.start();
    }


    //    Reusing node list
    public NodeList getNodelist() {
        File data_from_file = new File("C:\\Users\\baftj\\IdeaProjects\\JavaSchedulerApplication\\src\\main\\java\\model\\tasks.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = documentBuilder.parse(data_from_file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        NodeList nodeList = document.getElementsByTagName("task");

        return nodeList;

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        /* *View* */
        //labels
        Label task_Name = new Label("Tasks Names");
        Label schedule_list_lb = new Label("Schedule List for Task ");
        Label lb_message = new Label("Please select timerTask from list\nand load schedules pressing button Load");

        /*Buttons*/
        Button btn_load_scheduleList = new Button("Load Schedules");

        /*Time to display*/

//        ----------------------------------------------
        Label main_clock_lb = new Label();
        Thread timerThread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            while (true) {
                try {
                    Thread.sleep(1000); //1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final String time = simpleDateFormat.format(new Date());
                Platform.runLater(() -> {
                    main_clock_lb.setText(time);
//                    System.out.println(time);
                });
//                if (time.equals("2019/11/10 23:43:42")) {
//                    System.out.println("YES time is "+time);
//                }
            }

        });

        timerThread.start();
        /*Time display - END*/



//        Tasks
        ListView listView_Tasks = new ListView();
        ListView schedule_list = new ListView();



        /*------------------BEGIN-XML Panel Read -------------------------------*/
        try {
            /*Read from XML file*/
            NodeList nodeList = getNodelist();

            Task task = new Task();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementNode = (Element) node;

                    /*initialising the timerTask part*/
                    String task_name = elementNode.getElementsByTagName("task_name").item(0).getTextContent();
                    String task_Id = elementNode.getElementsByTagName("task_id").item(0).getTextContent();

                    task.setTask_name(task_name);
                    task.setTask_id(task_Id);


//                    For each sub-timerTask
                    for (int a = 0; a < elementNode.getElementsByTagName("schedule_name").getLength(); a++) {
                        String schedule_element = elementNode.getElementsByTagName("schedule_name").item(a).getTextContent();
//                        System.out.println(schedule_element);
                        task.add_Schedules(new Schedule(schedule_element, task_Id));
                    }
                    //            testing purpose
//                    System.out.println(timerTask.toString());
                    listView_Tasks.getItems().add(task.getTask_name());

                }
            }

            //getting sub node tasks
// System.out.println(document.getChildNodes().item(0).getChildNodes().getLength());

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*------------------END-XML Panel Read -------------------------------*/
         /*adding items for experiment*/
//        listView_Tasks.getItems().add("Element1");
        /*-------------------------*/

//        schedules
        ListView listView_Schedules = new ListView();
        listView_Schedules.setPadding(new Insets(20, 20, 20, 20));

        /*Orientation*/
//        main Horizontal Box
        HBox main_HBox = new HBox(30);
        main_HBox.setPadding(new Insets(20, 20, 20, 20));
        ;
        main_HBox.setSpacing(20.0);
        //Task part
        VBox main_Tasks_VBox = new VBox(30);
        main_Tasks_VBox.setSpacing(10.0);
//        schedules
        VBox main_schedules_VBox = new VBox(30);

        /*Buttons and messages to display*/
        VBox controller_pannel_VB = new VBox(30);

        main_Tasks_VBox.getChildren().addAll(task_Name, listView_Tasks);
        main_Tasks_VBox.setSpacing(10.0);

//        main_schedules_VBox.getChildren().addAll(main_clock);
        main_schedules_VBox.getChildren().addAll(schedule_list_lb);
        main_schedules_VBox.getChildren().addAll(schedule_list);
        main_schedules_VBox.setSpacing(10.0);

//        List of completed tasks
        ListView completed_Schedules_Lview = new ListView();
        /*Adding controller part*/
        controller_pannel_VB.getChildren().addAll(main_clock_lb, lb_message, btn_load_scheduleList,new Label("Completed Schedule list"),completed_Schedules_Lview);

        main_HBox.getChildren().addAll(main_Tasks_VBox, main_schedules_VBox, controller_pannel_VB);


        /*Buttons functions*/
        btn_load_scheduleList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                /*Re clean the list before processing */
                schedule_list.getItems().clear();

                String task_element_selected = (String) listView_Tasks.getSelectionModel().getSelectedItem();
                schedule_list_lb.setText("Schedule List for Task " + task_element_selected);

//                Test
                /*Loading schedule list content*/
                NodeList nodeList = getNodelist();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element elementNode = (Element) node;
                        String task_name = elementNode.getElementsByTagName("task_name").item(0).getTextContent();
                        if (task_name.equals(task_element_selected)) {

//                    For each sub-timerTask
                            for (int a = 0; a < elementNode.getElementsByTagName("schedule").getLength(); a++) {
                                String schedule_element = elementNode.getElementsByTagName("schedule_name").item(a).getTextContent();
                                String schedule_time = elementNode.getElementsByTagName("schedule_time").item(a).getTextContent();
                                schedule_list.getItems().addAll(schedule_element + "  " + schedule_time);

//                                Test Part initialise thread
                                /*Todo-> connect thread to edit label which can be generic or whatever it might be */
                               start_new_Thread_time_schedule(task_name,schedule_element,schedule_time, completed_Schedules_Lview);
                            }
                        }
//
//                        listView_Tasks.getItems().add(timerTask.getTask_name());
                    }
                }

            }
        });

        /*Main scene display*/
        Scene scene = new Scene(main_HBox, 900, 450);

        primaryStage.setTitle("Experimental Scheduler Application V1.0 Beta");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}

