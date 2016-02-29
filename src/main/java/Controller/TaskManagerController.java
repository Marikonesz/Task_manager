package Controller;



import Model.*;
import view.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.File;
import java.time.Duration;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Created by васыль on 02.02.2016.
 */

public class TaskManagerController  implements Monitor {
    final static Logger logger = Logger.getLogger(TaskManagerController.class);

    static TaskManagerJFrame mainWindow = new TaskManagerJFrame();
    public static TaskList taskList = new ArrayTaskList();
    public static SortedMap<Date, Set<Task>> onWeek = new TreeMap();
    public static Task task;
    public  static boolean repeatedTask;
    public static DefaultListModel<Task> model;
    public static DefaultListModel<Task> calendarModel;


    public static void main(String[] args) throws InterruptedException {


//        ArrayTaskList k = new ArrayTaskList();
//        for (int i = 0; i < 60; i++) {
//            k.add(new Task("task " + i, new Date(System.currentTimeMillis() + 110100000L * i)));
//        }
//        TaskIO.writeBinary(k, new File("filetasks"));


        NotfyController notfy = new NotfyController();
        TaskIO.readBinary(taskList, new File("filetasks"));
        task = taskList.getTask(0);
        repeatedTask = task.isRepeated();
        logger.warn("taskManager Started");
        modelCreater();
        modelCalendarCreater();

        mainWindow.paintPanel(new MainPanel());
        notfy.start();


    }

    public class TaskListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            task = (Task) AllTaskListPanel.allTasksList.getSelectedValue();
repeatedTask = task.isRepeated();
            mainWindow.paintPanel(new MainPanel());

        }
    }
    public class CalendarSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            TaskManagerController.task = (Task) CalendarPanel.CalendarList.getSelectedValue();
MainPanel.setFistList(true);
            mainWindow.paintPanel(new MainPanel());
            MainPanel.setFistList(false);
        }
    }
    public class TaskActiveListener implements ItemListener {
        boolean onOff;
        int selected;

        @Override
        public void itemStateChanged(ItemEvent e) {
            selected = e.getStateChange();
            if (selected == 1)
                onOff = true;
            else
                onOff = false;


            activeChanger(onOff);
            task.setActive(onOff);
            for (int i = 0; i < taskList.size(); i++) {
                if (taskList.getTask(i).equals(task)) {

                    taskList.getTask(i).setActive(TaskPanel.active);
                    break;
                }
            }

        }
    }
    public class TaskRepeatedListener implements ItemListener {

        int selected;

        @Override
        public void itemStateChanged(ItemEvent e) {
            selected = e.getStateChange();
            if (selected == 1)
                repeatedTask = true;
            else
                repeatedTask = false;
            mainWindow.paintPanel(new MainPanel());


//            for (int i = 0; i < taskList.size(); i++) {
//                if (taskList.getTask(i).equals(task)) {
//
//                    taskList.getTask(i).setActive(TaskPanel.active);
//                    break;
//                }
//            }
            mainWindow.paintPanel(new MainPanel());
        }

    }
    public class CreateTaskListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            TaskPanel.initializeParemeters();
            boolean noContain = true;
            Task newTask;
            if(repeatedTask)
                newTask = new Task(TaskPanel.title,TaskPanel.time,TaskPanel.end,TaskPanel.interval);
            else
          newTask  = new Task(TaskPanel.title, TaskPanel.time);
            for (Task task : taskList) {
                if (task.equals(newTask))
                    noContain = false;
                break;
            }
            if (noContain)
                newTask.setActive(TaskPanel.active);
                taskList.add(newTask);


        }

    }

    public class ChangeTaskButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            TaskPanel.initializeParemeters();

            for (int i = 0; i < taskList.size(); i++) {
                if (taskList.getTask(i).equals(task)) {

                    taskList.getTask(i).setTitle(TaskPanel.title);
                    taskList.getTask(i).setTime(TaskPanel.time);
                    taskList.getTask(i).setActive(TaskPanel.active);
                    if(task.isRepeated())
                    {
                        taskList.getTask(i).setEnd(TaskPanel.end);
                        taskList.getTask(i).setInterval(TaskPanel.interval);
                    }
                    break;
                }
            }


        }
    }

    public class CreateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CreateTaskPanel.initializeParemeters();
            if (CreateTaskPanel.time != null)

                taskList.add(new Task(CreateTaskPanel.title, CreateTaskPanel.time));
            else
                taskList.add(new Task(CreateTaskPanel.title, CreateTaskPanel.start, CreateTaskPanel.end, CreateTaskPanel.interval));
            mainWindow.paintPanel(new AllTaskListPanel(model));
            TitleChanger(CreateTaskPanel.title);
            TimeChanger(CreateTaskPanel.time);
            startChanger(CreateTaskPanel.start);
            endChanger(CreateTaskPanel.end);
            IntervalChanger(CreateTaskPanel.interval);


        }

    }

    public class RemoveTaskButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            taskList.remove(task);
            modelCreater();

        }
    }

    public class SeeTaskListListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            modelCreater();
            mainWindow.paintPanel(new AllTaskListPanel(model));


        }

    }

    public class CalendarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            modelCalendarCreater();
            mainWindow.paintPanel(new CalendarPanel(calendarModel));
        }
    }

//    public class TaskListener implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            mainWindow.paintPanel(new TaskPanel());
//        }
//    }

    public class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainWindow.paintPanel(new MainPanel());
        }

    }


    private int getTaskChangedIndex() {
        int indexChangedTask = 0;
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.getTask(i).equals(task)) {
                indexChangedTask = i;
                break;
            }
        }
        return indexChangedTask;


    }

    public class CloseAll implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
//            Date current = new Date(System.currentTimeMillis());
//            for (Task task : taskList) {
//                if (task!=null&task.getEnd().before(current) || task.getTime().before(current))
//                    taskList.remove(task);
//            }

            ArrayTaskList listToWrite = new ArrayTaskList();
            for (Task task : taskList) {
                if (task != null)
                    listToWrite.add(task);
            }

            File file = new File("filetasks");
            file.delete();
            TaskIO.writeBinary(listToWrite, file);
            System.exit(0);
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    }

    private void TitleChanger(String title) {
        taskList.getTask(this.getTaskChangedIndex()).setTitle(title);
    }

    private void TimeChanger(Date time) {
        taskList.getTask(this.getTaskChangedIndex()).setTime(time);
    }

    private void startChanger(Date start) {
        taskList.getTask(this.getTaskChangedIndex()).setStart(start);
    }

    private void endChanger(Date end) {
        taskList.getTask(this.getTaskChangedIndex()).setEnd(end);
    }

    private void IntervalChanger(Duration interval) {
        taskList.getTask(this.getTaskChangedIndex()).setInterval(interval);
    }

    private void activeChanger(boolean active) {
        taskList.getTask(this.getTaskChangedIndex()).setActive(active);
    }

    private static void modelCreater() {
        model = new DefaultListModel();

        for (int i = 0; i < taskList.size(); i++) {

            model.add(i, taskList.getTask(i));

        }
    }

    private static void modelCalendarCreater() {
        calendarModel = new DefaultListModel<>();
        Date from = new Date(System.currentTimeMillis() - 84000000);
        Date to = new Date(System.currentTimeMillis() + 86400000 * 7);
        int index = -1;

        onWeek = Tasks.calendar(taskList, from, to);
        for (Map.Entry entry : onWeek.entrySet()) {
            HashSet tasksnow = (HashSet) entry.getValue();
            for (Object task : tasksnow) {
                index++;
                calendarModel.add(index, (Task) task);
            }
        }
    }
}






