/*
 * Copyright (C) 2017 Naoghuman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.naoghuman.demo.template.project;

import com.github.naoghuman.demo.template.annotation.Project;
import com.github.naoghuman.demo.template.annotation.Sample;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.lang.annotation.Annotation;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public class ProjectConverter {
    
    public static List<ConcreteProject> convertProjectsToConcreteProjects(List<Class<?>> convertedProjectsToClasses) {
        LoggerFacade.getDefault().debug(ProjectConverter.class, "Convert Projects to ConcreteProject"); // NOI18N

        final List<ConcreteProject> convertedProjectsToConcreteProjects = FXCollections.observableArrayList();
        convertedProjectsToClasses.stream()
                .forEach(projectAsClass -> {
                    final Annotation annotation = projectAsClass.getAnnotation(Project.class);
                    final Project project       = (Project) annotation;
                    
                    final String name        = project.name();
                    final String projectURL  = project.projectURL();
                    final String version     = project.version();
                    
                    final ConcreteProject concreteProject = ConcreteProject.create(name, projectURL, version);
                    convertedProjectsToConcreteProjects.add(concreteProject);
                });
        
        return convertedProjectsToConcreteProjects;
    }

    public static List<Class<?>> convertProjectsToClasses(final List<String> collectedProjectsAsStrings) {
        LoggerFacade.getDefault().debug(ProjectConverter.class, "Convert Projects to Classes"); // NOI18N

        final List<Class<?>> convertedProjectsToClasses = FXCollections.observableArrayList();
        collectedProjectsAsStrings.stream()
                .forEach(projectAsString -> {
                    try {
                        final Class<?> clazz = Class.forName(projectAsString);
                        convertedProjectsToClasses.add(clazz);
                    } catch (Throwable e) {
                        LoggerFacade.getDefault().error(ProjectConverter.class, "Error converting project to class: " + projectAsString, e); // NOI18N
                    }
                });
        
        return convertedProjectsToClasses;
    }

    public static List<ConcreteSample> convertSamplesToConcreteSamples(List<Class<?>> convertedSamplesToClasses) {
        LoggerFacade.getDefault().debug(ProjectConverter.class, "Convert Projects to ConcreteProject"); // NOI18N

        final List<ConcreteSample> convertedSamplesToConcreteSamples = FXCollections.observableArrayList();
        convertedSamplesToClasses.stream()
                .forEach(projectAsClass -> {
                    final Annotation annotation = projectAsClass.getAnnotation(Sample.class);
                    final Sample sample         = (Sample) annotation;
                    
                    final String name        = sample.name();
                    final String description = sample.description();
                    final boolean visible    = sample.visible();
                    
                    final Project project = sample.project();
                    final ConcreteProject concreteProject = ConcreteProject.create(project.name(), project.projectURL(), project.version());
                    
                    final ObservableList<String> cssURLs = FXCollections.observableArrayList();
                    cssURLs.addAll(sample.cssURLs());
                    
                    final ObservableList<String> javaDocURLs = FXCollections.observableArrayList();
                    javaDocURLs.addAll(sample.javaDocURLs());
                    
                    final ObservableList<String> sourceCodeURLs = FXCollections.observableArrayList();
                    sourceCodeURLs.addAll(sample.sourceCodeURLs());
                    
                    final ConcreteSample concreteSample = ConcreteSample.create(name, concreteProject,
                            sourceCodeURLs, javaDocURLs, cssURLs, description, visible);
                    convertedSamplesToConcreteSamples.add(concreteSample);
                });
        
        return convertedSamplesToConcreteSamples;
    }
    
    public static List<Class<?>> convertSamplesToClasses(final List<String> collectedSamplesAsFiles) {
        LoggerFacade.getDefault().debug(ProjectConverter.class, "Convert Samples to Classes"); // NOI18N

        final List<Class<?>> collectedSamplesAsClasses = FXCollections.observableArrayList();
        collectedSamplesAsFiles.stream()
                .forEach(sampleAsString -> {
                    try {
                        final Class<?> clazz = Class.forName(sampleAsString);
                        collectedSamplesAsClasses.add(clazz);
                    } catch (Throwable e) {
                        LoggerFacade.getDefault().error(ProjectConverter.class, "Error converting sample to class: " + sampleAsString, e); // NOI18N
                    }
                });
        
        return collectedSamplesAsClasses;
    }
    
}
