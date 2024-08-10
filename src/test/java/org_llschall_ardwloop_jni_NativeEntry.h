/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_llschall_ardwloop_jni_NativeEntry */

#ifndef _Included_org_llschall_ardwloop_jni_NativeEntry
#define _Included_org_llschall_ardwloop_jni_NativeEntry
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    inject
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_inject
  (JNIEnv *, jobject);

/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    ping
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_ping
  (JNIEnv *, jobject, jint);

/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    pong
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_pong
  (JNIEnv *, jobject, jint);

/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    setup
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_setup
  (JNIEnv *, jobject);

/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    loop
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_loop
  (JNIEnv *, jobject);

/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    prg
 * Signature: ()C
 */
JNIEXPORT jchar JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_prg
  (JNIEnv *, jobject);

/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    rc
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_rc
  (JNIEnv *, jobject);

/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    sc
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_sc
  (JNIEnv *, jobject);

/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    delayRead
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_delayRead
  (JNIEnv *, jobject);

/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    delayPost
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_delayPost
  (JNIEnv *, jobject);

/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    reset
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_reset
  (JNIEnv *, jobject);

/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    exportR
 * Signature: (CC)I
 */
JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_exportR
  (JNIEnv *, jobject, jchar, jchar);

/*
 * Class:     org_llschall_ardwloop_jni_NativeEntry
 * Method:    importS
 * Signature: (CIIIII)V
 */
JNIEXPORT void JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_importS
  (JNIEnv *, jobject, jchar, jint, jint, jint, jint, jint);

#ifdef __cplusplus
}
#endif
#endif