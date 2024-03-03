
#include "ardwloop_read.h"

#include "ardwloop_buffer.h"
#include "ardwloop_core.h"

int impl_read0(const int n) {

  log("IMPL READ");

  char arr[n];
  int r = func_read(arr, n);
  for (int i = 0; i < r; i++) {
    buffer_set(i, arr[i]);
  }
  return r;
}