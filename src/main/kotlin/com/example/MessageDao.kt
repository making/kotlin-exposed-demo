package com.example


import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

object Messages : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val text = text("text")
}

@Repository
@Transactional
open class MessageDao {
    open fun selectAll(): List<Message> = Messages.selectAll().map { Message(it[Messages.id], it[Messages.text]) }

    open fun insert(message: Message): Message {
        message.id = Messages.insert({ it[text] = message.text }).get(Messages.id)
        return message
    }
}