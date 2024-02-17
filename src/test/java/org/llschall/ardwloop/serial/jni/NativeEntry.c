#include <jni.h>        // JNI header provided by JDK
#include <stdio.h>      // C Standard IO Header

#include "../../../../../../../../ino/src/ardwloop_jni.h"
#include "org_llschall_ardwloop_serial_jni_NativeEntry.h"

JNIEnv *ENV;

JNIEXPORT void JNICALL Java_jni_NativeEntry_init(JNIEnv *env, jobject obj, int log, int reboot, int read, int post, int beforeK, int j) {};

JNIEXPORT jint JNICALL Java_jni_NativeEntry_ping(JNIEnv *env, jobject obj) {
   return 2024;
 };

JNIEXPORT jint JNICALL Java_jni_NativeEntry_print(JNIEnv *env, jobject obj) {
   return 2024;
};

JNIEXPORT jint JNICALL Java_jni_NativeEntry_check(JNIEnv *env, jobject obj, int i) {
   return 2024;
};

JNIEXPORT void JNICALL Java_jni_NativeEntry_setup(JNIEnv *env, jobject obj) {
   entry_setup();
};

JNIEXPORT void JNICALL Java_jni_NativeEntry_loop(JNIEnv *env, jobject obj) {
   entry_loop();
};

JNIEXPORT jchar JNICALL Java_jni_NativeEntry_prg(JNIEnv *env, jobject obj) {
   return 'A';
};

JNIEXPORT jint JNICALL Java_jni_NativeEntry_sc(JNIEnv *env, jobject obj) {
   return 2024;
};

JNIEXPORT jint JNICALL Java_jni_NativeEntry_rc(JNIEnv *env, jobject obj) {
   return 2024;
};
