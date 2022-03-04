import java.util.Calendar;

import org.junit.internal.matchers.StacktracePrintingMatcher;

public class MyCalendar {
    MyTreeMap<Integer, Integer> calendar;
    
    public MyCalendar() {
       calendar = new MyTreeMap<>();
    }
    
    public boolean book(int start, int end) {
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

    public MyTreeMap getCalendar(){
        return this.calendar;
    }
}