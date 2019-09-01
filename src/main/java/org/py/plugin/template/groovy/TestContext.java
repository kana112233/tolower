package org.py.plugin.template.groovy;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class TestContext extends TemplateContextType {

    protected TestContext() {
        super("GROOVY", "Groovy");
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        return file.getName().endsWith(".groovy");
    }
}