# Room supplies its own consumer rules.
-keepattributes Signature,InnerClasses,EnclosingMethod
-keepattributes *Annotation*

# Avoid leaking reminder details through verbose release logs.
-assumenosideeffects class android.util.Log {
    public static int d(...);
    public static int v(...);
}
