package com.cqjtu.csi.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.util.*;

/**
 * @author mumu
 * @date 2020/2/2
 */
public class FileUtils {

    private FileUtils() {}

    public static class FileSuffixFilter {

        private final Set<String> suffixSet = new HashSet<>();

        public FileSuffixFilter() {
        }

        public FileSuffixFilter add(String suffix) {
            suffixSet.add(suffix);
            return this;
        }

        public FileSuffixFilter adds(String... suffixs) {
            Collections.addAll(suffixSet, suffixs);
            return this;
        }

        public boolean matchSuffix(String suffix) {
            return suffixSet.stream().anyMatch(s -> s.equalsIgnoreCase(suffix));
        }

        public String check(String filename, RuntimeException e) {
            String suffix = getSuffix(filename);
            if (!matchSuffix(suffix)) {
                throw e;
            }
            return BaseUtils.randomUUIDWithoutDash() + "." + suffix;
        }
    }

    public static String toPath(String... name) {
        return StringUtils.join(name, "/");
    }

    public static String getSuffix(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    public static FileSuffixFilter buildSuffixFilter(String... suffixs) {
        return new FileSuffixFilter().adds(suffixs);
    }

    public void delFile(File file) {
        if (!file.exists()) {
            return;
        }

        if (file.isDirectory()) {
            Optional.ofNullable(file.listFiles())
                    .ifPresent(files1 -> Arrays.stream(files1).forEach(this::delFile));
        }
        file.delete();
    }
}
