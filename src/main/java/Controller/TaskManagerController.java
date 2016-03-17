package Controller;


import Model.*;
import org.apache.log4j.Logger;
import view.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.File;
import java.util.*;

/**
 * Created by васыль on 02.02.2016.
 */

public class TaskManagerController {
    final static Logger logger = Logger.getLogger(TaskManagerController.class);

    public static TaskManagerJFrame mainWindow;
    public static TaskPanel taskPanel;
    public static TaskList taskList = new ArrayTaskList();
    public static SortedMap<Date, Set<Task>> onWeek = new TreeMap();
    public static Task task;
    public static boolean repeatedTask;
    public static boolean onOff;
    public static DefaultListModel<Task> model;
    public static DefaultListModel<Task> calendarModel;


    public static void main(String[] args) throws InterruptedException {


        NotifyController notify = new NotifyController();
        TaskIO.readBinary(taskList, new File("filetasks"));
        task = taskList.getTask(0);
        if (task != null) {
            repeatedTask = task.isRepeated();
            onOff = task.isActive();

        }
        logger.warn("taskManager Started");
        modelCreater();
        modelCalendarCreater();
        taskPanel = new TaskPanel(task);
        mainWindow = new TaskManagerJFrame();
        notify.start();


    }

    public class TaskListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {

            task = (Task) AllTaskListPanel.allTasksList.getSelectedValue();
            if (task != null) {
                repeatedTask = task.isRepeated();
                onOff = task.isActive();
                taskPanel.writeParameters(task);
            }

        }
    }

    public class CalendarSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            TaskManagerController.task = (Task) CalendarPanel.CalendarList.getSelectedValue();
            MainPanel.setFistList(true);
            mainWindow.repaint();
            MainPanel.setFistList(false);
        }
    }

    public class TaskActiveListener implements ItemListener {

        int selected;

        @Override
        public void itemStateChanged(ItemEvent e) {
            selected = e.getStateChange();
            if (selected == 1)
                onOff = true;
            else
                onOff = false;


            activeChanger(onOff);
            if (task != null) {
                task.setActive(onOff);
                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.getTask(i).equals(task)) {

                        taskList.getTask(i).setActive(task.isActive());
                        break;
                    }
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
            if (taskPanel != null) {
                taskPanel.writeParameters(task);
                mainWindow.repaint();
            }

        }

    }

    public class CreateTaskListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            TaskPanel.initializeParemeters();
            boolean noContain = true;
            Task newTask = new Task();
            if (TaskPanel.title != null && TaskPanel.time != null) {
                if (repeatedTask)
                    newTask = new Task(TaskPanel.title, TaskPanel.time, TaskPanel.end, TaskPanel.interval);
                else
                    newTask = new Task(TaskPanel.title, TaskPanel.time);
                for (Task task : taskList) {
                    if (newTask.equals(task))
                        noContain = false;
                    break;
                }
                if (noContain) {
                    taskList.add(newTask);
                    newTask.setActive(TaskPanel.active);
                    task = newTask;
                    model.addElement(newTask);

                    if (newTask.getTime().getTime() >= new Date(System.currentTimeMillis() - 10000).getTime() && newTask.getTime().getTime() <= new Date(System.currentTimeMillis() + 86400000 * 7).getTime())
                        calendarModel.addElement(newTask);

                    logger.warn("new task created");
                }
            }

        }

    }

    public class ChangeTaskButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            TaskPanel.initializeParemeters();

            for (int i = 0; i < taskList.size(); i++) {
                if (taskList.getTask(i).equals(task)) {

                    taskList.getTask(i).setTitle(TaskPanel.title);
                    taskList.getTask(i).setActive(TaskPanel.active);
                    if (TaskPanel.interval.toMillis() != 0 && repeatedTask) {
                        taskList.getTask(i).setTime(TaskPanel.time, TaskPanel.end, TaskPanel.interval);
                    } else
                        taskList.getTask(i).setTime(TaskPanel.time);
                    break;
                }
            }
            modelCreater();
            modelCalendarCreater();
            mainWindow.repaint();
            logger.warn("task was changed");
        }
    }

    public class RemoveTaskButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (taskList.size() > 0) {
                taskList.remove(task);
                model.removeElement(task);
                if (task != null) {
                    if (task.getTime().getTime() >= new Date().getTime() && task.getTime().getTime() <= new Date(System.currentTimeMillis() + 86400000 * 7).getTime())
                        calendarModel.removeElement(task);
                }
                logger.warn("task removed");
            }
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


            ArrayTaskList listToWrite = new ArrayTaskList();
            for (Task task : taskList) {
                if (task != null)
                    listToWrite.add(task);
            }

            File file = new File("filetasks");
            file.delete();
            TaskIO.writeBinary(listToWrite, file);
            logger.warn("Task manager was closed");
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

    private void activeChanger(boolean active) {
        if (task != null)
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
        Date from = new Date();
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






