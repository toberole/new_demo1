#include "test.h"
#include "log.h"

void test1() {
    LOGI("c test1");
}

int test2(int i1, int i2) {
    LOGI("c test2 i1: %d,i2: %d", i1, i2);
}

void test3(int arr[], int len) {
    LOGI("c test3");
}

void test4(int *p) {
    LOGI("c test4");
}

void test5(char ch) {
    LOGI("c test5 ch: %d",ch);
}

void test6(char chs[], int len) {
    LOGI("c test6");
}