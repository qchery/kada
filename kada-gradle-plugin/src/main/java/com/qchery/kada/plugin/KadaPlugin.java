package com.qchery.kada.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Chery
 * @date 2018/7/2 22:35
 */
public class KadaPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getExtensions().create("generating", KadaExtension.class);
        project.getTasks().create("generateEntity", KadaTask.class);
    }
}
