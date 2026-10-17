package ir.scooljavanrood.madreseyar.teacher;
public class TestGradebookRules {
    private static int checks=0;
    private static void eq(Object found,Object expected) {checks++;if(!expected.equals(found))throw new AssertionError("expected="+expected+" found="+found);}
    private static void yes(boolean okay){checks++;if(!okay)throw new AssertionError("failed check " + checks);}
    public static void main(String[] a){
        eq(GradebookRules.readScore("۱۴٫۵"),14.5d);
        eq(GradebookRules.readScore("١٢.٢٥"),12.25d);
        eq(GradebookRules.readScore("0"),0d);
        eq(GradebookRules.readScore("20.00"),20d);
        yes(GradebookRules.readScore("20.123")==null);
        yes(GradebookRules.readScore("abc")==null);
        yes(GradebookRules.readScore("-1")==null);
        yes(GradebookRules.readScore("")==null);
        yes(GradebookRules.within(0d,15d));
        yes(GradebookRules.within(15d,15d));
        yes(!GradebookRules.within(15.01d,15d));
        yes(!GradebookRules.within(0d,0d));
        eq(GradebookRules.finalCeiling(5d),15d);
        eq(GradebookRules.finalCeiling(8d),12d);
        eq(GradebookRules.finalCeiling(19.75d),0.25d);
        eq(GradebookRules.formatScore(12.5d),"12.5");
        eq(GradebookRules.formatScore(20d),"20");
        System.out.println("OK " + checks + " gradebook rules tests");
    }
}
