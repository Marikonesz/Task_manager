package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by васыль on 31.10.2015.
 */
public abstract class TaskList implements Iterable<Task>,Cloneable,Serializable {
    abstract public int size();

    public abstract Task getTask(int index);

  public   abstract  boolean remove(Task task);

  public   abstract void add(Task task);

    public Task[] incoming(Date from, Date to) {
        int incomingSize = 0;
        for (int i = 0; i < size(); i++) {
            if (getTask(i).nextTimeAfter(from) != -1 && getTask(i).nextTimeAfter(from) <= to.getTime()) {
                incomingSize++;
            }
        }
        Task[] tasks = new Task[incomingSize];
        incomingSize = 0;
        for (int i = 0; i < size(); i++) {
            if (getTask(i).nextTimeAfter(from) != -1 && getTask(i).nextTimeAfter(from) <= to.getTime()) {
                tasks[incomingSize] = getTask(i);
                incomingSize++;
            }
        }
        return tasks;
    }

    public Iterator iterator() {
        return new Iterator() {
            private int cursor;

            @Override
            public boolean hasNext() {

                return cursor < size();
            }

            @Override
            public Task next() {
                return getTask(cursor++);
            }

            @Override
            public void remove() {
                TaskList.this.remove(getTask(cursor--));

            }
        };
    }

}