/*
 * The MIT License
 * Copyright © 2013 Cube Island
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.cubeengine.pericopist.format.gettext;

import java.nio.charset.Charset;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.velocity.context.Context;
import org.cubeengine.pericopist.PericopistFactory;
import org.cubeengine.pericopist.exception.ConfigurationException;
import org.cubeengine.pericopist.format.AbstractCatalogConfiguration;
import org.cubeengine.pericopist.format.CatalogFormat;
import org.cubeengine.pericopist.format.HeaderConfiguration;

/**
 * <p>
 * This configuration is used for creating a gettext catalog which stores translatable messages.
 * </p>
 *
 * <p>
 * The configuration can be set up with an xml file.
 * </p>
 *
 * Example:
 *
 * <pre>
 * {@code
 * <catalog format="gettext" charset="utf-8"> <!-- default charset: charset set as extractor tag attribute -->
 *     <removeUnusedMessages>true</removeUnusedMessages> <!-- default: true -->
 *     <createEmptyTemplate>false</createEmptyTemplate> <!-- default: false -->
 *     <pluralAmount>2</pluralAmount> <!-- plural amount of the source language. default: 2 -->
 *     <template>TEMPLATE PATH</template> <!-- path of the template file -->
 *     <header>
 *         <comments>
 *             header comments of the template file.
 *             use ${resource.load("path")} to load an external file as comments
 *         </comments>
 *         <metadata>
 *             <entry key="Project-Id-Version">PACKAGE VERSION</entry>
 *             <entry key="POT-Creation-Date" variable="true">${date.get('yyyy-MM-dd HH:mm:ssZ')}</entry>
 *             <entry key="Last-Translator">FULL NAME &lt;EMAIL@ADDRESS&gt;</entry>
 *             ...
 *         </metadata>
 *     </header>
 * </catalog>
 * }
 * </pre>
 *
 * @see PericopistFactory#getPericopist(String, Charset, int, Context, Logger)
 * @see org.cubeengine.pericopist.format.gettext.PlaintextGettextCatalogFormat
 */
@SuppressWarnings("unused")
@XmlRootElement(name = "catalog")
public class GettextCatalogConfiguration extends AbstractCatalogConfiguration
{
    private HeaderConfiguration headerConfiguration;
    private Integer pluralAmount;

    /**
     * This method returns the header configuration of the gettext catalog
     *
     * @return header configuration
     */
    public HeaderConfiguration getHeaderConfiguration()
    {
        return this.headerConfiguration;
    }

    /**
     * This method sets the header configuration of the gettext catalog
     *
     * @param headerConfiguration header configuration
     */
    @XmlElement(name = "header")
    public void setHeaderConfiguration(HeaderConfiguration headerConfiguration)
    {
        this.headerConfiguration = headerConfiguration;
    }

    /**
     * this method returns the amount of plural messages of the source language
     *
     * @return plural message amount
     */
    public int getPluralAmount()
    {
        if (this.pluralAmount == null)
        {
            return 2;
        }
        return pluralAmount;
    }

    /**
     * This method sets the amount of plural messages of the source language
     *
     * @param pluralAmount plural message amount
     */
    @XmlElement(name = "pluralAmount")
    public void setPluralAmount(int pluralAmount)
    {
        this.pluralAmount = pluralAmount;
    }

    @Override
    public Class<? extends CatalogFormat> getCatalogFormatClass()
    {
        return PlaintextGettextCatalogFormat.class;
    }

    @Override
    public void validate() throws ConfigurationException
    {
        if (this.getTemplateFile() == null)
        {
            throw new ConfigurationException("You must specify the path which contains the location of the template");
        }
    }
}
