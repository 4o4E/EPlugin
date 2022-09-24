package top.e404.eplugin.mysql.hikari

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import top.e404.eplugin.mysql.SqlConfig

/**
 * 通过[SqlConfig]生成[HikariConfig]
 */
fun SqlConfig.toHikariConfig(isAutoCommit: Boolean = true) = HikariConfig().also {
    it.jdbcUrl = url
    it.username = username
    it.password = password
    it.addDataSourceProperty("cachePrepStmts", "true")
    it.addDataSourceProperty("prepStmtCacheSize", "250")
    it.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    it.isAutoCommit = isAutoCommit
}

/**
 * 通过[HikariConfig]生成[HikariDataSource]
 */
fun HikariConfig.toDataSource() = HikariDataSource(this)