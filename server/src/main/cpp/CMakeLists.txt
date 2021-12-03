cmake_minimum_required(VERSION 3.10.2)

project("server")

include_directories(${CMAKE_CURRENT_LIST_DIR}/include)

aux_source_directory(${CMAKE_CURRENT_LIST_DIR}/src SRC_FILES)

add_library(server-lib SHARED ${SRC_FILES})

target_link_libraries(
        server-lib
        log)