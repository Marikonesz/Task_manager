package Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by васыль on 26.09.2015.
 */
public class ArrayTaskList extends TaskList {
    /**
     * array of tasks
     */
    private Task[] arrayTaskList;
    /**
     * the number of tasks stored in the tasks array
     */
    private int size;

    /**
     * constructor which creates a new instance of class with empty array job with 10 cells and a variable size of 0
     */
    public ArrayTaskList() {
        this.arrayTaskList = new Task[10];
        this.size = 0;
    }




    /**
     * method which add new task in array of tasks
     *
     * @param task instance of class Task that will be added to the array
     */
    public void add(Task task) {
        size++;
        if (size % 10 == 0) {

            Task[] intermediateArray = Arrays.copyOf(arrayTaskList, size + 10);
            arrayTaskList = Arrays.copyOf(intermediateArray, size + 10);
            intermediateArray = null;
        }
        arrayTaskList[size - 1] = task;

    }

    public int size() {
        return size;

    }

    /**
     * @param index
     * @return
     */
   public Task getTask(int index) {


        return arrayTaskList[index];
    }

    /**
     * @param task
     * @return
     */
    public boolean remove(Task task) {
        Task[] intermediateArr = new Task[arrayTaskList.length - 1];
        int startCopy = -1;

        for (int i = 0; i < arrayTaskList.length; i++) {
            if (arrayTaskList[i] != null) {
                if (!arrayTaskList[i].equals(task))
                    intermediateArr[i] = arrayTaskList[i];
                else {
                    startCopy = i;
                    break;

                }
            }
        }
        for (int j = startCopy ; j < intermediateArr.length; j++) {
            intermediateArr[j] = arrayTaskList[j + 1];
        }


        if (startCopy == 0)
            return false;
        else {
            arrayTaskList = Arrays.copyOf(intermediateArr, intermediateArr.length);
            intermediateArr = null;
            size--;
            return true;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayTaskList tasks = (ArrayTaskList) o;

        if (size != tasks.size) return false;

        return Arrays.equals(arrayTaskList, tasks.arrayTaskList);

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(arrayTaskList);
        result = 31 * result + size;
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "arrayTaskList=" + Arrays.toString(arrayTaskList) +
                ", size=" + size +
                '}';
    }
}

