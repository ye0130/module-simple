@file:Isolated

package taboolib.module.simple.util

import com.google.gson.GsonBuilder
import taboolib.common.Isolated
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import java.io.File
import java.io.FileReader
import java.io.FileWriter

@RuntimeDependencies(
    RuntimeDependency("com.google.code.gson:gson:2.8.9", test = "com.google.gson.Gson")
)
object GsonUtil {
    val gson = GsonBuilder().setPrettyPrinting().create()

    /**
     * 实体序列化写入文件
     *
     * @param any 实体
     */
    fun File.writerAny(any: Any) {
        if (!this.exists()) {
            val fileParent: File = this.getParentFile()
            if (!fileParent.exists()) {
                fileParent.mkdirs()
            }
            this.createNewFile()
        }
        val writer = FileWriter(this)
        writer.write(gson.toJson(any))
        writer.close()
    }

    /**
     * 从文件读取实体
     *
     */
    fun <T> File.readAny(classOfT: Class<T>): T {
        val fileReader = FileReader(this)
        val data = fileReader.readText()
        fileReader.close()
        if (data.isEmpty()) {
            throw Error("file content cannot be empty ${this.path}")
        }
        return gson.fromJson(data, classOfT)
    }

    fun Any.toJSONString(): String {
        return gson.toJson(this)
    }

    fun <T> String.toAny(classOfT: Class<T>): T {
        return gson.fromJson(this, classOfT)
    }
}
