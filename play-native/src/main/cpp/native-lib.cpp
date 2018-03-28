#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_gengjiawen_play_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++, 耿加稳";
    return env->NewStringUTF(hello.c_str());
}
