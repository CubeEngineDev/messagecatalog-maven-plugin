/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Phillip Schichtel, Stefan Wolf
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
package de.cubeisland.messageextractor.format.gettext;

import org.fedorahosted.tennera.jgettext.Message;

import java.util.Collection;
import java.util.List;

class TranslatableGettextMessage extends GettextMessage
{
    private final String domain;
    private final String msgstr;
    private final List<String> msgstrPlural;

    private final String prevMsgctx;
    private final String prevMsgid;
    private final String prevMsgidPlural;

    private final Collection<String> comments;
    private final Collection<String> extractedCommentsFromGettext;
    private final List<String> gettextReferences;

    private final Collection<String> formats;
    private final boolean obsolete;
    private final Boolean allowWrap;

    public TranslatableGettextMessage(Message message, int position)
    {
        super(message.getMsgctxt(), message.getMsgid(), message.getMsgidPlural(), position);

        this.domain = message.getDomain();
        this.msgstr = message.getMsgstr();
        this.msgstrPlural = message.getMsgstrPlural();

        this.prevMsgctx = message.getPrevMsgctx();
        this.prevMsgid = message.getPrevMsgid();
        this.prevMsgidPlural = message.getPrevMsgidPlural();

        this.comments = message.getComments();
        this.extractedCommentsFromGettext = message.getExtractedComments();
        this.gettextReferences = message.getSourceReferences();
        this.formats = message.getFormats();

        this.obsolete = message.isObsolete();
        this.allowWrap = message.getAllowWrap();

        for (String extractedComment : this.getExtractedCommentsFromGettext())
        {
            this.addExtractedComment(extractedComment);
        }
    }

    public String getDomain()
    {
        return domain;
    }

    public String getMsgstr()
    {
        return msgstr;
    }

    public List<String> getMsgstrPlural()
    {
        return msgstrPlural;
    }

    public String getPrevMsgctx()
    {
        return prevMsgctx;
    }

    public String getPrevMsgid()
    {
        return prevMsgid;
    }

    public String getPrevMsgidPlural()
    {
        return prevMsgidPlural;
    }

    public Collection<String> getComments()
    {
        return comments;
    }

    public Collection<String> getExtractedCommentsFromGettext()
    {
        return extractedCommentsFromGettext;
    }

    public List<String> getGettextReferences()
    {
        return this.gettextReferences;
    }

    public Collection<String> getFormats()
    {
        return formats;
    }

    public boolean isObsolete()
    {
        return obsolete;
    }

    public Boolean getAllowWrap()
    {
        return allowWrap;
    }

    public Message toMessage()
    {
        Message message = new Message();

        message.setDomain(this.getDomain());
        message.setMsgctxt(this.getContext());
        message.setMsgid(this.getSingular());
        message.setMsgidPlural(this.getPlural());
        message.setMsgstr(this.getMsgstr());
        for (int i = 0; i < this.getMsgstrPlural().size(); i++)
        {
            message.addMsgstrPlural(this.getMsgstrPlural().get(i), i);
        }

        for (String comment : this.getComments())
        {
            message.addComment(comment);
        }
        for (String extractedComment : this.getExtractedComments())
        {
            message.addExtractedComment(extractedComment);
        }

        for (String format : this.getFormats())
        {
            message.addFormat(format);
        }
        message.setObsolete(this.isObsolete());
        message.setAllowWrap(this.getAllowWrap());

        return message;
    }
}