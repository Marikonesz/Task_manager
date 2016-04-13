package ua.sumdu.j2se.Moroz.tasks.Model;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;


/**
 * class the class describes an object "Task"
 */
public class Task implements Cloneable, Serializable {
    /**
     * task description
     */
    private String title;
    /**
     * date of start repeated task
     */
    private Date start;
    /**
     * date of end repeated task
     */
    private Date end;
    /**
     * interval of repeated task
     */
    private Duration interval;
    /**
     * active task
     */
    private boolean active;
    /**
     * date of start unrepeated task
     */
    private Date time;

    /**
     * the constructor creates an object of a recurring task
     * @param title
     * @param start
     * @param end
     * @param interval
     */
    public Task(String title, Date start, Date end, Duration interval) {
        this.title = title;
        this.start = start;

        this.end = end;
        this.interval = interval;
        this.time = new Date(0);

        }

    /**
     * the constructor creates an object of a empty task
     */
    public Task() {
        this.title = "";
        this.time = new Date();
        this.start = new Date();
        this.end = new Date();
        this.interval = Duration.ofMillis(0);
    }

    /**
     * the constructor creates an object of a non-recurring task
     * @param title
     * @param time
     */
    public Task(String title, Date time) {

        this.title = title;
        this.time = time;
        this.start = new Date();
        this.end = new Date();
        this.interval = Duration.ofMillis(0);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        if (!this.isRepeated())
            return time;
        else
            return start;
    }

    public void setTime(Date time) {

        this.time = time;
        this.end = new Date(0);
        this.interval = Duration.ofMillis(0);
        this.start = time;
    }

    public void setTime(Date start, Date end, Duration interval) {
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.time = start;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Duration getInterval() {
        return interval;
    }

    public void setInterval(Duration interval) {
        this.interval = interval;
    }

    public Date getEnd() {
        if (end.getTime() == 0)
            return time;
        else
            return end;
    }

    public void setEnd(Date end) {

        this.end = end;
    }

    public Date getStart() {
        if (start.getTime() == 0)
            return time;
        else

            return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }


    public boolean isRepeated() {
        if (!interval.isZero())
            return true;
        else
            return false;

    }

    /**
     * calculates the nearest beginning of task execution time from the specified
     * @param current start time
     * @return nearest beginning of task execution time from the specified
     */
    public Date nextTimeAfter(Date current) {
        // if (active) {
        long startAfterCurrent = -1;
        if (!this.isRepeated()) {
            if (current.before(time))
                startAfterCurrent = time.getTime();
        } else {

            for (long i = start.getTime(); i <= end.getTime(); i = i + interval.toMillis()) {


                if (i >= current.getTime()) {
                    startAfterCurrent = i;
                    break;
                }


            }


        }


        return new Date(startAfterCurrent);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;


        if (title != null ? !title.equals(task.title) : task.title != null) return false;
        if (start != null ? !start.equals(task.start) : task.start != null) return false;
        if (end != null ? !end.equals(task.end) : task.end != null) return false;
        if (interval != null ? !interval.equals(task.interval) : task.interval != null) return false;
        return !(time != null ? !time.equals(task.time) : task.time != null);

    }


    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (interval != null ? interval.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;

    }


}
