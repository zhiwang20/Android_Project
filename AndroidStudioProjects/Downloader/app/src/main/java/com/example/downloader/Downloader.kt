package com.example.downloader

import android.os.Environment
import android.util.Log
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory


class Downloader {

    companion object {
        const val DEFAULT_DELAY = 3000
    }

    /**
     * Downloads the file found at the given URL,
     * into the Android device's Downloads folder in its external storage,
     * and returns the file name it was saved to.
     */
    fun download(urlString: String): String {
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        Log.v("Downloader", "downloading $urlString to $folder")
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            // empty
        }

        if (!folder.exists()) {
            folder.mkdirs()
        }

        // download the file into a memory buffer
        val bytes = downloadToByteArray(urlString)

        // store the memory buffer contents into a file
        val urlFile = File(urlString)
        val filename = urlFile.name
        val outFile = File(folder, filename)
        try {
            val out = FileOutputStream(outFile)
            out.write(bytes)
            out.close()
            return filename
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    /**
     * Pretends to download the file found at the given URL,
     * and returns the file name it would have been saved to.
     */
    fun downloadFake(urlString: String): String {
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val urlFile = File(urlString)
        val filename = urlFile.name
        val outFile = File(folder, filename)
        Log.v("Downloader", "downloading $urlString to $outFile")

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            // empty
        }

        return filename
    }

    /**
     * Retrieves all of the links like [...](http://example.com/foo.html)
     * from the page and returns their href URLs as an array.
     * Doesn't work on some pages due to invalid HTML content.
     */

    fun getAllLinks(webPageURL: String): Array<String> {
        try {
            val bytes = downloadToByteArray(webPageURL)
            val list = ArrayList<String>()

            val factory = DocumentBuilderFactory.newInstance()
            factory.isNamespaceAware = true
            factory.isIgnoringElementContentWhitespace = true
            factory.isValidating = false
            val builder = factory.newDocumentBuilder()
            val document = builder.parse(ByteArrayInputStream(bytes))
            val linkNodes = document.getElementsByTagName("a")
            for (i in 0 until linkNodes.length) {
                val node = linkNodes.item(i)
                val hrefNode = node.attributes.getNamedItem("href")
                if (hrefNode != null) {
                    val href = hrefNode!!.nodeValue
                    try {
                        val url = URL(href)
                        list.add(href)
                    } catch (mfurle: MalformedURLException) {
                        // invalid URL; don't add
                    }

                }
            }

            return list.toArray(arrayOfNulls<String>(0))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Reads the entire contents of the given file from the device's Downloads folder,
     * returning the file's contents as a text string.
     */
    fun readEntireFile(filename: String): String {
        try {
            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(dir, filename)
            val sb = StringBuilder()
            val reader = BufferedReader(FileReader(file))
            while (reader.ready()) {
                sb.append(reader.read() as Char)
            }
            return sb.toString()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    /**
     * Downloads the file found at the given URL into a memory buffer of bytes,
     * then returns the bytes as an array.
     * This is used internally as a temporary helper method.
     */
    private fun downloadToByteArray(urlString: String): ByteArray {
        try {
            // download the file into a memory buffer
            val bytes = ByteArrayOutputStream()
            val url = URL(urlString)
            val stream = url.openStream()
            val reader = BufferedReader(InputStreamReader(stream))
            var ch: Int
            ch = reader.read()
            while (ch != -1) {
                bytes.write(ch)
            }
            stream.close()
            return bytes.toByteArray()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }


}