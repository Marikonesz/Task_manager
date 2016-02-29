package Controller;



import Model.ArrayTaskList;
import Model.Task;
import Model.Tasks;
import org.apache.log4j.Logger;
import view.TaskManagerJFrame;


import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;


/**
 * Created by васыль on 21.02.2016.
 */
public class NotfyController extends Thread implements Monitor
{
    final static Logger logger = Logger.getLogger(NotfyController.class);
    ArrayTaskList list;
    TreeMap<Long,Task> nextTasks = new TreeMap<>();
    Long nextDate;
    @Override
    public void run() {
        logger.warn("notify system started");

            while(true) {
       list = (ArrayTaskList) Tasks.incoming(TaskManagerController.taskList, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 6000000));
                System.out.println(list);
for(Task task : list) {
    System.out.println(task);
    if (task != null) {
        nextDate = task.nextTimeAfter(new Date(System.currentTimeMillis())).getTime();
        nextTasks.put(nextDate, task);

    }
}
                TaskManagerJFrame.noticatonField.setText("qwerty");
              //  nextTasks.get(nextTasks.firstKey()).getTitle()
                logger.warn("owo");
                try {
                    monitor.notify();

                    sleep(60000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            }
       }


