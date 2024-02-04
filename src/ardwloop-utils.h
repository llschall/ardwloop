#ifndef ardwloop_utils_h
#define ardwloop_utils_h

//const long BAUD = 300;
//const long BAUD = 600;
//const long BAUD = 1200;
//const long BAUD = 2400;
//const long BAUD = 4800;
const long BAUD = 9600;
//const long BAUD = 14400;
//const long BAUD = 19200;
//const long BAUD = 28800;
//const long BAUD = 38400;
//const long BAUD = 57600;
//const long BAUD = 115200;

int map_c(char c);
char map_i(int i);
void toBin(int dec, int a[12]);

#endif