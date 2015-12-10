/**
 * Created by antero on 09/12/15.
 */

import java.util.Random;

public class Rand {
    // Methods
    static int integer(int min, int max){
        Random r = new Random();
        min++;
        int result = r.nextInt(max - min) + min;
        return result;
    }

    static boolean bool(){
        Random r = new Random();
        boolean result = r.nextBoolean();
        return result;
    }

    static float flt(float min,float max){
        Random r = new Random();
        float result = r.nextFloat() * (max - min) + min;
        return result;
    }
}
