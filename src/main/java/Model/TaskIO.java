package Model;

import Model.TaskList;
import Model.Task;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


public class TaskIO {
    static void write(TaskList tasks, OutputStream out) {

        DataOutputStream writer = new DataOutputStream(out);
        try {
            writer.writeInt(tasks.size());
            for (Task task : tasks) {
                if (task != null) {
                    writer.writeInt(task.getTitle().length());
                    writer.writeUTF(task.getTitle());

                    if (task.isActive())
                        writer.writeInt(1);
                    else writer.writeInt(0);
                    writer.writeLong(task.getInterval().toMillis());
                    if (task.isRepeated()) {
                        writer.writeLong(task.getStart().getTime());
                        writer.writeLong(task.getEnd().getTime());
                    } else
                        writer.writeLong(task.getTime().getTime());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e) {


                }
        }
    }


    static void read(TaskList tasks, InputStream in) {
        DataInputStream reader = new DataInputStream(in);
        int count = 0;
        int titleLength = 0;
        try {
            count = reader.readInt();//чтение количества задач


            for (int i = 0; i < count; i++) {
                Task task = new Task();

                try {
                    titleLength = reader.readInt();//чтение длины тайтл

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    task.setTitle(reader.readUTF());//чтение тайтл

                } catch (IOException e) {
                    e.printStackTrace();
                }


                int isactiv = reader.readInt();
                task.setActive(isactiv == 1);//чтение актив

                task.setInterval(Duration.ofMillis(reader.readLong()));//чтение интервала

                if (task.getInterval().toMillis() == 0) {

                    task.setTime(new Date(reader.readLong()));

                } else {

                    task.setStart(new Date(reader.readLong()));
                    task.setEnd(new Date(reader.readLong()));

                }
                tasks.add(task);
            }
        } catch (EOFException e) {
            try {
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {


            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public static void writeBinary(TaskList tasks, File file) {
        //  String pathFile  =  "C:\\Users\\������\\IdeaProjects\\Tasknetcracker";
        try {
            write(tasks, new FileOutputStream(file.getPath()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readBinary(TaskList tasks, File file) {
        try {
            if(!file.exists())
                writeBinary(new ArrayTaskList(),file);
            read(tasks, new FileInputStream(file.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(TaskList tasks, Writer out) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMMM-DDD H:m:S.s");
        int forLast = 0;
        BufferedWriter writer = new BufferedWriter(out);
        String taskString;
        String inactive = " inactive";
        try {
            int size = tasks.size();
            writer.write("" + size);
            writer.write(System.getProperty("line.separator"));


            for (Task task : tasks) {
                forLast++;


                if (!task.isRepeated())
                    taskString = "\"" + task.getTitle() + "\"" + " at [" + format.format(task.getTime()) + "] ";
                else {
                    taskString = "\"" + task.getTitle() + "\"" + " from [" + format.format(task.getStart()) + "] to [" + format.format(task.getEnd()) + "] every [ " + dateparser(new Date(task.getInterval().toMillis())) + " ]";
                }
                if (!task.isActive())
                    taskString += inactive;
                if (tasks.size() > forLast)
                    taskString += ";";
                else
                    taskString += ".";
                writer.write(taskString);
                writer.write(System.getProperty("line.separator"));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public static void read(TaskList tasks, Reader in) {
        BufferedReader reader = new BufferedReader(in);
        try {
            int size = Integer.parseInt(reader.readLine());
            String[] textTime = new String[3];
            int marker = 0;


            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MMMM-DDD H:m:S.s");


            for (int i = 0; i < size; i++) {
                Task task = new Task();
                String strindTask = reader.readLine();
                String[] foundTitle = strindTask.split("\"");
                task.setTitle(foundTitle[1]);
                String[] forParse = foundTitle[2].split("]");
                for (int m = 0; m < 3; m++) {


                    for (int j = marker; j < forParse.length; j++) {
                        if (forParse[j].indexOf("inactive") == -1)
                            task.setActive(true);
                        marker++;
                        char[] taskLineChars = forParse[j].toCharArray();

                        for (int k = 0; k < taskLineChars.length; k++) {
                            // System.out.print(taskLineChars[k]);

                            if (taskLineChars[k] == '[') {
                                textTime[m] = forParse[j].substring(k + 1);


                            }

                        }
                        break;
                    }


                }
                try {
                    if (textTime[1] == null) {

                        task.setTime((format.parse(textTime[0])));


                    } else {
                        task.setStart((format.parse(textTime[0])));
                        task.setEnd((format.parse(textTime[1])));
                        String[] forInterval = textTime[2].split(" ");
                        long days = Long.parseLong(forInterval[1]);
                        long hours = Long.parseLong(forInterval[3]);
                        long minutes = Long.parseLong(forInterval[5]);
                        long seconds = Long.parseLong(forInterval[7]);

                        task.setInterval(Duration.ofMillis((days * 86400000) + (hours * 3600000) + (minutes * 60000) + (seconds * 1000)));

                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {

                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static void writeText(TaskList tasks, File file) {
        try {
            write(tasks, new FileWriter(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readText(TaskList tasks, File file) {
        try {
            read(tasks, new FileReader(file.getPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static String dateparser(Date date) {
        long miliseconds = date.getTime();
        long days = miliseconds / 86400000;
        long hours = (miliseconds % 86400000) / 3600000;
        long minutes = ((miliseconds % 86400000) % 3600000) / 60000;
        long seconds = (((miliseconds % 86400000) % 3600000) % 60000) / 1000;
        String day;
        String hour;
        String minute;
        String second;
        if (days > 1)
            day = "days";
        else
            day = "day";
        if (hours > 1)
            hour = "hours";
        else
            hour = "hour";
        if (minutes > 1)
            minute = "minutes";
        else
            minute = "minute";
        if (seconds > 1)
            second = "seconds";
        else
            second = "second";


        return days + " " + day + " " + hours + " " + hour + " " + minutes + " " + minute + " " + seconds + " " + second;
    }
}