#include <stdlib.h>
#include <string.h>


int LOG_LEVEL = 9;

// C
#define byte jbyte

char debug[16];
char bf[] = { '#' };

// Arduino
void back_delay(long ms);
void delay(long ms) {
  back_delay(ms);
}
int LOW = 0;
int HIGH = 0;
int OUTPUT = 0;
void digitalWrite(int i, int j) {}
void pinMode(int i, int j) {}

void back_print(int log, char* str);
void back_print(int log, char* str, va_list c);

int back_available();
int back_read(char* c, int i);
void back_write(char c);

// lcd.h
void msg(const char* str) {
      back_print(3, str);
}
void err(const char* str) {}

class Lcd
{

public:
    void setCursor(int i, int j) {}
public:
    void print(char* c) {
      char str[30] = "LCD ";
      strcat(str, c);
      back_print(3, str);
    }
public:
    void clear() {}
public:
    void backlight() {}
public:
    void noBacklight() {}
}; // class
Lcd lcd;

class Serial
{

public:
    int available()
    {
        int i = back_available();
        back_print(5, "AVAILABLE %d", i);
        return i;
    }

public:
    int readBytes(char* c, int i)
    {
        int r = back_read(c, i);
        back_print(5, "=== READ %d", r);
        for(int i = 0; i < r; i++) {
          back_print(5, "= READ %c", c[i]);
        }
        back_print(5, "=======", r);
        return r;
    }

public:
    void write(char c)
    {
        back_print(5, "WRITE %c", c);
        back_write(c);
    }

public:
    void setTimeout(int i) {}

public:
    void begin(int i)
    {
        back_print(1, "BEGIN");
    }

}; // class

Serial Serial;
