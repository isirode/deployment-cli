package com.isirode.deployment.cli.local;

import lombok.val;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class Files {

    // TODO : method which do either copyFile or copyDir

    void copyFile(String sourceFile, String destinationFile) throws IOException {
        val sourceFileAsFile = new File(sourceFile);
        val destinationFileAsFile = new File(destinationFile);

        FileUtils.copyFile(sourceFileAsFile, destinationFileAsFile);
    }

    void copyDir(String sourceDirectory, String destinationDirectory) throws IOException {
        val sourceDirectoryAsFile = new File(sourceDirectory);
        val destinationDirectoryAsFile = new File(destinationDirectory);

        FileUtils.copyDirectory(sourceDirectoryAsFile, destinationDirectoryAsFile);
    }

    void copyDir(String sourceDirectory, String destinationDirectory, FileFilter fileFilter) throws IOException {
        val sourceDirectoryAsFile = new File(sourceDirectory);
        val destinationDirectoryAsFile = new File(destinationDirectory);

        FileUtils.copyDirectory(sourceDirectoryAsFile, destinationDirectoryAsFile, fileFilter);
    }

    // TODO : generic rm

    void rmFile(String file) throws IOException {
        val fileAsFile = new File(file);

        FileUtils.delete(fileAsFile);
    }

    void rmDir(String dir) throws IOException {
        val dirAsFile = new File(dir);

        FileUtils.deleteDirectory(dirAsFile);
    }
}
