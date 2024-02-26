#include "ardwloop_utils.h"

int map_c(char c) {

  switch (c) {
  case '0':
    return 0;
  case '1':
    return 1;
  case '2':
    return 2;
  case '3':
    return 3;
  case '4':
    return 4;
  case '5':
    return 5;
  case '6':
    return 6;
  case '7':
    return 7;
  case '8':
    return 8;
  case '9':
    return 9;
  }
  return -1;
}

char map_i(int i) {

  switch (i) {
  case 0:
    return '0';
  case 1:
    return '1';
  case 2:
    return '2';
  case 3:
    return '3';
  case 4:
    return '4';
  case 5:
    return '5';
  case 6:
    return '6';
  case 7:
    return '7';
  case 8:
    return '8';
  case 9:
    return '9';
  }
  return '#';
}

void toBin(int dec, int a[12]) {

  int v = dec;

  for (int i = 0; i < 12; i++) {
    a[i] = v % 2;
    v = v / 2;
  }
}