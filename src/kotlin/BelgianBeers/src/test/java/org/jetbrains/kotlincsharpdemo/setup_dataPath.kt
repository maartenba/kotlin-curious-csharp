package org.jetbrains.kotlincsharpdemo

import java.io.File

tailrec fun determineDataPath(fileName: String,
                              base: File = File(".").canonicalFile): File {

  val data = File(base, "data/$fileName")
  if (data.isFile) return data

  return determineDataPath(
          fileName = fileName,
          base = base.parentFile ?: error("Failed to find data $fileName"))
}
