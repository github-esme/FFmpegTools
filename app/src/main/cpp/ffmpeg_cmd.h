/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class JniTest_FFmpegCmd */

#ifndef _Included_FFmpeg_Cmd
#define _Included_FFmpeg_Cmd
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jint JNICALL Java_com_example_ffmpegtools_MainActivity_exec(JNIEnv *, jclass, jint, jobjectArray);

JNIEXPORT void JNICALL Java_com_example_ffmpegtools_MainActivity_exit(JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif

void ffmpeg_progress(float progress);
void ffmpeg_complete(int errorCode);