/*
 ** Author: Baftjar Tabaku
 ** created on 11/7/2019 inside the package
 **
 */

package model;
/*TODO implement a functional date on xml and load it properly on corresponding classes and load it on list view , for time you can work even in seconds, or depend converting the string as a date based array*/

public class Schedule {
    private String schedule_name;
    private String Schedule_time, schedule_id, task_Id, end_time;

    public Schedule() {
    }

//    for the moment


    public Schedule(String schedule_name, String task_Id) {
        this.schedule_name = schedule_name;
        this.task_Id = task_Id;
    }

    public Schedule(String schedule_name, String schedule_time, String schedule_id, String task_Id, String end_time) {
        this.schedule_name = schedule_name;
        Schedule_time = schedule_time;
        this.schedule_id = schedule_id;
        this.task_Id = task_Id;
        this.end_time = end_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public String getSchedule_time() {
        return Schedule_time;
    }

    public void setSchedule_time(String schedule_time) {
        Schedule_time = schedule_time;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getTask_Id() {
        return task_Id;
    }

    public void setTask_Id(String task_Id) {
        this.task_Id = task_Id;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "schedule_name='" + schedule_name + '\'' +
                ", Schedule_time='" + Schedule_time + '\'' +
                ", schedule_id='" + schedule_id + '\'' +
                ", task_Id='" + task_Id + '\'' +
                '}';
    }

    /*Print->Testing*/

}

