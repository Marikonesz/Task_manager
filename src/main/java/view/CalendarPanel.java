package view;

import Controller.TaskManagerController;
import Model.Task;

import javax.swing.*;
import java.awt.*;

/**
 * Created by васыль on 05.02.2016.
 */
public class CalendarPanel extends JPanel {
    public static JList CalendarList ;
    public CalendarPanel(DefaultListModel model) {

        CalendarList = new JList(model);
        CalendarList.addListSelectionListener(new TaskManagerController().new TaskListSelectionListener());
        CalendarList.setLayoutOrientation(JList.VERTICAL);

        JScrollPane taskScrollList = new JScrollPane(CalendarList);
        taskScrollList.setPreferredSize(new Dimension(300, 400));

        this.add(taskScrollList);
    }
}
