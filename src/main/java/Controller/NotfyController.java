package Controller;



import Model.ArrayTaskList;
import Model.Task;
import Model.TaskIO;
import Model.Tasks;
import com.sun.org.apache.bcel.internal.generic.DCMPG;
import org.apache.log4j.Logger;
import view.MainPanel;
import view.TaskManagerJFrame;
import view.TaskPanel;


import java.io.File;
import java.util.*;


/**
 * Created by васыль on 21.02.2016.
 */
public class NotfyController extends Thread
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
                Date date = new Date();
        for(Task task : list)
        {
          if(  task.nextTimeAfter(date).before(new Date(date.getTime()+ 60000000)));
            MainPanel.notifyText(task.toString());
            break;
        }
                System.out.println(date);

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            }
       }


