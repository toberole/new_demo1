cmake_minimum_required(VERSION 3.10.2)

project("mylib")

include_directories(${CMAKE_CURRENT_LIST_DIR})

aux_source_directory(${CMAKE_CURRENT_LIST_DIR} SRC_FILES)

add_library(test-lib SHARED ${SRC_FILES})

target_link_libraries(
        test-lib
        log)