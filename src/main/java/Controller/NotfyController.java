package Controller;



import Model.ArrayTaskList;
import Model.Task;
import Model.TaskIO;
import Model.Tasks;
import com.sun.org.apache.bcel.internal.generic.DCMPG;
import org.apache.log4j.Logger;
import view.TaskManagerJFrame;


import java.io.File;
import java.util.*;


/**
 * Created by васыль on 21.02.2016.
 */
public class NotfyController extends Thread implements Monitor
{
    final static Logger logger = Logger.getLogger(NotfyController.class);
    ArrayTaskList list = new ArrayTaskList();
    TreeMap<Long,Task> nextTasks = new TreeMap<>();
    Long nextDate;
    @Override
    public void run() {
        logger.warn("notify system started");

            while(true) {
                TaskIO.readBinary(list, new File("filetasks"));
  //       ArrayList<Task> incomingTasks = (ArrayList) Tasks.incoming(TaskManagerController.taskList, new Date(), new Date(System.currentTimeMillis() + 6000000));
                System.out.println("");

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            }
       }


