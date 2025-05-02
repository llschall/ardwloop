#include <jni.h>   // JNI header provided by JDK
#include <stdio.h> // C Standard IO Header

#include <ardwloop_buffer.cpp>
#include <ardwloop_core.cpp>
#include <ardwloop_jni.cpp>
#include <ardwloop_read.cpp>
#include <ardwloop_utils.cpp>

#include "NativeEntry.h"
#include "org_llschall_ardwloop_jni_NativeEntry.h"

JNIEnv *ENV;

bool LOG_DEBUG = false;

int env_not_found_error() {
    return 1/0;
}

int class_not_found_error() {
    return 1/0;
}

JNIEXPORT void JNICALL
Java_org_llschall_ardwloop_jni_NativeEntry_inject(JNIEnv *env, jobject obj) {

  fct_inject(&fake_log, &fake_delay, &fake_write_low, &fake_write_high,
             &fake_pin_out, &fake_post);

  fct_inject_serial(&fake_serial_begin, &fake_available, &fake_read, &fake_write);
}

void fake_log(char *msg) { log_dbg(msg); }

void fake_delay(unsigned long){};
void fake_write_low(int){};
void fake_write_high(int){};
void fake_pin_out(int){};
void fake_serial_begin(int){};
void fake_post(bool){ return true;};

int fake_available() {

  if (ENV == NULL) env_not_found_error();
  jclass cls = ENV->FindClass("org/llschall/ardwloop/jni/BackEntry");
  if (cls == NULL) class_not_found_error();

  jmethodID id = ENV->GetStaticMethodID(cls, "available", "()I");
  jint i = ENV->CallStaticCharMethod(cls, id);

  return i;
};

int fake_read(char *buffer, int n) {

  if (ENV == NULL) env_not_found_error();
  jclass cls = ENV->FindClass("org/llschall/ardwloop/jni/BackEntry");
  if (cls == NULL) class_not_found_error();

  jmethodID id = ENV->GetStaticMethodID(cls, "read", "()C");

  for (int i = 0; i < n; i++) {
    char c = ENV->CallStaticCharMethod(cls, id);

    if (LOG_DEBUG)
      log_dbg("fake_read -> %c", c);
    buffer[i] = c;
  }
  return n;
};

int fake_write(char c) {

  if (ENV == NULL) env_not_found_error();
  jclass cls = ENV->FindClass("org/llschall/ardwloop/jni/BackEntry");
  if (cls == NULL) class_not_found_error();

  if (LOG_DEBUG)
    log_dbg("fake_write -> %c", c);

  jmethodID id = ENV->GetStaticMethodID(cls, "write", "(C)V");
  ENV->CallStaticCharMethod(cls, id, c);
  return 1;
};

//////////////////////

JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_init(JNIEnv *env, jobject obj) {

  ENV = env;
  jclass cls = ENV->FindClass("org/llschall/ardwloop/jni/BackEntry");
  if (cls == NULL) class_not_found_error();

  return 1010;
}

JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_ping(
    JNIEnv *env, jobject obj, jint i) {

  ENV = env;
  jclass cls = ENV->FindClass("org/llschall/ardwloop/jni/BackEntry");
  if (cls == NULL) class_not_found_error();

  return i;
};

JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_pong(
    JNIEnv *env, jobject obj, jint i) {

  ENV = env;
  jclass cls = ENV->FindClass("org/llschall/ardwloop/jni/BackEntry");
  if (cls == NULL) class_not_found_error();

  int j = i * 2;
  log_dbg("DEBUG i*2 = %d", j);

  jmethodID id = ENV->GetStaticMethodID(cls, "pong", "(I)I");
  return ENV->CallStaticCharMethod(cls, id, i);
};

JNIEXPORT jint JNICALL
Java_org_llschall_ardwloop_jni_NativeEntry_print(JNIEnv *env, jobject obj) {

  ENV = env;
  jclass cls = ENV->FindClass("org/llschall/ardwloop/jni/BackEntry");
  if (cls == NULL) class_not_found_error();

  back_print(1, "Returns 2024");
  return 2024;
};

JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_check(
    JNIEnv *env, jobject obj, int i) {

  ENV = env;
  jclass cls = ENV->FindClass("org/llschall/ardwloop/jni/BackEntry");
  if (cls == NULL) class_not_found_error();

  return entry_check(i);
};

