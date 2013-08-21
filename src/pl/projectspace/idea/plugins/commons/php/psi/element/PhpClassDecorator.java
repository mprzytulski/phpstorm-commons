package pl.projectspace.idea.plugins.commons.php.psi.element;

import com.jetbrains.php.lang.psi.elements.PhpClass;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class PhpClassDecorator extends PsiElementDecorator {

    /**
     * Create PhpClass decorator
     *
     * @param phpClass
     */
    public PhpClassDecorator(PhpClass phpClass) {
        super(phpClass);
    }

    /**
     * Get decorated PhpClass instance
     *
     * @return
     */
    public PhpClass getDecoratedObject() {
        return (PhpClass) this.element;
    }

}
