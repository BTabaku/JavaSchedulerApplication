/*
 ** Author: Baftjar Tabaku
 ** created on 11/7/2019
 **
 */

/*TODO -> Task 2 connect string date with gui*/
package model;

import java.util.Arrays;

public class Task {

    private String task_name, task_id;
    /*it can also be a dynamic list*/
    private Schedule[] task_schedules;
    private int counter;

    public Task() {
        counter = 0;
        task_schedules = new Schedule[10];
    }

    public Task(String task_name, String task_id, Schedule[] task_schedules) {
        this.task_name = task_name;
        this.task_id = task_id;
        this.task_schedules = task_schedules;
        task_schedules = new Schedule[10];
        counter = 0;
    }


    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public Schedule[] getTask_schedules() {
        return task_schedules;
    }

    public void setTask_schedules(Schedule[] task_schedules) {
        this.task_schedules = task_schedules;
    }

    public void add_Schedules(Schedule schedule) {
        task_schedules[counter++] = schedule;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    //no need for a removing method since it is not necessary and database is predefined


    @Override
    public String toString() {
        return "Task{" +
                "task_name='" + task_name + '\'' +
                ", task_id='" + task_id + '\'' +
                ", task_schedules=" + Arrays.toString(task_schedules) +
                ", counter=" + counter +
                '}';
    }

    public void printAllScheduleArray() {
        for (int a = 0; a <= task_schedules.length; a++) {
            System.out.println(task_schedules[a].getSchedule_name());
        }
    }
}