JNIEXPORT void JNICALL
Java_org_llschall_ardwloop_jni_NativeEntry_setup(JNIEnv *env, jobject obj, long baud) {
    ENV = env;
    if (ENV == NULL) env_not_found_error();
  entry_setup(baud);
};

JNIEXPORT void JNICALL
Java_org_llschall_ardwloop_jni_NativeEntry_loop(JNIEnv *env, jobject obj) {

  ENV = env;
  jclass cls = ENV->FindClass("org/llschall/ardwloop/jni/BackEntry");
  if (cls == NULL) class_not_found_error();

  entry_loop();
};

JNIEXPORT void JNICALL
Java_org_llschall_ardwloop_jni_NativeEntry_reset(JNIEnv *env, jobject obj) {

  ENV = env;
  jclass cls = ENV->FindClass("org/llschall/ardwloop/jni/BackEntry");
  if (cls == NULL) class_not_found_error();

  entry_reset();
}

JNIEXPORT jchar JNICALL
Java_org_llschall_ardwloop_jni_NativeEntry_prg(JNIEnv *env, jobject obj) {
  return entry_prg();
};

JNIEXPORT jint JNICALL
Java_org_llschall_ardwloop_jni_NativeEntry_resetPin(JNIEnv *env, jobject obj) {
  return entry_reset_pin();
};

JNIEXPORT jint JNICALL
Java_org_llschall_ardwloop_jni_NativeEntry_rc(JNIEnv *env, jobject obj) {
  return entry_rc();
};

JNIEXPORT jint JNICALL
Java_org_llschall_ardwloop_jni_NativeEntry_sc(JNIEnv *env, jobject obj) {
  return entry_sc();
};

JNIEXPORT jint JNICALL
Java_org_llschall_ardwloop_jni_NativeEntry_delayRead(JNIEnv *env, jobject obj) {
  return entry_delay_read();
};

JNIEXPORT jint JNICALL
Java_org_llschall_ardwloop_jni_NativeEntry_delayPost(JNIEnv *env, jobject obj) {
  return entry_delay_post();
};

JNIEXPORT void JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_importS(
    JNIEnv *env, jobject obj, jstring jstr, jint size, jchar c, jint v, jint w, jint x, jint y, jint z) {
    char* str;
    str = env -> GetStringUTFChars(jstr, 0);
    import_S(str, size, c, v, w, x, y, z);
    //env -> ReleaseStringUTFChars(jstr, str);
};

JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_exportR(
    JNIEnv *env, jobject obj, jchar v, jchar d) {
  V *R = entry_r();
  return export_v(*R, v, d);
}

//////////////////////////

void back_print(int log, char *str, va_list c) {
  char buf[32];
  snprintf(buf, sizeof(buf), str, c);
  back_print(log, buf);
}

void back_print(int log, char *str) {

  if (ENV == NULL) env_not_found_error();
  jclass cls = ENV->FindClass("org/llschall/ardwloop/jni/BackEntry");
  if (cls == NULL) class_not_found_error();

  jmethodID id = ENV->GetStaticMethodID(cls, "msg", "(Ljava/lang/String;)V");

  jstring jstr = ENV->NewStringUTF(str);
  ENV->CallStaticVoidMethod(cls, id, jstr);
}

void log_dbg(char *str, va_list c) { back_print(0, str, c); }

void log_dbg(char *str) { back_print(0, str); }

int import_S(char *str, int size, char c, int v, int w, int x, int y, int z) {

  entry_str(str, size);

  V *S = entry_s();

  D *data;
  switch (c) {
  case 'a':
    data = &S->a;
  }
  data->v = v;
  data->w = w;
  data->x = x;
  data->y = y;
  data->z = z;

  return 0;
}

int export_d(D data, jchar d) {
  switch (d) {
  case 'v':
    return data.v;
  case 'w':
    return data.w;
  case 'x':
    return data.x;
  case 'y':
    return data.y;
  case 'z':
    return data.z;
  }
}

int export_v(V data, jchar v, jchar d) {
  switch (v) {
  case 'a':
    return export_d(data.a, d);
  }
}