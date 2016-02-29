package Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by васыль on 20.11.2015.
 */
public class Tasks {
// public    static Iterable<Task> incoming( Iterable<Task> tasks, Date start, Date end) {
//        int incomingSize = 0;
//        final int stop;
//        final Date startFinal = start;
//        final Date endFinal = end;
//        final Iterable<Task> taskFinal = tasks;
//
//        for (Task task : tasks) {
//            if (task.nextTimeAfter(start).getTime() != -1 && task.nextTimeAfter(start).getTime() <= end.getTime()) {
//                incomingSize++;
//
//            }
//
//        }
//        stop = incomingSize;
//        final Iterable<Task> incomingTasks = new Iterable<Task>() {
//            @Override
//            public Iterator<Task> iterator() {
//                return new Iterator<Task>() {
//                    int cursor = 0;
//                    int counter;
//
//                    @Override
//                    public boolean hasNext() {
//
//                        return cursor < stop;
//                    }
//
//                    @Override
//                    public Task next() {
//                        Task returnedTask = null;
//                        for (Task task : taskFinal) {
//                            if (task.nextTimeAfter(startFinal).getTime() != -1 && task.nextTimeAfter(startFinal).getTime() <= endFinal.getTime())
//                                returnedTask = task;
//                            counter++;
//                            if (counter > cursor)
//                                break;
//                        }
//                        counter = 0;
//                        cursor++;
//                        return returnedTask;
//                    }
//                    @Override
//                    public void remove() {
//
//                    }
//                };
//            }
//        };
//
//        return  incomingTasks;
//
//   }





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




//      public static SortedMap<Date, Set<Task>> calendar (Iterable < Task > tasks, Date start, Date end){
//          Date forKey = null;
//          TreeMap<Date, Set<Task>> calendar = new TreeMap<>();
//SimpleDateFormat formatKey = new SimpleDateFormat();
//          formatKey.applyPattern("dd.MM.yyyy");
//           HashMap<String,Date> days = new HashMap();
//          String day;
//          for (Task task : Tasks.incoming(tasks, start, end)) {
//              if (task != null) {
//               day = formatKey.format(new Date(task.getTime().getTime()));
//                if (days.containsKey(day)) {
//                    System.out.println(calendar.values());
//                    System.out.println(days.keySet());
//                    System.out.println(days.values());
//                    System.out.println(day);
//                    System.out.println(task.getTime());
//
//
//                    calendar.get(days.get(day)).add(task);
//
//                }
//
//                  else
//                  forKey = new Date(task.getTime().getTime());
//                  days.put(day, new Date(task.getTime().getTime()));
//                  calendar.put(forKey, new LinkedHashSet<Task>());
//                  calendar.get(forKey).add(task);
//
//              }
//
//          }
//
//          return calendar;
//      }




public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end){
    SortedMap<Date, Set<Task>> calendarMap = new TreeMap<Date, Set<Task>>();

    ArrayList<Task> arrayTask = (ArrayList) incoming(tasks, start, end);

    for(Task iter: arrayTask){
        if (calendarMap.get(iter.nextTimeAfter(start)) != null){
            Set<Task> setTask = calendarMap.get(iter.nextTimeAfter(start));
            setTask.add(iter);
            calendarMap.put(iter.nextTimeAfter(start), setTask);
        }else{
            Set<Task> setTask = new HashSet<Task>();
            setTask.add(iter);
            calendarMap.put(iter.nextTimeAfter(start), setTask);
        }
    }
    return  calendarMap;
}
  }



