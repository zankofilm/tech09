package ir.scooljavanrood.madreseyar.teacher;

import java.util.List;
import java.util.Locale;

/** Pure rules for safely describing a finalized exam; no API or Android dependencies. */
final class ExamDetailRules {
    enum Outcome { CORRECT, WRONG, PARTIAL, UNANSWERED }
    static Outcome classify(String type, String status, boolean answered, double earned, double max) {
        if (!answered) return Outcome.UNANSWERED;
        String s=status == null ? "" : status.trim().toLowerCase(Locale.ROOT);
        if ("correct".equals(s)) return Outcome.CORRECT;
        if ("partial".equals(s)) return Outcome.PARTIAL;
        if ("incorrect".equals(s) || "wrong".equals(s)) return Outcome.WRONG;
        if (Double.isFinite(max) && max > 0 && Double.isFinite(earned)) {
            if (earned >= max-0.001) return Outcome.CORRECT;
            if (earned > 0) return Outcome.PARTIAL;
        }
        return Outcome.WRONG;
    }
    static final class Stats {
        final int total, answered, correct, wrong, partial, unanswered;
        Stats(int total,int answered,int correct,int wrong,int partial,int unanswered){
            this.total=total;this.answered=answered;this.correct=correct;
            this.wrong=wrong;this.partial=partial;this.unanswered=unanswered;
        }
    }
    static Stats summarize(List<Outcome> items) {
        int correct=0,wrong=0,partial=0,unanswered=0;
        for (Outcome x:items) {
            switch(x){case CORRECT:correct++;break;case WRONG:wrong++;break;
                case PARTIAL:partial++;break;case UNANSWERED:unanswered++;break;}
        }
        int total=items.size();return new Stats(total,total-unanswered,correct,wrong,partial,unanswered);
    }
}
