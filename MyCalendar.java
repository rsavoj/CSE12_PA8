import java.util.Calendar;

public class MyCalendar {
    MyTreeMap<Integer, Integer> calendar;
    
    public MyCalendar() {
       calendar = new MyTreeMap<>();
    }
    
    public boolean book(int start, int end) {
        if(start < 0 || start >= end){
            throw new IllegalArgumentException();
        }
        System.out.println(calendar.floorKey(start));
        System.out.println(calendar.ceilingKey(start));
        System.out.println(calendar.get(10));
       if( checkFloor(start, end)){
           calendar.put(start, end);
           return true;
       }
       System.out.println(calendar.floorKey(start));
       System.out.println(calendar.ceilingKey(start));
       return false;
    }

    private boolean checkFloor(int start, int end){
        // if there is no key smaller than the end value in the structure
        // returns true 
        System.out.println("The start is " + start + " the end is " + end + 
        " the floor is " +calendar.floorKey(end) );
        if(calendar.floorKey(end) == null){
            return true;
        }
        // there is no key less than the start therefore the key must be in 
        // the list 
        else if(calendar.floorKey(start) == null){
            return false;
        }
        // there is a key 
        else{
            if(calendar.get(calendar.floorKey(start)) + calendar.floorKey(start) >= start ){
                return false;
            }
            return true;
        }
    }
    

    public MyTreeMap getCalendar(){
        return this.calendar;
    }
}