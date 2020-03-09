/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// OpenGL ES 2.0 code

#include <jni.h>
#include <android/log.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "../../../../lib/fluid/include/Fluid.h"
#include <time.h>
#include <jni.h>
#include <jni.h>
#include <utility>
#include <memory>

#define  LOG_TAG    "fluid_sim"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)


bool userHasTouchedScreen = false;
void *fluid;


static double now_ms(void) {

    struct timespec res;
    clock_gettime(CLOCK_REALTIME, &res);
    return 1000.0 * res.tv_sec + (double) res.tv_nsec / 1e6;

}


extern "C" JNIEXPORT void JNICALL
Java_com_android_ui_fluidSimulation_FluidLib_init(JNIEnv *env, jobject obj, jint width,
                                                  jint height) {

    fluid = fluidCreate((int) width, (int) height);

}


extern "C" JNIEXPORT void JNICALL
Java_com_android_ui_fluidSimulation_FluidLib_fluidDestroy(JNIEnv *env, jobject clazz) {


    //fluidDestroy(fluid);
    delete fluid;

}



extern "C" JNIEXPORT void JNICALL
Java_com_android_ui_fluidSimulation_FluidLib_step(JNIEnv *env, jobject obj) {

    double t = now_ms();

    if (!userHasTouchedScreen) {

        double t_s = t / 1000;

        double x = sin(t_s) * 500 + 500; // x
        double y = cos(t_s) * 500 + 1000; // y

        fluidOnPointerDown(
                fluid,
                -1,
                TOUCH,
                x, // x
                y, // y
                0, 0, // button flags
                1.0, // pressure
                0.019607844, // radius
                0, 0, 0 // unused
        );
    } else {
        fluidOnPointerUp(
                fluid,
                -1,
                TOUCH,
                0, 0, // x, y
                0, 0, // button flags
                0, // pressure
                0, // radius
                0, 0, 0 // unused
        );
    }

    fluidOnFrame(fluid, t);

}


extern "C" JNIEXPORT void JNICALL
Java_com_android_ui_fluidSimulation_FluidLib_onTouchDown(JNIEnv * /*env*/, jobject obj, jint id,
                                                         jfloat x, jfloat y, jfloat pressure,
                                                         jfloat size) {

//__android_log_write(ANDROID_LOG_ERROR, "Tag", "Touch registered");//Or ANDROID_LOG_INFO, .

    userHasTouchedScreen = true;

    fluidOnPointerDown(
            fluid,
            id,
            TOUCH,
            x, y, // x, y
            0, 0, // button flags
            pressure, // pressure
            size, // radius
            0, 0, 0 // unused
    );
}


extern "C" JNIEXPORT void JNICALL
Java_com_android_ui_fluidSimulation_FluidLib_onTouchUp(JNIEnv * /*env*/, jobject obj, jint id,
                                                       jfloat x, jfloat y, jfloat pressure,
                                                       jfloat size) {

//__android_log_write(ANDROID_LOG_ERROR, "Tag", "Touch registered");//Or ANDROID_LOG_INFO, .

    userHasTouchedScreen = false;

    fluidOnPointerUp(
            fluid,
            id,
            TOUCH,
            x, y, // x, y
            0, 0, // button flags
            pressure, // pressure
            size, // radius
            0, 0, 0 // unused
    );


}

extern "C" JNIEXPORT void JNICALL
Java_com_android_ui_fluidSimulation_FluidLib_onTouchChange(JNIEnv * /*env*/, jobject obj, jint id,
                                                           jfloat x, jfloat y, jfloat pressure,
                                                           jfloat size) {

//__android_log_write(ANDROID_LOG_ERROR, "Tag", "Touch registered");//Or ANDROID_LOG_INFO, .

    userHasTouchedScreen = true;


    fluidOnPointerChange(
            fluid,
            id,
            TOUCH,
            x, y, // x, y
            0, 0, // button flags
            pressure, // pressure
            size, // radius
            0, 0, 0 // unused
    );

}