package io.sprotte

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PNGQuantTask extends DefaultTask {
    String greeting = 'hello from Sprotte'

    @TaskAction
    def greet() {
        println greeting
    }
}