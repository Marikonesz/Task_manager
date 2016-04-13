package ua.sumdu.j2se.Moroz.tasks.Model;


import java.util.*;


/**
 * utils class for working with tasks
 */
public class Tasks {


    /**
     *selects the task from the list that begin with d given period of time
     * @param taskList
     * @param from time of begin
     * @param to time of end
     * @return
     */
    public static Iterable<Task> incoming(Iterable<Task> taskList, Date from, Date to) {

        ArrayList arrayTask = new ArrayList();

        Iterator it = taskList.iterator();
        while (it.hasNext()) {
            Task task = (Task) it.next();
            if ((task != null) && (task.nextTimeAfter(from) != null) && (task.nextTimeAfter(from).before(to)) && (task.nextTimeAfter(from).getTime() >= from.getTime())) {
                arrayTask.add(task);
            }
        }
        return arrayTask;
    }


    /**
     * create a map of tasks. time of begin task as a key, list of tasks as value
     * @param tasks the list from which tasks are selected
     * @param start time of begin
     * @param end time of end
     * @return sorted map of tasks
     */
    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) {
        SortedMap<Date, Set<Task>> calendarMap = new TreeMap<Date, Set<Task>>();

        ArrayList<Task> arrayTask = (ArrayList) incoming(tasks, start, end);

        for (Task iter : arrayTask) {
            if (calendarMap.get(iter.nextTimeAfter(start)) != null) {
                Set<Task> setTask = calendarMap.get(iter.nextTimeAfter(start));
                setTask.add(iter);
                calendarMap.put(iter.nextTimeAfter(start), setTask);
            } else {
                Set<Task> setTask = new HashSet<Task>();
                setTask.add(iter);
                calendarMap.put(iter.nextTimeAfter(start), setTask);
            }
        }
        return calendarMap;
    }
}



