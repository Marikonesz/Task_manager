package ua.sumdu.j2se.Moroz.tasks.view;



import javax.swing.*;


import java.awt.*;



/**
 * Create a panel to display list of all tasks
 */
public class AllTaskListPanel extends JPanel  {
    public static JList allTasksList;

    /**
     * Create an instance of panel to display list of all tasks
     * @param model the model to build the list
     */
    public AllTaskListPanel(DefaultListModel model) {


        allTasksList = new JList(model);
        allTasksList.addListSelectionListener(TaskManagerJFrame.controller.new TaskListSelectionListener());
        allTasksList.setLayoutOrientation(JList.VERTICAL);

        JScrollPane taskScrollList = new JScrollPane(allTasksList);
        taskScrollList.setPreferredSize(new Dimension(300, 500));
        taskScrollList.createVerticalScrollBar();

        this.add(taskScrollList);
    }
}


