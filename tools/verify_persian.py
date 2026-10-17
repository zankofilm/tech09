#!/usr/bin/env python3
"""Lightweight static checks; a real APK build still requires GitHub Actions."""
from pathlib import Path
from xml.etree import ElementTree as ET
import re
root=Path(__file__).resolve().parents[1]
java=(root/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/MainActivity.java').read_text(encoding='utf-8')
manifest=(root/'app/src/main/AndroidManifest.xml').read_text(encoding='utf-8')
strings=(root/'app/src/main/res/values/strings.xml').read_text(encoding='utf-8')
api=(root/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/SchoolApi.java').read_text(encoding='utf-8')
ET.parse(root/'app/src/main/AndroidManifest.xml')
ET.parse(root/'app/src/main/res/values/strings.xml')
ET.parse(root/'app/src/main/res/xml/locales_config.xml')
assert 'android:supportsRtl="true"' in manifest
assert 'android:localeConfig="@xml/locales_config"' in manifest
assert '<locale android:name="fa-IR"' in (root/'app/src/main/res/xml/locales_config.xml').read_text()
assert 'attachBaseContext(Context base)' in java and 'config.setLocale(persian)' in java
assert 'config.setLayoutDirection(persian)' in java
assert 'digitsFa' in java and 'localizedStatus' in java and 'persianError' in java
assert 'Native A05 ·' not in java
assert 'Android Keystore نگهداری' not in java
assert all(s in java for s in ['خانه','کلاس‌ها','حضور','نمرات','بیشتر','ورود با اثر انگشت'])
assert 'WebView' not in java and 'WebView' not in api
assert '/api/staff/login' in java and '/api/teacher/workspace' in java
assert 'رمز' in strings
print('Persian localization static checks: PASS')
