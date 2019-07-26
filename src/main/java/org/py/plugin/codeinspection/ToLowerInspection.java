package org.py.plugin.codeinspection;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiFieldImpl;
import com.intellij.ui.DocumentAdapter;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.regex.Pattern;

/**
 *
 */
public class ToLowerInspection extends AbstractBaseJavaLocalInspectionTool {
  // This string holds a list of classes relevant to this inspection.

  @NonNls
  private String myCheckedClasses = "java.lang.String;java.util.Date";

  /**
   * This method is called to get the panel describing the inspection.
   * It is called every time the user selects the inspection in preferences.
   * The user has the option to edit the list of CHECKED_CLASSES.
   * Adds a document listener to see if
   *
   * @return panel to display inspection information.
   */
  @Override
  public JComponent createOptionsPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    final JTextField checkedClasses = new JTextField(myCheckedClasses);
    checkedClasses.getDocument().addDocumentListener(new DocumentAdapter() {
      @Override
      public void textChanged(@NotNull DocumentEvent event) {
          myCheckedClasses = checkedClasses.getText();
      }
    });
    panel.add(checkedClasses);
    return panel;
  }

  private static boolean isNumeric(@NotNull String str){
    if (StringUtils.isEmpty(str)) {
      return false;
    }
    for(int i=str.length();--i>=0;){
      int chr=str.charAt(i);
      if (chr < 48 || chr > 57) {
        return false;
      }
    }
    return true;
  }

  /**
   * This method is overridden to provide a custom visitor
   * that inspects expressions with relational operators '==' and '!='
   * The visitor must not be recursive and must be thread-safe.
   *
   * @param holder     object for visitor to register problems found.
   * @param isOnTheFly true if inspection was run in non-batch mode
   * @return non-null visitor for this inspection.
   * @see JavaElementVisitor
   */
  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
    return new JavaElementVisitor() {

      @Override
      public void visitReferenceExpression(PsiReferenceExpression expression) {
          String referenceName = expression.getReferenceName();
          if ("".equals(referenceName)) {
              return;
          }
          if(expression.getElement() instanceof PsiEnumConstant ) {
              assert referenceName != null;
              String lowerText = referenceName.toLowerCase();
              holder.registerProblem(expression, "tip: "+lowerText, ProblemHighlightType.LIKE_UNUSED_SYMBOL );
          }else{
              assert referenceName != null;
              if (isNumeric(referenceName)) {
                  return;
              }
              boolean matches = Pattern.matches("_?[A-Z,0-9]{2,20}(_?[A-Z,0-9]{2,20}){0,6}", referenceName);
              if (matches) {
                  String lowerText = referenceName.toLowerCase();
                  holder.registerProblem(expression, "tip: "+lowerText, ProblemHighlightType.LIKE_UNUSED_SYMBOL );
              }
          }

      }

      @Override
      public void visitField(PsiField field) {
          if(field instanceof PsiEnumConstant){
              String name = field.getName();
              if (!StringUtils.isEmpty(name)) {
                  String lowerText = name.toLowerCase();
                  holder.registerProblem(field,
                          "tip: "+lowerText, ProblemHighlightType.LIKE_UNKNOWN_SYMBOL);
              }
              this.visitVariable(field);

          }else if(field instanceof PsiFieldImpl){
              PsiFieldImpl psiField = (PsiFieldImpl) field;
              PsiModifierList modifierList = psiField.getModifierList();

              if( !modifierList.hasModifierProperty(PsiModifier.FINAL) ){
                  return;
              }
              if( !modifierList.hasModifierProperty(PsiModifier.STATIC)){
                  return;
              }

              String name = field.getName();
              if (!StringUtils.isEmpty(name)) {
                  String lowerText = name.toLowerCase();
                  holder.registerProblem(field,
                          "tip: "+lowerText, ProblemHighlightType.LIKE_UNKNOWN_SYMBOL);
              }
              this.visitVariable(field);
          }

      }
    };
  }
}