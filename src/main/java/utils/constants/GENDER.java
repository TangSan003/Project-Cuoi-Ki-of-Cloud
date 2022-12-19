package utils.constants;

import java.util.ArrayList;
import java.util.HashMap;

public class GENDER {
    public static String MALE = "Nam";
    public static String FEMALE = "Nữ";
    public static String OTHER = "Khác";

    public static HashMap<String, String> Gender = new HashMap<String, String>(){
        {
            put("Nam", MALE);
            put("Nữ", FEMALE);
            put("Khác", OTHER);
        }
    };
}
