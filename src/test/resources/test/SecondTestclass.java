package test;

import test.anot.TestNormalAnnotation;
import test.wronganot.TestSingleMemberAnnotation;

import static test.I18n._;

public class SecondTestclass
{
    public SecondTestclass(I18n i18n, String hello)
    {
        i18n.getTranslation("what's" + " up" + "?");

        i18n.getTranslation("goodbye");
    }

    @TestSingleMemberAnnotation("wrongAnnotation!")
    @TestNormalAnnotation(desc = "normalAnnotString")
    public void test()
    {
        _("hello everyone");
    }
}
