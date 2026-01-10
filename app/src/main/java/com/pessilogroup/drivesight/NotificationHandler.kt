package com.pessilogroup.drivesight

/**
 * "Hợp đồng" (Contract) giữa tầng Android (Kotlin) và C++ (Native).
 * Interface này định nghĩa cách thức dữ liệu thông báo được gửi đi.
 */
interface NotificationHandler {
    /**
     * Hàm được gọi từ Android Service mỗi khi có thông báo mới.
     * @param title Tiêu đề của thông báo.
     * @param content Nội dung của thông báo.
     * @param timestamp Thời gian thông báo được nhận (Unix timestamp).
     * @return Boolean cho biết C++ đã nhận và xử lý thành công hay chưa.
     */
    fun onNotificationReceived(title: String, content: String, timestamp: Long): Boolean
}

/**
 * Lớp giả lập (Mock) để team Android phát triển UI/Service mà không cần chờ C++.
 * Logic ở đây chỉ là in ra Logcat.
 */
class MockNotificationHandler : NotificationHandler {
    override fun onNotificationReceived(title: String, content: String, timestamp: Long): Boolean {
        // Giả lập việc xử lý thành công
        println("MockNotificationHandler: Received notification - Title: $title, Content: $content, Timestamp: $timestamp")
        return true
    }
}
