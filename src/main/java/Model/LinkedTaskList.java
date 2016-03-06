package Model;


import java.util.Iterator;

/**
 * Created by ������ on 30.10.2015.
 */
public class LinkedTaskList extends TaskList {
    /**
     * Returns an iterator over a set of elements of type T.
     *
     * @return an Iterator.
     */

    class Link {
        private Link previosLink;
        //   private Link currentLink;
        private Link nextLink;
        private Task task;


        public Link(Task task) {
            this.task = task;
        }

        public Task getTask() {
            return task;
        }


    }

    private int size;
    private Link first;
    private Link last;
    private Link current;


    public void add(Task task) {
        current = new Link(task);

        if (first == null) {

            first = current;
            size++;

        } else {
            if (first.nextLink == null) {
                first.nextLink = current;
                current.previosLink = first;
                last = current;

            } else {
                current.previosLink = last;
                last.nextLink = current;
                last = current;

                size++;
            }
        }
    }


    public boolean remove(Task task) {
        current = first;
        if (first.getTask().equals(task)) {
            first.nextLink.previosLink = null;
            size--;
            return true;
        }
        if (last.equals(task)) {
            last.previosLink.nextLink = null;
            size--;
            return true;
        }
        for (int i = 1; i < size - 1; i++) {
            current = current.nextLink;
            if (current.getTask().equals(task)) {
                current.previosLink.nextLink = current.nextLink;
                current.nextLink.previosLink = current.previosLink;
                size--;
                return true;

            }

        }


        return false;
    }

    @Override
    public int size() {
        return size;
    }

    public Task getTask(int index) {

        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        if (index == 0) {
            return first.getTask();
        }

        if (index == size)
            return last.getTask();
        else {
            current = first;
            for (int i = 1; i < size; i++) {
                current = current.nextLink;
                if (i == index) {
                    break;

                }

            }
        }
        return current.getTask();
    }

    public Iterator iterator() {
        return new Iterator() {
            private int cursor;
            private Link lastReturned;

            @Override
            public boolean hasNext() {

                return cursor < size();
            }

            @Override
            public Task next() {

                if (lastReturned == null) {
                    lastReturned = first;
                    cursor++;
                    return lastReturned.getTask();
                } else {
                    lastReturned = lastReturned.nextLink;
                    cursor++;
                    return lastReturned.getTask();
                }
            }

            @Override
            public void remove() {
                LinkedTaskList.this.remove(getTask(cursor--));

            }
        };
    }
//    LinkedTaskList incoming(int from, int to){
//        LinkedTaskList incoming = new LinkedTaskList();
//        current = first;
//        for (int i = 0; i < size ; i++) {
//           // if (current.getTask() !=null && current.getTask().getStart() >= from && (current.getTask().getStart() <= to)) {
//           if (current.getTask().nextTimeAfter(from) != -1 && current.getTask().nextTimeAfter(from) <= to){
//                incoming.add(current.getTask());
//            }
//            current = current.nextLink;
//        }
//       return incoming;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedTaskList tasks = (LinkedTaskList) o;

        if (size != tasks.size) return false;
        if (first != null ? !first.equals(tasks.first) : tasks.first != null) return false;
        if (last != null ? !last.equals(tasks.last) : tasks.last != null) return false;
        return !(current != null ? !current.equals(tasks.current) : tasks.current != null);

    }

    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + (first != null ? first.hashCode() : 0);
        result = 31 * result + (last != null ? last.hashCode() : 0);
        result = 31 * result + (current != null ? current.hashCode() : 0);
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        Link thisLink = first;
        String string = "";
        {
            for (int i = 0; i <= size; i++) {


                string = string + thisLink.getTask().toString();
                thisLink = thisLink.nextLink;
            }
            return " size = " + size + "," + string;
        }
    }
}


