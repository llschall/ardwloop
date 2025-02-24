

void fake_log(char *msg);
void fake_delay(unsigned long);
void fake_write_low(int);
void fake_write_high(int);
void fake_pin_out(int);
void fake_serial_begin(int);
int fake_available();
int fake_read(char *, int);
int fake_write(char);
void fake_post(bool);

void back_print(int log, char *str);
void back_print(int log, char *str, va_list c);

void log_dbg(char *str);
void log_dbg(char *str, va_list c);

jclass find_back_entry_class(JNIEnv *env);

int import_S(char* str, int size, char c, int v, int w, int x, int y, int z);
int export_d(D data, jchar d);
int export_v(V data, jchar v, jchar d);