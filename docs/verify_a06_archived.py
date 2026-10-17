#!/usr/bin/env python3
from pathlib import Path
import re, json, subprocess
p=Path(__file__).resolve().parents[1]
java=(p/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/MainActivity.java').read_text()
api=(p/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/SchoolApi.java').read_text()
build=(p/'app/build.gradle').read_text()
wf=(p/'.github/workflows/build-debug-apk.yml').read_text()
checks={
 "no_webview":'WebView' not in java and 'WebView' not in api,
 "gradebook_get": '"/api/staff/gradebook"+gradebookQuery' in java,
 "gradebook_entry_post": '"/api/staff/gradebook/entry"' in java,
 "gradebook_approve_post": '"/api/staff/gradebook/approve-continuous"' in java,
 "final_mark_get_post":java.count('"/api/staff/gradebook/final-mark"')>=2,
 "native_login":"/api/staff/login" in java,
 "biometric_kept":"unlockSavedSession" in java,
 "attendance_kept":"/api/teacher/attendance/submit" in java,
 "school_api_tls":'https://scooljavanrood.ir/app' in api,
 "force_persian_locale":'new Locale("fa", "IR")' in java,
 "version_9":'versionCode 9' in build,
 "workflow_a06":'Madreseyar-Teacher-A06-Native-Debug-APK' in wf,
 "rules_test_in_action":'Verify gradebook validation rules' in wf,
 "no_fake_homework":'مرکز تکالیف هنوز به‌صورت کامل منتقل نشده‌اند' in java,
 "read_only_when_unconfigured":'configured && !locked' in java,
 "no_server_rewrite":not (p/'server.mjs').exists(),
}
for k,v in checks.items():print(('PASS' if v else 'FAIL')+' '+k)
assert all(checks.values()), 'STATIC CHECK FAILURE'
print('\nOK',len(checks),'static tests')
