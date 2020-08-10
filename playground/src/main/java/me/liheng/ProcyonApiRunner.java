package me.liheng;

import com.strobel.assembler.InputTypeLoader;
import com.strobel.assembler.metadata.ClasspathTypeLoader;
import com.strobel.assembler.metadata.CompositeTypeLoader;
import com.strobel.assembler.metadata.JarTypeLoader;
import com.strobel.decompiler.Decompiler;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.PlainTextOutput;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.jar.JarFile;

public class ProcyonApiRunner {

    // Not working...
    public static void run() throws IOException {

        final DecompilerSettings settings = DecompilerSettings.javaDefaults();
        settings.setTypeLoader(
                new InputTypeLoader(                // allow more relaxed type names
                        new CompositeTypeLoader(
                                new JarTypeLoader(new JarFile("6.13.0/jasperreports-6.13.0.jar")),   // search your specific jar first
                                new ClasspathTypeLoader()   // fall back to your classpath
                        )
                )
        );

        try (final FileOutputStream stream = new FileOutputStream("a.out");

             final OutputStreamWriter writer = new OutputStreamWriter(stream)) {

            Decompiler.decompile(
                    "net/sf/jasperreports/jasperreports",
                    new PlainTextOutput(writer),
                    settings
            );
        }
        catch (final IOException e) {
            // handle error
        }

    }
}
