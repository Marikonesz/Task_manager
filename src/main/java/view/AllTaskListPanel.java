package view;



import javax.swing.*;


import java.awt.*;



/**
 * Created by васыль on 05.02.2016.
 */
public class AllTaskListPanel extends JPanel  {
    public static JList allTasksList;

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


