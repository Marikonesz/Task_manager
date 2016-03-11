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
    public static String title;
    public static Date end;
    public static Duration interval = Duration.ofMillis(0);
    public static Date time;
    public static boolean active;

    public TaskPanel(Task task) {
        this.setSize(200, 600);
        this.setLayout(new GridLayout(5, 2, 0, 0));
        boolean onOff = TaskManagerController.onOff;
        boolean repeated  = TaskManagerController.repeatedTask;
        lableTitleTask = new JLabel("  Task");
        fieldTitleTask = new JTextField(20);
        lableTimeTask = new JLabel("  begin");
        fieldTimeTask = new JDateChooser();
        lableEndTask = new JLabel("   end");
        fieldEndTask = new JDateChooser();
        fieldTimeTask.setDateFormatString("dd.MM.yyyy HH:mm");
        fieldEndTask.setDateFormatString("dd.MM.yyyy HH:mm");
        if (task != null) {
             onOff = task.isActive();

            fieldTitleTask.setText(task.getTitle());
            fieldTimeTask.setDate(task.getTime());
            fieldEndTask.setDate(task.getEnd());

        }
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
            if (task != null && task.isRepeated()) {
                hours.setText("" + task.getInterval().getSeconds() / 3600);
                minutes.setText("" + (task.getInterval().getSeconds() % 3600) / 60);
            }
            intervalPanel.add(hours);
            intervalPanel.add(new JLabel("hours"));
            intervalPanel.add(minutes);
            intervalPanel.add(new JLabel("minutes"));


            JCheckBox activCheck = new JCheckBox("active", onOff);
            JCheckBox repeatedCheck = new JCheckBox("repeated", repeated);
            repeatedCheck.addItemListener(controller.new TaskRepeatedListener());
            activCheck.addItemListener(controller.new TaskActiveListener());
            if (!repeatedCheck.isSelected()) {
                fieldEndTask.setEnabled(false);
                hours.setEnabled(false);
                minutes.setEnabled(false);
            }
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


    }

    public static void initializeParemeters() {

        title = fieldTitleTask.getText();
        end = fieldEndTask.getDate();
        time = fieldTimeTask.getDate();
        time = fieldTimeTask.getDate();
        interval = Duration.ofHours(Long.parseLong(hours.getText())).plusMinutes(Long.parseLong(minutes.getText()));


    }
}
