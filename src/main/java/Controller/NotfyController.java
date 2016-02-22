package Controller;



import Model.ArrayTaskList;
import Model.Task;
import Model.Tasks;


import java.util.Date;
import java.util.HashSet;


/**
 * Created by васыль on 21.02.2016.
 */
public class NotfyController extends Thread
{


    @Override
    public void run() {




            while (true) {
               // Tasks.incoming(TaskManagerController.taskList, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 10000));
                System.out.println(new Date(System.currentTimeMillis()).toString());
for(Task task : TaskManagerController.taskList)
                 task.nextTimeAfter(new Date(System.currentTimeMillis()));



                try {
                    sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            }
        }


