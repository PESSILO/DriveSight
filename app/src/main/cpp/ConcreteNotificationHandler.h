#ifndef CONCRETE_NOTIFICATION_HANDLER_H
#define CONCRETE_NOTIFICATION_HANDLER_H

#include "NotificationHandler.h"
#include <iostream>

class ConcreteNotificationHandler : public NotificationHandler {
public:
    bool onNotificationReceived(const std::string& title, const std::string& content, long timestamp) override {
        // Logic xử lý thông báo cụ thể
        // Hiện tại, chúng ta chỉ in ra để kiểm thử
        std::cout << "Received notification: " << title << " - " << content << " - " << timestamp << std::endl;
        return true;
    }
};

#endif // CONCRETE_NOTIFICATION_HANDLER_H
