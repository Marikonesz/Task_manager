package ua.sumdu.j2se.Moroz.tasks.Controller;


import ua.sumdu.j2se.Moroz.tasks.Model.ArrayTaskList;
import ua.sumdu.j2se.Moroz.tasks.Model.Task;
import org.apache.log4j.Logger;
import ua.sumdu.j2se.Moroz.tasks.view.MainPanel;

import java.util.Date;


/**
 * the class implements a notification indicating that the beginning of the task after 1 minute
 */
public class NotifyController extends Thread {
    final static Logger logger = Logger.getLogger(NotifyController.class);
    ArrayTaskList list = new ArrayTaskList();

    /**
     * method every minute checks all tasks.if prior to the completion of a task less than 1 minute zapisyvaet task
     * in text field notify text
     */

    @Override
    public void run() {
        logger.warn("notify system started");
        Date date;
        while (true) {
            //  TaskIO.readBinary(list, new File("filetasks"));
            list = (ArrayTaskList) TaskManagerController.taskList;
            date = new Date();
            for (Task task : list) {
                if (task.nextTimeAfter(date).before(new Date(date.getTime() + 60000)) && task.nextTimeAfter(date).after(date)) {
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


