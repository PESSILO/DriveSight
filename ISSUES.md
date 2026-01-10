# Project Issues

This file outlines the issues to be addressed based on the `Collaboration Workflow.md` document.

## 1. [Task] Define JNI Interface between Kotlin and C++

*   **Description**: Create `NativeInterface.kt` with the methods to be called from Kotlin. Create `NativeEngine.h` with the corresponding C++ function declarations. Define the data structures for communication (e.g., what data is passed and returned).
*   **Status**: Open

## 2. [Android] Implement Mock Engine for UI Development

*   **Description**: Create a `MockNotificationEngine` class in Kotlin that implements the defined `NativeInterface.kt`. This mock will return dummy data to allow for UI development without waiting for the C++ implementation.
*   **Status**: Open

## 3. [Android] Develop UI for Displaying Notifications

*   **Description**: Implement the UI for displaying notifications. Use the `MockNotificationEngine` to get data for the UI. Handle permissions for notifications. Implement a foreground service if needed.
*   **Status**: Open

## 4. [C++] Implement Core Notification Processing Logic

*   **Description**: Implement the core logic in `NotificationEngine.cpp` based on the `NativeEngine.h` interface. Write C++ unit tests using Google Test. Perform memory analysis with Valgrind/AddressSanitizer.
*   **Status**: Open

## 5. [Integration] Implement JNI Bridge

*   **Description**: Write the JNI wrapper code in `native-lib.cpp` to connect the Kotlin `external` functions to the C++ implementation. Handle data marshalling between Java types (`jstring`, etc.) and C++ types (`std::string`, etc.).
*   **Status**: Open

## 6. [QA] Perform Integration Testing

*   **Description**: Run the app on a real device. Perform stress testing by sending a high volume of notifications. Profile the app using Android Profiler to check for memory leaks in the native heap.
*   **Status**: Open

## 7. [Process] Establish Cross-Team Code Review Process

*   **Description**: Ensure Android developers review C++ code for null safety. Ensure C++ developers review Kotlin code for correct thread usage when calling native methods.
*   **Status**: Open

## 8. [Process] Enforce Safety Checklist for NDK Development

*   **Description**: Before merging code, ensure the following are checked:
    *   `[ ] Thread Safety`
    *   `[ ] JNI References`
    *   `[ ] Exception Handling in C++`
    *   `[ ] Proguard/R8 rules`
*   **Status**: Open
