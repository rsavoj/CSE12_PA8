public class file {
    
    public static void main(String args[]){
    int[] test = {1,2,3,4,5,6};
    System.out.println(insertionSort(test));
   // qsort(test, 0, 7);
   for( int i=0;i< test.length; i++ ){
    System.out.print(test[i]);
}
System.out.println();

      
       
    }
    private static void qsort(Integer list[], int low, int high) {
        if (low < high) {
            for( int i=0;i< list.length; i++ ){
                System.out.print(list[i]);
            }
            System.out.println();
            
            int pivot = partition(list, low, high);
            qsort(list, low, pivot);
            qsort(list, pivot + 1, high);
          
        }
    }
    private static int partition(Integer numbers[], int lowIndex, int highIndex) {
     // Pick middle element as pivot

 int  pivot = 3;

 boolean done = false;
  while (!done) {
     while (numbers[lowIndex] < pivot) {
        lowIndex += 1;
     }
     while (pivot < numbers[highIndex]) {
        highIndex -= 1;
     }

     // If zero or one elements remain, then all numbers are
     // partitioned. Return highIndex.
     if (lowIndex >= highIndex) {
        done = true;
     }
     else {
        // Swap numbers[lowIndex] and numbers[highIndex]
        int temp = numbers[lowIndex];
        numbers[lowIndex] = numbers[highIndex];
        numbers[highIndex] = temp;
   
        // Update lowIndex and highIndex
        lowIndex += 1;
        highIndex -= 1;
     }
  }
  return highIndex;
    }
    private static void swap(Integer nums[], int ele1, int ele2){
        int temp = nums[ele2];
        nums[ele2] = nums[ele1];
        nums[ele1] = temp;

    }
    private static void msort(int list[], int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            for( int i=0;i< list.length; i++ ){
                System.out.print(list[i]);
            }
            System.out.println();
            msort(list, low, mid);
            msort(list, mid + 1, high);
            merge(list, low, mid, high);
           
        }
    }

    private static void merge(int list[], int low, int mid, int high) {
        int h = low;
        int i = 0;
        int j = mid + 1;
        int otherList[] = new int[high - low + 1];

        while ((h <= mid) && (j <= high)) {
  
            if (list[h] <= list[j]) {
      
                otherList[i] = list[h];
                h++;
            } else {
    
                otherList[i] = list[j];
                j++;
            }
            i++;
        }
        if (h > mid) {
            for (int k = j; k <= high; k++) {
 
                otherList[i] = list[k];
                i++;
            }
        } else {
            for (int k = h; k <= mid; k++) {

                otherList[i] = list[k];
                i++;
            }
        }

        for (int m = 0; m < otherList.length; m++) {
    
            list[low + m] = otherList[m];
        }

    } ;


    protected static void ssort(int list[]) {
        for (int j = list.length - 1; j > 0; j--) {
            int maxpos = 0;
            for (int k = 1; k <= j; k++) {
                
                if (list[k] > list[maxpos]) {
                    maxpos = k;
                }
            }
            if (j != maxpos)    // Only move if we must
            {
              
                int temp = list[j];
                list[j] = list[maxpos];
                list[maxpos] = temp;
            }
            for( int i=0;i< list.length; i++ ){
                System.out.print(list[i]);
            }
            System.out.println();
           
        }
    }
    public static int insertionSort(int array[]){
        int count2 =0;
        int count1 =0;
        int n = array.length;
        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;
            count2 ++;
    
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
                count1++;
            }
            array[j + 1] = key;

        }
        return count2;
    }



}
