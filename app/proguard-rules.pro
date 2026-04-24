# Xposed module entry point (loaded by reflection)
-keep class io.github.mirukurusan.wificountrycode.xposed.HookEntry { *; }

# Xposed hook classes
-keep class io.github.mirukurusan.wificountrycode.xposed.BaseHook { *; }
-keep class io.github.mirukurusan.wificountrycode.xposed.hook.** { *; }

# Configuration classes (serialized/deserialized by Xposed)
-keep class io.github.mirukurusan.wificountrycode.config.** { *; }
-keep class io.github.mirukurusan.wificountrycode.data.Configuration { *; }

# kotlinx.serialization
-keepattributes *AnnotationSerializerMarker*
-keep class kotlinx.serialization.** { *; }