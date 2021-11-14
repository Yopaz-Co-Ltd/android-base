1. Mô hình sử dụng
   https://developer.android.com/topic/libraries/architecture/images/final-architecture.png
    - Thực hiện việc chọn luồng data trong repository, cache data cũng tại đây luôn
    - Luồng data chuẩn: Lấy local data -> hiển thị (data cũ)
      -> nếu chưa load/data hết hạn -> load online -> save local -> observe để hiển thị data mới
    - Sử dụng Flow/Flowable đối với Room để observe mỗi khi data dưới database thay đổi
    - Nếu data hết hạn (thường đặt vài ngày), nếu hiển thị data cũ gây hậu quả nghiêm trọng (thường
      là gây hiểu nhầm nghiêm trọng cho người dùng), thì có thể hiển thị placeholder thay cho data
      cũ, trường hợp chưa có data cũng nên hiển thị placeholder như vậy, tham khảo
      https://medium.com/default-to-open/smooth-loading-animations-in-android-11dcae4ecfd0
    - Được khuyên lựa chọn 1 data source làm single truth source, thường là local database luôn, còn
      các nguồn khác đều coi thành nguồn phụ

2. Yêu cầu đọc: https://developer.android.com/jetpack/guide

3. Best libs

- Carousel View: https://github.com/yarolegovich/DiscreteScrollView
- Loading Indicator: https://github.com/ybq/Android-SpinKit
- Skeleton View: https://github.com/ethanhua/Skeleton (to replace loading)

4. Sử dụng Navigation Component

- Để kết hợp giữa navigation component type-safe arguments với databiding, mình đã tạo một data
  class Args cho mỗi fragment, class này được extends từ Parcelable nên có thể pass khi navigate
  giữa các fragment, mỗi fragment chỉ có 1 args duy nhất này được thêm vào nav_graph, nếu có truyền
  nhiều trường thì mỗi trường là một trường trong class Args đó. Khi đó data sẽ được pass trực tiếp
  sang xml để sử dụng với databiding. Nếu có ý tưởng cải tiến hãy góp ý nhé, thanks!
