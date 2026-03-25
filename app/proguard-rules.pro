# ===== Debugging =====
# -keepattributes SourceFile,LineNumberTable
# -renamesourcefileattribute SourceFile

# ===== Власні класи (Firebase моделі) =====
-keep class com.vadym.gvd.bestfriendskotlin.** { *; }
-keepattributes Signature, *Annotation*, RuntimeVisibleAnnotations, AnnotationDefault

# ===== Firebase SDK =====
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# ===== Suppressed warnings =====
-dontwarn android.media.LoudnessCodecController$OnLoudnessCodecUpdateListener
-dontwarn android.media.LoudnessCodecController
-dontwarn com.google.protobuf.**