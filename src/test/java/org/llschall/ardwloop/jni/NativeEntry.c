#include <jni.h>        // JNI header provided by JDK
#include <stdio.h>      // C Standard IO Header

#include <ardwloop_jni.cpp>
#include <ardwloop_core.cpp>
#include <ardwloop_buffer.cpp>

#include "NativeEntry.h"
#include "org_llschall_ardwloop_jni_NativeEntry.h"

JNIEnv *ENV;

jclass findBackEntryClass() {
    return ENV->FindClass("org/llschall/ardwloop/serial/jni/BackEntry");
};

JNIEXPORT void JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_inject(JNIEnv *env, jobject obj) {

fct_init(
      &fake_delay,
      &fake_write_low,&fake_write_high,&fake_pin_out,
      &fake_serial_begin,&fake_available,&fake_read,&fake_write
      );
}
      void fake_delay(unsigned long){};
      void fake_write_low(int){};
      void fake_write_high(int){};
      void fake_pin_out(int){};
      void fake_serial_begin(int){};

      int fake_available(){
  jclass cls = ENV->FindClass("org/llschall/ardwloop/serial/jni/BackEntry");
  jmethodID id = ENV->GetStaticMethodID(cls, "available", "()I");

  return ENV->CallStaticCharMethod(cls, id);
      };

      int fake_read(int i) {};

      int fake_write(char c) {};

//////////////////////

JNIEXPORT void JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_init(JNIEnv *env, jobject obj, int reboot, int read, int post, int beforeK, int j) {
    ENV = env;
    entry_begin(reboot,read, post,beforeK,j);
};

JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_ping(JNIEnv *env, jobject obj, jint i) {
   ENV = env;
   return i;
};

JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_pong(JNIEnv *env, jobject obj, jint i) {
   ENV = env;
   jclass cls = findBackEntryClass();
   jmethodID id = ENV->GetStaticMethodID(cls, "pong", "(I)I");
   return ENV->CallStaticCharMethod(cls, id, i);
};

JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_print(JNIEnv *env, jobject obj) {
   ENV = env;
   back_print(1, "Returns 2023");
   return 2023;
};

JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_check(JNIEnv *env, jobject obj, int i) {
   ENV = env;
   return entry_check(i);
};

JNIEXPORT void JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_setup(JNIEnv *env, jobject obj) {
   ENV = env;
   entry_setup();
};

JNIEXPORT void JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_loop(JNIEnv *env, jobject obj) {
   ENV = env;
   entry_loop();
};

JNIEXPORT void JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_reset(JNIEnv *env, jobject obj) {
  ENV = env;
  entry_reset();
}

JNIEXPORT jchar JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_prg(JNIEnv *env, jobject obj) {
   return entry_prg();
};

JNIEXPORT void JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_importS(JNIEnv *env, jobject obj, jchar c, jint v, jint w, jint x, jint y, jint z) {

V* S = entry_s();

S->a.w = 128;

D* data;
  switch(c) {
    case 'a': data = &S->a;
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

JNIEXPORT jint JNICALL Java_org_llschall_ardwloop_jni_NativeEntry_exportR(JNIEnv *env, jobject obj, jchar v, jchar d) {

  V* R = entry_r();
  return export_v(*R, v, d);
}

void back_print(int log, char* str, va_list c) {
  char buf[32];
  snprintf(buf, sizeof(buf), str, c);
  back_print(log, buf);
}

void back_print(int log, char* str) {
  //if(log > LOG_LEVEL) return;

  jclass cls = ENV->FindClass("org/llschall/ardwloop/serial/jni/BackEntry");
  jmethodID id = ENV->GetStaticMethodID(cls, "msg", "(Ljava/lang/String;)V");

  jstring jstr = ENV->NewStringUTF(str);
  ENV->CallStaticVoidMethod(cls, id, jstr);
}

void back_delay(long ms) {
  jclass cls = ENV->FindClass("org/llschall/ardwloop/serial/jni/BackEntry");
  jmethodID id = ENV->GetStaticMethodID(cls, "delay", "(J)V");
  ENV->CallStaticVoidMethod(cls, id, ms);
}

// https://www.arduino.cc/reference/en/language/functions/communication/serial/available/
int back_available() {
  jclass cls = ENV->FindClass("org/llschall/ardwloop/serial/jni/BackEntry");
  jmethodID id = ENV->GetStaticMethodID(cls, "available", "()I");

  return ENV->CallStaticCharMethod(cls, id);
}

// https://www.arduino.cc/reference/en/language/functions/communication/serial/readbytes/
int back_read(char* buffer, int length) {
  jclass cls = ENV->FindClass("org/llschall/ardwloop/serial/jni/BackEntry");
  jmethodID id = ENV->GetStaticMethodID(cls, "read", "()C");

  for(int i = 0; i < length; i++) {
    char c = ENV->CallStaticCharMethod(cls, id);
    buffer[i] = c;
  }
  return length;
}

void back_write(char c) {
    jclass cls = ENV->FindClass("org/llschall/ardwloop/serial/jni/BackEntry");
    jmethodID id = ENV->GetStaticMethodID(cls, "write", "(C)V");

    ENV->CallStaticVoidMethod(cls, id, c);
}