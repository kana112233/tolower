package org.py.plugin;

import com.intellij.codeInsight.hints.HintInfo;
import com.intellij.codeInsight.hints.InlayInfo;
import com.intellij.codeInsight.hints.InlayParameterHintsProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class MyInlayParameterHintsProvider implements InlayParameterHintsProvider {
    @NotNull
    @Override
    public List<InlayInfo> getParameterHints(PsiElement element) {
        System.out.println("----------------1-");
        System.out.println(element);
        return null;
    }

    @Nullable
    @Override
    public HintInfo getHintInfo(PsiElement element) {
        System.out.println("---------------2--");
        System.out.println(element);
        return null;
    }

    @NotNull
    @Override
    public Set<String> getDefaultBlackList() {
        System.out.println("element");
        return null;
    }
}
