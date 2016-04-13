package ua.sumdu.j2se.Moroz.tasks.Controller;


import ua.sumdu.j2se.Moroz.tasks.Model.*;
import org.apache.log4j.Logger;
import ua.sumdu.j2se.Moroz.tasks.view.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.File;
import java.util.*;

/**
 * the class reads the tasks from a file,the entry in the tasks file,adding,deleting and modifying tasks
 *
 * @param
 */

public class TaskManagerController {
    /**
     * connect a logging system to the class
     */
    final static Logger logger = Logger.getLogger(TaskManagerController.class);
    /**
     * initialize frame
     */
    public static TaskManagerJFrame mainWindow;
    /**
     * the display panel of the selected task
     */
    public static TaskPanel taskPanel;
    /**
     * the working collection to work with tasks
     */

    public static TaskList taskList = new ArrayTaskList();
    /**
     * the working collection to work with tasks that are scheduled for the coming week
     */
    public static SortedMap<Date, Set<Task>> onWeek = new TreeMap();
    /**
     * object Task to work with current task
     */
    public static Task task;
    /**
     * indicates whether the task is repeatable
     */
    public static boolean repeatedTask;
    /**
     * indicates whether the task is active
     */
    public static boolean onOff;
    /**
     * the model to display the list of tasks
     */
    public static DefaultListModel<Task> model;
    /**
     * the model to display the list of tasks on week
     */
    public static DefaultListModel<Task> calendarModel;


    public static void main(String[] args) throws InterruptedException {


        NotifyController notify = new NotifyController();
        notify.setDaemon(true);
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
        /**
         * method handles the selection of a task from the list
         */
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
        /**
         * method handles the selection of a task from the list on week
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {

            task = (Task) CalendarPanel.CalendarList.getSelectedValue();
            if (task != null) {
                repeatedTask = task.isRepeated();
                onOff = task.isActive();
                taskPanel.writeParameters(task);
            }

        }
    }

    public class TaskActiveListener implements ItemListener {

        int selected;

        /**
         * method handles the change of the parameter "active"
         *
         * @param e
         */
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
                if (task != null) {
                    if (!calendarModel.contains(task) && task.getTime().after(new Date()) && task.getTime().before(new Date(System.currentTimeMillis() + 86400000 * 7)) && task.isActive())
                        calendarModel.addElement(task);
                    if (calendarModel.contains(task) && !task.isActive())
                        calendarModel.removeElement(task);
                }
            }

        }
    }

    public class TaskRepeatedListener implements ItemListener {

        int selected;

        /**
         * method handles the change of the parameter "repeated"
         */

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
        /**
         * method create a new task
         *
         * @param e
         */
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

                    newTask.setActive(onOff);
                    taskList.add(newTask);
                    task = newTask;
                    model.addElement(newTask);


                    if (newTask.nextTimeAfter(new Date()).after(new Date(System.currentTimeMillis() - 10000)) && newTask.nextTimeAfter(new Date()).before(new Date(System.currentTimeMillis() + 86410000 * 7)) && newTask.isActive())
                        calendarModel.addElement(newTask);


                    logger.warn("new task created");
                }
            }
            mainWindow.repaint();
        }

    }

    public class ChangeTaskButtonListener implements ActionListener {

        /**
         * method change selected task
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            TaskPanel.initializeParemeters();
            Task editTask = new Task();
            int position = calendarModel.indexOf(task);
            for (int i = 0; i < taskList.size(); i++) {
                if (taskList.getTask(i).equals(task)) {
                    TaskPanel.active = onOff;
                    taskList.getTask(i).setTitle(TaskPanel.title);
                    taskList.getTask(i).setActive(TaskPanel.active);
                    if (TaskPanel.interval.toMillis() != 0 && repeatedTask) {
                        taskList.getTask(i).setTime(TaskPanel.time, TaskPanel.end, TaskPanel.interval);
                    } else
                        taskList.getTask(i).setTime(TaskPanel.time);
                    editTask = taskList.getTask(i);
                    model.insertElementAt(editTask, model.indexOf(task));
                    model.removeElement(task);
                    if (!editTask.nextTimeAfter(new Date()).after(new Date()) || !editTask.nextTimeAfter(new Date()).before(new Date(System.currentTimeMillis() + 86400000 * 7)) || !editTask.isActive())
                        calendarModel.removeElement(task);
                    if (calendarModel.contains(task) && editTask.isActive()) {
                        calendarModel.insertElementAt(editTask, position);
                        calendarModel.removeElement(task);
                    } else if (editTask.nextTimeAfter(new Date()).after(new Date()) && editTask.nextTimeAfter(new Date()).before(new Date(System.currentTimeMillis() + 86400000 * 7)) && editTask.isActive())
                        calendarModel.addElement(editTask);

                    break;

                }
            }

            mainWindow.repaint();
            logger.warn("task was changed");
        }
    }

    public class RemoveTaskButtonListener implements ActionListener {
        /**
         * method delete selected task
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            Task removeTask = task;
            int sure = JOptionPane.showConfirmDialog(mainWindow, "Are you sure?", "deleting task", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.CLOSED_OPTION);
            if (sure == JOptionPane.OK_OPTION) {

                taskList.remove(removeTask);
                model.removeElement(removeTask);
                calendarModel.removeElement(removeTask);
                logger.warn("task removed");

                mainWindow.repaint();

            }
        }
    }

    /**
     * the variable task index in the list
     *
     * @return index of the task
     */
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

        /**
         * method finish executing programm and write all tasks into file
         *
         * @param e
         */
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

    /**
     * create a new model for displaying a list of all tasks
     */
    private static void modelCreater() {
        model = new DefaultListModel();

        for (int i = 0; i < taskList.size(); i++) {

            model.add(i, taskList.getTask(i));

        }
    }

    /**
     * create a new model for displaying a list of tasks on week
     */
    private static void modelCalendarCreater() {
        calendarModel = new DefaultListModel<>();
        Date from = new Date();
        Date to = new Date(System.currentTimeMillis() + 86400000 * 7);
        int index = -1;

        onWeek = Tasks.calendar(taskList, from, to);
        for (Map.Entry entry : onWeek.entrySet()) {
            HashSet tasksnow = (HashSet) entry.getValue();
            for (Object task : tasksnow) {
                Task currentTask = (Task) task;
                if (currentTask.isActive()) {
                    index++;
                    calendarModel.add(index, (Task) task);
                }
            }
        }
    }
}






