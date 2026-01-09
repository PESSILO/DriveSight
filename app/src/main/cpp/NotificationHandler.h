#ifndef NOTIFICATION_HANDLER_H
#define NOTIFICATION_HANDLER_H

#include <string>

/**
 * @class NotificationHandler
 * @brief Interface for handling notifications from the Android system.
 *
 * This abstract class defines the contract for processing notifications.
 * It is designed to be implemented by different handlers for various purposes,
 * such as storing, filtering, or displaying notifications.
 */
class NotificationHandler {
public:
    /**
     * @brief Virtual destructor.
     */
    virtual ~NotificationHandler() = default;

    /**
     * @brief Handles a new notification.
     *
     * This pure virtual function is called when a new notification is received
     * from the Android system.
     *
     * @param title The title of the notification.
     * @param content The content of the notification.
     * @param timestamp The time the notification was received (Unix timestamp).
     * @return bool True if the notification was handled successfully, false otherwise.
     */
    virtual bool onNotificationReceived(const std::string& title, const std::string& content, long timestamp) = 0;
};

#endif // NOTIFICATION_HANDLER_H
