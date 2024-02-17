#include <jni.h>        // JNI header provided by JDK
#include <stdio.h>      // C Standard IO Header

#include "../../../../../../../../ino/src/ardwloop_jni.h"
#include "org_llschall_ardwloop_serial_jni_NativeEntry.h"
#include "fake.h"

JNIEnv *ENV;

JNIEXPORT void JNICALL Java_jni_NativeEntry_init(JNIEnv *env, jobject obj, int log, int reboot, int read, int post, int beforeK, int j) {
// int log ?
    ardw_begin(reboot,read, post,beforeK,j);
};

JNIEXPORT jint JNICALL Java_jni_NativeEntry_ping(JNIEnv *env, jobject obj) {
   ENV = env;
   return 2023;
};

JNIEXPORT jint JNICALL Java_jni_NativeEntry_print(JNIEnv *env, jobject obj) {
   ENV = env;
   back_print(1, "Returns 2023");
   return 2023;
};

JNIEXPORT jint JNICALL Java_jni_NativeEntry_check(JNIEnv *env, jobject obj, int i) {
   ENV = env;
   return entry_check(i);
};

JNIEXPORT void JNICALL Java_jni_NativeEntry_setup(JNIEnv *env, jobject obj) {
   ENV = env;
   entry_setup();
};

JNIEXPORT void JNICALL Java_jni_NativeEntry_loop(JNIEnv *env, jobject obj) {
   ENV = env;
   entry_loop();
};

JNIEXPORT void JNICALL Java_jni_NativeEntry_reset(JNIEnv *env, jobject obj) {
  ENV = env;
  entry_reset();
}

JNIEXPORT jchar JNICALL Java_jni_NativeEntry_prg(JNIEnv *env, jobject obj) {
   return PRG;
};

JNIEXPORT jint JNICALL Java_jni_NativeEntry_sc(JNIEnv *env, jobject obj) {
   return Sc;
};

JNIEXPORT jint JNICALL Java_jni_NativeEntry_rc(JNIEnv *env, jobject obj) {
   return Rc;
};

JNIEXPORT void JNICALL Java_jni_NativeEntry_importS(JNIEnv *env, jobject obj, jchar c, jint v, jint w, jint x, jint y, jint z) {

S.a.w = 128;

D* data;
  switch(c) {
    case 'a': data = &S.a;
  }
  data->v = v;
  data->w = w;
  data->x = x;
  data->y = y;
  data->z = z;
};

int export_d(D data, jchar d) {
  switch(d) {
    case 'v': return data.v;
    case 'w': return data.w;
    case 'x': return data.x;
    case 'y': return data.y;
    case 'z': return data.z;
  }
}

int export_v(V data, jchar v, jchar d) {
  switch(v) {
    case 'a':
      return export_d(data.a, d);
  }
}

JNIEXPORT jint JNICALL Java_jni_NativeEntry_exportR(JNIEnv *env, jobject obj, jchar v, jchar d) {
  return export_v(R, v, d);
}

void back_print(int log, char* str, va_list c) {
  char buf[32];
  snprintf(buf, sizeof(buf), str, c);
  back_print(log, buf);
}

void back_print(int log, char* str) {
  if(log > LOG_LEVEL) return;

  jclass cls = ENV->FindClass("jni/BackEntry");
  jmethodID id = ENV->GetStaticMethodID(cls, "msg", "(Ljava/lang/String;)V");

  jstring jstr = ENV->NewStringUTF(str);
  ENV->CallStaticVoidMethod(cls, id, jstr);
}

void back_delay(long ms) {
  jclass cls = ENV->FindClass("jni/BackEntry");
  jmethodID id = ENV->GetStaticMethodID(cls, "delay", "(J)V");
  ENV->CallStaticVoidMethod(cls, id, ms);
}

// https://www.arduino.cc/reference/en/language/functions/communication/serial/available/
int back_available() {
  jclass cls = ENV->FindClass("jni/BackEntry");
  jmethodID id = ENV->GetStaticMethodID(cls, "available", "()I");

  return ENV->CallStaticCharMethod(cls, id);
}

// https://www.arduino.cc/reference/en/language/functions/communication/serial/readbytes/
int back_read(char* buffer, int length) {
  jclass cls = ENV->FindClass("jni/BackEntry");
  jmethodID id = ENV->GetStaticMethodID(cls, "read", "()C");

  for(int i = 0; i < length; i++) {
    char c = ENV->CallStaticCharMethod(cls, id);
    buffer[i] = c;
  }
  return length;
}

void back_write(char c) {
    jclass cls = ENV->FindClass("jni/BackEntry");
    jmethodID id = ENV->GetStaticMethodID(cls, "write", "(C)V");

    ENV->CallStaticVoidMethod(cls, id, c);
}