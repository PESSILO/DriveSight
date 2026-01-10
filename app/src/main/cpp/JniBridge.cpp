#include <jni.h>
#include <string>
#include "NotificationHandler.h"
#include "ConcreteNotificationHandler.h"

// Khởi tạo một đối tượng xử lý thông báo cụ thể.
// Trong thực tế, bạn có thể muốn sử dụng một factory hoặc dependency injection ở đây.
static ConcreteNotificationHandler notificationHandler;

extern "C" JNIEXPORT jboolean JNICALL
Java_com_pessilogroup_drivesight_MainActivity_onNotificationReceived(JNIEnv* env, jobject /* this */, jstring title, jstring content, jlong timestamp) {
    const char* titleStr = env->GetStringUTFChars(title, nullptr);
    const char* contentStr = env->GetStringUTFChars(content, nullptr);

    bool result = notificationHandler.onNotificationReceived(titleStr, contentStr, timestamp);

    env->ReleaseStringUTFChars(title, titleStr);
    env->ReleaseStringUTFChars(content, contentStr);

    return static_cast<jboolean>(result);
}
