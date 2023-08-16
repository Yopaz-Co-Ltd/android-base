package com.kira.android_base.main.fragments.post_detai

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kira.android_base.R


class PostDetailFragment : Fragment(R.layout.fragment_post_detail) {

    val unencodeHtml = """
        <html>
        <head>
          <title>Hướng dẫn cài đặt Ruby on Rails trên Windows</title>
        </head>
        <body>
          <h1>How To Install #RubyonRails on Window 7/8/10/11 -> Tutorial 2022"</h1>

          <h2>Bước 1: Cài đặt Ruby</h2>
          <ol>
            <li>Truy cập <a href="https://rubyinstaller.org/downloads/">trang chính thức của RubyInstaller</a> để tải bản cài đặt Ruby mới nhất cho Windows.</li>
            <li>Tải và chạy tập tin cài đặt Ruby.</li>
            <li>Đảm bảo bạn chọn "Add Ruby executables to your PATH" trong quá trình cài đặt.</li>
          </ol>

          <h2>Bước 2: Cài đặt Node.js và Yarn</h2>
          <ol>
            <li>Truy cập <a href="https://nodejs.org/">trang chính thức của Node.js</a> để tải bản cài đặt Node.js cho Windows.</li>
            <li>Tải và chạy tập tin cài đặt Node.js.</li>
            <li>Truy cập <a href="https://classic.yarnpkg.com/en/docs/install/#windows-stable">trang chính thức của Yarn</a> để tải Yarn cho Windows.</li>
            <li>Tải và chạy tập tin cài đặt Yarn.</li>
          </ol>

          <h2>Bước 3: Cài đặt SQLite</h2>
          <ol>
            <li>Truy cập <a href="https://www.sqlite.org/download.html">trang chính thức của SQLite</a> để tải bản cài đặt SQLite Precompiled Binaries for Windows.</li>
            <li>Tải và giải nén tập tin cài đặt SQLite vào một thư mục tùy chọn trên máy.</li>
          </ol>

          <h2>Bước 4: Cài đặt Ruby on Rails</h2>
          <ol>
            <li>Mở Command Prompt hoặc PowerShell trên máy tính của bạn.</li>
            <li>Sử dụng lệnh sau để cài đặt Rails:
              <pre><code>gem install rails</code></pre>
            </li>
          </ol>

          <h2>Bước 5: Kiểm tra cài đặt</h2>
          <ol>
            <li>Sau khi cài đặt xong, bạn có thể kiểm tra phiên bản Ruby và Rails đã cài bằng cách chạy lệnh sau trong Command Prompt hoặc PowerShell:
              <pre><code>ruby -v
        rails -v</code></pre>
            </li>
            <li>Nếu bạn nhìn thấy phiên bản của Ruby và Rails xuất hiện trong kết quả, điều đó có nghĩa là bạn đã cài đặt thành công Ruby on Rails trên hệ điều hành Windows của mình.</li>
          </ol>
          <p><em>Lưu ý: Các bước trên có thể thay đổi tùy theo phiên bản và cập nhật mới nhất của các phần mềm. Đảm bảo bạn kiểm tra trang web chính thức của từng phần mềm để cập nhật thông tin mới nhất trước khi cài đặt.</em></p>
        </body>
        </html>
    """.trimIndent()

    private var suggesstionListView: RecyclerView? = null
    private var sugesstionListData: List<SuggestionPostModel> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        suggesstionListView = view.findViewById(R.id.suggesstion_post_list)

        //TODO REMOVE
        val data = listOf(
            SuggestionPostModel("Trương Nhật 1", "Ha Noi", "11 August, 2023", "Hello World 1"),
            SuggestionPostModel("Trương Nhật 2", "Ha Noi", "12 August, 2023", "Hello World 2"),
            SuggestionPostModel("Trương Nhật 3", "Ha Noi", "13 August, 2023", "Hello World 3"),
            SuggestionPostModel("Trương Nhật 4", "Ha Noi", "14 August, 2023", "Hello World 4"),
            SuggestionPostModel("Trương Nhật 5", "Ha Noi", "15 August, 2023", "Hello World 5"),
            SuggestionPostModel("Trương Nhật 6", "Ha Noi", "16 August, 2023", "Hello World 6"),
            SuggestionPostModel("Trương Nhật 7", "Ha Noi", "17 August, 2023", "Hello World 7"),
            SuggestionPostModel("Trương Nhật 8", "Ha Noi", "18 August, 2023", "Hello World 8"),
        )

        sugesstionListData = data

        val adapter = SuggestionPostRecyclerViewAdapter(sugesstionListData)

        suggesstionListView?.adapter = adapter

        suggesstionListView?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        val webView = view.findViewById<WebView>(R.id.post_content)
        webView.isVerticalScrollBarEnabled = false
        val encodeHtml = android.util.Base64.encodeToString(
            unencodeHtml.toByteArray(),
            android.util.Base64.NO_PADDING
        )
        webView.loadData(encodeHtml, "text/html", "base64")
    }
}
