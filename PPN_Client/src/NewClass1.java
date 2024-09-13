
import com.ppnlib.function.StringFunction;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author matajari
 */
public class NewClass1 {

    public static void main(String[] args) {
        HashMap a = new HashMap();
        a.put("a","1");
        a.put("b","2");
        a.put("c","3");
        HashMap b = (HashMap)a.clone();
        a.remove("b");
        System.out.println(a);
        System.out.println(b);
    }
}
