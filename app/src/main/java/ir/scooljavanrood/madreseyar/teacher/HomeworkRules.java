package ir.scooljavanrood.madreseyar.teacher;

/** Pure-Java validation matching P65.2 homework server constraints. */
final class HomeworkRules {
    private HomeworkRules() {}
    static String ascii(String value) {
        if(value==null) return "";
        StringBuilder b=new StringBuilder();
        for(char c:value.toCharArray()) {
            if(c>='۰'&&c<='۹') b.append((char)('0'+c-'۰'));
            else if(c>='٠'&&c<='٩') b.append((char)('0'+c-'٠'));
            else if(c=='٫' || c=='،') b.append('.');
            else b.append(c);
        }
        return b.toString().trim();
    }
    static void validateText(String title,String description) {
        if(title==null||title.trim().length()<3||title.trim().length()>120) throw new IllegalArgumentException("عنوان تکلیف باید بین ۳ تا ۱۲۰ نویسه باشد.");
        if(description==null||description.trim().length()<5||description.trim().length()>5000) throw new IllegalArgumentException("توضیحات تکلیف باید بین ۵ تا ۵۰۰۰ نویسه باشد.");
    }
    static double score(String source,double max) {
        double n;
        try { n=Double.parseDouble(ascii(source)); } catch(Exception e){throw new IllegalArgumentException("نمره معتبر وارد کنید.");}
        if(!Double.isFinite(n)||n<0||n>max+0.000001) throw new IllegalArgumentException("نمره باید بین صفر تا بارم تکلیف باشد.");
        return Math.round(n*100.0)/100.0;
    }
    static double totalScore(String input){double n=score(input,20);if(n<=0)throw new IllegalArgumentException("بارم تکلیف باید بیشتر از صفر باشد.");return Math.round(n*4.0)/4.0;}
    static String time24(String input,boolean hasDate){
        String value=ascii(input);
        if(value.isEmpty())return "";
        if(!hasDate)throw new IllegalArgumentException("برای انتخاب ساعت ابتدا تاریخ تحویل را تعیین کنید.");
        if(!value.matches("(?:[01]\\d|2[0-3]):[0-5]\\d"))throw new IllegalArgumentException("ساعت را به‌شکل ۲۴ساعته مانند ۱۷:۳۰ بنویسید.");
        return value;
    }
    static String persianDate(String raw){
        String value=ascii(raw).replace('-','/');
        if(value.isEmpty())return "";
        if(!value.matches("1[34]\\d{2}/(?:0[1-9]|1[0-2])/(?:0[1-9]|[12][0-9]|3[01])"))throw new IllegalArgumentException("تاریخ شمسی را به‌شکل ۱۴۰۵/۰۸/۲۵ بنویسید.");
        String[] items=value.split("/");int m=Integer.parseInt(items[1]),day=Integer.parseInt(items[2]);
        if((m<=6&&day>31)||(m>=7&&day>30))throw new IllegalArgumentException("روز انتخاب‌شده برای این ماه شمسی معتبر نیست.");
        return value;
    }
    static void validateAttachment(String mime,int size,int count){
        if(count>=2)throw new IllegalArgumentException("حداکثر دو پیوست مجاز است.");
        if(size<=0||size>650*1024)throw new IllegalArgumentException("حجم هر پیوست باید حداکثر ۶۵۰ کیلوبایت باشد.");
        if(!("image/jpeg".equals(mime)||"image/png".equals(mime)||"image/webp".equals(mime)||"application/pdf".equals(mime)))throw new IllegalArgumentException("فقط JPG، PNG، WebP یا PDF مجاز است.");
    }
}
