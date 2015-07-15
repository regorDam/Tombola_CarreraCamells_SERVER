/**
 *    █║▌│ █│║▌ ║││█║▌ │║║█║ 
      Author     : Regør [★]
 *      Who in Black Byte 
 */
public class cRandom {
    public static int generaInt(){
        int i=(int)(Math.random()*10);
        return i;
    }
    public static int generaInt(int val){
        //genera num valor max restringit
        int i=(int)(Math.random()*val)+1;
        return i;
    }
    public static int generaInt(int inici, int fin){
        //genera un nemero dintre dun interval
        int range = (fin-inici)+1;
        int i=(int)(Math.random()*range)+inici;
        float b = (float)(((int)(Math.random()*10)))/10;
        return (int)(i);        
    }
    
    public static float generaFloat(){
        //genera un numero del 0 al 10
        float i=((int)(Math.random()*10));
        float b = (float)(((int)(Math.random()*10)))/10;
        return i+b;
    }
    public static float generaFloat(int inici, int fin){
        //genera un nemero dintre dun interval
        int range = (fin-inici)+1;
        float i=(int)(Math.random()*range)+inici;
        float b = (float)(((int)(Math.random()*10)))/10;
        return i+b;        
    }
    
}
