package view;

import Controller.TaskManagerController;
import Model.Task;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.util.Date;


public class TaskPanel extends JPanel {

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
        boolean onOff = task.isActive();
        boolean repeated = TaskManagerController.repeatedTask;
        this.setLayout(new GridLayout(5, 2, 0, 0));
        lableTitleTask = new JLabel("  Task");
        fieldTitleTask = new JTextField(20);

        fieldTitleTask.setText(task.getTitle());
        lableTimeTask = new JLabel("  begin");
        fieldTimeTask = new JDateChooser();

        fieldTimeTask.setDate(task.getTime());

        lableEndTask = new JLabel("   end");
        fieldEndTask = new JDateChooser();
        fieldTimeTask.setDateFormatString("dd.MM.yyyy HH:mm");
        fieldEndTask.setDateFormatString("dd.MM.yyyy HH:mm");
        fieldEndTask.setDate(task.getEnd());


        JButton deleteTaskButton = new JButton("delete");
        deleteTaskButton.addActionListener(new TaskManagerController().new RemoveTaskButtonListener());
        JButton changeTaskButton = new JButton("change");
        changeTaskButton.addActionListener(new TaskManagerController().new ChangeTaskButtonListener());
        intervalLabel = new JLabel("interval");
        intervalPanel = new JPanel();
        hours = new JTextField(2);
        minutes = new JTextField(2);
        if (!task.isRepeated())
            hours.setText("0");
        else
            System.out.println("");
            hours.setText("" + task.getInterval().getSeconds() / 3600);
        if (!task.isRepeated())
            minutes.setText("0");
        else
            minutes.setText("" + (task.getInterval().getSeconds() % 3600) / 60);
        intervalPanel.add(hours);
        intervalPanel.add(new JLabel("hours"));
        intervalPanel.add(minutes);
        intervalPanel.add(new JLabel("minutes"));


        JCheckBox activCheck = new JCheckBox("active", onOff);
        JCheckBox repeatedCheck = new JCheckBox("repeated", repeated);
        repeatedCheck.addItemListener(new TaskManagerController().new TaskRepeatedListener());
        activCheck.addItemListener(new TaskManagerController().new TaskActiveListener());
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
