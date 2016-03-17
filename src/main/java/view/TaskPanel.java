package view;

import Controller.TaskManagerController;
import Model.Task;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.util.Date;


public class TaskPanel extends JPanel implements ControllerInterface {

    private JLabel lableTitleTask;
    private static JTextField fieldTitleTask;
    private JLabel lableTimeTask;
    private static JDateChooser fieldTimeTask;
    private JLabel lableEndTask;
    private static JDateChooser fieldEndTask;
    private JLabel intervalLabel;
    private JPanel intervalPanel;
    private static JTextField hours;
    private static JTextField minutes;
    private JCheckBox activCheck;
    private JCheckBox repeatedCheck;
    public static String title;
    public static Date end;
    public static Duration interval = Duration.ofMillis(0);
    public static Date time;
    public static boolean active;
    private static boolean onOff;
    private static boolean repeated;

    public TaskPanel(Task task) {
        this.setSize(200, 600);
        this.setLayout(new GridLayout(5, 2, 0, 0));
        lableTitleTask = new JLabel("  Task");
        fieldTitleTask = new JTextField(20);
        lableTimeTask = new JLabel("  begin");
        fieldTimeTask = new JDateChooser();
        lableEndTask = new JLabel("   end");
        fieldEndTask = new JDateChooser();
        fieldTimeTask.setDateFormatString("dd.MM.yyyy HH:mm");
        fieldEndTask.setDateFormatString("dd.MM.yyyy HH:mm");
        JButton deleteTaskButton = new JButton("delete");
        deleteTaskButton.addActionListener(controller.new RemoveTaskButtonListener());
        JButton changeTaskButton = new JButton("change");
        changeTaskButton.addActionListener(controller.new ChangeTaskButtonListener());
        intervalLabel = new JLabel("interval");
        intervalPanel = new JPanel();
        hours = new JTextField(2);
        minutes = new JTextField(2);
        hours.setText("0");
        minutes.setText("0");
        intervalPanel.add(hours);
        intervalPanel.add(new JLabel("hours"));
        intervalPanel.add(minutes);
        intervalPanel.add(new JLabel("minutes"));
        activCheck = new JCheckBox("active");
        repeatedCheck = new JCheckBox("repeated");
        repeatedCheck.addItemListener(controller.new TaskRepeatedListener());
        activCheck.addItemListener(controller.new TaskActiveListener());
        this.add(lableTitleTask);
        this.add(fieldTitleTask);
        this.add(lableTimeTask);
        this.add(fieldTimeTask);
        this.add(lableEndTask);
        this.add(fieldEndTask);
        this.add(intervalLabel);
        this.add(intervalPanel);
        this.add(activCheck);
        this.add(repeatedCheck);
        writeParameters(task);

    }

    public static void initializeParemeters() {

        title = fieldTitleTask.getText();
        end = fieldEndTask.getDate();
        time = fieldTimeTask.getDate();
        interval = Duration.ofHours(Long.parseLong(hours.getText())).plusMinutes(Long.parseLong(minutes.getText()));
        active = onOff;

    }

    public void writeParameters(Task task) {
        if (task != null) {
            onOff = TaskManagerController.onOff;
            repeated = TaskManagerController.repeatedTask;
            fieldTitleTask.setText(task.getTitle());
            fieldTimeTask.setDate(task.getTime());
            fieldEndTask.setDate(task.getEnd());
            if (task != null && task.isRepeated()) {
                hours.setText("" + task.getInterval().getSeconds() / 3600);
                minutes.setText("" + (task.getInterval().getSeconds() % 3600) / 60);
            }
            activCheck.setSelected(onOff);
            repeatedCheck.setSelected(repeated);

            this.repaint();

        }
        if (!repeatedCheck.isSelected()) {
            fieldEndTask.setEnabled(false);
            hours.setEnabled(false);
            minutes.setEnabled(false);
        } else {
            fieldEndTask.setEnabled(true);
            hours.setEnabled(true);
            minutes.setEnabled(true);
        }
    }
}

