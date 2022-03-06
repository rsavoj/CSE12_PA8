
/**
 * Name: Roya Savoj
 * Email: rsavoj@ucsd.edu
 * PID: A16644834
 * Sources used: Zyebooks, lecture slides 
 * 
 * This is a class that can create a calender object. The calander can book 
 * events so long as the evens do not overlap.
 */
import java.util.Calendar;
import org.junit.internal.matchers.StacktracePrintingMatcher;

/**
 * The calander object uses a MyTreeMap which extends MyBTS tree. When 
 * an object is added to the calander it is added to the data structure.
 * Items are added so long as there are no overlaping events 
 */
public class MyCalendar {
    MyTreeMap<Integer, Integer> calendar;  
    
    /**
     * The calander constructor creates a calander with no events 
     */
    public MyCalendar() {
       calendar = new MyTreeMap<>();
    }
    
     /**
     * This method will add an event to the calander so long as the time 
     * between its start and end times do not woverlap with another event 
     * 
     * @param start the start time of the event 
     * @param end the end time of the event 
     * @return returns true if the event was successfully added to the 
     * calander 
     */
    public boolean book(int start, int end) {
        //The start of the event cannot be less than time 0 or greater
        if(start < 0 || start >= end){
            throw new IllegalArgumentException();
        }
    
        if(calendar.floorKey(end) == null && calendar.ceilingKey(start) == null){
            calendar.put(start, end);
            return true;
        } 
        if(calendar.floorKey(end) == null){
            int ceiling = calendar.ceilingKey(start);
            if(ceiling < end){
                return false;
            }
            calendar.put(start, end);
            return true;
        }
        if(calendar.ceilingKey(start) == null){
            int floor = calendar.floorKey(end);
            if(calendar.get(floor) > start){
                return false;
            }
            calendar.put(start, end);
            return true;
        }
        // the greatest key less than the end
        int floor = calendar.floorKey(end);

        //the smallest key greater than the start
        int ceiling = calendar.ceilingKey(start);
        int ceilingVal = calendar.get(ceiling);
        if(ceiling < end || floor <start || ceilingVal < end){
            return false;
        }
        calendar.put(start, end);
        return true;
   
    }  

     /**
     * returns the calander object
     *
     * @return returns the calander object
     */
    public MyTreeMap getCalendar(){
        return this.calendar;
    }
}