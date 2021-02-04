1. Mô hình sử dụng
    https://developer.android.com/topic/libraries/architecture/images/final-architecture.png
    - Thực hiện việc chọn luồng data trong repository, cache data cũng tại đây luôn
    - Luồng data chuẩn: Lấy local data -> hiển thị (data cũ)
                -> nếu chưa load/data hết hạn -> load online
                -> save local -> observe để hiển thị data mới
    - Sử dụng Flow/Flowable đối với Room để observe mỗi khi data dưới database thay đổi
    - Nếu data hết hạn (thường đặt vài ngày), nếu hiển thị data cũ gây hậu quả nghiêm trọng (thường
        là gây hiểu nhầm nghiêm trọng cho người dùng), thì có thể hiển thị placeholder thay cho data cũ,
        trường hợp chưa có data cũng nên hiển thị placeholder như vậy, tham khảo
        https://medium.com/default-to-open/smooth-loading-animations-in-android-11dcae4ecfd0
    - Được khuyên lựa chọn 1 data source làm single truth source, thường là local database luôn, còn các nguồn
        khác đều coi thành nguồn phụ
2. Yêu cầu đọc: https://developer.android.com/jetpack/guide
