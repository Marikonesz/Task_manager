package view;

import Controller.TaskManagerController;
import Model.Task;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;


public class CreateTaskPanel extends JPanel {
    public static String title;
    public static Date start;
    public static Date end;
    public static Duration interval;
    public static Date time;
    private static JTextField fieldTitleTask;
    private static JDateChooser enterTime;
    private static JDateChooser enterStart;
    private static JDateChooser enterEnd;
    private static JFormattedTextField enterInterval;


    public CreateTaskPanel() {
        this.setLayout(new GridLayout(6, 2, 5, 30));
        JLabel lableTitleTask = new JLabel("Task");
        fieldTitleTask = new JTextField(30);

        JLabel lableTimeTask = new JLabel("time");
        enterTime = new JDateChooser();

        JLabel lableStartTask = new JLabel("start");
        enterStart = new JDateChooser();

        JLabel lableEndTask = new JLabel("end");
        enterEnd = new JDateChooser();

        JLabel lableintervalTask = new JLabel("interval");
        enterInterval = new JFormattedTextField();
        enterInterval.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat(
                "H'h' mm'm'"))));
        enterInterval.setValue(Calendar.getInstance().getTime());

        enterInterval.addPropertyChangeListener("value", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {


            }
        });

//        enterInterval
//        JLabel hours = new JLabel("hours");
// hoursfield = new JFormattedTextField(new DecimalFormat("#"));
//        JLabel minutes = new JLabel("min");
//        JTextField minutesField = new JTextField(2);
//        JLabel secjnds = new JLabel("sec");
//        JTextField secField = new JFormattedTextField();
//        secField.setSize(20, 1);
//        enterInterval.add(hours);
//        enterInterval.add(hoursfield);
//        enterInterval.add(minutes);
//        enterInterval.add(minutesField);
//        enterInterval.add(secjnds);
//        enterInterval.add(secField);


        JButton createTaskButton = new JButton("create");

        enterTime.setDateFormatString("yyyy-MMMM-dd H:m:s");
        enterStart.setDateFormatString("yyyy-MMMM-dd H:m:s");
        enterEnd.setDateFormatString("yyyy-MMMM-dd H:m:s");


        this.add(lableTitleTask);
        this.add(fieldTitleTask);
        this.add(lableStartTask);
        this.add(enterStart);
        this.add(lableTimeTask);
        this.add(enterTime);
        this.add(lableEndTask);
        this.add(enterEnd);
        this.add(lableintervalTask);
        this.add(enterInterval);
        this.add(createTaskButton);


        createTaskButton.addActionListener(new TaskManagerController().new CreateButtonListener());
    }

    public static void initializeParemeters() {
        title = fieldTitleTask.getText();
        start = enterStart.getDate();
        end = enterEnd.getDate();
        interval = Duration.parse(intervalParser(enterInterval.getText()));
        time = enterTime.getDate();
    }

    public static void primaryParameters(Task task) {
        fieldTitleTask.setText(task.getTitle());
        enterTime.setDate(task.getTime());
        enterStart.setDate(task.getStart());
        enterEnd.setDate(task.getEnd());
        enterInterval.setText(intervalString(task.getInterval()));
    }

    private static String intervalParser(String interval) {
        return "PT" + interval.substring(0, 2) + "H" + interval.substring(4, 6) + "M";
    }

    private static String intervalString(Duration interval) {
        long hours = interval.getSeconds() / 3600;
        long minutes = (interval.getSeconds() % 3600) / 60;
        return hours + "h " + minutes + "m";
    }
}
