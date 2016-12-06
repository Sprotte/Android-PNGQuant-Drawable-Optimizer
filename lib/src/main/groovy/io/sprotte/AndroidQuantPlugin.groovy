package io.sprotte

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidQuantPlugin implements Plugin<Project> {
    void apply(Project target) {
        target.task('hello', type: io.sprotte.PNGQuantTask)
    }
}