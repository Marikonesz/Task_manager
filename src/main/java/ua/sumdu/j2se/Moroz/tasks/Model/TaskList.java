package ua.sumdu.j2se.Moroz.tasks.Model;

import java.io.Serializable;
import java.util.Iterator;

/**
 * class describes a list of tasks
 */
public abstract class TaskList implements Iterable<Task>, Cloneable, Serializable {
    /**
     * count of size
     * @return count of size
     */
    abstract public int size();

    /**
     *
     * @param index task in task list
     * @return task from task list
     */
    public abstract Task getTask(int index);

    /**
     * delete Task from the list
     * @param task task for remoove
     * @return true when task was deleting susesfuly
     */

    public abstract boolean remove(Task task);

    /**
     * add new task into task list
     * @param task new task
     */
    public abstract void add(Task task);

    /**
     *
     * @return default iterator for task list
     */
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