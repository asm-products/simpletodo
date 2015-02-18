package std.shakayu;

/**
 * Created by yuxin on 15/2/17.
 */
public class STDUtil {
    public static String EMPTYSTRING = "";
    public static final String  DBPATH = "DB/";
    public static boolean IsStringAvaliable(String s, boolean bCanEmpty){
        boolean bRes = false;
        if(s != null){
            if(bCanEmpty){
                bRes = true;
            }else{
                bRes = !s.equals(EMPTYSTRING);
            }
        }
        return bRes;
    }

    public static boolean IsStringAvaliable(String s, int nMinlength, int nMaxlength) {
        boolean bRes = false;
        if(s != null){
            int nLength = s.length();
            if((nMinlength <= nLength) && nMaxlength >= nLength){
                bRes = true;
            }
        }
        return bRes;
    }

    public static boolean IsStringArrayAvaliable(String[] stringarray, boolean bCanItemEmpty){
        boolean bRes = false;
        if(stringarray != null){
            if(stringarray.length >0){
                for(int i = 0; i<stringarray.length;i++){
                    if(!IsStringAvaliable(stringarray[i],bCanItemEmpty)){
                        return bRes;
                    }
                }
                bRes = true;
            }
        }
        return bRes;
    }
}
