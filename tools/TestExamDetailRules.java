package ir.scooljavanrood.madreseyar.teacher;
import java.util.Arrays;
import static ir.scooljavanrood.madreseyar.teacher.ExamDetailRules.*;
public final class TestExamDetailRules {
    static int n=0;
    static void check(boolean ok,String message){n++;if(!ok)throw new AssertionError(message);}
    public static void main(String[] args){
        check(classify("mcq","incorrect",false,0,1)==Outcome.UNANSWERED,"Unanswered MCQ cannot be wrong");
        check(classify("mcq","correct",true,1,1)==Outcome.CORRECT,"Correct MCQ");
        check(classify("mcq","incorrect",true,0,1)==Outcome.WRONG,"Incorrect MCQ");
        check(classify("descriptive","partial",true,0.5,2)==Outcome.PARTIAL,"Partial essay");
        check(classify("descriptive","",true,1,2)==Outcome.PARTIAL,"Points infer partial");
        check(classify("descriptive","",true,2,2)==Outcome.CORRECT,"Points infer correct");
        check(classify("descriptive","",true,0,2)==Outcome.WRONG,"Points infer wrong");
        check(classify("descriptive","correct",false,2,2)==Outcome.UNANSWERED,"No answer always unanswered");
        check(classify("mixed","",true,0,Double.NaN)==Outcome.WRONG,"Unknown score conservative");
        Stats s=summarize(Arrays.asList(Outcome.CORRECT,Outcome.CORRECT,Outcome.WRONG,Outcome.PARTIAL,Outcome.UNANSWERED));
        check(s.total==5,"total");check(s.answered==4,"answered");check(s.correct==2,"correct");
        check(s.wrong==1,"wrong");check(s.partial==1,"partial");check(s.unanswered==1,"unanswered");
        Stats z=summarize(Arrays.asList());check(z.total==0&&z.answered==0,"empty");
        System.out.println(n+" exam detail tests passed");
    }
}
