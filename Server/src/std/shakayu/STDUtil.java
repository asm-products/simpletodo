package std.shakayu;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class STDUtil {
    public static String    EMPTYSTRING         = "";
    public static String    DOUBLEQUOTE         = "\"";
    public static int       HTTPPORT            = 8080;
    public static int       HTTPSPORT           = 8443;

    public static String MD5(String sInput){
        try {
            MessageDigest md =MessageDigest.getInstance("MD5");
            byte[] bInputarray = sInput.getBytes();
            md.update(bInputarray);
            return byteArrayToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    public static String MD5Pro(String sInput, String sSalt){
        try {
            MessageDigest md =MessageDigest.getInstance("MD5");
            byte[] bInputarray = sInput.getBytes();
            md.update(bInputarray);
            String s = byteArrayToHex(md.digest());
            bInputarray = (s+sSalt).getBytes();
            md.update(bInputarray);
            return byteArrayToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String byteArrayToHex(byte[] bInputarray) {

        char[] bDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
        char[] cResultarray =new char[bInputarray.length * 2];
        int i = 0;
        for (byte b : bInputarray) {
            cResultarray[i++] = bDigits[b>>> 4 & 0xf];//b>>> 4 & 0xf extracts the high nibble of a byte, hexadecimal
            cResultarray[i++] = bDigits[b& 0xf];     //b& 0xf extracts the low nibble of a byte, hexadecimal
        }
        return new String(cResultarray);
    }
    
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

    public static int CheckStringArrayAvaliable(String[] stringarray, boolean bCanItemEmpty){
        int nRes = 0;
        if(stringarray != null){
            if(stringarray.length >0){
                for(int i = 0; i<stringarray.length;i++){
                    if(!IsStringAvaliable(stringarray[i],bCanItemEmpty)){
                        return 0-i;
                    }
                }
                nRes = 1;
            }
        }
        return nRes;
    }
    
    public static String InDoubleQuote(String sContent){
        return DOUBLEQUOTE + sContent + DOUBLEQUOTE;
    }
    
    public static String CurrentTimeMillis(){
        return Long.toString(System.currentTimeMillis());
    }
    
    public static String MakeStringNoEmpty(String s, String sIfEmpty){
        if(IsStringAvaliable(s,false)){
            return s;
        }else{
            return sIfEmpty;
        }
    }

    public static String StringEncrypt(String sValue) {
        StringBuffer sbuf = new StringBuffer(sValue);
        byte[] bt = sbuf.reverse().toString().getBytes();
        return byteArrayToHex(bt);
    }

    public static String StringUncrypt(String sValue) {
        byte[] bt = HexTobyteArray(sValue);
        StringBuffer sbuf = new StringBuffer(new String(bt,0,bt.length));
        return sbuf.reverse().toString();
    }

    public static byte[] HexTobyteArray(String sHex){
        return new BigInteger(sHex,16).toByteArray();
    }
    
    public static void PrintDebug(String sHint, Object oValue, boolean bDebug){
        if(bDebug){
            System.out.println(sHint + oValue);
        }
    }
}
