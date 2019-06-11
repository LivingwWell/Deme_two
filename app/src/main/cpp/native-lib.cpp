#include <jni.h>
#include <string.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lee_deme_1two_fragment_FragmentB_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());

}

extern "C" JNIEXPORT jstring JNICALL

Java_com_example_lee_deme_1two_fragment_FragmentC_getStr(JNIEnv *env,jobject) {
    std::string hello="fragmentC";
    return env->NewStringUTF(hello.c_str());
}
