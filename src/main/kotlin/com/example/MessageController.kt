package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController @Autowired constructor(val messageDao: MessageDao) {
    @RequestMapping("/")
    fun list() = messageDao.selectAll()

    @RequestMapping(value = "/", params = arrayOf("text"))
    fun add(@RequestParam text: String) = messageDao.insert(Message(null, text))
}