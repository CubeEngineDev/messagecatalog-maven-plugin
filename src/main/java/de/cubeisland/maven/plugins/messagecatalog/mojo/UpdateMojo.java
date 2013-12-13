package de.cubeisland.maven.plugins.messagecatalog.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.io.IOException;

import de.cubeisland.maven.plugins.messagecatalog.config.Config;
import de.cubeisland.maven.plugins.messagecatalog.format.CatalogFormat;
import de.cubeisland.maven.plugins.messagecatalog.format.CatalogFormatFactory;
import de.cubeisland.maven.plugins.messagecatalog.message.TranslatableMessageManager;
import de.cubeisland.maven.plugins.messagecatalog.parser.SourceParser;
import de.cubeisland.maven.plugins.messagecatalog.parser.SourceParserFactory;

/**
 * @goal update
 */
public class UpdateMojo extends AbstractMessageCatalogMojo
{
    @Override
    protected void doExecute(Config config) throws MojoExecutionException, MojoFailureException
    {
        CatalogFormat catalogFormat = CatalogFormatFactory.newCatalogFormat(config.getCatalog().getOutputFormat(), config, this.getLog());    // create catalogFormat
        File file = new File(config.getCatalog().getTemplateFile() + "." + catalogFormat.getFileExtension());   // load pot file

        TranslatableMessageManager messageManager;
        try
        {
            messageManager = catalogFormat.read(file);      // try to read existing catalog file
        }
        catch (IOException e)
        {
            throw new MojoExecutionException("Failed to read the existing message catalog.", e);
        }

        SourceParser parser = SourceParserFactory.newSourceParser(config.getSource().getLanguage(), config, this.getLog());  // create SourceParser
        messageManager = parser.parse(config.getSource().getDirectory(), messageManager);     // search source files for translatable string literals

        try
        {
            catalogFormat.write(file, messageManager);    // write new catalog file
        }
        catch (IOException e)
        {
            throw new MojoExecutionException("Failed to write the message catalog.", e);
        }
    }
}
