#include <jni.h>        // JNI header provided by JDK
#include <stdio.h>      // C Standard IO Header

#include "../../../../../../../../ino/src/ardwloop_jni.h"
#include "org_llschall_ardwloop_serial_jni_NativeEntry.h"

JNIEnv *ENV;

JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_serial_jni_NativeEntry_ping(JNIEnv *env, jobject obj) {
   return 2024;
 };
