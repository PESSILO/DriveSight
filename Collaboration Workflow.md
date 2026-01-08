Quy Trình Phối Hợp (Collaboration Workflow).md
Quy Trình Phối Hợp Phát Triển: 
Android Native (JNI)Tài liệu này quy định cách thức làm việc giữa Team Android (Kotlin) và Team Core (C++) để đảm bảo tính ổn định, hiệu năng và khả năng bảo trì của dự án NativeNotify.
1. Nguyên tắc cốt lõi: "Contract-First" (Hợp đồng trước tiên)Trước khi viết bất kỳ dòng logic nào, hai team phải thống nhất về Interface. Interface này chính là "Hợp đồng" (Contract).Tại sao? Để Team Android có thể làm UI/Service giả lập (Mock) trong khi Team C++ viết logic xử lý. Hai bên không phải chờ đợi nhau.Artifact: File NativeInterface.kt và NativeEngine.h.

2. Quy trình làm việc chi tiết (5 Bước)
  Bước 1: Định nghĩa Interface (Họp chung)Input: Dữ liệu gì từ Android gửi xuống? (Title, Content, Timestamp).Output: C++ trả về gì? (Boolean thành công, hay struct dữ liệu đã xử lý?).Action:Tạo file Kotlin external fun.Tạo file C++ header (.h).Freeze: Không ai được tự ý sửa signature sau bước này.
  Bước 2: Phát triển Song song (Parallel Development)Đây là giai đoạn hai team tách ra làm việc độc lập:Team Android (Kotlin)Tạo một class MockNotificationEngine (giả lập) implement interface giống hệt Native.Tập trung phát triển UI, Animation, Permission Flow, Foreground Service.Sử dụng dữ liệu giả để test hiển thị.Team Core (C++)Phát triển NotificationEngine.cpp.Quan trọng: Viết Unit Test C++ (sử dụng Google Test) chạy trực tiếp trên máy tính (Local Host), không cần chạy trên Emulator Android.Kiểm tra logic parse, filter, memory leak bằng Valgrind hoặc AddressSanitizer trên môi trường local.
  Bước 3: Tích hợp (The Handshake - JNI Implementation)Sau khi hai bên hoàn thành module riêng:Team C++ viết hàm JNIEXPORT trong native-lib.cpp.Thực hiện data marshalling (chuyển đổi jstring <-> std::string).Team Android xóa bỏ MockEngine, chuyển sang gọi System.loadLibrary().
  Bước 4: Kiểm thử tích hợp (Integration Testing)Chạy ứng dụng trên thiết bị thật.Stress Test: 
    Gửi 1000 thông báo liên tục từ Android xuống C++ để kiểm tra độ trễ và crash.Profiling: Dùng Android Profiler để soi Memory (xem Native Heap có tăng mãi không?).
  Bước 5: Code Review (Cross-Review)Quy tắc review đặc biệt cho dự án NDK:
    Android Dev: Review code C++ để xem có check NULL khi nhận dữ liệu từ Java không.
    C++ Dev: Review code Kotlin để đảm bảo không gọi hàm Native từ Main Thread (nếu hàm đó nặng).

3. Checklist An toàn (Safety Checklist)Khi merge code, phải tích vào các ô sau:
  [ ] Thread Safety: Hàm C++ có đang truy cập biến static toàn cục mà không có std::mutex không?
  [ ] JNI References: Đã ReleaseStringUTFChars sau khi dùng xong chuỗi chưa?
  [ ] Exception: C++ có try-catch không? (Tuyệt đối không để C++ exception lan ra làm crash JVM).
  [ ] Proguard/R8: Đã thêm rules để giữ lại tên class/method gọi từ JNI chưa? (@Keep annotation).

5. Mô hình Git Branchingmain: Code ổn định, đã tích hợp.feature/ui-notification: Team Android làm việc.feature/cpp-core-engine: Team C++ làm việc.integration/jni-binding: Branch tạm thời để nối dây hai bên.
6. Ví dụ Mocking (Cho Team Android)Trong khi chờ Team C++ viết xong logic xử lý phức tạp, Team Android dùng code này:interface NotificationProcessor {
    fun process(title: String, content: String)
}

// 1. Dùng cái này khi Dev UI
class MockProcessor : NotificationProcessor {
    override fun process(title: String, content: String) {
        println("MOCK: Đã nhận thông báo $title. Giả vờ xử lý xong trong 10ms.")
    }
}

// 2. Dùng cái này khi Production (JNI)
class NativeProcessor : NotificationProcessor {
    external override fun process(title: String, content: String)
    
    companion object { init { System.loadLibrary("native-lib") } }
}
Bằng cách này, dự án không bao giờ bị block.
