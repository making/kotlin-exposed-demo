package com.example

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.TestRestTemplate
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.util.UriComponentsBuilder
import org.hamcrest.CoreMatchers.*;
import org.junit.Assert.*;
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod

@RunWith(SpringJUnit4ClassRunner::class)
@SpringApplicationConfiguration(classes = arrayOf(KotlinExposedDemoApplication::class))
@WebIntegrationTest(randomPort = true)
class MessageControllerTests {
    val restTemplate = TestRestTemplate();
    @Value("\${local.server.port}")
    var port: Int = 0;

    @Test
    fun test() {
        val message1 = restTemplate.getForObject(
                UriComponentsBuilder.fromUriString("http://localhost").port(port)
                        .queryParam("text", "hello").build().toUri(), String::class.java)
        assertThat(message1, `is`("""{"id":1,"text":"hello"}"""))

        val message2 = restTemplate.getForObject(
                UriComponentsBuilder.fromUriString("http://localhost").port(port)
                        .queryParam("text", "world").build().toUri(), String::class.java)
        assertThat(message2, `is`("""{"id":2,"text":"world"}"""))

        val message3 = restTemplate.getForObject(
                UriComponentsBuilder.fromUriString("http://localhost").port(port)
                        .build().toUri(), String::class.java);
        assertThat(message3, `is`("""[{"id":1,"text":"hello"},{"id":2,"text":"world"}]"""))
    }

}
