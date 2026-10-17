#!/usr/bin/env python3
from pathlib import Path
import re,sys,subprocess
root=Path(__file__).resolve().parents[1]
pkg=root/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher'
source=(pkg/'MainActivity.java').read_text()
api=(pkg/'SchoolApi.java').read_text()
workflow=(root/'.github/workflows/build-debug-apk.yml').read_text()
checks={
 'no_WebView': 'WebView' not in source,
 'safe_read_state': 'SchoolApi.request("/api/state","GET",token,null)' in source,
 'only_scoped_write_homework': 'SchoolApi.request("/api/staff/homework/p652/save","POST",token,body)' in source,
 'grade_submission': 'SchoolApi.request("/api/staff/homework/grade","POST",token,body)' in source,
 'secure_student_attachment': 'SchoolApi.request("/api/files/"+key,"GET",token,null)' in source,
 'no_unsafe_state_POST': 'SchoolApi.request("/api/state","POST"' not in source,
 'school_year_filter': 'year.equals(s(h,"academicYear"))' in source,
 'teacher_assignments': 'hwAssigned(classId,subject)' in source,
 'publish_default_draft': 'publish.setChecked(editing' in source and '"publicationState",publish.isChecked()?"published":"draft"' in source,
 'confirm_score': '.setTitle("تأیید نمره")' in source,
 'confirm_publish': 'تأیید "+action' in source,
 'review_answers': 'showHomeworkDetail(JSONObject h)' in source,
 'semester': '"semester1":"semester2"' in source,
 'jalali': 'PersianCalendar' in source,
 'no_duplicate_dataset': 'private JSONObject homeworkState = new JSONObject()' in source,
 'max_20mb_scoped': '("/api/state".equals(path) ? 20 : 5)' in api,
 'github_test': 'Verify homework validation rules' in workflow,
 'min_sdk_26': 'minSdk 26' in (root/'app/build.gradle').read_text(),
 'icon_preserved': (root/'app/src/main/res/mipmap-xxxhdpi/ic_launcher.png').exists(),
}
for k,v in checks.items():print(('PASS' if v else 'FAIL'),k)
if not all(checks.values()):sys.exit(1)
print('STATIC OK',len(checks))
