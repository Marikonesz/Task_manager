package Controller;


import Model.ArrayTaskList;
import Model.Task;
import Model.TaskIO;
import org.apache.log4j.Logger;
import view.MainPanel;

import java.io.File;
import java.util.Date;


/**
 * Created by васыль on 21.02.2016.
 */
public class NotifyController extends Thread {
    final static Logger logger = Logger.getLogger(NotifyController.class);
    ArrayTaskList list = new ArrayTaskList();


    @Override
    public void run() {
        logger.warn("notify system started");
        Date date;
        while (true) {
          //  TaskIO.readBinary(list, new File("filetasks"));
            list = (ArrayTaskList) TaskManagerController.taskList;
            date = new Date();
            for (Task task : list) {
                if (task.nextTimeAfter(date).before(new Date(date.getTime() + 300000)) && task.nextTimeAfter(date).after(date)) {
                    MainPanel.notifyText(task.toString());
                    System.out.println(task);
                    break;
                }

            }
            try {
                sleep(60000);
            } catch (InterruptedException e) {
               logger.error("InterruptedException in notify system");
            }


        }
    }
}


