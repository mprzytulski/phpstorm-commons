package pl.projectspace.idea.plugins.commons.php.code.completion;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.yaml.YAMLElementTypes;
import org.jetbrains.yaml.YAMLLanguage;
import org.jetbrains.yaml.YAMLTokenTypes;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class YamlElementPatternHelper {

    /**
     * auto complete on
     * <p/>
     * keyName: refer|
     *
     * @param keyName
     */
    public static ElementPattern<PsiElement> getOrmSingleLineScalarKey(String keyName) {
        return getKeyPattern(keyName).inFile(getOrmFilePattern()).withLanguage(YAMLLanguage.INSTANCE);
    }

    public static ElementPattern<PsiElement> getSingleLineScalarKey(String... keyName) {
        // key: | and key: "quote" is valid here
        // getKeyPattern
        return PlatformPatterns.or(
            PlatformPatterns
                .psiElement(YAMLTokenTypes.TEXT)
                .withParent(PlatformPatterns
                    .psiElement(YAMLKeyValue.class)
                    .withName(
                        PlatformPatterns.string().oneOf(keyName)
                    )
                )
                .withLanguage(YAMLLanguage.INSTANCE)
            ,
            PlatformPatterns
                .psiElement(YAMLTokenTypes.SCALAR_DSTRING)
                .withParent(PlatformPatterns
                    .psiElement(YAMLKeyValue.class)
                    .withName(
                        PlatformPatterns.string().oneOf(keyName)
                    )
                )
                .withLanguage(YAMLLanguage.INSTANCE)
        );
    }

    /**
     * provides auto complete on
     * <p/>
     * keyName:
     * refer|
     * refer|: xxx
     * refer|
     *
     * @param keyName key name
     */
    public static ElementPattern<PsiElement> getOrmParentLookup(String keyName) {
        return PlatformPatterns.or(
            // match
            //
            // keyName:
            //   refer|: xxx
            PlatformPatterns
                .psiElement(YAMLTokenTypes.SCALAR_KEY)
                .withParent(PlatformPatterns
                    .psiElement(YAMLKeyValue.class)
                    .withParent(PlatformPatterns
                        .psiElement(YAMLElementTypes.COMPOUND_VALUE)
                        .withParent(PlatformPatterns
                            .psiElement(YAMLKeyValue.class)
                            .withName(
                                PlatformPatterns.string().equalTo(keyName)
                            )
                        )
                    )
                )
                .inFile(getOrmFilePattern())
                .withLanguage(YAMLLanguage.INSTANCE),

            // match
            //
            // keyName:
            //   xxx: xxx
            //   refer|
            PlatformPatterns
                .psiElement(YAMLTokenTypes.TEXT)
                .withParent(PlatformPatterns
                    .psiElement(YAMLElementTypes.COMPOUND_VALUE)
                    .withParent(PlatformPatterns
                        .psiElement(YAMLKeyValue.class)
                        .withName(
                            PlatformPatterns.string().equalTo(keyName)
                        )
                    )
                )
                .inFile(getOrmFilePattern())
                .withLanguage(YAMLLanguage.INSTANCE),

            // match
            //
            // keyName:
            //   refer|
            //   xxx: xxx
            getKeyPattern(keyName)
                .inFile(getOrmFilePattern())
                .withLanguage(YAMLLanguage.INSTANCE)
        );
    }

    /**
     * provides auto complete on
     * <p/>
     * xxx:
     * refer|
     * refer|: xxx
     * refer|
     */
    public static ElementPattern<PsiElement> getOrmRoot() {
        return PlatformPatterns.or(

            // match
            //
            // xxx:
            //   refer|: xxx
            PlatformPatterns
                .psiElement(YAMLTokenTypes.SCALAR_KEY)
                .withParent(PlatformPatterns
                    .psiElement(YAMLKeyValue.class)
                    .withParent(PlatformPatterns
                        .psiElement(YAMLElementTypes.COMPOUND_VALUE)
                        .withParent(PlatformPatterns
                            .psiElement(YAMLKeyValue.class)
                            .withParent(PlatformPatterns
                                .psiElement(YAMLDocument.class)
                            )
                        )
                    )
                )
                .inFile(getOrmFilePattern())
                .withLanguage(YAMLLanguage.INSTANCE),

            // match
            //
            // xxx:
            //   xxx: xxx
            //   refer|
            PlatformPatterns
                .psiElement(YAMLTokenTypes.TEXT)
                .withParent(PlatformPatterns
                    .psiElement(YAMLElementTypes.COMPOUND_VALUE)
                    .withParent(PlatformPatterns
                        .psiElement(YAMLKeyValue.class)
                        .withParent(PlatformPatterns
                            .psiElement(YAMLDocument.class)
                        )
                    )
                )
                .inFile(getOrmFilePattern())
                .withLanguage(YAMLLanguage.INSTANCE),

            // match
            //
            // xxx:
            //   refer|
            //   xxx: xxx
            PlatformPatterns
                .psiElement(YAMLTokenTypes.TEXT)
                .withParent(PlatformPatterns
                    .psiElement(YAMLKeyValue.class)
                    .withParent(PlatformPatterns
                        .psiElement(YAMLDocument.class)
                    )
                )
                .inFile(getOrmFilePattern())
                .withLanguage(YAMLLanguage.INSTANCE)
        );
    }

    /**
     * provides auto complete on
     * <p/>
     * tree:
     * xxx:
     * refer|
     * refer|: xxx
     * refer|
     */
    public static ElementPattern<PsiElement> getFilterOnPrevParent(String... tree) {
        return PlatformPatterns.or(

            // match
            //
            // tree:
            //   xxx:
            //     refer|: xxx
            PlatformPatterns
                .psiElement(YAMLTokenTypes.SCALAR_KEY)
                .withParent(PlatformPatterns
                    .psiElement(YAMLKeyValue.class)
                    .withParent(PlatformPatterns
                        .psiElement(YAMLElementTypes.COMPOUND_VALUE)
                        .withParent(PlatformPatterns
                            .psiElement(YAMLKeyValue.class)
                            .withParent(PlatformPatterns
                                .psiElement(YAMLElementTypes.COMPOUND_VALUE)
                                .withParent(PlatformPatterns
                                    .psiElement(YAMLKeyValue.class)
                                    .withName(PlatformPatterns
                                        .string().oneOfIgnoreCase(tree)
                                    )
                                )
                            )
                        )
                    )
                )
                .inFile(getOrmFilePattern())
                .withLanguage(YAMLLanguage.INSTANCE),

            // match
            //
            // tree:
            //   xxx:
            //     xxx: xxx
            //     refer|
            PlatformPatterns
                .psiElement(YAMLTokenTypes.TEXT)
                .withParent(PlatformPatterns
                    .psiElement(YAMLElementTypes.COMPOUND_VALUE)
                    .withParent(PlatformPatterns
                        .psiElement(YAMLKeyValue.class)
                        .withParent(PlatformPatterns
                            .psiElement(YAMLElementTypes.COMPOUND_VALUE)
                            .withParent(PlatformPatterns
                                .psiElement(YAMLKeyValue.class)
                                .withName(PlatformPatterns
                                    .string().oneOfIgnoreCase(tree)
                                )
                            )
                        )
                    )
                )
                .inFile(getOrmFilePattern())
                .withLanguage(YAMLLanguage.INSTANCE),

            // match
            //
            // tree:
            //   xxx:
            //     refer|
            //     xxx: xxx
            PlatformPatterns
                .psiElement(YAMLTokenTypes.TEXT)
                .withParent(PlatformPatterns
                    .psiElement(YAMLKeyValue.class)
                    .withParent(PlatformPatterns
                        .psiElement(YAMLElementTypes.COMPOUND_VALUE)
                        .withParent(PlatformPatterns
                            .psiElement(YAMLKeyValue.class)
                            .withName(PlatformPatterns
                                .string().oneOfIgnoreCase(tree)
                            )
                        )
                    )
                )
                .inFile(getOrmFilePattern())
                .withLanguage(YAMLLanguage.INSTANCE)
        );
    }

    /**
     * find common services
     */
    public static ElementPattern<PsiElement> getServiceDefinition() {

        return PlatformPatterns.or(
            PlatformPatterns
                .psiElement(YAMLTokenTypes.TEXT)
                .withText(
                    StandardPatterns.string().startsWith("@")
                )
                .withLanguage(YAMLLanguage.INSTANCE)
            ,
            PlatformPatterns
                .psiElement(YAMLTokenTypes.SCALAR_DSTRING)
                .withText(
                    StandardPatterns.string().startsWith("'@")
                )
                .withLanguage(YAMLLanguage.INSTANCE),
            PlatformPatterns
                .psiElement(YAMLTokenTypes.SCALAR_DSTRING)
                .withText(
                    StandardPatterns.string().startsWith("\"@")
                )
                .withLanguage(YAMLLanguage.INSTANCE)
        );
    }

    /**
     * find common service parameter
     */
    public static ElementPattern<PsiElement> getServiceParameterDefinition() {
        return PlatformPatterns.or(
            PlatformPatterns
                .psiElement(YAMLTokenTypes.TEXT)
                .withText(
                    StandardPatterns.string().startsWith("%")
                )
                .withLanguage(YAMLLanguage.INSTANCE)
            ,
            PlatformPatterns
                .psiElement(YAMLTokenTypes.SCALAR_DSTRING)
                .withText(
                    StandardPatterns.string().startsWith("'%")
                )
                .withLanguage(YAMLLanguage.INSTANCE),
            PlatformPatterns
                .psiElement(YAMLTokenTypes.SCALAR_DSTRING)
                .withText(
                    StandardPatterns.string().startsWith("\"%")
                )
                .withLanguage(YAMLLanguage.INSTANCE)
        );
    }

    private static ElementPattern<? extends PsiFile> getOrmFilePattern() {
        return PlatformPatterns.psiFile().withName(PlatformPatterns.string().endsWith("orm.yml"));
    }


    private static PsiElementPattern.Capture<PsiElement> getKeyPattern(String keyName) {
        return PlatformPatterns
            .psiElement(YAMLTokenTypes.TEXT)
            .withParent(PlatformPatterns
                .psiElement(YAMLKeyValue.class)
                .withName(
                    PlatformPatterns.string().equalTo(keyName)
                )
            );
    }

    /**
     * parameters:
     * foo.example.class: |
     */
    static PsiElementPattern.Capture<PsiElement> getParameterClassPattern() {
        return PlatformPatterns
            .psiElement(YAMLTokenTypes.TEXT)
            .withParent(PlatformPatterns
                .psiElement(YAMLKeyValue.class)
                .withName(
                    PlatformPatterns.string().endsWith(".class")
                )
                .withParent(PlatformPatterns
                    .psiElement(YAMLElementTypes.COMPOUND_VALUE)
                    .withParent(PlatformPatterns
                        .psiElement(YAMLKeyValue.class)
                        .withName("parameters")
                    )
                )
            );

    }

    static PsiElementPattern.Capture<PsiElement> getInsideServiceKeyPattern() {
        return getInsideKeyValue("services", "parameters");
    }

    static PsiElementPattern.Capture<PsiElement> getInsideKeyValue(String... keys) {
        return PlatformPatterns
            .psiElement()
            .inside(
                PlatformPatterns
                    .psiElement(YAMLKeyValue.class)
                    .withName(
                        PlatformPatterns.string().oneOf(keys)
                    )
            );
    }

}
