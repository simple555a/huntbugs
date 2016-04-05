/*
 * Copyright 2015, 2016 Tagir Valeev
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package one.util.huntbugs;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.jar.JarFile;

import one.util.huntbugs.analysis.Context;
import one.util.huntbugs.repo.DirRepository;
import one.util.huntbugs.repo.JarRepository;
import one.util.huntbugs.repo.Repository;

/**
 * @author lan
 *
 */
public class HuntBugs {
    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        String classPath = args[0];
        Path root = Paths.get(classPath);
        Repository repository = Files.isDirectory(root) ? new DirRepository(root) : new JarRepository(new JarFile(root
                .toFile()));
        Context ctx = new Context(repository);
        ctx.analyzePackage("");
        ctx.reportErrors(new PrintStream("huntbugs.errors.txt"));
        ctx.reportWarnings(new PrintStream("huntbugs.warnings.txt"));
        long end = System.nanoTime();
        Duration dur = Duration.ofNanos(end - start);
        System.out.println("Analyzed "+ctx.getClassesCount()+" classes");
        System.out.println("Found "+ctx.getWarningCount()+" warnings");
        System.out.println("Encountered "+ctx.getErrorCount()+" analyzer errors");
        System.out.println("Analyzis time "+dur.toMinutes()+"m"+dur.getSeconds()%60+"s");
    }
}
