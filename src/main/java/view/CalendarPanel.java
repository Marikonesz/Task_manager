package view;

import Model.Task;

import javax.swing.*;
import java.awt.*;

/**
 * Created by васыль on 05.02.2016.
 */
public class CalendarPanel extends JPanel {
    public CalendarPanel() {
        JList<Task> calendarJList = new JList<Task>();
        //  calendarPanel.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        this.add(calendarJList);
    }
}
