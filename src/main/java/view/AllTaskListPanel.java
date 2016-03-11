package view;

import Controller.TaskManagerController;
import Model.Task;
import Model.TaskList;

import javax.swing.*;


import java.awt.*;

import static Controller.TaskManagerController.taskList;

/**
 * Created by васыль on 05.02.2016.
 */
public class AllTaskListPanel extends JPanel implements ControllerInterface {
    public static JList allTasksList;

    public AllTaskListPanel(DefaultListModel model) {


        allTasksList = new JList(model);
        allTasksList.addListSelectionListener(controller.new TaskListSelectionListener());
        allTasksList.setLayoutOrientation(JList.VERTICAL);

        JScrollPane taskScrollList = new JScrollPane(allTasksList);
        taskScrollList.setPreferredSize(new Dimension(300, 500));
        taskScrollList.createVerticalScrollBar();

        this.add(taskScrollList);
    }
}


