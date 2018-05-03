package org.jetbrains.kotlincsharpdemo

actual object TestDataPlatform {

  actual val beers: Sequence<BeerItem>
    get() = sequenceOf()

  /*
  tailrec fun determineDataPath(fileName: String,
                                base: File = File("").canonicalFile): File {

    val data = File(base, "data/$fileName")
    if (data.isFile) return data

    return determineDataPath(
            fileName = fileName,
            base = base.parentFile ?: error("Failed to find data $fileName"))
  }*/
}
