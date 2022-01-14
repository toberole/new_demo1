#include <dlfcn.h>  // for dladdr
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef NO_INSTRUMENT
#define NO_INSTRUMENT __attribute__((no_instrument_function))
#endif

//extern "C" __attribute__((no_instrument_function)) void __cyg_profile_func_enter(void *callee, void *caller) {
//    Dl_info info;
//    if (dladdr(callee, &info)) {
//        int status;
//        const char *name;
//        char *demangled = abi::__cxa_demangle(info.dli_sname, NULL, 0, &status);
//        if (status == 0) {
//            name = demangled ? demangled : "[not demangled]";
//        } else {
//            name = info.dli_sname ? info.dli_sname : "[no dli_sname nd std]";
//        }
//
//        printf("enter %s (%s)\n", name, info.dli_fname);
//
//        if (demangled) {
//            free(demangled);
//            demangled = NULL;
//        }
//    }
//}
//
//extern "C" __attribute__((no_instrument_function)) void __cyg_profile_func_exit(void *callee, void *caller) {
//    Dl_info info;
//    if (dladdr(callee, &info)) {
//        int status;
//        const char *name;
//        char *demangled = abi::__cxa_demangle(info.dli_sname, NULL, 0, &status);
//        if (status == 0) {
//            name = demangled ? demangled : "[not demangled]";
//        } else {
//            name = info.dli_sname ? info.dli_sname : "[no dli_sname and std]";
//        }
//        printf("exit %s (%s)\n", name, info.dli_fname);
//
//        if (demangled) {
//            free((void *)demangled);
//            demangled = NULL;
//        }
//    }
//}

