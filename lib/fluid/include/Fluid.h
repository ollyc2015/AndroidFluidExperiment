/**
 * C-Style API to enable the fluid component to be used with C-based clients (i.e. Swift)
 */

#ifndef Fluid_h
#define Fluid_h

enum PointerType {
    MOUSE = 0,
    TOUCH = 1,
    PEN = 2
};

#ifdef __cplusplus
extern "C" {
#endif

    void* fluidCreate(int width, int height);
    void fluidDestroy(void* fluidPtr);

    void fluidUpdateSettings(void* fluidPtr, const char *json);
    void fluidSaveSettings(void* fluidPtr);
    void fluidRestoreSettings(void* fluidPtr);
    
    void fluidOnPointerDown(void* fluidPtr, int pointerId, enum PointerType type, double x, double y, int buttonState, int buttonChange, double pressure, double radius, double angle, double altitudeAngle, double azimuthAngle);
    void fluidOnPointerUp(void* fluidPtr, int pointerId, enum PointerType type, double x, double y, int buttonState, int buttonChange, double pressure, double radius, double angle, double altitudeAngle, double azimuthAngle);
    void fluidOnPointerChange(void* fluidPtr, int pointerId, enum PointerType type, double x, double y, int buttonState, int buttonChange, double pressure, double radius, double angle, double altitudeAngle, double azimuthAngle);
    
    void fluidSetGravity(void* fluidPtr, double x, double y);
    
    void fluidOnFrame(void* fluidPtr, double frameTime_ms);

    void fluidResize(void* fluidPtr, int width, int height);

#ifdef __cplusplus
}
#endif

#endif /* Fluid_h */
