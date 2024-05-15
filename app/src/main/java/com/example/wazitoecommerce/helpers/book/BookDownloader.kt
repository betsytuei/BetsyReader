package com.example.wazitoecommerce.helpers.book

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.wazitoecommerce.models.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File

class BookDownloader(private val context: Context) {
    companion object {
        private const val TAG = "BookDownloader"
        const val BOOKS_FOLDER = "ebooks"
        const val TEMP_FOLDER = "temp_books"
        private const val MAX_FILENAME_LENGTH = 200

        /**
         * Sanitizes book title by replacing forbidden chars which are not allowed
         * as the file name and builds file name for the epub file by joining all
         * of the words in the  book title at the end.
         * @param title title of the book for which file name is required.
         * @return [String] file name for the given book.
         */
        fun createFileName(title: String): String {
            val sanitizedTitle = title
                .replace(":", ";")
                .replace("\"", "")
                .replace("/", "-")
                .replace("\\", "-")
                .split(" ")
                .joinToString(separator = "+") { word ->
                    word.replace(Regex("[^\\p{ASCII}]"), "")
                }.take(MAX_FILENAME_LENGTH).trim()
            return "$sanitizedTitle.epub"
        }
    }

    private val downloadJob = Job()
    private val downloadScope = CoroutineScope(Dispatchers.IO + downloadJob)
    private val downloadManager by lazy { context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }

    /**
     * Data class to store download info for a book.
     * @param downloadId id of the download.
     * @param status status of the download.
     * @param progress progress of the download.
     */
    data class DownloadInfo(
        val downloadId: Long,
        var status: Int = DownloadManager.STATUS_RUNNING,
        val progress: MutableStateFlow<Float> = MutableStateFlow(0f)
    )

    /** Stores running download with book id as key */
    private val runningDownloads = HashMap<Int, DownloadInfo>()

    /**
     * Start downloading epub file for the given [Book] object.
     * @param book [Book] which needs to be downloaded.
     * @param downloadProgressListener a callable which takes download progress; [Float] and
     * download status; [Int] as arguments.
     * @param onDownloadSuccess: a callable which will be executed after download has been
     * completed successfully.
     */
    @SuppressLint("Range")
    fun downloadBook(
        book: Book, downloadProgressListener: (progress: Float, status: Int) -> Unit,
        onDownloadSuccess: (filePath: String) -> Unit
    ) {
        // Check if book is already being downloaded.


        // Create file for the downloaded book.
        val filename = createFileName(book.book)
        val tempFolder = File(context.getExternalFilesDir(null), TEMP_FOLDER)
        if (!tempFolder.exists()) tempFolder.mkdirs()
        val tempFile = File(tempFolder, filename)
        Log.d(TAG, "downloadBook: Destination file path: ${tempFile.absolutePath}")

        // Start download...


        Log.d(TAG, "downloadBook: Starting download for book: ${book.book}")


        // Start coroutine to listen for download progress.
        downloadScope.launch {
            var isDownloadFinished = false
            var progress = 0f
            var status: Int


            while (!isDownloadFinished) {
                val downloadId = 0
                val cursor: Cursor =
                    downloadManager.query(DownloadManager.Query().setFilterById(downloadId.toLong()))
                if (cursor.moveToFirst()) {
                    status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    when (status) {
                        DownloadManager.STATUS_RUNNING -> {
                            val totalBytes: Long =
                                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                            if (totalBytes > 0) {
                                val downloadedBytes: Long =
                                    cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                                progress = (downloadedBytes * 100 / totalBytes).toFloat() / 100
                            }
                        }

                        DownloadManager.STATUS_SUCCESSFUL -> {
                            Log.d(TAG, "downloadBook: Download successful for book: ${book.book}")
                            isDownloadFinished = true
                            progress = 1f
                            // Move file to books folder.
                            val booksFolder = File(context.filesDir, BOOKS_FOLDER)
                            if (!booksFolder.exists()) booksFolder.mkdirs()
                            val bookFile = File(booksFolder, filename)
                            tempFile.copyTo(bookFile, true)
                            tempFile.delete()
                            onDownloadSuccess(bookFile.absolutePath)
                        }

                        DownloadManager.STATUS_PAUSED, DownloadManager.STATUS_PENDING -> {
                            // Do nothing, wait for download to resume.
                            Log.d(TAG, "downloadBook: Download pending for book: ${book.book}")
                        }

                        DownloadManager.STATUS_FAILED -> {
                            Log.d(TAG, "downloadBook: Download failed for book: ${book.book}")
                            isDownloadFinished = true
                            progress = 0f
                        }
                    }

                } else {
                    /** Download cancelled by the user. */
                    Log.d(TAG, "downloadBook: Download cancelled for book: ${book.book}")
                    isDownloadFinished = true
                    progress = 0f
                    status = DownloadManager.STATUS_FAILED
                }

                /** update download info at the end of iteration. */

            }
            /**
            Remove download from running downloads when loop ends.
            added dome delay here so we get time to update our UI before
            download info gets removed.
             */

        }
    }

    /**
     * Returns true if book with the given id is currently being downloaded
     * false otherwise.
     * @param bookId id of the book for which download status is required.
     * @return [Boolean] true if book is currently being downloaded, false otherwise.
     */
    fun isBookCurrentlyDownloading(bookId: Int) = runningDownloads.containsKey(bookId)

    /**
     * Returns [DownloadInfo] if book with the given id is currently being downloaded.
     * @param bookId id of the book for which download info is required.
     * @return [DownloadInfo] if book is currently being downloaded, null otherwise.
     */
    fun getRunningDownload(bookId: Int) = runningDownloads[bookId]

    /**
     * Cancels download of book by using it's download id (if download is running).
     * @param downloadId id of the download which needs to be cancelled.
     */
    fun cancelDownload(downloadId: Long?) = downloadId?.let { downloadManager.remove(it) }

}