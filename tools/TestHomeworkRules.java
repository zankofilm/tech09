package ir.scooljavanrood.madreseyar.teacher;
final class TestHomeworkRules {
  static int n=0;
  static void ok(boolean yes,String hint){n++;if(!yes)throw new AssertionError(hint);}
  static void rejects(Runnable r){n++;try{r.run();throw new AssertionError("should reject");}catch(IllegalArgumentException good){}}
  public static void main(String[] args){
    ok("1905".equals(HomeworkRules.ascii("۱۹۰۵")),"fa digits");
    ok("2016".equals(HomeworkRules.ascii("٢٠١٦")),"arabic digits");
    ok("13.5".equals(HomeworkRules.ascii("۱۳٫۵")),"decimal");
    ok(HomeworkRules.score("۱۰٫۷۵",20)==10.75,"score");
    ok(HomeworkRules.score("۰",20)==0,"zero allowed");
    rejects(()->HomeworkRules.score("۲۱",20));
    rejects(()->HomeworkRules.score("-۱",20));
    rejects(()->HomeworkRules.score("bad",20));
    ok(HomeworkRules.totalScore("۱۹٫۷۵")==19.75,"quarter");
    ok(HomeworkRules.totalScore("۱۹٫۶")==19.5,"quarter rounding");
    rejects(()->HomeworkRules.totalScore("۰"));
    HomeworkRules.validateText("عنوان تکلیف","توضیح کافی برای تکلیف");n++;
    rejects(()->HomeworkRules.validateText("ab","طول مناسب برای شرح"));
    rejects(()->HomeworkRules.validateText("عنوان","abc"));
    ok("17:30".equals(HomeworkRules.time24("۱۷:۳۰",true)),"24h");
    rejects(()->HomeworkRules.time24("۲۵:۳۰",true));
    rejects(()->HomeworkRules.time24("۱۳:۴۰",false));
    ok("".equals(HomeworkRules.time24("",false)),"time empty");
    ok("1405/08/25".equals(HomeworkRules.persianDate("۱۴۰۵/۰۸/۲۵")),"jalali");
    ok("".equals(HomeworkRules.persianDate("")),"empty date");
    rejects(()->HomeworkRules.persianDate("۱۴۰۵/۰۷/۳۱"));
    rejects(()->HomeworkRules.persianDate("۱۴۰۵/۱۳/۰۱"));
    rejects(()->HomeworkRules.persianDate("۱۴۰۵/۰۱/۰۰"));
    HomeworkRules.validateAttachment("image/png",650*1024,0);n++;
    HomeworkRules.validateAttachment("application/pdf",100,1);n++;
    rejects(()->HomeworkRules.validateAttachment("application/zip",100,0));
    rejects(()->HomeworkRules.validateAttachment("image/png",700*1024,0));
    rejects(()->HomeworkRules.validateAttachment("image/jpeg",100,2));
    System.out.println("A08 homework validation checks passed: "+n+"/"+n);
  }
}
