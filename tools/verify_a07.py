#!/usr/bin/env python3
from pathlib import Path
import json,hashlib
p=Path(__file__).resolve().parents[1]
main=(p/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/MainActivity.java').read_text()
api=(p/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/SchoolApi.java').read_text()
manifest=(p/'app/src/main/AndroidManifest.xml').read_text()
grad=(p/'app/build.gradle').read_text()
workflow=(p/'.github/workflows/build-debug-apk.yml').read_text()
checks={
 "native_no_webview":'WebView' not in main,
 "teacher_report_readonly":'/api/teacher/continuous-p11?studentId=' in main,
 "report_uses_authorized_get":'SchoolApi.request(path,"GET",token,null)' in main,
 "report_answers":'پاسخ دانش‌آموز:' in main,
 "report_correct_answer":'پاسخ معیار:' in main,
 "report_question_outcome":'ExamDetailRules.classify(' in main,
 "report_count_from_actual_questions":'outcomes.add(ExamDetailRules.classify' in main,
 "report_back_to_exam":'back.setOnClickListener(v->showExamResults(selectedExam));' in main,
 "mcq_blank_handling":'selectedIndex' in main and 'بدون پاسخ' in main,
 "gradebook_still_present":'/api/staff/gradebook/entry' in main,
 "biometric_still_present":'unlockSavedSession' in main,
 "teacher_api_still_tls":'https://scooljavanrood.ir/app' in api,
 "only_persian":'new Locale("fa", "IR")' in main,
 "version10":"versionCode 10" in grad,
 "workflow_a07":"Madreseyar-Teacher-A07-Native-Debug-APK" in workflow,
 "sdk_setup_v4":"android-actions/setup-android@v4" in workflow,
 "rules_test_in_workflow":"Verify detailed exam statistics" in workflow,
 "icon_official":"@mipmap/ic_launcher" in manifest,
 "mipmaps_five": all((p/f'app/src/main/res/mipmap-{x}/ic_launcher.png').exists() for x in ('mdpi','hdpi','xhdpi','xxhdpi','xxxhdpi')),
 "server_unchanged":not (p/'server.mjs').exists(),
}
for k,v in checks.items():print(('PASS' if v else 'FAIL'),k)
assert all(checks.values()),'Static checks failed'
print('OK',len(checks),'static checks')
