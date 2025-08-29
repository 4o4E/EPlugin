package top.e404.eplugin.mysql

import kotlinx.serialization.Serializable
import org.bukkit.configuration.ConfigurationSection
import java.sql.DriverManager

/**
 * 数据库设置
 *
 * @property address 数据库链接地址
 * @property port 数据库端口
 * @property username 用户名
 * @property password 密码
 * @property database 数据库名字
 * @property prefix 数据表前缀
 */
@Serializable
data class SqlConfig(
    val address: String,
    val port: Int,
    val username: String,
    val password: String,
    val database: String,
    val prefix: String,
    val params: MutableList<String> = mutableListOf()
) {
    val url get() = "jdbc:mysql://$address:$port/$database${
            if (params.isEmpty()) ""
            else "?${params.joinToString("&")}"
        }"

    val connection get() = DriverManager.getConnection(
        url,
        username,
        password
    )!!
}

/**
 * 从[ConfigurationSection]中读取[SqlConfig]
 */
fun ConfigurationSection.getSqlConfig(path: String) = getConfigurationSection(path)?.let { cfg ->
    SqlConfig(
        address = cfg.getString("address") ?: error("address不可为空"),
        port = cfg.getInt("port", 3306),
        username = cfg.getString("username") ?: error("username不可为空"),
        password = cfg.getString("password") ?: error("password不可为空"),
        database = cfg.getString("database") ?: error("database不可为空"),
        prefix = cfg.getString("prefix") ?: error("prefix不可为空"),
        params = cfg.getStringList("params")
    )
}
