#include <jni.h>
#include <string>
#include <android/log.h>

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "native-lib", __VA_ARGS__))

extern "C" JNIEXPORT jstring

JNICALL
Java_com_gengjiawen_play_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++, 耿加稳";
    int a = 1;
    LOGI("int %d, string: %s", a, hello.c_str());
    return env->NewStringUTF(hello.c_str());
}
