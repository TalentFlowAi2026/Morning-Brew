# Morning Brew

تطبيق أندرويد بسيط لطلب المشروبات، باستخدام Java وواجهات XML.

المشروبات: Tea 10، Coffee 15، Cappuccino 20، Latte 25.
يمكن اختيار مشروب واحد وتحديد الكمية والحجم وإضافة السكر أو الحليب.
الأحجام: صغير ×1، متوسط ×1.5، كبير ×2. السكر +2 والحليب +3 لكل كوب.
الإجمالي يتحدث مباشرة. بعد تأكيد الطلب تظهر رسالة ثم تعود القيم للبداية.
الطلب غير المكتمل يبقى محفوظًا عند تدوير الشاشة أو إعادة فتح التطبيق.

## التشغيل

```sh
git clone https://github.com/TalentFlowAi2026/Morning-Brew.git
```

افتح مجلد المشروع في Android Studio، واستخدم JDK 21 مع SDK 36، ثم Sync وRun.
الحزمة: com.yahya.brewmorning. الحد الأدنى Android 6.0.

للبناء على Windows: `gradlew.bat assembleDebug`
لفحص المشروع: `gradlew.bat lintDebug connectedDebugAndroidTest`
على macOS وLinux استخدم `./gradlew` بدلًا من `gradlew.bat`.

ملف APK وفيديو التشغيل موجودان داخل deliverables.
