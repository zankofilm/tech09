package ir.scooljavanrood.madreseyar.teacher;

import java.util.Locale;
/** Input guards for native teacher gradebook. The server is still authoritative. */
final class GradebookRules {
    private GradebookRules() {}
    static String ascii(String input) {
        if(input==null)return "";
        StringBuilder b=new StringBuilder();
        for(char c:input.trim().toCharArray()){
            if(c>='۰'&&c<='۹')c=(char)('0'+c-'۰');
            else if(c>='٠'&&c<='٩')c=(char)('0'+c-'٠');
            else if(c=='٫')c='.';
            else if(c=='٬')c=',';
            b.append(c);
        }
        return b.toString();
    }
    static Double readScore(String raw) {
        String cleaned=ascii(raw).replace(',','.');
        if(!cleaned.matches("(?:[0-9]+(?:\\.[0-9]{1,2})?|\\.[0-9]{1,2})"))return null;
        try {double x=Double.parseDouble(cleaned);return Double.isFinite(x)?x:null;}
        catch(NumberFormatException e){return null;}
    }
    static boolean within(Double score,Double max) {
        return score!=null&&max!=null&&Double.isFinite(score)&&Double.isFinite(max)&&max>0&&score>=0&&score<=max;
    }
    static double finalCeiling(double continuousMax) {
        if(!Double.isFinite(continuousMax)||continuousMax<0||continuousMax>20)return Double.NaN;
        return Math.round((20d-continuousMax)*100)/100d;
    }
    static String formatScore(double d) {
        if(!Double.isFinite(d))return "—";
        return String.format(Locale.ROOT,"%.2f",d).replaceAll("\\.?0+$", "");
    }
}
