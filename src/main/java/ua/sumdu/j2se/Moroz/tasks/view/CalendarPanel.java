package ua.sumdu.j2se.Moroz.tasks.view;

import javax.swing.*;
import java.awt.*;

/**
 * Create a panel to display list of  tasks on week
 */
public class CalendarPanel extends JPanel  {
    public static JList CalendarList;

    /**
     * Create an instance of panel to display list of all tasks
     * @param model the model to build the list
     */
    public CalendarPanel(DefaultListModel model) {

        CalendarList = new JList(model);
        CalendarList.addListSelectionListener(TaskManagerJFrame.controller.new CalendarSelectionListener());
        CalendarList.setLayoutOrientation(JList.VERTICAL);

        JScrollPane taskScrollList = new JScrollPane(CalendarList);
        taskScrollList.setPreferredSize(new Dimension(300, 500));

        this.add(taskScrollList);
    }
}
