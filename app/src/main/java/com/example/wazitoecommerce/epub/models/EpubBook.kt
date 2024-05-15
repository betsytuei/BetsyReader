package com.example.wazitoecommerce.epub.models
import android.graphics.Bitmap

/**
 * Represents an epub book.
 *
 * @param fileName The name of the epub file.
 * @param title The title of the book.
 * @param author The author of the book.
 * @param language The language code of the book.
 * @param coverImage The cover image of the book.
 * @param chapters The list of chapters in the book.
 * @param images The list of images in the book.
 */
 data class EpubBook(
    val fileName: String,
    val title: String,
    val author: String,
    val language: String,
    val coverImage: Bitmap?,
    val chapters: List<EpubChapter>,
    val images: List<EpubImage>)
