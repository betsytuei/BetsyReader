package com.example.wazitoecommerce.ui.theme.screens.books

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wazitoecommerce.MainActivity
import com.example.wazitoecommerce.database.library.LibraryDao
import com.example.wazitoecommerce.database.library.LibraryItem
import com.example.wazitoecommerce.epub.EpubParser

import com.example.wazitoecommerce.helpers.PreferenceUtil
import com.example.wazitoecommerce.helpers.book.BookDownloader
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

enum class ImportStatus {
    IMPORTING, SUCCESS, ERROR, IDLE
}

data class LibraryScreenState(
    val showImportUI: Boolean = false,
    val importStatus: ImportStatus = ImportStatus.IDLE
)

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val libraryDao: LibraryDao,
    private val epubParser: EpubParser,
    private val preferenceUtil: PreferenceUtil
) : ViewModel() {
    
       


    

    val allItems: LiveData<List<LibraryItem>> = libraryDao.getAllItems()
    var state by mutableStateOf(LibraryScreenState())

    fun deleteItemFromDB(item: LibraryItem) {
        viewModelScope.launch(Dispatchers.IO) { libraryDao.delete(item) }
    }

    fun getInternalReaderSetting() = preferenceUtil.getBoolean(
        PreferenceUtil.INTERNAL_READER_BOOL, true
    )

    fun shouldShowLibraryTooltip(): Boolean {
        return preferenceUtil.getBoolean(PreferenceUtil.SHOW_LIBRARY_TOOLTIP_BOOL, true)
                && allItems.value?.isNotEmpty() == true
                && allItems.value?.any { !it.isExternalBook } == true
    }

    fun libraryTooltipDismissed() = preferenceUtil.putBoolean(
        PreferenceUtil.SHOW_LIBRARY_TOOLTIP_BOOL, false
    )


    fun importBook(context: Context, fileStream: FileInputStream) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(showImportUI = true, importStatus = ImportStatus.IMPORTING)
                fileStream.use { fis ->
                    val epubBook = epubParser.createEpubBook(fis)
                    // reset the stream position to 0 so that it can be read again
                    fis.channel.position(0)
                    // copy the book to internal storage
                    val filePath = copyBookToInternalStorage(
                        context, fis,
                        BookDownloader.createFileName(epubBook.title)
                    )
                    // insert the book into the database
                    val libraryItem = LibraryItem(
                        bookId = 0,
                        title = epubBook.title,
                        authors = epubBook.author,
                        filePath = filePath,
                        createdAt = System.currentTimeMillis(),
                        isExternalBook = true
                    )
                    libraryDao.insert(libraryItem)
                    delay(500)
                    state = state.copy(importStatus = ImportStatus.SUCCESS)
                }
            } catch (exc: Exception) {
                delay(500)
                state = state.copy(importStatus = ImportStatus.ERROR)
                exc.printStackTrace()
            } finally {
                delay(1500) // delay to show either success or error message
                state = state.copy(showImportUI = false)
                delay(150) // Hide import Ui before setting the state to idle
                state = state.copy(importStatus = ImportStatus.IDLE)
            }
        }
    }

    private suspend fun copyBookToInternalStorage(
        context: Context,
        fileStream: FileInputStream,
        filename: String
    ): String = withContext(Dispatchers.IO) {
        val booksFolder = File(context.filesDir, BookDownloader.BOOKS_FOLDER)
        if (!booksFolder.exists()) booksFolder.mkdirs()
        val bookFile = File(booksFolder, filename)
        // write the file to the internal storage
        bookFile.outputStream().use { fileStream.copyTo(it) }
        bookFile.absolutePath
    }

    companion object {
        fun importBook(context: MainActivity, fileInputStream: FileInputStream) {

        }
    }
}
